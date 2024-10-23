package com.fcu.android.bottlerecycleapp.database.entity;

public class RecycleRecord {
    private int recycleRecordId;
    private String userName;
    private String userTag;
    private int recycleStationId;
    private String recycleTime;
    private Double recycleWeight;



    public RecycleRecord() {

    }

    public RecycleRecord(int recycleRecordId, String userName, String userTag, int recycleStationId, String recycleTime, Double recycleWeight) {
        this.recycleRecordId = recycleRecordId;
        this.userName = userName;
        this.userTag = userTag;
        this.recycleStationId = recycleStationId;
        this.recycleTime = recycleTime;
        this.recycleWeight = recycleWeight;
    }

    public int getRecycleRecordId() {
        return recycleRecordId;
    }

    public void setRecycleRecordId(int recycleRecordId) {
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
}
