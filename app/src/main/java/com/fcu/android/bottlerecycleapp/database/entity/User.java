package com.fcu.android.bottlerecycleapp.database.entity;

import java.io.Serializable;

public class User implements Serializable {
    private String userName;
    private String userTag;
    private String email;
    private String password;
    private String phoneNumber;
    private Double earnMoney;
    private String qrCode;
    private Double donateMoney;
    private Gender gender;
    private Role role;
    private String userImage;


    public User(String userName, String userTag, String email, String password, String phoneNumber,
                Double earnMoney, String qrCode, Double donateMoney, Gender gender, Role role,
                String userImage) {
        this.userName = userName;
        this.userTag = userTag;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.earnMoney = earnMoney;
        this.qrCode = qrCode;
        this.donateMoney = donateMoney;
        this.gender = gender;
        this.role = role;
        this.userImage = userImage;
    }

    public User() {

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserTag() {
        return userTag;
    }

    public void setUserTag(String userTag) {
        this.userTag = userTag;
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

    public Double getDonateMoney() {
        return donateMoney;
    }

    public void setDonateMoney(Double donateMoney) {
        this.donateMoney = donateMoney;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
