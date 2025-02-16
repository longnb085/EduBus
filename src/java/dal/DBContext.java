package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBContext {

    protected Connection connection;

    public DBContext() {
        try {
            // Thông tin cố định trong code
            String url = "jdbc:sqlserver://localhost:1433;databaseName=BUS_SCHOOL_MANAGEMENT;encrypt=false";

            // Lấy username & password từ biến môi trường
            String username = System.getenv("DB_USERNAME");
            String password = System.getenv("DB_PASSWORD");

            // Kiểm tra nếu biến môi trường chưa được thiết lập
            if (username == null || password == null) {
                throw new SQLException("Database username or password is not set in environment variables.");
            }

            
            
            System.out.println(username + " " + password);
            // Load driver và kết nối SQL Server
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, username, password);

        } catch (ClassNotFoundException | SQLException ex) {
            System.err.println("Database connection error: " + ex.getMessage());
        }
    }
    
    public static void main(String[] args) {
        new DBContext();
    }
}
