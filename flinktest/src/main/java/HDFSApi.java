import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;

import java.io.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class HDFSApi {
    /**
     * 读取文件内容
     */
//    public static StringBuffer cat(String dir) throws IOException {
//        Configuration conf = new Configuration();
//        FileSystem fs = FileSystem.get(URI.create(dir), conf);
//        Path remotePath = new Path(dir);
//        FSDataInputStream in = fs.open(remotePath);
//        BufferedReader d = new BufferedReader(new InputStreamReader(in));
//        StringBuffer sb = new StringBuffer();
//        String line = null;
//        while ((line = d.readLine()) != null) {
//            String[] strarray = line.split(" ");
//            for (int i = 0; i < strarray.length; i++) {
////                System.out.print(strarray[i]);
//                sb.append(strarray[i]);
////                System.out.print(" ");
//            }
//        }
//        d.close();
//        in.close();
//        fs.close();
//        return sb;
//    }

    public static List<String> listAll(String dir) throws IOException {
        if (StringUtils.isBlank(dir)) {
            return new ArrayList<String>();
        }
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(dir), conf);
        FileStatus[] stats = fs.listStatus(new Path(dir));
        List<String> names = new ArrayList<String>();
        for (int i = 0; i < stats.length; ++i) {
            if (stats[i].isFile()) {
                // regular file
                names.add(stats[i].getPath().toString());
            } else if (stats[i].isDirectory()) {
                // dir
                names.add(stats[i].getPath().toString());
            } else if (stats[i].isSymlink()) {
                // is s symlink in linux
                names.add(stats[i].getPath().toString());
            }
        }
        fs.close();
        return names;
    }

    /**
     * 主函数
     */
    public static void main(String[] args) throws IOException {
        List<String> list = listAll(null);
        int i = 0;
        String result = "";
        for (String s : list) {
            List<String> list1 = listAll(s);
            for (String s1 : list1) {
                if (s1.contains("chk-")) {
                    int i1 = Integer.parseInt(s1.split("chk-")[1]);
                    if (i < i1) {
                        i = i1;
                        result = s1;
                    }
                }
            }
        }
        if (result != "") {
            List<String> list1 = listAll(result);
            if (list1.size() == 0) {
                String s = result.split("chk-")[1];
                result = result.replaceAll(s, String.valueOf(Integer.parseInt(s) - 1));
            }
        }


        System.out.println(result);
    }
}