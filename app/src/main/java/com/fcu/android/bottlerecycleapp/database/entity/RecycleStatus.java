package com.fcu.android.bottlerecycleapp.database.entity;

public class RecycleStatus {
    private String userName;
    private String userTag;
    private int recycleTimes;
    private double totalWeight;
    private double totalMoney;
    private double totalDonation;
    private double totalCarbonReduction;

    public RecycleStatus(String userName, String userTag, int recycleTimes, double totalWeight, double totalMoney, double totalDonation, double totalCarbonReduction) {
        this.userName = userName;
        this.userTag = userTag;
        this.recycleTimes = recycleTimes;
        this.totalWeight = totalWeight;
        this.totalMoney = totalMoney;
        this.totalDonation = totalDonation;
        this.totalCarbonReduction = totalCarbonReduction;
    }

    public RecycleStatus() {
        this.recycleTimes = 0;
        this.totalWeight = 0.0;
        this.totalMoney = 0.0;
        this.totalDonation = 0.0;
        this.totalCarbonReduction = 0.0;
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

    public int getRecycleTimes() {
        return recycleTimes;
    }

    public void setRecycleTimes(int recycleTimes) {
        this.recycleTimes = recycleTimes;
    }

    public double getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public double getTotalDonation() {
        return totalDonation;
    }

    public void setTotalDonation(double totalDonation) {
        this.totalDonation = totalDonation;
    }

    public double getTotalCarbonReduction() {
        return totalCarbonReduction;
    }

    public void setTotalCarbonReduction(double totalCarbonReduction) {
        this.totalCarbonReduction = totalCarbonReduction;
    }
}
