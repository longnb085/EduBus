/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import model.Route;
import model.Stop;

public class RouteDAO extends DBContext {

    public List<Route> getAllRoute() {
        List<Route> list = new ArrayList<>();
        // Câu truy vấn để lấy ra các tuyến đường có ít nhất một stop
        String sql = "SELECT r.* FROM Route r "
                + "INNER JOIN Stop s ON r.RouteID = s.RouteID "
                + "GROUP BY r.RouteID, r.RouteName"; // Nhóm theo RouteID và RouteName

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Route o = new Route(rs.getInt("RouteID"), getStopByRouteID(rs.getInt("RouteID")), rs.getString("RouteName"));
                list.add(o);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // Ghi lại lỗi nếu có
        }
        return list;
    }

    public String getRouteNameByRouteID(int routeID) {
        String sql = "SELECT RouteName FROM Route WHERE RouteID = ?";
        String routeName = null;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, routeID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                routeName = rs.getString("RouteName");
            }
        } catch (SQLException ex) {
        }

        return routeName;
    }

    public List<Stop> getStopNameAndOrderByRouteID(int routeId) {
        String sql = "select s.StopName, s.StopOrder from stop s join Route r on s.RouteID = r.RouteID where r.RouteID = ?";
        List<Stop> l = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, routeId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String stopName = rs.getString("StopName");
                int stopOrder = rs.getInt("StopOrder");
                Stop s = new Stop();
                s.setStopname(stopName);
                s.setOrder(stopOrder);
                l.add(s);
            }
        } catch (SQLException ex) {
        }
        return l;
    }

    public String getStopByRouteID(int rid) {
        String sql = "select StopName from Stop where RouteID = ?";
        StringBuilder result = new StringBuilder();
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, rid);
            ResultSet rs = ps.executeQuery();
            boolean first = true;
            while (rs.next()) {
                if (!first) {
                    result.append("<br>");
                }
                result.append(rs.getString(1));
                first = false;
            }
        } catch (SQLException ex) {
        }
        return result.toString();
    }

    public List<Route> getRouteByStop(String name) {
        List<Route> list = new ArrayList<>();
        String sql = """
                     select * from Route where RouteID in(select distinct r.routeID from Route r join Stop s
                              on r.routeID = s.routeID
                     where s.stopName like '%""" + name + "%')";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Route o = new Route(rs.getInt(1), getStopByRouteID(rs.getInt(1)), rs.getString(2));
                list.add(o);
            }
        } catch (SQLException ex) {
        }
        return list;
    }

    public List<Stop> getStopByRouteID(String rid) {
        List<Stop> list = new ArrayList<>();
        String sql = "select * from Stop Where RouteID = " + rid;

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Stop o = new Stop(rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getInt(4));
                list.add(o);
            }
        } catch (SQLException ex) {
        }
        return list;
    }

    public void addRouteWithStops(String routeName, List<Stop> stops) throws SQLException {
        PreparedStatement routeStatement = null;
        PreparedStatement stopStatement = null;
        try {
            connection.setAutoCommit(false);
            String addRouteSQL = "INSERT INTO Route (RouteName) VALUES (?)";
            routeStatement = connection.prepareStatement(addRouteSQL, PreparedStatement.RETURN_GENERATED_KEYS);
            routeStatement.setString(1, routeName);
            routeStatement.executeUpdate();
            int routeID;
            try (ResultSet generatedKeys = routeStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    routeID = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Failed to retrieve RouteID.");
                }
            }
            String addStopSQL = "INSERT INTO Stop (StopOrder, StopName, RouteID) VALUES (?, ?, ?)";
            stopStatement = connection.prepareStatement(addStopSQL);
            for (Stop stop : stops) {
                stopStatement.setInt(1, stop.getOrder());
                stopStatement.setString(2, stop.getStopname());
                stopStatement.setInt(3, routeID);
                stopStatement.executeUpdate();
            }
            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback();
            }
            throw e;
        } finally {
            if (routeStatement != null) {
                routeStatement.close();
            }
            if (stopStatement != null) {
                stopStatement.close();
            }
        }
    }

    public void updateStop(int stopId, int stopOrder, String stopName) {
        String sql = "UPDATE Stop SET StopName = ? WHERE StopID = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, stopName);
            ps.setInt(2, stopId);
            ps.executeUpdate();
        } catch (SQLException ex) {
        }
    }

    public void updateRouteName(int routeId, String newRouteName) {
        String sql = "UPDATE Route SET RouteName = ? WHERE RouteID = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, newRouteName);
            ps.setInt(2, routeId);
            ps.executeUpdate();
        } catch (SQLException ex) {
        }
    }

    public void insertStop(int stopOrder, String stopName, int routeID) {
        String query = "INSERT INTO Stop (StopOrder, StopName, RouteID) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, stopOrder);
            stmt.setString(2, stopName);
            stmt.setInt(3, routeID);
            stmt.executeUpdate();
        } catch (SQLException e) {
        }
    }

    public List<Integer> getStopIdsByRouteId(int routeId) {
        List<Integer> stopIds = new ArrayList<>();
        String sql = "SELECT StopID FROM Stop WHERE RouteID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, routeId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    stopIds.add(rs.getInt("StopID"));
                }
            }
        } catch (SQLException ex) {
        }
        return stopIds;
    }

    public static void main(String[] args) {
        RouteDAO r = new RouteDAO();

        List<Stop> stops = List.of(
                new Stop(0, 1, "Stop 1", 0),
                new Stop(0, 2, "Stop 2", 0),
                new Stop(0, 3, "Stop 3", 0)
        );

        try {
            r.addRouteWithStops("New Route", stops);
        } catch (SQLException e) {
        }
    }
}
