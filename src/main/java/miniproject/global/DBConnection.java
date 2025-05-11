package miniproject.global;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static Connection conn;

    public static Connection connect(){
        try{
            Class.forName("oracle.jdbc.OracleDriver");

            conn = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521/XEPDB1",
                    "paintfinder",
                    "tiger"
            );

            return conn;

        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("❌ DB 연결 실패: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
