/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Drivers;
import model.Manager;

/**
 *
 * @author DIEN MAY XANH
 */
public class SchoolDAO extends DBContext {

    public List<Drivers> getAllDriver() {
        List<Drivers> list = new ArrayList<>();
        String sql = "SELECT * FROM Driver";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Drivers d = new Drivers();
                d.setId(rs.getInt("DriverID"));
                d.setName(rs.getString("DriverName"));
                d.setPhone(rs.getString("Phone"));
                d.setGender(rs.getString("Gender"));
                d.setDob(rs.getDate("DateOfBirth"));
                d.setImg(rs.getString("Image"));
                d.setAccid(rs.getInt("AccountID"));
                list.add(d);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public Drivers getDriver(String accid) {
        String sql = "select * from Driver where 1 = 1 ";
        if (accid != null) {
            sql += "and AccountID = " + accid;
        }
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Drivers o = new Drivers(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getDate(5),
                        rs.getString(6),
                        rs.getInt(7)
                );
                return o;
            }
        } catch (SQLException ex) {
        }
        return null;
    }

    public Manager getManager(String accid) {
        String sql = "select * from Manager where 1 = 1 ";
        if (accid != null) {
            sql += "and AccountID = " + accid;
        }
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Manager o = new Manager(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getDate(5),
                        rs.getString(6),
                        rs.getInt(7)
                );
                return o;
            }
        } catch (SQLException ex) {
        }
        return null;
    }

    public void updateManager(Manager m) {
        String sql = "UPDATE Manager\n"
                + "   SET ManagerName = ?,\n"
                + "       Phone = ?,\n"
                + "	   Gender = ?,\n"
                + "	   DateOfBirth = ?,\n"
                + "	   Image = ?\n"
                + " WHERE AccountID = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, m.getName());
            ps.setString(2, m.getPhone());
            ps.setString(3, m.getGender());
            ps.setDate(4, m.getDob());
            ps.setString(5, m.getImg());
            ps.setInt(6, m.getAccid());
            ps.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Hello");
        }
    }

    public void updateDriver(Drivers m) {
        String sql = "UPDATE Driver\n"
                + "   SET DriverName = ?\n"
                + "      ,Phone= ?\n"
                + "      ,Gender = ?\n"
                + "      ,DateOfBirth = ?\n"
                + "      ,Image = ?\n"
                + " WHERE AccountID = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, m.getName());
            ps.setString(2, m.getPhone());
            ps.setString(3, m.getGender());
            ps.setDate(4, m.getDob());
            ps.setString(5, m.getImg());
            ps.setInt(6, m.getAccid());
            ps.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Hello");
        }
    }

    public String getDriverNameById(int driverID) {
        String driverName = "";
        String sql = "SELECT driverName FROM Driver WHERE driverID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, driverID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                driverName = rs.getString("driverName");
            }
        } catch (SQLException e) {
        }
        return driverName;
    }

    public List<Drivers> searchDrivers(String name, String phone, String gender, Date dob) {
        List<Drivers> drivers = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM Driver WHERE 1=1");

        if (name != null && !name.isEmpty()) {
            sql.append(" AND DriverName LIKE ?");
        }
        if (phone != null && !phone.isEmpty()) {
            sql.append(" AND Phone = ?");
        }
        if (gender != null && !gender.isEmpty()) {
            sql.append(" AND Gender = ?");
        }
        if (dob != null) {
            sql.append(" AND DateOfBirth = ?");
        }

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            int index = 1;
            if (name != null && !name.isEmpty()) {
                ps.setString(index++, "%" + name + "%");
            }
            if (phone != null && !phone.isEmpty()) {
                ps.setString(index++, phone);
            }
            if (gender != null && !gender.isEmpty()) {
                ps.setString(index++, gender);
            }
            if (dob != null) {
                ps.setDate(index++, dob);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Drivers driver = new Drivers();
                driver.setDid(rs.getInt("DriverID"));
                driver.setName(rs.getString("DriverName"));
                driver.setPhone(rs.getString("Phone"));
                driver.setGender(rs.getString("Gender"));
                driver.setDob(rs.getDate("DateOfBirth"));
                driver.setImg(rs.getString("Image"));
                driver.setAccid(rs.getInt("AccountID"));
                drivers.add(driver);
            }
        } catch (SQLException e) {
        }
        return drivers;
    }

    public static void main(String[] args) {
        SchoolDAO sd = new SchoolDAO();

    }
}
