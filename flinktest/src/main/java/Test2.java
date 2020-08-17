import org.apache.flink.api.java.io.jdbc.JDBCInputFormat;
import org.apache.flink.connector.jdbc.JdbcInputFormat;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.TableResult;
import org.apache.flink.table.functions.ScalarFunction;

public class Test2 {
    public static void main(String[] args) {
        EnvironmentSettings settings = EnvironmentSettings.newInstance().inBatchMode().build();
        TableEnvironment tableEnv = TableEnvironment.create(settings);

//        tableEnv.executeSql(
//                "CREATE TABLE MyUserTable ( A VARCHAR , B VARCHAR, C VARCHAR) WITH ('connector' = 'jdbc','url' = 'jdbc:mysql://192.168.2.234:3306/ygn_bigdata','username'='root','password'='yagena*2020&2019','driver'='com.mysql.jdbc.Driver','tableName'='MyUserTable')"
//        );

//        TableResult tableResult1 = tableEnv.executeSql(
//                "INSERT INTO MyUserTable SELECT id,mmsi,created_time FROM ygn_bigdata.rt_dt_arrival_departure WITH ('connector' = 'jdbc','url' = 'jdbc:mysql://192.168.2.234:3306/ygn_bigdata','username'='root','password'='yagena*2020&2019','driver'='com.mysql.jdbc.Driver')"
//        );
//        // 通过 TableResult 来获取作业状态
//        System.out.println(tableResult1.getJobClient().get().getJobStatus());




        String sql = "CREATE TABLE `MyUserTable` ( `id` int, `event_id` VARCHAR, `mmsi` VARCHAR,`velocity` bigint,`sail_state` int,`created_time` TIMESTAMP) WITH ('connector' = 'jdbc','url' = 'jdbc:mysql://192.168.2.234:3306/ygn_bigdata','username'='root','password'='yagena*2020&2019','table-name'='rt_dt_arrival_departure')";
        tableEnv.executeSql(sql);
//        tableEnv.executeSql("insert into MyUserTable select `id`, `event_id`, `mmsi`,`velocity`,`sail_state`,`created_time` from rt_dt_arrival_departure");
        tableEnv.registerFunction("abc", new CompareNum());
//        tableEnv.executeSql("select id,event_id,abc(velocity) vel from MyUserTable").print();
        tableEnv.executeSql("select row_number()over(partition by mmsi order by id) abc from MyUserTable").print();

    }
    public static class CompareNum extends ScalarFunction{
        public Boolean eval(long velocity) {
            if (velocity > 20) {
                return true;
            }else{
                return false;
            }
        }
    }
}
