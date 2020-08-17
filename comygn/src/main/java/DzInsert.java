import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DzInsert {
    public static void main(String[] args) {
        String str = readFileContent("C:\\Users\\hp\\Desktop\\etl\\dwd拉链表\\606张全量表的建表语句.txt");
        String strInsertDz = readFileContent("C:\\Users\\hp\\Desktop\\etl\\dwd拉链表\\拉练操作.txt");
        String strInsertDf = readFileContent("C:\\Users\\hp\\Desktop\\etl\\dwd拉链表\\拉链的全量操作.txt");
        String[] strArr = str.split("CREATE TABLE ");
        List<String> paths = getFile("C:\\Users\\hp\\Desktop\\etl\\oracle的建表语句", 0);

        ArrayList<String> list = new ArrayList<>();
        for (int i = 1; i < strArr.length; i++) {
            String dfTableName = strArr[i].split("[(]")[0];
            String oracleTableName = dfTableName.substring(8, dfTableName.length() - 4).toUpperCase();
            String regex = oracleTableName + "\"" + " ADD PRIMARY KEY " + ".{50}";
            Pattern pattern = Pattern.compile(regex);
            String group = "";
            for (String path : paths) {
                String file = readFileContent(path);
                Matcher match = pattern.matcher(file);
                if (match.find()) {
                    group = match.group();
                }
            }
            String[] primaryKeys = group.split("\"");
            StringBuffer idEqual = new StringBuffer();
            StringBuffer idIsNotNull = new StringBuffer();
            for (int i1 = 1; i1 < primaryKeys.length; i1++) {
                if (i1 % 2 == 0) {
                    if (i1 == primaryKeys.length - 2) {
                        idEqual.append("a." + primaryKeys[i1] + "=" + "b." + primaryKeys[i1]);
                        idIsNotNull.append("b." + primaryKeys[i1] + " is not null ");
                    } else {
                        idEqual.append("a." + primaryKeys[i1] + "=" + "b." + primaryKeys[i1] + " and ");
                        idEqual.append("b." + primaryKeys[i1] + " is not null " + " or ");
                    }
                }
            }
//            判断哪些表没有主键
//            if (idEqual.toString().equals("")) {
//                System.out.println(dfTableName);
//                System.out.println(idEqual);
//            }
            String[] columns = strArr[i].split("[`]");
            StringBuffer aColumn = new StringBuffer();
            StringBuffer cColumn = new StringBuffer();
            for (int j = 0; j < columns.length; j++) {
                if (j % 2 != 0) {
                    aColumn.append("a." + columns[j] + ",\n");
                    if (j == columns.length - 2) {
                        cColumn.append("c." + columns[j] + "\n");
                    } else {
                        cColumn.append("c." + columns[j] + ",\n");
                    }
                }
            }
            String tempstr = dfTableName.substring(0, dfTableName.length() - 2);
            String dzTableName = tempstr+"z";
            String diTableName = tempstr+"i";
            String odsTableName = diTableName.replaceAll("dwd.dwd_", "ods.ods_");
            String insertDz = strInsertDz
                    .replaceAll("dz表", dzTableName)
                    .replaceAll("前天", "20200705")
                    .replaceAll("a的所有字段", String.valueOf(aColumn))
                    .replaceAll("昨天", "20200706")
                    .replaceAll("ods表", odsTableName)
                    .replaceAll("c的所有字段", String.valueOf(cColumn))
                    .replaceAll("union all","\nunion all")
                    .replaceAll("as  a ","as  a \n")
                    .replaceAll("FROM","\nFROM")
                    .replaceAll("SELECT","\nSELECT \n")
                    .replaceAll("WHERE","\nWHERE")
                    .replaceAll("as b ","as b \n")
                    .replaceAll("idEqual", String.valueOf(idEqual))
                    .replaceAll("主键不是null",String.valueOf(idIsNotNull));
//            拉链表操作
//            System.out.println(insertDz);
//            System.out.println();

            String insertDf = strInsertDf
                    .replaceAll("dz表", dzTableName)
                    .replaceAll("昨天", "20200706")
                    .replaceAll("c的所有字段", String.valueOf(cColumn))
                    .replaceAll("as  c ", "as  c \n")
                    .replaceAll("FROM", "\nFROM")
                    .replaceAll("SELECT", "\nSELECT \n")
                    .replaceAll("dfTableName",dfTableName);
//            拉链表的全量操作
            System.out.println(insertDf);
            System.out.println();
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
