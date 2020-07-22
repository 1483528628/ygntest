
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Test4 {
    public static void main(String[] args) {
        try {
            smallHours();
        }catch (ParseException e){
            e.printStackTrace();
        }
    }

    public static void smallHours() throws ParseException {
        //当天日期
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar=Calendar.getInstance();
        String todayDate=dateFormat.format(calendar.getTime());
        calendar.set(Calendar.HOUR_OF_DAY,-24);
        String yesterdayDate=dateFormat.format(calendar.getTime());
        Date parse = dateFormat.parse(todayDate);
        System.out.println(parse);
    }

}
