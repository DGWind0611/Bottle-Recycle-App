package com.fcu.android.bottlerecycleapp.database;

import java.io.Serializable;

public class MyActivity implements Serializable {
    private int id;
    private String activityName;
    private String activityDescription;
    private String activityStartTime;
    private String activityEndTime;
    private int activityGoal;

    public MyActivity(int id, String activityName, String activityDescription, String activityStartTime, String activityEndTime, int activityGoal) {
        this.id = id;
        this.activityName = activityName;
        this.activityDescription = activityDescription;
        this.activityStartTime = activityStartTime;
        this.activityEndTime = activityEndTime;
        this.activityGoal = activityGoal;
    }

    public MyActivity() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityDescription() {
        return activityDescription;
    }

    public void setActivityDescription(String activityDescription) {
        this.activityDescription = activityDescription;
    }

    public String getActivityStartTime() {
        return activityStartTime;
    }

    public void setActivityStartTime(String activityStartTime) {
        this.activityStartTime = activityStartTime;
    }

    public String getActivityEndTime() {
        return activityEndTime;
    }

    public void setActivityEndTime(String activityEndTime) {
        this.activityEndTime = activityEndTime;
    }

    public int getActivityGoal() {
        return activityGoal;
    }

    public void setActivityGoal(int activityGoal) {
        this.activityGoal = activityGoal;
    }
}
