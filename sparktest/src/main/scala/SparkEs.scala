import org.apache.spark.sql.{DataFrame, SparkSession}


object SparkEs {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession.builder()
      .master("local[*]")
      .appName("es")
      .getOrCreate()
    val options = Map(
      "es.nodes.wan.only" -> "true",
      "es.nodes" -> "192.168.2.243",
      "es.port" -> "9200"
    )
    val abcDF: DataFrame = spark.read.format("es")
      .options(options)
      .load("abc/_doc")
    abcDF.show()
  }
}
