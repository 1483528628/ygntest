import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Test01 {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setMaster("local[*]")
      .setAppName("asd")
    val sc = new SparkContext(conf)
    val file: RDD[String] = sc.textFile("C:\\Users\\hp\\Desktop\\sqoop.txt")
    file.foreach(print)
  }
}
