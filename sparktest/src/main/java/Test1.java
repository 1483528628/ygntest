import org.apache.spark.SparkConf;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.api.java.UDF1;
import org.apache.spark.sql.types.DataTypes;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test1 {
    public static void main(String[] args) {
        String warehouseLocation = new File("C:\\Users\\hp\\Desktop\\etl\\hivepeizhi\\hive-site.xml").getAbsolutePath();
        SparkSession spark = SparkSession
                .builder()
                .appName("Spark Hive Example")
                .config("spark.sql.warehouse.dir", warehouseLocation)
                .enableHiveSupport()
                .master("local[2]")
                .getOrCreate();
        spark.sparkContext().setLogLevel("ERROR");

        spark.udf().register("from_unixtime", new UDF1<String, String>() {
            String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
            public String call(String s) throws Exception {
                SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
                return sdf.format(new Date(Long.parseLong(s) * 1000));
            }
        }, DataTypes.StringType);


        spark.sql("SELECT  t_start_date, t_end_date, from_unixtime(id) as hhah FROM dwd.dwd_test_dz limit 10").show();
    }

}
