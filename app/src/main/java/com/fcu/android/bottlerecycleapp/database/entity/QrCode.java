package com.fcu.android.bottlerecycleapp.database.entity;

public class QrCode {
    private int qrCodeId;
    private String qrCodeText;
    private String userName;
    private String userTag;
    private String qrCodeDate;

    public QrCode() {
    }

    public QrCode(int qrCodeId, String qrCodeText, String userName, String userTag, String qrCodeDate) {
        this.qrCodeId = qrCodeId;
        this.qrCodeText = qrCodeText;
        this.userName = userName;
        this.userTag = userTag;
        this.qrCodeDate = qrCodeDate;
    }

    public int getQrCodeId() {
        return qrCodeId;
    }

    public void setQrCodeId(int qrCodeId) {
        this.qrCodeId = qrCodeId;
    }

    public String getQrCodeText() {
        return qrCodeText;
    }

    public void setQrCodeText(String qrCodeText) {
        this.qrCodeText = qrCodeText;
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

    public String getQrCodeDate() {
        return qrCodeDate;
    }

    public void setQrCodeDate(String qrCodeDate) {
        this.qrCodeDate = qrCodeDate;
    }
}
