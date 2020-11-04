import java.util

import org.apache.hadoop.hive.ql.exec.{UDFArgumentException, UDFArgumentLengthException}
import org.apache.hadoop.hive.ql.udf.generic.GenericUDTF
import org.apache.hadoop.hive.serde2.objectinspector.{ObjectInspector, ObjectInspectorFactory, StructObjectInspector}
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory

class UserDefinedUDTF extends GenericUDTF{
  //这个方法的作用：1.输入参数校验  2. 输出列定义，可以多于1列，相当于可以生成多行多列数据
  override def initialize(args:Array[ObjectInspector]): StructObjectInspector = {
    if (args.length != 1) {
      throw new UDFArgumentLengthException("UserDefinedUDTF takes only one argument")
    }
    if (args(0).getCategory() != ObjectInspector.Category.PRIMITIVE) {
      throw new UDFArgumentException("UserDefinedUDTF takes string as a parameter")
    }

    val fieldNames = new util.ArrayList[String]
    val fieldOIs = new util.ArrayList[ObjectInspector]

    //这里定义的是输出列默认字段名称
    fieldNames.add("col1")
    //这里定义的是输出列字段类型
    fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector)

    ObjectInspectorFactory.getStandardStructObjectInspector(fieldNames, fieldOIs)
  }

  //这是处理数据的方法，入参数组里只有1行数据,即每次调用process方法只处理一行数据
  override def process(args: Array[AnyRef]): Unit = {
    //将字符串切分成单个字符的数组
    val strLst = args(0).toString.split("")
    for(i <- strLst){
      var tmp:Array[String] = new Array[String](1)
      tmp(0) = i
      //调用forward方法，必须传字符串数组，即使只有一个元素
      forward(tmp)
    }
  }

  override def close(): Unit = {}
}
