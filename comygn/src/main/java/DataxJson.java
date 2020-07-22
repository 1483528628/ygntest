import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.util.ArrayList;

public class DataxJson {
    public static void main(String[] args) {
//        String fileName = "C:\\code\\comygn\\src\\main\\resources\\databaseAndTable";
//        ArrayList<String> databaseAndTableFile = readFileContent(fileName);
//        for (String line : databaseAndTableFile) {
//            String[] arr = line.split("[.]");
//            System.out.println(arr[0]+"---"+arr[1]);
//        }
        String mysql2hive = readJsonFile("C:\\code\\comygn\\src\\main\\resources\\mysql2hive.json");
        String mysql_columns = "来源表所有的字段";
        String source_database = "来源库名";
        String source_table = "来源表名";
        String source_password = "来源用户密码";
        String source_username = "来源用户名";
        String update_field = "来源判断字段";
        String target_column = "目标表的所有字段";
        String target_table = "目标表名";
        String x = mysql2hive
                .replaceAll("[{]mysql_columns}", mysql_columns)
                .replaceAll("[{]source_database}", source_database)
                .replaceAll("[{]source_table}", source_table)
                .replaceAll("[{]source_password}", source_password)
                .replaceAll("[{]source_username}", source_username)
                .replaceAll("[{]update_field}", update_field)
                .replaceAll("[{]target_column}", target_column)
                .replaceAll("[{]target_table}", target_table);
        System.out.println(x);
        writeJson(x,"表名");

    }

    public static void writeJson(String json,String table) {
        File file = new File("C:\\code\\comygn\\src\\main\\resources\\azkaban\\resources\\import_"+table+".json");
        try {
            if (!file.exists()) {
                file.createNewFile();
            } else {
                file.delete();
                file.createNewFile();
                FileWriter fw = new FileWriter(file, true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(json);
                bw.flush();
                bw.close();
                fw.close();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String acquireColumn(String database, String table) {
        return null;
    }


    public static ArrayList readFileContent(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        ArrayList<String> list = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempStr;
            while ((tempStr = reader.readLine()) != null) {
                list.add(tempStr);
            }
            reader.close();
            return list;
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
        return list;
    }

    /**
     * 读取json文件，返回json串
     *
     * @param fileName
     * @return
     */
    public static String readJsonFile(String fileName) {
        String jsonStr = "";
        try {
            File jsonFile = new File(fileName);
            FileReader fileReader = new FileReader(jsonFile);

            Reader reader = new InputStreamReader(new FileInputStream(jsonFile), "utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
