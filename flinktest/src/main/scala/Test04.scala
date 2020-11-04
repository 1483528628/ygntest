import java.util.Properties

import com.alibaba.fastjson.JSON
import lombok.extern.slf4j.Slf4j
import org.apache.flink.api.common.functions.ReduceFunction
import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.assigners.{TumblingEventTimeWindows, TumblingProcessingTimeWindows}
import org.apache.flink.streaming.api.windowing.time.Time

@Slf4j
object Test04 {
  def main(args: Array[String]): Unit = {
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    val props = new Properties()
    props.setProperty("bootstrap.servers", "node1:9092")
    props.setProperty("scan.startup.mode", "latest-offset")
    val strDS: DataStream[String] = env.addSource(new FlinkKafkaConsumer[String]("test", new SimpleStringSchema, props))
    val orderDS: DataStream[Order.type] = strDS.map(JSON.parseObject(_, Order.getClass))
//    val resultDS: DataStream[Order] = orderDS.keyBy(_.name).reduce(new ReduceFunction[Order] {
//      override def reduce(value1: Order, value2: Order): Order = {
//        val sumnum: Int = value1.num + value2.num
//        Order(value1.name, sumnum)
//      }
//    })
//    resultDS.print()
//  env.execute()
  }

  case class Order(name:String,num:Int)



}
