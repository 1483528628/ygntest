import org.apache.flink.api.scala.{DataSet, ExecutionEnvironment}
import org.apache.flink.api.scala._

object Test01 {
  def main(args: Array[String]): Unit = {
    val a = new Fruit("a",1)

  }

  case class Fruit(name: String, money: Int)

}
