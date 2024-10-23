package com.fcu.android.bottlerecycleapp.database.entity;

import java.io.Serializable;

public class ActivityItem implements Serializable {
    private String userName;
    private String userTag;
    private int activityId;
    private String activityName;
    private int activityAchievement;
    private int activityGoal;

    public ActivityItem() {

    }

    public ActivityItem(String userName, String userTag, int activityId, String activityName, int activityAchievement, int activityGoal) {
        this.userName = userName;
        this.userTag = userTag;
        this.activityId = activityId;
        this.activityName = activityName;
        this.activityAchievement = activityAchievement;
        this.activityGoal = activityGoal;
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
