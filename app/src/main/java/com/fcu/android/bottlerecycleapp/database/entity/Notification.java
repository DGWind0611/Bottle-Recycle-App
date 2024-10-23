package com.fcu.android.bottlerecycleapp.database.entity;

public class Notification {
    private int notificationId;
    private String UserName;
    private String UserTag;
    private String title;
    private String content;
    private String date;
    private String time;
    private Type type;

    public Notification(int notificationId, String userName, String userTag, String title, String content, String date, String time, Type type) {
        this.notificationId = notificationId;
        UserName = userName;
        UserTag = userTag;
        this.title = title;
        this.content = content;
        this.date = date;
        this.time = time;
        this.type = type;
    }

    public Notification() {

    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserTag() {
        return UserTag;
    }

    public void setUserTag(String userTag) {
        UserTag = userTag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
