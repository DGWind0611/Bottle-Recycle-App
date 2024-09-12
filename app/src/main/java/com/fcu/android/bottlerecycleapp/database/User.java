package com.fcu.android.bottlerecycleapp.database;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String userName;
    private String email;
    private String password;
    private String phoneNumber;
    private Double earnMoney;
    private String QrCode;
    private Double donateMoney;


    public User(int id, String userName, String email, String password, String phoneNumber,
                Double earnMoney, String qrCode, Double donateMoney) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.earnMoney = earnMoney;
        QrCode = qrCode;
        this.donateMoney = donateMoney;
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
        return QrCode;
    }

    public void setQrCode(String qrCode) {
        QrCode = qrCode;
    }

    public Double getDonateMoney() {
        return donateMoney;
    }

    public void setDonateMoney(Double donateMoney) {
        this.donateMoney = donateMoney;
    }
}
