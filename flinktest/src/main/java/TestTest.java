
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.typeutils.RowTypeInfo;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.bridge.java.BatchTableEnvironment;
import org.apache.flink.table.functions.AggregateFunction;
import org.apache.flink.table.functions.ScalarFunction;
import org.apache.flink.table.functions.TableAggregateFunction;
import org.apache.flink.table.functions.TableFunction;
import org.apache.flink.types.Row;
import org.apache.flink.util.Collector;

import java.util.Iterator;


public class TestTest {
    public static void main(String[] args) throws Exception {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        BatchTableEnvironment tEnv = BatchTableEnvironment.create(env);
        DataSet<WC> input = env.fromElements(
                new WC("a 2", 4l, 5),
                new WC("b 2", 1l, 3),
                new WC("a 2", 1l, 2));

        // register the DataSet as table "WordCount"
        tEnv.registerDataSet("WordCount", input, "a, b,c");
//        tEnv.registerFunction("abc", new SubstringFunction());
        tEnv.registerFunction("UDAFSum",new UDAFSum());
//        tEnv.createTemporarySystemFunction("UDAFSum",new UDAFSum());
        // run a SQL query on the Table and retrieve the result as a new Table

        Table table = tEnv.sqlQuery(
                "SELECT a, UDAFSum(c) FROM WordCount group by a");

        DataSet<Row> result = tEnv.toDataSet(table, Row.class);

        result.print();
    }

    public static class WC {
        public String a;
        public Long b;
        public Integer c;

        // public constructor to make it a Flink POJO
        public WC() {
        }


        public WC(String a, Long b, int c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }

        @Override
        public String toString() {
            return "WC " + a + " " + b + " " + c;
        }
    }

    public static class SubstringFunction extends ScalarFunction {
        public String eval(String s, Integer begin, Integer end) {
            return s.substring(begin, end);
        }
    }

    /**
     * 自定义UDAF
     */
    public static class UDAFSum extends AggregateFunction<Long, UDAFSum.SumAccumulator>{

        /**
         * 定义一个Accumulator，存放聚合的中间结果
         */
        public static class SumAccumulator{
            public long sumPrice;
        }

        /**
         * 初始化Accumulator
         * @return
         */
        @Override
        public SumAccumulator createAccumulator() {
            SumAccumulator sumAccumulator = new SumAccumulator();
            sumAccumulator.sumPrice=0;
            return sumAccumulator;
        }

        /**
         * 定义如何根据输入更新Accumulator
         * @param accumulator  Accumulator
         * @param productPrice 输入
         */
        public void accumulate(SumAccumulator accumulator,int productPrice){
            accumulator.sumPrice += productPrice;
        }

        /**
         * 返回聚合的最终结果
         * @param accumulator Accumulator
         * @return
         */
        @Override
        public Long getValue(SumAccumulator accumulator) {
            return accumulator.sumPrice;
        }

        public void resetAccumulator(SumAccumulator sa) {
            sa.sumPrice = 0;
        }
    }


    public static class UDTFOneColumnToMultiColumn extends TableFunction<Row> {
        public void eval(String value) {
            String[] valueSplits = value.split(",");

            //一行，两列
            Row row = new Row(2);
            row.setField(0,valueSplits[0]);
            row.setField(1,valueSplits[1]);
            collect(row);
        }
        @Override
        public TypeInformation<Row> getResultType() {
            return new RowTypeInfo(Types.STRING,Types.STRING);
        }
    }
}
