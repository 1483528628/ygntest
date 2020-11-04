import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.api.java.UDF1
import org.apache.spark.sql.expressions.{MutableAggregationBuffer, UserDefinedAggregateFunction}
import org.apache.spark.sql.types._

object Test01 {
  def main(args: Array[String]): Unit = {
    val warehouseLocation = new File("C:\\Users\\hp\\Desktop\\etl\\hivepeizhi\\hive-site.xml").getAbsolutePath();
    val spark: SparkSession = SparkSession.builder()
      .appName("wdas")
      .master("local[*]")
      .enableHiveSupport()
      .config("spark.sql.warehouse.dir", warehouseLocation)
      .getOrCreate()
    spark.sparkContext.setLogLevel("WARN")
    spark.udf.register("from_unixtime", new asds, DataTypes.StringType)
    spark.udf.register("u_avg", new MyAverage)
    //    spark.sql("SELECT  t_start_date, t_end_date, from_unixtime(id) as hhah FROM dwd.dwd_test_dz limit 10").show();
    //    spark.sql("SELECT  count(1),u_avg(id) FROM dwd.dwd_test_dz").show();
    spark.sql("CREATE TEMPORARY FUNCTION UserDefinedUDTF as 'UserDefinedUDTF'")
    spark.sql("SELECT  t_start_date, t_end_date, UserDefinedUDTF(name) as hhah FROM dwd.dwd_test_dz").show();

//    spark.sql("CREATE TEMPORARY FUNCTION TotalNumOfLettersGenericUDAF as 'TotalNumOfLettersGenericUDAF'")
//    spark.sql("SELECT TotalNumOfLettersGenericUDAF(name) as hhah FROM dwd.dwd_test_dz").show();

  }
}

class asds extends UDF1[String, String] {
  val DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

  override def call(t1: String): String = {
    val sdf = new SimpleDateFormat(DATE_FORMAT);
    return sdf.format(new Date(t1.toLong * 1000));
  }
}

class MyAverage extends UserDefinedAggregateFunction {

  // 聚合函数的输入数据结构
  override def inputSchema: StructType = StructType(StructField("input", LongType) :: Nil)

  // 缓存区数据结构
  override def bufferSchema: StructType = StructType(StructField("sum", LongType) :: StructField("count", LongType) :: Nil)

  // 聚合函数返回值数据结构
  override def dataType: DataType = DoubleType

  // 聚合函数是否是幂等的，即相同输入是否总是能得到相同输出
  override def deterministic: Boolean = true

  // 初始化缓冲区
  override def initialize(buffer: MutableAggregationBuffer): Unit = {
    buffer(0) = 0L
    buffer(1) = 0L
  }

  // 给聚合函数传入一条新数据进行处理
  override def update(buffer: MutableAggregationBuffer, input: Row): Unit = {
    if (input.isNullAt(0)) return
    buffer(0) = buffer.getLong(0) + input.getLong(0)
    buffer(1) = buffer.getLong(1) + 1
  }

  // 合并聚合函数缓冲区
  override def merge(buffer1: MutableAggregationBuffer, buffer2: Row): Unit = {
    buffer1(0) = buffer1.getLong(0) + buffer2.getLong(0)
    buffer1(1) = buffer1.getLong(1) + buffer2.getLong(1)
  }

  // 计算最终结果
  override def evaluate(buffer: Row): Any = buffer.getLong(0).toDouble / buffer.getLong(1)
}

