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
public class Student {

    private int sid;
    private String status, img, name, gender;
    private Date dob;
    private String classes;
    private String stopname;
    private int  accid;

    public Student() {
    }

    public Student(int sid, String status, String img, String name, String gender, Date dob, String classes, String stopname, int accid) {
        this.sid = sid;
        this.status = status;
        this.img = img;
        this.name = name;
        this.gender = gender;
        this.dob = dob;
        this.classes = classes;
        this.stopname = stopname;
        this.accid = accid;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getStopname() {
        return stopname;
    }

    public void setStopname(String stopname) {
        this.stopname = stopname;
    }

    public int getAccid() {
        return accid;
    }

    public void setAccid(int accid) {
        this.accid = accid;
    }

    @Override
    public String toString() {
        return "Student{" + "sid=" + sid + ", status=" + status + ", img=" + img + ", name=" + name + ", gender=" + gender + ", dob=" + dob + ", classes=" + classes + ", stopname=" + stopname + ", accid=" + accid + '}';
    }

    
    
    
}
