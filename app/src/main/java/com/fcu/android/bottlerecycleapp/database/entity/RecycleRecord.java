package com.fcu.android.bottlerecycleapp.database.entity;

public class RecycleRecord {
    private int recycleRecordId;
    private int userId;
    private int recycleStationId;
    private String recycleTime;
    private Double recycleWeight;

    public RecycleRecord(int recycleRecordId, int userId, int recycleStationId, String recycleTime, Double recycleWeight) {
        this.recycleRecordId = recycleRecordId;
        this.userId = userId;
        this.recycleStationId = recycleStationId;
        this.recycleTime = recycleTime;
        this.recycleWeight = recycleWeight;
    }

    public RecycleRecord() {

    }

    public int getRecycleRecordId() {
        return recycleRecordId;
    }

    public void setRecycleRecordId(int recycleRecordId) {
        this.recycleRecordId = recycleRecordId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
