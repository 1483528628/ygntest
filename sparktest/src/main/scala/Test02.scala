import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{Dataset, SparkSession}
import org.apache.spark.{SparkConf, SparkContext}

object Test02 {
  def main(args: Array[String]): Unit = {
    //    val conf: SparkConf = new SparkConf()
    //      .setAppName("asd")
    //      .setMaster("local[*]")
    //    val sc = new SparkContext(conf)
    //    sc.setLogLevel("WARN")
    //    val lr = List(4, 7, 10, 11, 50, 31, 7, 15, 3, 7, 11, 31)
    ////    val listRDD: RDD[Int] = sc.makeRDD(lr)
    //    val listRDD: RDD[Int] = sc.parallelize(lr)
    //    listRDD.map((_,1)).reduceByKey(_+_).sortBy(_._2,false,1).take(2).foreach(print)
    //
    //    sc.stop()
    val logFile = "C:\\Users\\hp\\Desktop\\etl\\20.txt"
    val spark: SparkSession = SparkSession
      .builder().
      master("local[*]")
      .appName("abad")
      .getOrCreate()
    val numAs: Dataset[String] = spark.read.textFile(logFile)
    import spark.implicits._
    val mapDS: Dataset[Mysql] = numAs.map(a => {
      val arr: Array[String] = a.split("[.]")
      Mysql(arr(0), arr(1))
    }
    )
    mapDS.createOrReplaceTempView("person")

//    spark.sql("select * from person").show()
    mapDS.select("*").show()
    spark.stop()
  }
  case class Mysql(database:String,table:String)
}
