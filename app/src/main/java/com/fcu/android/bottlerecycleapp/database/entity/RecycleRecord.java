package com.fcu.android.bottlerecycleapp.database.entity;

public class RecycleRecord {
    private String recycleRecordId;
    private String userName;
    private String userTag;
    private int recycleStationId;
    private String recycleTime;
    private Double recycleWeight;
    private Double earnMoney;


    public RecycleRecord() {

    }

    public RecycleRecord(String recycleRecordId, String userName, String userTag, int recycleStationId,
                         String recycleTime, Double recycleWeight, Double earnMoney) {
        this.recycleRecordId = recycleRecordId;
        this.userName = userName;
        this.userTag = userTag;
        this.recycleStationId = recycleStationId;
        this.recycleTime = recycleTime;
        this.recycleWeight = recycleWeight;
        this.earnMoney = earnMoney;

    }

    public RecycleRecord(String userName, String userTag, int recycleStationId, String recycleDate, Double recycleWeight, Double earnMoney) {
        this.userName = userName;
        this.userTag = userTag;
        this.recycleStationId = recycleStationId;
        this.recycleTime = recycleDate;
        this.recycleWeight = recycleWeight;
        this.earnMoney = earnMoney;
    }

    public String getRecycleRecordId() {
        return recycleRecordId;
    }

    public void setRecycleRecordId(String recycleRecordId) {
        this.recycleRecordId = recycleRecordId;
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

    public int getRecycleStationId() {
        return recycleStationId;
    }

    public void setRecycleStationId(int recycleStationId) {
        this.recycleStationId = recycleStationId;
    }

    public String getRecycleTime() {
        return recycleTime;
    }

    public void setRecycleTime(String recycleTime) {
        this.recycleTime = recycleTime;
    }

    public Double getRecycleWeight() {
        return recycleWeight;
    }

    public void setRecycleWeight(Double recycleWeight) {
        this.recycleWeight = recycleWeight;
    }

    public Double getEarnMoney() {
        return earnMoney;
    }

    public void setEarnMoney(Double earnMoney) {
        this.earnMoney = earnMoney;
    }
}
