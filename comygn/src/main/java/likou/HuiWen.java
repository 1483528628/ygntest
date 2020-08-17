package likou;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HuiWen {
    public static void main(String[] args) {
        String[] words = {"abcd", "dcba", "lls", "s", "sssll"};
        List<List<Integer>> lists = palindromePairs(words);
        System.out.println(lists);
    }
    public static List<List<Integer>> palindromePairs(String[] words) {
        List<List<Integer>> list = new ArrayList();

        for(int i=0;i<words.length;i++){
            for(int j=0;j<words.length;j++){
                if(i!=j){
                    String s1 = words[i]+words[j];
                    String s2 = new StringBuffer(s1).reverse().toString();
                    if (s1.equals(s2)) {
                        List<Integer> list1 = new ArrayList();
                        list1.add(i);
                        list1.add(j);
                        list.add(list1);
                    }
                }
            }
        }
        return list;
    }
}
