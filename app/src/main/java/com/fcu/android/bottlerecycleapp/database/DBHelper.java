package com.fcu.android.bottlerecycleapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fcu.android.bottlerecycleapp.Gender;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "bottle_recycle.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_USER = "User";
    private static final String TABLE_STAFF = "Staff";
    private static final String TABLE_DONATION_RECORD = "Donation_Record";
    private static final String TABLE_NOTIFICATIONS = "Notifications";
    private static final String TABLE_QR_CORD_AGENCY = "QR_Cord_Agency";
    private static final String TABLE_RECYCLE_STATION = "RecycleStation";
    private static final String TABLE_REMITTANCE_RECORD = "Remittance_Record";
    private static final String TABLE_STATION_FIX_RECORD = "Station_Fix_Record";
    private static final String TABLE_USER_RECYCLE_RECORD = "User_recycle_Record";


    public DBHelper(@NonNull Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // 重新建立資料表
        createTableUser(db);
        createTableStaff(db);
        createTableDonationRecord(db);
        createTableNotifications(db);
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
                + "E_mail TEXT NOT NULL UNIQUE, "
                + "Password TEXT NOT NULL, "
                + "Phone_Number TEXT NOT NULL UNIQUE, "
                + "Earn_Money REAL, "
                + "QR_Code String, "
                + "Donate_Money REAL, "
                + "Gender TEXT, "
                + "Role TEXT, "
                + "UserImage TEXT ) ";
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

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // onUpgrade 則是如果資料庫結構有改變了就會觸發 onUpgrade
    }

    /**
     * 新增用戶
     * @param user 用戶
     * @return 是否新增成功
     */
    public boolean addUser(User user) {
        try (SQLiteDatabase db = getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put("User_Name", user.getUserName());
            values.put("E_mail", user.getEmail());
            values.put("Password", user.getPassword());
            values.put("Phone_Number", user.getPhoneNumber());
            values.put("Earn_Money", user.getEarnMoney());
            values.put("QR_Code", user.getQrCode());
            values.put("Donate_Money", user.getDonateMoney());
            values.put("Gender", user.getGender().toString());
            values.put("Role", user.getRole().toString());
            values.put("UserImage", user.getUserImage());
            return db.insert(TABLE_USER, null, values) != -1;
        }
    }

    /**
     * 根據用戶 ID 查找用戶
     *
     * @param userId 用戶 ID
     * @return 用戶
     */
    public User findUserById(int userId) {
        return findUser("User_ID", String.valueOf(userId));
    }

    /**
     * 根據郵箱查找用戶
     *
     * @param email 郵箱
     * @return 用戶
     */
    public User findUserByEmail(String email) {
        return findUser("E_mail", email);
    }

    /**
     * 根據欄位名和值查找用戶
     *
     * @param column 欄位名
     * @param value  欄位值
     * @return
     */
    @Nullable
    private User findUser(String column, String value) {
        String query = "SELECT * FROM " + TABLE_USER + " WHERE " + column + " = ?";
        try (SQLiteDatabase db = getReadableDatabase();
             Cursor cursor = db.rawQuery(query, new String[]{value})) {
            if (cursor != null && cursor.moveToFirst()) {
                return buildUserObject(cursor);
            }
        }
        return null;
    }

    /**
     * 獲取所有用戶
     *
     * @param cursor 游標
     * @return 用戶列表
     */
    @NonNull
    private User buildUserObject(@NonNull Cursor cursor) {
        User user = new User();
        user.setId(cursor.getInt(cursor.getColumnIndexOrThrow("User_ID")));
        user.setUserName(cursor.getString(cursor.getColumnIndexOrThrow("User_Name")));
        user.setEmail(cursor.getString(cursor.getColumnIndexOrThrow("E_mail")));
        user.setPassword(cursor.getString(cursor.getColumnIndexOrThrow("Password")));
        user.setPhoneNumber(cursor.getString(cursor.getColumnIndexOrThrow("Phone_Number")));
        user.setEarnMoney(cursor.getDouble(cursor.getColumnIndexOrThrow("Earn_Money")));
        user.setQrCode(cursor.getString(cursor.getColumnIndexOrThrow("QR_Code")));
        user.setDonateMoney(cursor.getDouble(cursor.getColumnIndexOrThrow("Donate_Money")));
        user.setGender(Gender.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("Gender"))));
        user.setRole(Role.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("Role"))));
        user.setUserImage(cursor.getString(cursor.getColumnIndexOrThrow("UserImage")));
        return user;
    }

    /**
     * 更新用戶資料
     *
     * @param user 用戶
     * @return 是否更新成功
     */
    public boolean updateUser(@NonNull User user) {
        try (SQLiteDatabase db = getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put("User_Name", user.getUserName());
            values.put("E_mail", user.getEmail());
            values.put("Password", user.getPassword());
            values.put("Phone_Number", user.getPhoneNumber());
            values.put("Earn_Money", user.getEarnMoney());
            values.put("QR_Code", user.getQrCode());
            values.put("Donate_Money", user.getDonateMoney());
            values.put("Gender", user.getGender().toString());
            values.put("Role", user.getRole().toString());
            values.put("UserImage", user.getUserImage());
            return db.update(TABLE_USER, values, "User_ID = ?", new String[]{String.valueOf(user.getId())}) > 0;
        }
    }

    /**
     * 檢查用戶是否唯一
     *
     * @param column 欄位名
     * @param value  欄位值
     * @return
     */
    public boolean isUnique(String column, String value) {
        String query = "SELECT COUNT(1) FROM " + TABLE_USER + " WHERE " + column + " = ?";
        try (SQLiteDatabase db = getReadableDatabase();
             Cursor cursor = db.rawQuery(query, new String[]{value})) {
            return cursor != null && cursor.moveToFirst() && cursor.getInt(0) == 0;
        }
    }

    /**
     * 檢查郵箱是否唯一
     * @param email 郵箱
     * @return 是否唯一
     */
    public boolean checkEmail(String email) {
        return isUnique("E_mail", email);
    }

    /**
     * 檢查手機號碼是否唯一
     * @param phoneNumber 手機號碼
     * @return
     */
    public boolean checkPhoneNumber(String phoneNumber) {
        return isUnique("Phone_Number", phoneNumber);
    }

    /**
     * 獲取用戶數量
     * @return 當前用戶數量
     */
    public int getUserCount() {
        String query = "SELECT COUNT(*) FROM " + TABLE_USER;
        try (SQLiteDatabase db = getReadableDatabase();
             Cursor cursor = db.rawQuery(query, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                return cursor.getInt(0);
            }
        }
        return 0;
    }

    public boolean addRecycleStation(@NonNull RecycleStation recycleStation) {
        try (SQLiteDatabase db = getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put("RecycleStation_Name", recycleStation.getName());
            values.put("RecycleStation_Address", recycleStation.getAddress());
            values.put("RecycleStation_Latitude", recycleStation.getLatitude());
            values.put("RecycleStation_Longitude", recycleStation.getLongitude());
            return db.insert(TABLE_RECYCLE_STATION, null, values) != -1;
        }
    }

    /**
     * 根據用戶 ID 獲取剛用戶的所有回收記錄
     * @param userId 用戶 ID
     * @return 回收記錄列表
     */
    public List<RecycleRecord> getAllRecycleRecordsByUserId(int userId) {
        String query = "SELECT * FROM " + TABLE_USER_RECYCLE_RECORD + " WHERE User_ID = ?";
        try (SQLiteDatabase db = getReadableDatabase();
             Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)})) {
            if (cursor != null && cursor.moveToFirst()) {
                List<RecycleRecord> records = new ArrayList<>();
                do {
                    RecycleRecord record = new RecycleRecord();
                    record.setRecycleRecordId(cursor.getInt(cursor.getColumnIndexOrThrow("User_Recycle_Record_ID")));
                    record.setUserId(cursor.getInt(cursor.getColumnIndexOrThrow("User_ID")));
                    record.setRecycleStationId(cursor.getInt(cursor.getColumnIndexOrThrow("RecycleStation_ID")));
                    record.setRecycleTime(cursor.getString(cursor.getColumnIndexOrThrow("Recycle_Date")));
                    record.setRecycleWeight(cursor.getDouble(cursor.getColumnIndexOrThrow("Recycle_Weight")));
                    records.add(record);
                } while (cursor.moveToNext());
                return records;
            }
        }
        return null;
    }

    /**
     * 根據回收站 ID 查找回收站
     * @param recycleStationId 回收站 ID
     * @return 回收站
     */
    public RecycleStation findStationById(int recycleStationId) {
        String query = "SELECT * FROM " + TABLE_RECYCLE_STATION + " WHERE RecycleStation_ID = ?";
        try (SQLiteDatabase db = getReadableDatabase();
             Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(recycleStationId)})) {
            if (cursor != null && cursor.moveToFirst()) {
                RecycleStation station = new RecycleStation();
                station.setId(cursor.getInt(cursor.getColumnIndexOrThrow("RecycleStation_ID")));
                station.setName(cursor.getString(cursor.getColumnIndexOrThrow("RecycleStation_Name")));
                station.setAddress(cursor.getString(cursor.getColumnIndexOrThrow("RecycleStation_Address")));
                station.setLatitude(cursor.getDouble(cursor.getColumnIndexOrThrow("RecycleStation_Latitude")));
                station.setLongitude(cursor.getDouble(cursor.getColumnIndexOrThrow("RecycleStation_Longitude")));
                return station;
            }
        }
        return null;
    }

    public boolean addRecycleRecord(@NonNull RecycleRecord record) {
        try (SQLiteDatabase db = getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put("User_ID", record.getUserId());
            values.put("RecycleStation_ID", record.getRecycleStationId());
            values.put("Recycle_Date", record.getRecycleTime());
            values.put("Recycle_Weight", record.getRecycleWeight());
            return db.insert(TABLE_USER_RECYCLE_RECORD, null, values) != -1;
        }
    }
}
