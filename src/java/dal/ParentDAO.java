/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.util.List;
import model.Parent;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author DIEN MAY XANH
 */
public class ParentDAO extends DBContext {

    public List<Parent> getParentByAccId(String id) {
        List<Parent> list = new ArrayList<>();
        String sql = "select * from Parent where AccountID = " + id;

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Parent o = new Parent(rs.getInt(1),
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

    public void insert(Parent parent) {
        String sql = "INSERT INTO [dbo].[Parent]\n"
                + "           ([Fullname]\n"
                + "           ,[Email]\n"
                + "           ,[Phone]\n"
                + "           ,[Gender]\n"
                + "           ,[DateOfBirth]\n"
                + "           ,[Role]\n"
                + "           ,[Image]\n"
                + "           ,[AccountID])\n"
                + "     VALUES\n"
                + "           (?,?,?,?,?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, parent.getName());
            ps.setString(2, parent.getEmail());
            ps.setString(3, parent.getPhone());
            ps.setString(4, parent.getGender());
            ps.setDate(5, parent.getDob());
            ps.setString(6, parent.getRole());
            ps.setString(7, parent.getImg());
            ps.setInt(8, parent.getAccid());

            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void update(Parent parent) {
        String sql = "UPDATE [dbo].[Parent] SET "
                + "[Fullname] = ?, "
                + "[Email] = ?, "
                + "[Phone] = ?, "
                + "[Gender] = ?, "
                + "[DateOfBirth] = ?, "
                + "[Role] = ?, "
                + "[Image] = ? "
                + "WHERE [ParentID] = ?"; 

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, parent.getName());
            ps.setString(2, parent.getEmail());
            ps.setString(3, parent.getPhone());
            ps.setString(4, parent.getGender());
            ps.setDate(5, parent.getDob());
            ps.setString(6, parent.getRole());
            ps.setString(7, parent.getImg());
            ps.setInt(8, parent.getPid()); 

            ps.executeUpdate(); // Thực hiện cập nhật dữ liệu

        } catch (SQLException ex) {
            ex.printStackTrace(); // In ra lỗi nếu có
        }
    }

    public static void main(String[] args) {
        ParentDAO p = new ParentDAO();
        List<Parent> l = p.getParentByAccId("12");
        for (Parent parent : l) {
            System.out.println(parent.getName());
        }
    }
}
