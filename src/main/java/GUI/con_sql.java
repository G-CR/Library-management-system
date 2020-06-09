package GUI;

import java.sql.*;

public class con_sql {
    public con_sql() {
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://127.0.0.1:3306/Java?useUnicode=true&characterEncoding=utf-8&useSSL=false";
        String username = "root";
        String password = "12345678";
        Connection conn = null;

        try {
            Class.forName(driver);
            conn = (Connection) DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }


}
