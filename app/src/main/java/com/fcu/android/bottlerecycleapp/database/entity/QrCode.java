package com.fcu.android.bottlerecycleapp.database.entity;

public class QrCode {
    private int qrCodeId;
    private String qrCodeName;
    private String bankNumber;
    private String bankAccount;
    private double monthlyDonation;
    private double totalDonation;

    public QrCode(int qrCodeId, String qrCodeName, String bankNumber, String bankAccount,
                  double monthlyDonation, double totalDonation) {
        this.qrCodeId = qrCodeId;
        this.qrCodeName = qrCodeName;
        this.bankNumber = bankNumber;
        this.bankAccount = bankAccount;
        this.monthlyDonation = monthlyDonation;
        this.totalDonation = totalDonation;
    }

    public QrCode() {
        this.totalDonation = 0.0;
        this.monthlyDonation = 0.0;
    }

    public int getQrCodeId() {
        return qrCodeId;
    }

    public void setQrCodeId(int qrCodeId) {
        this.qrCodeId = qrCodeId;
    }

    public String getQrCodeName() {
        return qrCodeName;
    }

    public void setQrCodeName(String qrCodeName) {
        this.qrCodeName = qrCodeName;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public double getMonthlyDonation() {
        return monthlyDonation;
    }

    public void setMonthlyDonation(double monthlyDonation) {
        this.monthlyDonation = monthlyDonation;
    }

    public double getTotalDonation() {
        return totalDonation;
    }

    public void setTotalDonation(double totalDonation) {
        this.totalDonation = totalDonation;
    }
}
