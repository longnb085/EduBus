/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalTime;
import java.util.Date;

/**
 *
 * @author DIEN MAY XANH
 */
public class Trip {

    private int tripid;
    private Date tripdate;
    private LocalTime departtime;
    private String timePeriod;
    private int did, mid, busid;

    public Trip() {
    }

    public Trip(int tripid, Date tripdate, LocalTime departtime, String timePeriod, int did, int mid, int busid) {
        this.tripid = tripid;
        this.tripdate = tripdate;
        this.departtime = departtime;
        this.timePeriod = timePeriod;
        this.did = did;
        this.mid = mid;
        this.busid = busid;
    }

    public int getTripid() {
        return tripid;
    }

    public void setTripid(int tripid) {
        this.tripid = tripid;
    }

    public Date getTripdate() {
        return tripdate;
    }

    public void setTripdate(Date tripdate) {
        this.tripdate = tripdate;
    }

    public LocalTime getDeparttime() {
        return departtime;
    }

    public void setDeparttime(LocalTime departtime) {
        this.departtime = departtime;
    }

    public String getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(String timePeriod) {
        this.timePeriod = timePeriod;
    }

    public int getDid() {
        return did;
    }

    public void setDid(int did) {
        this.did = did;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public int getBusid() {
        return busid;
    }

    public void setBusid(int busid) {
        this.busid = busid;
    }

   
}
