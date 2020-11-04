import java.io.BufferedReader;
import java.io.InputStreamReader;

public class RunShell {
    public static void main(String[] args) {
        try {
//            String shpath = "C:\\Users\\Administrator\\Desktop\\111.sh";
//            String shpath = "/home/test/zhang/a.sh";
//            Runtime.getRuntime().exec("chmod +777 "+shpath);
//            Process ps = Runtime.getRuntime().exec(shpath);
            Process ps = Runtime.getRuntime().exec("yarn application --list");
            ps.waitFor();

            BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            String result = sb.toString();
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}