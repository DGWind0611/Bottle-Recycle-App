package com.fcu.android.bottlerecycleapp.database;

import java.io.Serializable;

public class ActivityItem implements Serializable {
    private int userId;
    private int activityId;
    private String activityName;
    private int activityAchievement;
    private int activityGoal;

    public ActivityItem(int userId,int activityId, String activityName, int activityAchievement, int activityGoal) {
        this.userId = userId;
        this.activityId = activityId;
        this.activityName = activityName;
        this.activityAchievement = activityAchievement;
        this.activityGoal = activityGoal;
    }

    public ActivityItem() {

    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public int getActivityAchievement() {
        return activityAchievement;
    }

    public void setActivityAchievement(int activityAchievement) {
        this.activityAchievement = activityAchievement;
    }

    public int getActivityGoal() {
        return activityGoal;
    }

    public void setActivityGoal(int activityGoal) {
        this.activityGoal = activityGoal;
    }
}
