import java.util.ArrayList;

public class Test2 {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        list.add("asd");
        list.add("assdfd");
        list.add("dfg");
        list.add("zhang");
        String x = "";
        for (int i = 0; i < list.size(); i++) {
            if (i == list.size() - 1) {
                String s = "\"" + list.get(i) + "\"";
                x += s;
            } else {
                String s = "\"" + list.get(i) + "\",";
                x += s;
            }
        }
        System.out.println(x);
    }
}
