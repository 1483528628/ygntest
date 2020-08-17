import java.io.*;
import java.util.ArrayList;


public class DzBatch {

    public static void main(String[] args) {
//        String str = readFileContent("C:\\Users\\hp\\Desktop\\etl\\dwd拉链表\\698张拉链表表的建表语句.txt");
//        String[] strArr = str.split("CREATE TABLE ");
//        ArrayList<String> list = new ArrayList<>();
//        for (String s : strArr) {
//            if (s.contains("_di (")) {
//                    String s1 = s
//                            .replaceAll("_di \\(","_di \\(\n")
//                            .replaceAll(",", ",\n")
//                            .replaceAll("PARTITIONED BY","\nPARTITIONED BY")
//                            .replaceAll("STORED AS ORC","\nSTORED AS ORC")
//                            .replaceAll("LOCATION '","\nLOCATION '")
//                            .replaceAll("TBLPROPERTIES","\nTBLPROPERTIES")
//                            .replaceAll("\\) COMMENT '","\n\\) COMMENT '");
//                s1 = "CREATE TABLE " + s1;
//                    list.add(s1);
//            }
//        }
//        File file = new File("C:\\Users\\hp\\Desktop\\etl\\dwd拉链表\\698张全量表的建表语句.txt");
//        try {
//            if(!file.exists()) {
//                file.createNewFile();
//            } else {
//                file.delete();
//                file.createNewFile();
//            }
//            FileWriter fw = new FileWriter(file, true);
//            BufferedWriter bw = new BufferedWriter(fw);
//            for (String s : list) {
//                bw.write(s+"\n");
//            }
//            bw.flush();
//            bw.close();
//            fw.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        ArrayList<String> list = getFile("C:\\Users\\hp\\Desktop\\etl\\oracle的建表语句",0);
        for (String s : list) {
            System.out.println(s);
        }
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

    private static ArrayList<String> getFile(String path, int deep){
        ArrayList<String> list = new ArrayList<>();
        // 获得指定文件对象
        File file = new File(path);
        // 获得该文件夹内的所有文件
        File[] array = file.listFiles();

        for(int i=0;i<array.length;i++)
        {
            if(array[i].isFile())//如果是文件
            {
                for (int j = 0; j < deep; j++)//输出前置空格
//                    System.out.print(" ");
                // 只输出文件名字
//                System.out.println( array[i].getName());
                // 输出当前文件的完整路径
//                 System.out.println("#####" + array[i]);
//                 同样输出当前文件的完整路径   大家可以去掉注释 测试一下
//                System.out.println(array[i].getPath());
                list.add(array[i].getPath());
            }
            else if(array[i].isDirectory())//如果是文件夹
            {
                for (int j = 0; j < deep; j++)//输出前置空格
//                    System.out.print(" ");

//                System.out.println( array[i].getName());
                list.add(array[i].getName());
                //System.out.println(array[i].getPath());
                //文件夹需要调用递归 ，深度+1
                getFile(array[i].getPath(),deep+1);
            }
        }
        return list;
    }
}
