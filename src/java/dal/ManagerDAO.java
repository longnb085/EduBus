/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import model.Student;
import java.sql.ResultSet;
import model.Manager;
import model.Trip;

public class ManagerDAO extends DBContext {

    public List<Manager> getAllManager() {
        List<Manager> list = new ArrayList<>();
        String sql = "SELECT * FROM Manager";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Manager m = new Manager();
                m.setId(rs.getInt("ManagerID"));
                m.setName(rs.getString("ManagerName"));
                m.setPhone(rs.getString("Phone"));
                m.setImg(rs.getString("Image"));
                m.setGender(rs.getString("Gender"));
                m.setDob(rs.getDate("DateOfBirth"));
                m.setAccid(rs.getInt("AccountID"));
                list.add(m);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public void addTrip(Date tripDate, Time departureTime, String timePeriod, int driverID, int managerID, int busID) {
        String sql = "INSERT INTO Trip (TripDate, DepartureTime, TimePeriod, DriverID, ManagerID, BusID) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setDate(1, tripDate);
            st.setTime(2, departureTime);
            st.setString(3, timePeriod);
            st.setInt(4, driverID);
            st.setInt(5, managerID);
            st.setInt(6, busID);
            st.executeUpdate();
        } catch (SQLException e) {
        }
    }

    public int getManagerIDByAccountID(int accountID) {
        String query = "SELECT ManagerID FROM Manager WHERE AccountID = ?";
        try (
                PreparedStatement st = connection.prepareStatement(query)) {
            st.setInt(1, accountID);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt("ManagerID");
            }
        } catch (SQLException e) {
        }
        return -1;
    }

    public List<Student> searchStudents(String tripDate, int routeID, String timePeriod) {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT s.StudentID, s.StudentName, s.Gender, s.DateOfBirth, s.Class, s.Status, "
                + "p.PickTime, p.DropTime, stop.StopName, s.StopID "
                + "FROM Student s "
                + "JOIN Pick p ON s.StudentID = p.StudentID "
                + "JOIN Trip t ON p.TripID = t.TripID "
                + "JOIN Stop stop ON p.StopID = stop.StopID "
                + "JOIN Route r ON stop.RouteID = r.RouteID "
                + "WHERE t.TripDate = ? AND t.TimePeriod = ? AND r.RouteID = ?";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setDate(1, Date.valueOf(tripDate));
            st.setString(2, timePeriod);
            st.setInt(3, routeID);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Student student = new Student();
                student.setSid(rs.getInt("StudentID"));
                student.setName(rs.getString("StudentName"));
                student.setGender(rs.getString("Gender"));
                student.setDob(rs.getDate("DateOfBirth"));
                student.setClasses(rs.getString("Class"));
                student.setStatus(rs.getString("Status"));
                student.setStopname(rs.getString("StopName"));
                students.add(student);
            }
        } catch (SQLException e) {
        }
        return students;
    }

    public void updateStudentStatus(int studentID, String status) {
        String sql = "UPDATE Student SET status = ? WHERE StudentID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, studentID);
            ps.executeUpdate();
        } catch (SQLException e) {

        }
    }

    public List<Trip> getTripsByManagerId(int managerId) {
        List<Trip> tripList = new ArrayList<>();
        String sql = "SELECT TripID, TripDate, DepartureTime, TimePeriod, DriverID, BusID "
                + "FROM Trip WHERE ManagerID = ?";
        try (
                PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, managerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Trip t = new Trip();
                t.setTripid(rs.getInt("TripID"));
                t.setTripdate(rs.getDate("TripDate"));
                t.setDeparttime(rs.getTime("DepartureTime").toLocalTime());
                t.setTimePeriod(rs.getString("TimePeriod"));
                t.setBusid(rs.getInt("BusID"));
                t.setDid(rs.getInt("DriverID"));
                tripList.add(t);
            }
        } catch (SQLException e) {
        }
        return tripList;
    }

    public String getManagerNameById(int managerID) {
        String managerName = "";
        String query = "SELECT managerName FROM Manager WHERE managerID = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, managerID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                managerName = rs.getString("managerName");
            }
        } catch (SQLException e) {
        }
        return managerName;
    }

    public boolean updateTrip(int tripID, Date newTripDate, Time newDepartureTime, String newTimePeriod, int newDriverID, int newManagerID, int newBusID) {
        String sql = "UPDATE Trip SET TripDate = ?, DepartureTime = ?, TimePeriod = ?, DriverID = ?, ManagerID = ?, BusID = ? WHERE TripID = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setDate(1, newTripDate);
            st.setTime(2, newDepartureTime);
            st.setString(3, newTimePeriod);
            st.setInt(4, newDriverID);
            st.setInt(5, newManagerID);
            st.setInt(6, newBusID);
            st.setInt(7, tripID);
            int rowsUpdated = st.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public List<Trip> searchTrips(Integer managerID, Integer busID, Integer driverID, Date tripDate) {
        List<Trip> trips = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT * FROM Trip WHERE 1=1"); // Bắt đầu với điều kiện luôn đúng

        // Danh sách các tham số động
        List<Object> parameters = new ArrayList<>();

        if (tripDate != null) {
            query.append(" AND TripDate = ?");
            parameters.add(tripDate);
        }

        try (PreparedStatement ps = connection.prepareStatement(query.toString())) {

            // Thiết lập tham số cho PreparedStatement
            for (int i = 0; i < parameters.size(); i++) {
                ps.setObject(i + 1, parameters.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Trip trip = new Trip();
                trip.setTripdate(rs.getDate("TripDate"));
                trip.setDeparttime(rs.getTime("DepartureTime").toLocalTime());
                trip.setTimePeriod(rs.getString("TimePeriod"));
                trip.setDid(rs.getInt("DriverID"));
                trip.setMid(rs.getInt("ManagerID"));
                trip.setBusid(rs.getInt("BusID"));

                trips.add(trip);
            }

        } catch (SQLException e) {
        }
        return trips;
    }

    public void addManager(String managerName, String managerPhone, String managerImg, String managerGender, Date managerDob) {
        String query = "INSERT INTO Manager (ManagerName, Phone, Image, Gender, DateOfBirth) "
                + "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, managerName);
            ps.setString(2, managerPhone);
            ps.setString(3, managerImg);
            ps.setString(4, managerGender);
            ps.setDate(5, managerDob);
            ps.executeUpdate();
        } catch (SQLException e) {
        }
    }

    public boolean updatePickTime(int studentId, String tripDate, String timePeriod, Time newPickTime) {
        String sql = "UPDATE p "
                + "SET p.PickTime = ? "
                + "FROM Pick p "
                + "JOIN Trip t ON p.TripID = t.TripID "
                + "JOIN Student s ON p.StudentID = s.StudentID "
                + "WHERE s.StudentID = ? "
                + "AND t.TripDate = ? "
                + "AND t.TimePeriod = ?;";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setTime(1, newPickTime);
            statement.setInt(2, studentId);
            statement.setDate(3, Date.valueOf(tripDate));
            statement.setString(4, timePeriod);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean updateDropTime(int studentId, String tripDate, String timePeriod, Time newDropTime) {
        String sql = "UPDATE p "
                + "SET p.DropTime = ? "
                + "FROM Pick p "
                + "JOIN Trip t ON p.TripID = t.TripID "
                + "JOIN Student s ON p.StudentID = s.StudentID "
                + "WHERE s.StudentID = ? "
                + "AND t.TripDate = ? "
                + "AND t.TimePeriod = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setTime(1, newDropTime);
            statement.setInt(2, studentId);
            statement.setDate(3, Date.valueOf(tripDate));
            statement.setString(4, timePeriod);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public List<Manager> searchManagers(String name, String phone, String gender, Date dob) {
        List<Manager> managers = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM Manager WHERE 1=1");

        if (name != null && !name.isEmpty()) {
            sql.append(" AND ManagerName LIKE ?");
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
                Manager manager = new Manager();
                manager.setId(rs.getInt("ManagerID"));
                manager.setName(rs.getString("ManagerName"));
                manager.setPhone(rs.getString("Phone"));
                manager.setGender(rs.getString("Gender"));
                manager.setDob(rs.getDate("DateOfBirth"));
                manager.setImg(rs.getString("Image"));
                manager.setAccid(rs.getInt("AccountID"));
                managers.add(manager);
            }
        } catch (SQLException e) {
        }
        return managers;
    }

    public static void main(String[] args) {
        ManagerDAO m = new ManagerDAO();
        List<Student> s = m.searchStudents("2024-09-03", 2, "AM");
        for (Student student : s) {
            System.out.println(student.getName());
        }

    }
}
