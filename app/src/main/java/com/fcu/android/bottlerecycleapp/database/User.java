package com.fcu.android.bottlerecycleapp.database;

public class User {
    private String id;
    private String userName;
    private String email;
    private String password;
    private String phoneNumber;
    private String earnMoney;
    private String donateMoney;

    public User(String userName, String email, String password, String phoneNumber, String earnMoney, String donateMoney) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.earnMoney = earnMoney;
        this.donateMoney = donateMoney;
    }
    public User(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserName() {
        return userName;
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

    public String getEarnMoney() {
        return earnMoney;
    }

    public void setEarnMoney(String earnMoney) {
        this.earnMoney = earnMoney;
    }

    public String getDonateMoney() {
        return donateMoney;
    }

    public void setDonateMoney(String donateMoney) {
        this.donateMoney = donateMoney;
    }
}
