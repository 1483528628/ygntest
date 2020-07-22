
public class Test3 {
    public static void main(String[] args) {
        double d = distance(30.26615533, 120.38633972, 30.27190034, 120.38799196);
        double d1 = distance(30.26615533, 120.38633972, 30.27190034, 120.38633972);
        double d2 = distance(30.26615533, 120.38633972, 30.26615533, 120.38799196);
        System.out.println(d * 1.609344 * 1000);
        System.out.println(d2 * 1.609344 * 1000);
        System.out.println(d1 * 1.609344 * 1000);
        System.out.println(Math.sqrt(16));
        System.out.println(658*658-158*158);
        System.out.println(Math.sqrt(658*658-158*158));
    }


    static double distance(double lat1, double lon1, double lat2, double lon2) {

        double theta = lon1 - lon2;

        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))

                + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2))

                * Math.cos(deg2rad(theta));

        dist = Math.acos(dist);

        dist = rad2deg(dist);

        double miles = dist * 60 * 1.1515;

        return miles;

    }

//将角度转换为弧度

    static double deg2rad(double degree) {

        return degree / 180 * Math.PI;

    }

//将弧度转换为角度

    static double rad2deg(double radian) {

        return radian * 180 / Math.PI;

    }

}
