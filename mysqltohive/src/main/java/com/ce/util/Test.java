package com.ce.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static ArrayList<String> txt2String(File file){
        ArrayList<String> list = new ArrayList<String>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while ((s = br.readLine()) != null) {//使用readLine方法，一次读一行
                list.add(s);
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public static void main(String[] args){
        String s = "abc";
        String x = "gdabcdfg";
        System.out.println(x.contains(s));
        System.out.println(s.contains(x));
    }
}