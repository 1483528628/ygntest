package com.ygn

object TestTest {
  def main(args: Array[String]): Unit = {
    val s = "abc  d"
    val arr: Array[String] = s.split("\\W+")
    for (elem <- arr) {
      println(elem)
    }
  }
}
