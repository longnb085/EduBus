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
public class Attend {
    private int aid, sid;
    private String name;
    private Date ad;
    private String tp;
    private Time pe, de, pt, dt;
    private String pname, status, attend;

    public Attend() {
    }

    public Attend(int aid, int sid, String name, Date ad, String tp, Time pe, Time de, Time pt, Time dt, String pname, String status, String attend) {
        this.aid = aid;
        this.sid = sid;
        this.name = name;
        this.ad = ad;
        this.tp = tp;
        this.pe = pe;
        this.de = de;
        this.pt = pt;
        this.dt = dt;
        this.pname = pname;
        this.status = status;
        this.attend = attend;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getAd() {
        return ad;
    }

    public void setAd(Date ad) {
        this.ad = ad;
    }

    public String getTp() {
        return tp;
    }

    public void setTp(String tp) {
        this.tp = tp;
    }

    public Time getPe() {
        return pe;
    }

    public void setPe(Time pe) {
        this.pe = pe;
    }

    public Time getDe() {
        return de;
    }

    public void setDe(Time de) {
        this.de = de;
    }

    public Time getPt() {
        return pt;
    }

    public void setPt(Time pt) {
        this.pt = pt;
    }

    public Time getDt() {
        return dt;
    }

    public void setDt(Time dt) {
        this.dt = dt;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAttend() {
        return attend;
    }

    public void setAttend(String attend) {
        this.attend = attend;
    }

    @Override
    public String toString() {
        return "Attend{" + "aid=" + aid + ", sid=" + sid + ", name=" + name + ", ad=" + ad + ", tp=" + tp + ", pe=" + pe + ", de=" + de + ", pt=" + pt + ", dt=" + dt + ", pname=" + pname + ", status=" + status + ", attend=" + attend + '}';
    }

   
    

}
