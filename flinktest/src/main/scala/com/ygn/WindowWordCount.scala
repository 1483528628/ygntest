//package com.ygn
//
//
//import java.util
//import java.util.Collections
//
//import org.apache.flink.api.common.functions.MapFunction
//import org.apache.flink.api.java.tuple.Tuple
//import org.apache.flink.streaming.api.TimeCharacteristic
//import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
//import org.apache.flink.api.scala._
//import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor
//import org.apache.flink.streaming.api.scala.function.WindowFunction
//import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows
//import org.apache.flink.streaming.api.windowing.time.Time
//import org.apache.flink.streaming.api.windowing.windows.TimeWindow
//import org.apache.flink.util.Collector
//
//import scala.collection.mutable.ListBuffer
//
//object WindowWordCount {
//  def main(args: Array[String]): Unit = {
//    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
//    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
//    val text: DataStream[String] = env.socketTextStream("node1", 9999)
//    val inputMap: DataStream[(String, Long)] = text.map(new MapFunction[String, (String, Long)] {
//      override def map(value: String): (String, Long) = {
//        val arr: Array[String] = value.split(",")
//        (arr(0), (arr(1).toLong))
//      }
//    })
//    val waterMarkStream: DataStream[(String, Long)] = inputMap.assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor[(String, Long)](Time.seconds(10)) {
//      override def extractTimestamp(element: (String, Long)): Long = element._2
//    })
//    waterMarkStream.keyBy(0)
//      .window(TumblingEventTimeWindows.of(Time.seconds(3)))
//      .apply(new WindowFunction[(String, Long), String, Tuple, TimeWindow] {
//        override def apply(key: Tuple, window: TimeWindow, input: Iterable[(String, Long)], out: Collector[String]): Unit = {
//          val key: String = key
//          val list = new util.ArrayList[Long]()
//          for (elem <- input) {
//            list.add(elem._2)
//          }
//        }
//      })
//
//  }
//
//
//}
