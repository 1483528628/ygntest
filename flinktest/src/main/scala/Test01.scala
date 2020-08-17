import org.apache.flink.api.scala.{DataSet, ExecutionEnvironment}
import org.apache.flink.api.scala._

object Test01 {
  def main(args: Array[String]): Unit = {
    val env: ExecutionEnvironment = ExecutionEnvironment.getExecutionEnvironment
    val file: DataSet[String] = env.fromCollection(List("apple,2", "banan,3", "apple,4"))
    //    val fruitDS: DataSet[fruit] = file.map { text =>
    //      val strArr: Array[String] = text.split(",")
    //      fruit(strArr(0), strArr(1).toInt)
    //    }
    val fruitDS: AggregateDataSet[(String, Int)] = file.map { text =>
      val strArr: Array[String] = text.split(",")
      (strArr(0), strArr(1).toInt)
    }.groupBy(0).sum(1)
    fruitDS.print()
  }

  case class fruit(name: String, money: Int)

}
