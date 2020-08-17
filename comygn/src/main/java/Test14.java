
import java.io.BufferedWriter;

import java.io.File;

import java.io.FileWriter;

import java.util.Arrays;

import java.util.List;



public class Test14 {
    public static void main(String[] args) {
        File file = new File("C:\\code\\comygn\\src\\main\\resources\\schedule.json");
        try {
            if(!file.exists()) {
                file.createNewFile();
            } else {
                file.delete();
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            List<String> actions = Arrays.asList("riding", "running", "swimming");
            List<String> userNames = Arrays.asList("lily", "tom", "bob");
            List<String> times = Arrays.asList("2018-2-2", "2018-3-3");
            for(int i= 0; i<1000; i++) {
                for(String username: userNames) {
                    for(String action: actions) {
                        for(String time: times) {
                            String request = username+" is "+action+" on "+time+"\r\n";
                            bw.write(request);
                        }
                    }
                }
            }
            bw.flush();
            bw.close();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Write is over");
    }
}
