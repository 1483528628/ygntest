import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.table.api.EnvironmentSettings
import org.apache.flink.table.api.bridge.scala.StreamTableEnvironment

object Test03 {
  def main(args: Array[String]): Unit = {
    val fsSettings: EnvironmentSettings = EnvironmentSettings.newInstance().inStreamingMode().build()
    val fsEnv: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    val fsTableEnv: StreamTableEnvironment = StreamTableEnvironment.create(fsEnv,fsSettings)
    fsTableEnv.executeSql("CREATE TABLE user_log ( " +
      "`name` String,`age` int" +
      ") WITH ( " +
      " 'connector' = 'kafka', " +
      " 'topic' = 'test', " +
      " 'properties.bootstrap.servers' = 'node1:9092', " +
      " 'properties.group.id' = 'testGroup', " +
      " 'format' = 'json', " +
      " 'scan.startup.mode' = 'latest-offset'" +
//      " ,'scan.startup.timestamp-millis' = '123456'" +
      ")")
    fsTableEnv.executeSql("CREATE TABLE `MyUserTable` ( `id` int, `name` VARCHAR, `age` int) WITH ('connector' = 'jdbc','url' = 'jdbc:mysql://192.168.2.234:3306/ygn_bigdata','username'='root','password'='','table-name'='person')")
//    fsTableEnv.executeSql("select * from user_log").print()
//    fsTableEnv.executeSql("select * from MyUserTable").print()
//    fsTableEnv.executeSql("create TABLE `sinks`(`aname` VARCHAR, `aage` int,`bname` VARCHAR, `bage` int) with ('connector' = 'print')");
//    fsTableEnv.executeSql("insert into `sinks` select a.name,a.age,b.name,b.age from `MyUserTable` a left join `user_log` b on a.name=b.name");
    fsTableEnv.executeSql("CREATE TABLE `MyUserTable` ( " +
      "`name` String,`age` int" +
      ") WITH ( " +
      " 'connector' = 'kafka', " +
      " 'topic' = 'test1', " +
      " 'properties.bootstrap.servers' = 'node1:9092', " +
      " 'properties.group.id' = 'testGroup', " +
      " 'format' = 'json', " +
      " 'scan.startup.mode' = 'timestamp'" +
      " ,'scan.startup.timestamp-millis' = '123456'" +
      ")")
//    fsTableEnv.executeSql("create TABLE `sinks`(`aname` VARCHAR, `aage` int) with ('connector' = 'print')");
//    fsTableEnv.executeSql("insert into `sinks` select a.name,a.age from `MyUserTable` a");
        fsTableEnv.executeSql("create TABLE `sinks`(`aname` VARCHAR, `aage` int,`bname` VARCHAR, `bage` int) with ('connector' = 'print')");
        fsTableEnv.executeSql("insert into `sinks` select a.name,a.age,b.name,b.age from `MyUserTable` a left join `user_log` b on a.name=b.name");
  }
}
