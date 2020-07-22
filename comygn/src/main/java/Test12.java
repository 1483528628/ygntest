import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test12 {
    public static void main(String[] args) {
        List<String> list = getFile("C:\\Users\\hp\\Desktop\\脚本\\全量\\azkaban", 0);
        for (String s : list) {
            System.out.println(s);
        }
//        for (String fileName : list) {
//            String s = readFileContent(fileName);
//            String[] tableCreate = fileName.split("\\\\");
//            String tableSql = tableCreate[tableCreate.length - 1];
//            String[] databases = tableSql.split("[.]");
//            String regex = "CREATE TABLE \"" + databases[0] + "\".\"" + ".{50}";
//            Pattern pattern = Pattern.compile(regex);
//            Matcher match = pattern.matcher(s);
//            while (match.find()) {
//                String line = match.group();
//                String[] x = line.split("\"");
////                所有的表
//                System.out.println(x[1] + "." + x[3]);
//                String regex1 = "COMMENT ON COLUMN \"" + x[1] + "\".\"" + x[3] + "\".\"LAST_UPDATE_DATE\"";
//                Pattern pattern1 = Pattern.compile(regex1);
//                Matcher match1 = pattern1.matcher(s);
//                if (match1.find()) {
//                    System.out.println(x[1]+"."+x[3]);
//                }
//            }
//        }
    }

    public static String readFileContent(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        StringBuffer sbf = new StringBuffer();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempStr;
            while ((tempStr = reader.readLine()) != null) {
                sbf.append(tempStr);
            }
            reader.close();
            return sbf.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return sbf.toString();
    }

    private static List<String> getFile(String path, int deep) {
        ArrayList<String> list = new ArrayList<>();
        // 获得指定文件对象
        File file = new File(path);
        // 获得该文件夹内的所有文件
        File[] array = file.listFiles();

        for (int i = 0; i < array.length; i++) {
            if (array[i].isFile())//如果是文件
            {
                for (int j = 0; j < deep; j++)//输出前置空格
                    System.out.print(" ");
                // 只输出文件名字
//                System.out.println( array[i].getName());
                // 输出当前文件的完整路径
                // System.out.println("#####" + array[i]);
                // 同样输出当前文件的完整路径   大家可以去掉注释 测试一下
//                 System.out.println(array[i].getPath());
                list.add(array[i].getPath());
            } else if (array[i].isDirectory())//如果是文件夹
            {
                for (int j = 0; j < deep; j++)//输出前置空格
                    System.out.print(" ");

                System.out.println(array[i].getName());
                //System.out.println(array[i].getPath());
                //文件夹需要调用递归 ，深度+1
                getFile(array[i].getPath(), deep + 1);
            }
        }
        return list;
    }
}
