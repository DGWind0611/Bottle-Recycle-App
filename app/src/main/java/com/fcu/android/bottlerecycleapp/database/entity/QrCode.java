package com.fcu.android.bottlerecycleapp.database.entity;

public class QrCode {
    private int qrCodeId;
    private String qrCodeText;
    private int userId;
    private String qrCodeDate;

    public QrCode() {
    }

    public QrCode(int qrCodeId, String qrCodeText, int userId, String qrCodeDate) {
        this.qrCodeId = qrCodeId;
        this.qrCodeText = qrCodeText;
        this.userId = userId;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getQrCodeDate() {
        return qrCodeDate;
    }

    public void setQrCodeDate(String qrCodeDate) {
        this.qrCodeDate = qrCodeDate;
    }
}
