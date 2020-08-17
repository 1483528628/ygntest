import java.util
import java.util.{List, Properties}

import org.apache.flink.api.common.functions._
import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.api.common.state._
import org.apache.flink.api.java.tuple.Tuple2
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer
import org.apache.flink.api.scala._
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.util.Collector

object Test02 {
  def main(args: Array[String]): Unit = {
    val properties = new Properties()
    properties.setProperty("bootstrap.servers", "node1:9092")
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    val myConsumer = new FlinkKafkaConsumer[String]("test",new SimpleStringSchema,properties)
    val lineDS: DataStream[String] = env.addSource(myConsumer)
    lineDS.map((_,1)).keyBy(0).flatMap(new RichFlatMapFunction[(String,Int),(String,Int)] {

      case class ThresholdWarning(threshold:String,numberOfTimes:Int)
      override def open(parameters: Configuration): Unit = {
        val context: RuntimeContext = getRuntimeContext
//        context.getListState(new ListStateDescriptor[String,Int]("",createTypeInformation))
      }

      override def flatMap(value: (String, Int), out: Collector[(String, Int)]): Unit = {
        val inputValue = value._1
      }
    })


    env.execute()
  }
}