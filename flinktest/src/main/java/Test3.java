import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.typeutils.RowTypeInfo;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.table.functions.TableFunction;
import org.apache.flink.types.Row;


public class Test3 {
    public static void main(String[] args) throws Exception {
        EnvironmentSettings settings = EnvironmentSettings.newInstance().useBlinkPlanner().inStreamingMode().build();
        StreamExecutionEnvironment fsEnv = StreamExecutionEnvironment.getExecutionEnvironment();
//        fsEnv.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime);
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(fsEnv, settings);

        tableEnv.executeSql("CREATE TABLE user_log ( " +
                "`name` STRING, `num` INT" +
                ") WITH ( " +
                " 'connector' = 'kafka', " +
                " 'topic' = 'test', " +
                " 'properties.bootstrap.servers' = 'node1:9092', " +
                " 'properties.group.id' = 'testGroup', " +
                " 'format' = 'json', " +
                " 'scan.startup.mode' = 'latest-offset')");
//        tableEnv.executeSql("CREATE TABLE user_log ( " +
//                "name STRING,num Int,user_action_time  TIMESTAMP(3),WATERMARK FOR user_action_time AS user_action_time - INTERVAL '5' SECOND" +
//                ") WITH ( " +
//                " 'connector' = 'kafka', " +
//                " 'topic' = 'test', " +
//                " 'properties.bootstrap.servers' = 'node1:9092', " +
//                " 'properties.group.id' = 'testGroup', " +
//                " 'format' = 'json', " +
//                " 'scan.startup.mode' = 'latest-offset')");
        tableEnv.createTemporarySystemFunction("abc",new SplitFunction());
//        tableEnv.executeSql("create TABLE `sinks`(`allnum` STRING,`num` INT) with ('connector' = 'print')");
        Table table = tableEnv.sqlQuery(" select name,num from user_log left join lateral table(abc(name)) as t(newWord,newlength) on true");
//        tableEnv.executeSql("create table user_sum(`a` TIMESTAMP,`b` String,`c` Int) with('connector' = 'print')");
//        tableEnv.executeSql("insert into user_sum SELECT TUMBLE_START(user_action_time, INTERVAL '1' second),name, sum(num) " +
//                "FROM user_log " +
//                "GROUP BY TUMBLE(user_action_time, INTERVAL '1' second),name ");


//        Table table = tableEnv.sqlQuery("SELECT TUMBLE_START(user_action_time, INTERVAL '10' second) as tumble_date,name, sum(num) as sum_num FROM user_log GROUP BY TUMBLE(user_action_time, INTERVAL '10' second),name");
//        DataStream<Tuple2<Boolean, Row>> rowDataStream = tableEnv.toRetractStream(table, Row.class);
////        DataStream<Row> rowDataStream = tableEnv.toAppendStream(table, Row.class);
//        rowDataStream.print();
//        fsEnv.execute("abc");


//        Table table = tableEnv.sqlQuery("SELECT name, sum(num) as sum_num FROM user_log GROUP BY name");
        DataStream<Row> rowDataStream = tableEnv.toAppendStream(table, Row.class);
        rowDataStream.print();
        fsEnv.execute("rfer");
    }

}
