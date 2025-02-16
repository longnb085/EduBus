package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBContext {

    protected Connection connection;

    public DBContext() {
        try {
            String url = "jdbc:sqlserver://localhost:1433;databaseName=BUS_SCHOOL_MANAGEMENT;encrypt=false";
            String username = System.getenv("DB_USERNAME");
            String password = System.getenv("DB_PASSWORD");

            if (username == null || password == null) {
                throw new RuntimeException("❌ ERROR: Database username or password is not set in environment variables.");
            }

            System.out.println("🔍 DEBUG: Connecting with -> " + username + " / " + password);

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, username, password);

            System.out.println("✅ Database connected successfully!");

        } catch (ClassNotFoundException ex) {
            System.out.println("❌ ERROR: SQL Server JDBC Driver not found!");
            ex.printStackTrace();
        } catch (SQLException ex) {
            System.out.println("❌ ERROR: Cannot connect to database.");
            ex.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    // ✅ Hàm main để test
    public static void main(String[] args) {
        DBContext db = new DBContext();
        if (db.getConnection() != null) {
            System.out.println("🎉 SUCCESS: Database connection is working fine!");
        } else {
            System.out.println("🚨 ERROR: Connection is null! Check logs above.");
        }
    }
}
