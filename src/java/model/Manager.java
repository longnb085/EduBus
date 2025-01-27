/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.*;

/**
 *
 * @author DIEN MAY XANH
 */
public class Manager {
    private int id;
    private String name, phone,  gender;
    private Date dob;
    private String img;
    private int accid;
    
    public Manager() {
    }

    public Manager(int id, String name, String phone, String gender, Date dob, String img, int accid) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.gender = gender;
        this.dob = dob;
        this.img = img;
        this.accid = accid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    
  
    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getAccid() {
        return accid;
    }

    public void setAccid(int accid) {
        this.accid = accid;
    }

    @Override
    public String toString() {
        return "Manager{" + "id=" + id + ", name=" + name + ", phone=" + phone + ", gender=" + gender + ", dob=" + dob + ", img=" + img + ", accid=" + accid + '}';
    }

  
    
    
    
}
