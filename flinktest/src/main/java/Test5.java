import com.alibaba.fastjson.JSON;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.common.serialization.AbstractDeserializationSchema;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.formats.json.JsonNodeDeserializationSchema;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.JsonNode;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.datastream.WindowedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.runtime.operators.util.AssignerWithPeriodicWatermarksAdapter;
import org.apache.flink.streaming.util.serialization.JSONKeyValueDeserializationSchema;
import org.junit.Test;

import java.io.IOException;
import java.util.Properties;


public class Test5 {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "node1:9092");
        DataStreamSource<Order> map = env.addSource(new FlinkKafkaConsumer<>("test", new AbstractDeserializationSchema<Order>() {
            private final ObjectMapper mapper = new ObjectMapper();
            @Override
            public Order deserialize(byte[] message) throws IOException {
                return  this.mapper.readValue(message, Order.class);
            }
        }, props));


        SingleOutputStreamOperator<Order> singleOutputStreamOperator = map.assignTimestampsAndWatermarks(
                new AssignerWithPeriodicWatermarksAdapter.Strategy(new BoundedOutOfOrdernessTimestampExtractor<Order>(Time.seconds(5)) {
                    @Override
                    public long extractTimestamp(Order element) {
                        return element.getTimedate();
                    }
                })
        );


        KeyedStream<Order, String> orderStringKeyedStream = singleOutputStreamOperator.keyBy(value -> value.getName());
        WindowedStream<Order, String, TimeWindow> window = orderStringKeyedStream.window(TumblingEventTimeWindows.of(Time.seconds(5)));

        SingleOutputStreamOperator<Order> reduce = window.reduce(new ReduceFunction<Order>() {
            @Override
            public Order reduce(Order value1, Order value2) throws Exception {
                int i = value1.getNum() + value2.getNum();
                value1.setNum(i);
                return value1;
            }
        });

        reduce.print();

        env.execute();
    }

    @Test
    public void asd() {
        String s = "{\"name\":\"b\",\"num\":4}";
        Order order = JSON.parseObject(s, Order.class);
        System.out.println(order);
    }
}
