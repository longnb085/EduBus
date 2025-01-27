/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Bus;

/**
 *
 * @author DIEN MAY XANH
 */
public class BusDAO extends DBContext {

    public List<Bus> getAllBus() {
        List<Bus> list = new ArrayList<>();
        String sql = "select * from Bus ";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Bus o = new Bus(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5));
                list.add(o);
            }
        } catch (SQLException ex) {
        }
        return list;
    }

    public void insert(Bus b) {
        String sql = "insert into Bus(LicensePlate, Status, RouteID) values(?,?,?,?)";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.executeUpdate();

        } catch (SQLException ex) {
        }
    }

    public void update(Bus b) {
        String sql = " update Bus\n"
                + " set Status = ?,\n"
                + "     Issues = ?\n"
                + " where  LicensePlate = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, b.getStatus());
            ps.setString(2, b.getIssues());
            ps.setString(3, b.getLicense());
            ps.executeUpdate();

        } catch (SQLException ex) {
        }
    }

    public String getLicensePlateById(int busID) {
        String licensePlate = "";
        String sql = "SELECT licensePlate FROM Bus WHERE busID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, busID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                licensePlate = rs.getString("licensePlate");
            }
        } catch (SQLException e) {
        }
        return licensePlate;
    }

    
    public List<Map<String, Object>> getAllBusesWithRouteNames() {
        List<Map<String, Object>> busList = new ArrayList<>();
        String sql = "SELECT b.BusID, b.Status, b.Issues, b.LicensePlate, COALESCE(r.RouteName, 'Sự dự phòng') AS RouteName "
                + "FROM Bus b "
                + "LEFT JOIN Route r ON b.RouteID = r.RouteID;";

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Map<String, Object> bus = new HashMap<>();
                bus.put("BusID", rs.getInt("BusID"));
                bus.put("Status", rs.getString("Status"));
                bus.put("Issues", rs.getString("Issues"));
                bus.put("LicensePlate", rs.getString("LicensePlate"));
                bus.put("RouteName", rs.getString("RouteName"));
                busList.add(bus);
            }
        } catch (SQLException e) {
        }
        return busList; // Return the list of buses
    }

    public boolean updateBusRouteAndStatus(int busId1, int busId2) {
        String sql = "UPDATE Bus "
                + "SET RouteID = CASE "
                + "WHEN BusID = ? THEN (SELECT RouteID FROM Bus WHERE BusID = ?) "
                + "WHEN BusID = ? THEN (SELECT RouteID FROM Bus WHERE BusID = ?) "
                + "END, "
                + "Status = 'Active' "
                + "WHERE BusID IN (?, ?);";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, busId1);
            ps.setInt(2, busId2);
            ps.setInt(3, busId2);
            ps.setInt(4, busId1);
            ps.setInt(5, busId1);
            ps.setInt(6, busId2);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0; // Trả về true nếu có ít nhất một dòng bị ảnh hưởng
        } catch (SQLException e) {
            return false; // Trả về false nếu có lỗi xảy ra
        }
    }

    public static void main(String[] args) {
        BusDAO b = new BusDAO();

    }
}
