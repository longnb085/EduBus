/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author DIEN MAY XANH
 */
public class User {

    private int accid;
    private String name, phone, role, img, username;
    private String status;

    public User() {
    }

    public User(int accid, String name, String phone, String role, String img, String username, String status) {
        this.accid = accid;
        this.name = name;
        this.phone = phone;
        this.role = role;
        this.img = img;
        this.username = username;
        this.status = status;
    }

    public int getAccid() {
        return accid;
    }

    public void setAccid(int accid) {
        this.accid = accid;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "User{" + "accid=" + accid + ", name=" + name + ", phone=" + phone + ", role=" + role + ", img=" + img + ", username=" + username + ", status=" + status + '}';
    }

}
