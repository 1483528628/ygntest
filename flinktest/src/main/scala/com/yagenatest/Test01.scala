//package com.yagenatest
//
//import org.apache.flink.api.common.accumulators.IntCounter
//import org.apache.flink.api.common.functions.{RichMapFunction, RuntimeContext}
//import org.apache.flink.api.scala.ExecutionEnvironment
//import org.apache.flink.api.scala._
//import org.apache.flink.configuration.Configuration
//object Test01 {
//  def main(args: Array[String]): Unit = {
//    val env: ExecutionEnvironment = ExecutionEnvironment.getExecutionEnvironment
//    env.setParallelism(2)
//    val fileDS: DataSet[Int] = env.fromCollection(Array(2,3,2,4))
//    fileDS.map(new RichMapFunction[Int,Int] {
//
//      override def getRuntimeContext: RuntimeContext = super.getRuntimeContext
//
//      var intCounter: IntCounter
//      override def open(parameters: Configuration): Unit = {
//        intCounter = new IntCounter
//      }
//
//      override def close(): Unit = println(intCounter)
//
//      override def map(value: Int): Int = {
//        intCounter.add(value)
//
//      }
//    })
//  }
//}
