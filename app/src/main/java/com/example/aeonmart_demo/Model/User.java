package com.example.aeonmart_demo.Model;

public class User {
    private String name;
    private String birth;
    private String email;
    private String phone;
    private String cccd;
    private String address;
    private String password;


    // Constructor
    public User(String name, String birth, String email,String password, String phone, String cccd, String address) {
        this.name = name;
        this.birth = birth;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.cccd = cccd;
        this.address = address;
    }
    public User() {

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
