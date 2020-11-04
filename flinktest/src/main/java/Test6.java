import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableResult;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.types.Row;

public class Test6 {
    private final static String kafka_source1 = "create table binlog (" +
            "`afterColumns` row(created String,extra Row(canGiving Boolean),`parameter` Array<Int>)," +
            "`beforeColumns` String," +
            "`tableVersion` row(binlogFile String,binlogPosition Int,version Int)," +
            "`touchTime` bigInt" +
            ")with(" +
            " 'connector' = 'kafka', " +
            " 'topic' = 'test', " +
            " 'properties.bootstrap.servers' = 'node1:9092', " +
            " 'properties.group.id' = 'testGroup', " +
            " 'format' = 'json', " +
            " 'scan.startup.mode' = 'latest-offset'"+
            ")";
    public static void main(String[] args) throws Exception {
        EnvironmentSettings envs = EnvironmentSettings.newInstance().inStreamingMode().build();
        StreamExecutionEnvironment senv = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment tenv = StreamTableEnvironment.create(senv, envs);
        tenv.executeSql(kafka_source1);
        Table table = tenv.sqlQuery("select * from binlog");
        DataStream<Row> rowDataStream = tenv.toAppendStream(table, Row.class);
        rowDataStream.print();
        senv.execute("binlog");
    }
}
