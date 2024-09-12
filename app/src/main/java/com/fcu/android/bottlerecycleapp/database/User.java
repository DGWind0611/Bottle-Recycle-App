package com.fcu.android.bottlerecycleapp.database;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String userName;
    private String email;
    private String password;
    private String phoneNumber;
    private Double earnMoney;
    private String qrCode;
    private Double donateMoney;
    private String userImage;


    public User(int id, String userName, String email, String password, String phoneNumber,
                Double earnMoney, String qrCode, Double donateMoney, String userImage) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.earnMoney = earnMoney;
        this.qrCode = qrCode;
        this.donateMoney = donateMoney;
        this.userImage = userImage;
    }

    public User(){

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Double getEarnMoney() {
        return earnMoney;
    }

    public void setEarnMoney(Double earnMoney) {
        this.earnMoney = earnMoney;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public Double getDonateMoney() {
        return donateMoney;
    }

    public void setDonateMoney(Double donateMoney) {
        this.donateMoney = donateMoney;
    }
}
