import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

public class Test5 {
    public static void main(String[] args) {

// 输出所有时区

        Set<String> set = ZoneId.getAvailableZoneIds();
//        set.forEach(System.out::println);



        System.out.println(LocalDateTime.now(ZoneId.of("Asia/Shanghai")));  // 2019-01-26T14:58:50.535
//
//        System.out.println(LocalDateTime.now(ZoneId.of("US/Pacific")));     // 2019-01-25T23:04:02.452(US/Pacific太平洋)
//
//
//
//// 带时区的时间或日期
//
//        System.out.println(ZonedDateTime.now(ZoneId.of("US/Pacific")));     // 2019-01-25T22:58:50.537-08:00[US/Pacific]
//
//
//
//// LocalDateTime转为ZonedDateTime（带时区的时间或日期）
//
        LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));    // 上海时区
//
//        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("US/Pacific"));    // 太平洋时区
//
        System.out.println(localDateTime);  // 2019-01-26T15:10:54.626
//
//        System.out.println(zonedDateTime);  // 2019-01-26T15:10:54.626-08:00[US/Pacific]
//
//
//
//// ZonedDateTime（带时区的时间或日期）转 LocalDateTime
//
//        LocalDateTime localDateTime1 = zonedDateTime.toLocalDateTime();
//
//        System.out.println(localDateTime1); // 2019-01-26T15:16:04.746
//
//
        ZonedDateTime zdt = ZonedDateTime.parse("2007-12-03T10:15:30+01:00[Europe/Paris]",DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        System.out.println(zdt);
        ZoneId zoneId = ZoneId.of("Asia/Shanghai");
//
        ZonedDateTime from = ZonedDateTime.from(LocalDateTime.now().atZone(zoneId));
//
//        System.out.println(from);   // 2019-01-26T16:18:22.171+08:00[Asia/Shanghai]
//
//
//
//// 一样的get获取，plus加时间，minus减时间...

    }
}
