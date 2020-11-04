import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.common.time.Time;
import org.apache.flink.runtime.rest.messages.job.savepoints.SavepointInfo;
import org.apache.flink.runtime.state.filesystem.AbstractFsCheckpointStorage;
import org.apache.flink.runtime.state.filesystem.FsStateBackend;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

import static org.apache.flink.streaming.api.CheckpointingMode.EXACTLY_ONCE;


/**
 * @author yiwei  2020/4/4
 */
public class FlinkTable {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment bsEnv = StreamExecutionEnvironment.getExecutionEnvironment();
        FsStateBackend fsStateBackend = new FsStateBackend("hdfs://node2:8020/user/flink/user_logCheckpoints");
        bsEnv.setStateBackend(fsStateBackend);
        bsEnv.setRestartStrategy(RestartStrategies.fixedDelayRestart(1, Time.seconds(5)));
        bsEnv.enableCheckpointing(10,EXACTLY_ONCE);
        bsEnv.getCheckpointConfig().setMinPauseBetweenCheckpoints(500);
        bsEnv.getCheckpointConfig().setCheckpointTimeout(60 * 1000);
        bsEnv.getCheckpointConfig().setMaxConcurrentCheckpoints(1);
        bsEnv.getCheckpointConfig().enableExternalizedCheckpoints(CheckpointConfig.ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION);
        bsEnv.getCheckpointConfig().setPreferCheckpointForRecovery(true);
//        bsEnv.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime);
        EnvironmentSettings bsSettings = EnvironmentSettings.newInstance().inStreamingMode().build();
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(bsEnv, bsSettings);
        tableEnv.executeSql("CREATE TABLE user_log ( " +
                "`msg` STRING, `item_id` STRING , `category_id` INT ,`behavior` BOOLEAN" +
                ") WITH ( " +
                " 'connector' = 'kafka', " +
                " 'topic' = 'test', " +
                " 'properties.bootstrap.servers' = 'node1:9092', " +
                " 'properties.group.id' = 'testGroup', " +
                " 'format' = 'json', " +
                " 'scan.startup.mode' = 'latest-offset')");
//, `item_id` STRING , `category_id` INT ,`behavior` BOOLEAN
//        tableEnv.executeSql("create TABLE sink (`msg` STRING, `item_id` STRING , `category_id` INT ,`behavior` BOOLEAN ) with ('connector' = 'print' )");
//        tableEnv.executeSql("insert into sink select * from user_log");
        tableEnv.executeSql("create TABLE `sinks`(`allnum` STRING,`num` BIGINT) with ('connector' = 'print')");
        tableEnv.executeSql("insert into `sinks` select '暂时总数', COUNT(*) from user_log");
//        while (true) {
//            System.out.println(fsStateBackend);
//        }
//        while (true) {
//            int i = 0;
//            if (i == 0) {
//                tableEnv.executeSql("create TABLE `sinks" + i + "`(`allnum` STRING,`num` BIGINT) with ('connector' = 'print')");
//                tableEnv.executeSql("insert into `sinks" + i + "` select '暂时总数', COUNT(*) from user_log");
//            } else {
//                tableEnv.executeSql("create TABLE `sinks"+i+"` (`allnum` STRING,`num` BIGINT) with ('connector' = 'print')");
//                tableEnv.executeSql("insert into sinks"+i+" select  '暂时总数',(select `num` from sinks"+(i-1)+")+(select COUNT(*) from user_log)");
//                tableEnv.executeSql("drop table sinks" + (i - 1));
//            }
//            i++;
//        }
    }
}
