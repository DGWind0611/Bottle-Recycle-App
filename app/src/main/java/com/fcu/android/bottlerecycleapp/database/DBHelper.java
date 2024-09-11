package com.fcu.android.bottlerecycleapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.Date;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "bottle_recycle.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_USER = "User";
    private static final String TABLE_STAFF = "Staff";
    private static final String TABLE_DONATION_RECORD = "Donation_Record";
    private static final String TABLE_NOTIFICATIONS = "Notifications";
    private static final String TABLE_QR_CORD = "QR_Cord";
    private static final String TABLE_QR_CORD_AGENCY = "QR_Cord_Agency";
    private static final String TABLE_RECYCLE_STATION = "RecycleStation";
    private static final String TABLE_REMITTANCE_RECORD = "Remittance_Record";
    private static final String TABLE_STATION_FIX_RECORD = "Station_Fix_Record";
    private static final String TABLE_USER_RECYCLE_RECORD = "User_recycle_Record";



    public DBHelper(@NonNull Context context, @NonNull String name, @NonNull CursorFactory factory,
                    int version) {
        super(context, DB_NAME, null, DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // 重新建立資料表
        createTableUser(db);
        createTableStaff(db);
        createTableDonationRecord(db);
        createTableNotifications(db);
        createTableQrCord(db);
        createTableQrCordAgency(db);
        createTableRecycleStation(db);
        createTableRemittanceRecord(db);
        createTableStationFixRecord(db);
        createTableUserRecycleRecord(db);
    }


    private void createTableUser(@NonNull SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_USER + " ("
                + "User_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "User_Name TEXT, "
                + "E_mail TEXT, "
                + "Password TEXT, "
                + "Phone_Number TEXT, "
                + "Earn_Money REAL, "
                + "Donate_Money REAL)";
        db.execSQL(sql);
    }

    private void createTableStaff(@NonNull SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_STAFF + " ("
                + "Staff_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "Staff_Name TEXT, "
                + "E_mail TEXT, "
                + "Password TEXT, "
                + "Phone_Number TEXT)";
        db.execSQL(sql);
    }

    private void createTableDonationRecord(@NonNull SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_DONATION_RECORD + " ("
                + "Donation_Record_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "User_ID INTEGER, "
                + "Donate_Money REAL, "
                + "Donate_Date TEXT, "
                + "FOREIGN KEY(User_ID) REFERENCES User(User_ID))";
        db.execSQL(sql);
    }

    private void createTableNotifications(@NonNull SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NOTIFICATIONS + " ("
                + "Notification_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "User_ID INTEGER, "
                + "Notification_Content TEXT, "
                + "Notification_Date TEXT, "
                + "FOREIGN KEY(User_ID) REFERENCES User(User_ID))";
        db.execSQL(sql);
    }

    private void createTableQrCord(@NonNull SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_QR_CORD + " ("
                + "QR_Cord_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "User_ID TEXT, "
                + "FOREIGN KEY(User_ID) REFERENCES User(User_ID))";
        db.execSQL(sql);
    }

    private void createTableQrCordAgency(@NonNull SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_QR_CORD_AGENCY + " ("
                + "QR_Cord_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "Agency_Name TEXT, "
                + "QR_CODE_ORIGIN_Name INTEGER, "
                + "User_Custom_Name TEXT )";
        db.execSQL(sql);
    }

    private void createTableRecycleStation(@NonNull SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_RECYCLE_STATION + " ("
                + "RecycleStation_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "RecycleStation_Name TEXT, "
                + "RecycleStation_Address TEXT, "
                + "RecycleStation_Latitude REAL, "
                + "RecycleStation_Longitude REAL)";
        db.execSQL(sql);
    }

    private void createTableRemittanceRecord(@NonNull SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_REMITTANCE_RECORD + " ("
                + "Remittance_Record_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "User_ID INTEGER, "
                + "Remittance_Money REAL, "
                + "Remittance_Date TEXT, "
                + "FOREIGN KEY(User_ID) REFERENCES User(User_ID))";
        db.execSQL(sql);
    }

    private void createTableStationFixRecord(@NonNull SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_STATION_FIX_RECORD + " ("
                + "Station_Fix_Record_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "RecycleStation_ID INTEGER, "
                + "Fix_Date TEXT, "
                + "Fix_Content TEXT, "
                + "FOREIGN KEY(RecycleStation_ID) REFERENCES RecycleStation(RecycleStation_ID))";
        db.execSQL(sql);
    }

    private void createTableUserRecycleRecord(@NonNull SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_USER_RECYCLE_RECORD + " ("
                + "User_Recycle_Record_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "User_ID INTEGER, "
                + "RecycleStation_ID INTEGER, "
                + "Recycle_Date TEXT, "
                + "Recycle_Weight REAL, "
                + "FOREIGN KEY(User_ID) REFERENCES User(User_ID), "
                + "FOREIGN KEY(RecycleStation_ID) REFERENCES RecycleStation(RecycleStation_ID))";
        db.execSQL(sql);
    }

    // User Table Add Data
    public boolean addUser(User user) {
        SQLiteDatabase db = null;
        try {
            db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("User_Name", user.getUserName());
            values.put("E_mail", user.getEmail());
            values.put("Password", user.getPassword());
            values.put("Phone_Number", user.getPhoneNumber());
            values.put("Earn_Money", user.getEarnMoney());
            values.put("Donate_Money", user.getDonateMoney());

            long result = db.insert(TABLE_USER, null, values);
            return result != -1;
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    /**
     * 透過使用者名稱尋找使用者
     *
     * @param userName 使用者名稱
     * @return 使用者資料
     */
    public Cursor findUserByUserName(String userName) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM User WHERE User_Name = ?";
        return db.rawQuery(query, new String[]{userName});
    }

    public Cursor findUserIdByUserName(String userName) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT User_ID FROM User WHERE User_Name = ?";
        return db.rawQuery(query, new String[]{userName});
    }

    /**
     * 新增QE CODE
     */
    public void createQRCode(int userId) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM QR_Cord";
        Cursor cursor = db.rawQuery(query, null);
        int length = cursor.getCount();
        StringBuilder qrCode = new StringBuilder();
        Date date = new Date();
        qrCode.append(date.getTime());
        qrCode.append(length);
        cursor.close();  // 關閉 Cursor

        ContentValues values = new ContentValues();
        values.put("QR_Cord_ID", qrCode.toString());
        values.put("User_ID", userId);
        db.insert(TABLE_QR_CORD, null, values);
        db.close();  // 在最後才關閉資料庫
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // onUpgrade 則是如果資料庫結構有改變了就會觸發 onUpgrade
    }
    /** 檢查電子郵件是否已經註冊過
     *
     * @param email 電子郵件
     * @return 是否已經註冊過
     */
    public boolean checkEmail(String email) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM User WHERE E_mail = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email});
        boolean result = cursor.getCount() == 0;
        cursor.close();
        db.close();
        return result;
    }
    /** 檢查手機號碼是否已經註冊過
     *
     * @param phoneNumber 手機號碼
     * @return 是否已經註冊過
     */
    public boolean checkPhoneNumber(String phoneNumber) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM User WHERE Phone_Number = ?";
        Cursor cursor = db.rawQuery(query, new String[]{phoneNumber});
        boolean result = cursor.getCount() == 0;
        cursor.close();
        db.close();
        return result;
    }
}
