package com.fcu.android.bottlerecycleapp.database;

public class User {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private String earnMoney;
    private String donateMoney;

    public User(String id, String firstName, String lastName, String email, String password, String phoneNumber, String earnMoney, String donateMoney) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
