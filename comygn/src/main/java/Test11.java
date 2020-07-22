import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test11 {
    public static void main(String[] args) {
//        String fileName1 = "C:\\Users\\hp\\Desktop\\所有的表名\\hive.txt";
//        String fileName2 = "C:\\Users\\hp\\Desktop\\所有的表名\\oracle.txt";
//        List<String> list1 = readFileContent(fileName1);
//        List<String> list2 = readFileContent(fileName2);
//        String regex = "CREATE TABLE \"" + "MSAPUB_DICT" + "\".\"" + x;
//        Pattern pattern = Pattern.compile(regex);
//        Matcher match = pattern.matcher(s);
//        while (match.find()) {
//            System.out.println(match.group());
//        }
//        for (String s2 : list2) {
//            String s1 = s2.toLowerCase();
//            Pattern pattern = Pattern.compile("ods_"+s1+"_d[i,f]");
//            for (String s : list1) {
//                Matcher match = pattern.matcher(s);
//                while (match.find()) {
//                    System.out.println(s2+","+s);
//                }
//            }
//        }
        //        select 'ods_c_cb_import_crew_di' as table_name,COUNT(*) as table_num FROM ods.ods_c_cb_import_crew_di where dt = '{yyyyMMdd%-1d}' union
//        for (String s : list1) {
//            System.out.println("select '"+s+"' as table_name,COUNT(*) as table_num FROM ods."+s+" where dt = '{yyyyMMdd%-1d}' union");
//        }
//        String fileName1 = "C:\\Users\\hp\\Desktop\\所有的表名\\oracledi.txt";
//        String fileName2 = "C:\\Users\\hp\\Desktop\\所有的表名\\oracledf.txt";
//        String fileName3 = "C:\\Users\\hp\\Desktop\\所有的表名\\698张oracle表的库名及其表名.txt";
//        List<String> list1 = readFileContent(fileName1);
//        List<String> list2 = readFileContent(fileName2);
//        List<String> list3 = readFileContent(fileName3);
//        Set<String> set = new HashSet<>();
//        for (String s : list2) {
//            Pattern pattern = Pattern.compile(s);
//            for (String s1 : list1) {
//                Matcher matcher = pattern.matcher(s1);
//                while (matcher.find()) {
//                    set.add(s1);
//                }
//            }
//        }
//        for (String s1 : set) {
//            System.out.println(s1);
//        }

        String fileName1 = "C:\\Users\\hp\\Desktop\\所有的表名\\oracledi.txt";
        String fileName2 = "C:\\Users\\hp\\Desktop\\所有的表名\\oracledf.txt";
        List<String> list1 = readFileContent(fileName1);
        List<String> list2 = readFileContent(fileName2);
//        select 'C_WF_WORK_OS_OIL' as table_name,COUNT(*) as table_num FROM MSAPUB_WF.C_WF_WORK_OS_OIL where LAST_UPDATE_DATE<to_date('{yyyy-MM-dd}','yyyy-MM-dd') and LAST_UPDATE_DATE>=to_date('{yyyy-MM-dd%-1}','yyyy-MM-dd') union
        for (String s : list1) {
            String[] s1 = s.split("[.]");
            System.out.println("select '"+s1[1]+"' as table_name,COUNT(*) as table_num FROM "+s1[0]+"."+s1[1]+" where LAST_UPDATE_DATE<to_date('{yyyy-MM-dd}','yyyy-MM-dd') and LAST_UPDATE_DATE>=to_date('{yyyy-MM-dd%-1}','yyyy-MM-dd') union");
        }
        for (String s : list2) {
            String[] s1 = s.split("[.]");
            System.out.println("select '"+s1[1]+"' as table_name,COUNT(*) as table_num FROM "+s1[0]+"."+s1[1]+" union");
        }
    }

    public static List<String> readFileContent(String fileName) {
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
}
