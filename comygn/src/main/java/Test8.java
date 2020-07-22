import java.util.HashMap;
import java.util.Map;

public class Test8 {
    public static void main(String[] args) {

        HashMap<String, String> map1 = new HashMap<>();
        map1.put("a", "3");
        map1.put("b", "4");
        map1.put("c", "5");
        HashMap<String, String> map2 = new HashMap<>();
        map2.put("a", "1");
        map2.put("b", "2");
        for (Map.Entry<String, String> maps : map1.entrySet()) {
            if (!map2.containsKey(maps.getKey())) {
                System.out.println(maps.getKey());
            }
        }
    }
}
