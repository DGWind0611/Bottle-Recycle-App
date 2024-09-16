package com.fcu.android.bottlerecycleapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;

import com.fcu.android.bottlerecycleapp.Gender;
import com.fcu.android.bottlerecycleapp.R;

import java.util.Date;

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
        createTableQrCordAgency(db);
        createTableRecycleStation(db);
        createTableRemittanceRecord(db);
        createTableStationFixRecord(db);
        createTableUserRecycleRecord(db);
        if(getUserLength() == 0){
            testUser(db);
        }
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



    /**
     * 新增使用者
     *
     * @param user 使用者資料
     * @return 是否新增成功
     */
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
            values.put("QR_Code", user.getQrCode());
            values.put("Donate_Money", user.getDonateMoney());
            values.put("Gender", user.getGender().toString());
            values.put("UserImage", user.getUserImage());
            long result = db.insert(TABLE_USER, null, values);
            return result != -1;
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    // 建立user物件
    private User buildUserObject(Cursor cursor) {
        User user = new User();
        user.setId(cursor.getInt(cursor.getColumnIndexOrThrow("User_ID")));
        user.setUserName(cursor.getString(cursor.getColumnIndexOrThrow("User_Name")));
        user.setEmail(cursor.getString(cursor.getColumnIndexOrThrow("E_mail")));
        user.setPassword(cursor.getString(cursor.getColumnIndexOrThrow("Password")));
        user.setPhoneNumber(cursor.getString(cursor.getColumnIndexOrThrow("Phone_Number")));
        user.setEarnMoney(cursor.getDouble(cursor.getColumnIndexOrThrow("Earn_Money")));
        user.setQrCode(cursor.getString(cursor.getColumnIndexOrThrow("QR_Code")));
        user.setDonateMoney(cursor.getDouble(cursor.getColumnIndexOrThrow("Donate_Money")));
        user.setUserImage(cursor.getString(cursor.getColumnIndexOrThrow("UserImage")));
        cursor.close();
        return user;
    }

    /**
     * 根據使用者 ID 取得使用者資料
     *
     * @param userId 使用者 ID
     * @return 使用者資料
     */
    public User findUserById(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("User", null, "id = ?", new String[]{String.valueOf(userId)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            User user = new User();
            user = buildUserObject(cursor);
            return user;
        }
        return null;
    }

    /**
     * 透過電子郵件尋找使用者
     *
     * @param email 電子郵件
     * @return 使用者資料
     */
    public User findUserByEmail(String email) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER, null, "E_mail = ?", new String[]{email}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            User user = new User();
            user = buildUserObject(cursor);
            return user;
        }
        return null;
    }

    /**
     * 更新使用者資料
     * @param user 使用者資料
     * @return 是否更新成功
     */
    public boolean updateUser(User user) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("User_Name", user.getUserName());
        values.put("E_mail", user.getEmail());
        values.put("Password", user.getPassword());
        values.put("Phone_Number", user.getPhoneNumber());
        values.put("Earn_Money", user.getEarnMoney());
        values.put("QR_Code", user.getQrCode());
        values.put("Donate_Money", user.getDonateMoney());
        values.put("Gender", user.getGender().toString());
        values.put("UserImage", user.getUserImage());
        int result = db.update(TABLE_USER, values, "User_ID = ?", new String[]{String.valueOf(user.getId())});
        return result > 0;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // onUpgrade 則是如果資料庫結構有改變了就會觸發 onUpgrade
    }

    /**
     * 檢查電子郵件是否已經註冊過
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

    /**
     * 檢查手機號碼是否已經註冊過
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

    /**
     * 取得當前使用者數量
     *
     * @return 使用者數量
     */
    public int getUserLength() {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM User";
        Cursor cursor = db.rawQuery(query, null);
        int result = cursor.getCount();
        cursor.close();
        db.close();
        return result;
    }

    // 測試資料
    private void testUser(@NonNull SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        Date date = new Date();
        values.put("User_Name", "test");
        values.put("E_mail", "test@test.com");
        values.put("Password", "Test123456");
        values.put("Phone_Number", "0912345678");
        values.put("Earn_Money", 0);
        values.put("QR_Code", date.getTime() + getUserLength());
        values.put("Donate_Money", 0);
        values.put("Gender", Gender.Undefine.toString());
        values.put("UserImage", R.drawable.avatar);
        db.insert(TABLE_USER, null, values);
    }
}
