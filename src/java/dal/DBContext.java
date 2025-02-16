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
            String username = "sa";
            String password  = "123";
            
                        // Load driver và kết nối SQL Server
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, username, password);

        } catch (ClassNotFoundException | SQLException ex) {
            System.err.println("Database connection error: " + ex.getMessage());
        }
    }
    
}
