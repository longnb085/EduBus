/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.*;

/**
 *
 * @author DIEN MAY XANH
 */
public class StatisticDAO extends DBContext {

    public int getNumOfParent() {
        int num = 0;
        String sql = " select count(*) from Parent";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                num = rs.getInt(1);
            }
        } catch (SQLException ex) {
        }
        return num;
    }

    public int getNumOfStudent() {
        int num = 0;
        String sql = "select count(*) from Student";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                num = rs.getInt(1);
            }
        } catch (SQLException ex) {
        }
        return num;
    }

    public int getNumOfDriver() {
        int num = 0;
        String sql = "select count(*) from Driver";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                num = rs.getInt(1);
            }
        } catch (SQLException ex) {
        }
        return num;
    }

    public int getNumOfManager() {
        int num = 0;
        String sql = "select count(*) from Manager";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                num = rs.getInt(1);
            }
        } catch (SQLException ex) {
        }
        return num;
    }

    public int getNumOfBus() {
        int num = 0;
        String sql = "select count(*) from Bus";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                num = rs.getInt(1);
            }
        } catch (SQLException ex) {
        }
        return num;
    }

    public int getNumOfStop() {
        int num = 0;
        String sql = "select count(*) from Stop";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                num = rs.getInt(1);
            }
        } catch (SQLException ex) {
        }
        return num;
    }

    public static void main(String[] args) {
        StatisticDAO s = new StatisticDAO();
        System.out.println(s.getNumOfParent());
    }
}
