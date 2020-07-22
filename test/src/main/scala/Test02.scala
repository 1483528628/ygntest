import org.apache.flink.api.scala.{DataSet, ExecutionEnvironment}
import org.apache.flink.api.scala._
object Test02 {
  def main(args: Array[String]): Unit = {
    val env: ExecutionEnvironment = ExecutionEnvironment.getExecutionEnvironment
    val line: DataSet[String] = env.readTextFile("table.txt")
    val arrDS: DataSet[Array[String]] = line.map(_.split("."))
    arrDS.map(print(_))



  }
}
