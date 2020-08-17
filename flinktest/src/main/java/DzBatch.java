import java.sql.*;

public class DzBatch {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("org.apache.hive.jdbc.HiveDriver");
        Connection con = DriverManager.getConnection("jdbc:hive2://node3:10000/ods", "hive", "hive");
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * from ods_c_zj_warehouse_receipt_di limit 10");
        while (rs.next()) {
            System.out.println(rs.getString(1) + "," + rs.getString(2));
        }
        rs.close();
        st.close();
        con.close();
    }
}
