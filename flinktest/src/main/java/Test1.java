import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.TableResult;

public class Test1 {
    public static void main(String[] args) {
        EnvironmentSettings settings = EnvironmentSettings.newInstance().inBatchMode().build();
        TableEnvironment tableEnv = TableEnvironment.create(settings);
        tableEnv.executeSql(
                "create table MyUserTable( `user` bigint,message string,ts timestamp)with ('connector' = 'kafka','topic' = 'user_behavior','scan.startup.mode' = 'latest-offset','properties.bootstrap.servers' = 'localhost:9092','format' = 'json')"
        );
        TableResult tableResult1 = tableEnv.executeSql(
                "INSERT INTO MyUserTable SELECT product, amount FROM Orders WHERE product LIKE '%Rubber%'"
        );
        // 通过 TableResult 来获取作业状态
        System.out.println(tableResult1.getJobClient().get().getJobStatus());
    }
}
