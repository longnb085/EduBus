/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.*;

/**
 *
 * @author DIEN MAY XANH
 */
public class StudentDAO extends DBContext {

    public List<Student> getAllStudent() {
        List<Student> list = new ArrayList<>();
        String sql = "select * from Student";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Student o = new Student(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getDate(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getInt(9));
                list.add(o);
            }
        } catch (SQLException ex) {
        }
        return list;
    }

    public List<Student> getStudentByAccid(String accountid) {
        List<Student> list = new ArrayList<>();
        String sql = "select s.StudentID, s.StudentName, s.Image,\n"
                + "s.Class, s.Gender, st.StopName, s.DateOfBirth, s.Status, \n"
                + "s.AccountID\n"
                + "from Student s join Stop st\n"
                + "         on s.StopID = st.StopID\n"
                + "where s.AccountID = " + accountid;

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Student o = new Student(rs.getInt(1),
                        rs.getString(8),
                        rs.getString(3),
                        rs.getString(2),
                        rs.getString(5),
                        rs.getDate(7),
                        rs.getString(4),
                        rs.getString(6),
                        rs.getInt(9));
                list.add(o);
            }
        } catch (SQLException ex) {
        }
        return list;
    }

    public List<Student> getStudentInfo(String accountId) {
        List<Student> list = new ArrayList<>();
        String sql = "select StudentID, Image, StudentName,\n"
                + "Gender, DateOfBirth, Class, AccountID, st.StopName as StopID\n"
                + "from Student s left join Stop st\n"
                + "on s.StopID = st.StopID\n"
                + "where AccountID = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, accountId); // Sử dụng tham số để tránh SQL Injection
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String stopName = rs.getString("StopID"); // Giả sử StopID là trường bạn muốn kiểm tra

                Student o = new Student(
                        rs.getInt("StudentID"),
                        "-", // Giá trị mặc định cho status
                        rs.getString("Image"),
                        rs.getString("StudentName"),
                        rs.getString("Gender"),
                        rs.getDate("DateOfBirth"),
                        rs.getString("Class"),
                        (stopName != null) ? stopName : "-", // Chèn StopID hoặc "-"
                        rs.getInt("AccountID") // Lấy AccountID từ ResultSet
                );
                list.add(o);

            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // In ra lỗi nếu có
        }
        return list;
    }

    public void updateStudent(Student s) {
        String sql = "UPDATE Student SET StudentName = ?, Gender = ?, DateOfBirth = ?, Class = ?, Image = ? WHERE StudentID = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, s.getName());
            ps.setString(2, s.getGender());
            ps.setDate(3, s.getDob());
            ps.setString(4, s.getClasses());
            ps.setString(5, s.getImg());
            ps.setInt(6, s.getSid());

            ps.executeUpdate(); // Thực hiện cập nhật

        } catch (SQLException ex) {
            ex.printStackTrace(); // In ra lỗi nếu có
        }
    }

    public void insert(Student s) {
        String sql = "INSERT INTO [dbo].[Student](\n"
                + "            [Image]\n"
                + "           ,[StudentName]\n"
                + "           ,[Gender]\n"
                + "           ,[DateOfBirth]\n"
                + "           ,[Class]\n"
                + "           ,[AccountID])\n"
                + "     VALUES (?,?,?,?,?,?)";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, s.getImg());
            ps.setString(2, s.getName());
            ps.setString(3, s.getGender());
            ps.setDate(4, s.getDob());
            ps.setString(5, s.getClasses());
            ps.setInt(6, s.getAccid());
            ps.executeUpdate();

        } catch (SQLException ex) {
        }
    }

    public void registerStop(String sid, String stid) {
        String sql = "	update Student \n"
                + "	set StopID = " + stid
                + "	where StudentID = " + sid;

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.executeUpdate();

        } catch (SQLException ex) {
        }
    }

    public void insertAttendance(Attend a) {
        String sql = "insert into Attendance(AttendanceDate, TimePeriod, Status, ParentID, StudentID)\n"
                + "		        values(?,?,?," + a.getPname() + ",?)";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setDate(1, a.getAd());
            ps.setString(2, a.getTp());
            ps.setString(3, a.getAttend());
            ps.setInt(4, a.getSid());
            ps.executeUpdate();
        } catch (SQLException e) {
        }

    }

    public List<Attend> getAttend(String ad, int accid) {
        List<Attend> list = new ArrayList<>();
        String sql = "	WITH a AS (\n"
                + "    SELECT DISTINCT \n"
                + "        s.AccountID,\n"
                + "        a.AttendanceID, \n"
                + "        s.StudentID, \n"
                + "        s.StudentName, \n"
                + "        a.AttendanceDate, \n"
                + "        a.TimePeriod, \n"
                + "        a.PickEstimate, \n"
                + "        a.DropEstimate, \n"
                + "        pr.Fullname, \n"
                + "        a.Status AS Attend,\n"
                + "        s.Status\n"
                + "    FROM \n"
                + "        Student s \n"
                + "    JOIN \n"
                + "        Attendance a ON s.StudentID = a.StudentID\n"
                + "    JOIN \n"
                + "        Parent pr ON a.ParentID = pr.ParentID\n"
                + "    WHERE  \n"
                + "        s.AccountID = "+accid+" \n"
                + "        AND a.AttendanceDate = '"+ad+"'\n"
                + "),\n"
                + "b AS (\n"
                + "    SELECT \n"
                + "        p.PickTime, \n"
                + "        p.DropTime, \n"
                + "        st.AccountID, \n"
                + "		p.StudentID\n"
                + "    FROM \n"
                + "        Trip t \n"
                + "    JOIN \n"
                + "        Pick p ON t.TripID = p.TripID\n"
                + "    JOIN \n"
                + "        Student st ON p.StudentID = st.StudentID\n"
                + "    WHERE \n"
                + "        t.TripDate = '"+ad+"' \n"
                + "        AND st.AccountID = "+accid+"\n"
                + ")\n"
                + "SELECT DISTINCT \n"
                + "    a.AttendanceID, \n"
                + "    a.StudentID, \n"
                + "    a.StudentName, \n"
                + "    a.AttendanceDate,\n"
                + "    a.TimePeriod, \n"
                + "    a.PickEstimate, \n"
                + "    a.DropEstimate, \n"
                + "    b.PickTime, \n"
                + "    b.DropTime,\n"
                + "    a.Fullname, \n"
                + "    a.Status, \n"
                + "    a.Attend\n"
                + "FROM \n"
                + "    a \n"
                + "LEFT JOIN \n"
                + "    b ON a.AccountID = b.AccountID and a.StudentID = b.StudentID;";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Attend o = new Attend(rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getDate(4),
                        rs.getString(5),
                        rs.getTime(6),
                        rs.getTime(7),
                        rs.getTime(8),
                        rs.getTime(9),
                        rs.getString(10),
                        rs.getString(11),
                        rs.getString(12));
                list.add(o);
            }
        } catch (SQLException ex) {

        }
        return list;
    }

    public void deleteAttend(String aid) {

        String sql = " delete from Attendance where AttendanceID = " + aid;

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Loi");
        }
    }

    public static void main(String[] args) {
        StudentDAO d = new StudentDAO();
        Date cur = new Date(System.currentTimeMillis());

        Attend a = new Attend(0,
                1,
                "",
                cur,
                "AM",
                null,
                null,
                null,
                null,
                1 + "",
                "on bus",
                 "Absent");
        d.insertAttendance(a);
    }
}
