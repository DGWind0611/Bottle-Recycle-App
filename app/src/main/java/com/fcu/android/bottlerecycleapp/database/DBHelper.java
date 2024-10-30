package com.fcu.android.bottlerecycleapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fcu.android.bottlerecycleapp.database.entity.ActivityItem;
import com.fcu.android.bottlerecycleapp.database.entity.Gender;
import com.fcu.android.bottlerecycleapp.database.entity.MyActivity;
import com.fcu.android.bottlerecycleapp.database.entity.Notification;
import com.fcu.android.bottlerecycleapp.database.entity.RecycleRecord;
import com.fcu.android.bottlerecycleapp.database.entity.RecycleStation;
import com.fcu.android.bottlerecycleapp.database.entity.Role;
import com.fcu.android.bottlerecycleapp.database.entity.Type;
import com.fcu.android.bottlerecycleapp.database.entity.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "bottle_recycle.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_USER = "User";
    private static final String TABLE_DONATION_RECORD = "Donation_Record";
    private static final String TABLE_NOTIFICATIONS = "Notifications";
    private static final String TABLE_QR_CORD_AGENCY = "QR_Cord_Agency";
    private static final String TABLE_RECYCLE_STATION = "RecycleStation";
    private static final String TABLE_REMITTANCE_RECORD = "Remittance_Record";
    private static final String TABLE_STATION_FIX_RECORD = "Station_Fix_Record";
    private static final String TABLE_USER_RECYCLE_RECORD = "User_recycle_Record";
    private static final String TABLE_USER_NOTIFICATION = "User_Notification";
    private static final String TABLE_ACTIVITY = "Activity";
    private static final String TABLE_USER_ACTIVITY = "User_Activity";
    private static final String TABLE_STATION_STATUS_HISTORY = "Station_Status_History";

    public DBHelper(@NonNull Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // 重新建立資料表
        createTableUser(db);
        createTableDonationRecord(db);
        createTableNotifications(db);
        createTableQrCordAgency(db);
        createTableRecycleStation(db);
        createTableRemittanceRecord(db);
        createTableStationFixRecord(db);
        createTableUserRecycleRecord(db);
        createUserNotification(db);
        createActivityTable(db);
        createUserActivityRelationTable(db);
        createStationStatusHistory(db);
    }


    private void createTableUser(@NonNull SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_USER + " ("
                + "User_Name TEXT NOT NULL, "
                + "User_Tag TEXT NOT NULL, "
                + "E_mail TEXT NOT NULL UNIQUE, "
                + "Password TEXT NOT NULL, "
                + "Phone_Number TEXT NOT NULL UNIQUE, "
                + "Earn_Money REAL, "
                + "QR_Code String, "
                + "Donate_Money REAL, "
                + "Gender TEXT, "
                + "Role TEXT, "
                + "UserImage TEXT, "
                + "PRIMARY KEY (User_Name, User_Tag) )";
        db.execSQL(sql);
    }

    private void createTableDonationRecord(@NonNull SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_DONATION_RECORD + " (" +
                "Donation_Record_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "User_Name TEXT NOT NULL, " +
                "User_Tag TEXT NOT NULL, " +
                "Donate_Money REAL NOT NULL, " +
                "Donate_Date TEXT NOT NULL, " +
                "FOREIGN KEY(User_Name, User_Tag) REFERENCES User(User_Name, User_Tag))";
        db.execSQL(sql);
    }

    private void createTableNotifications(@NonNull SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NOTIFICATIONS + " (" +
                "Notification_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "User_Name TEXT, " +
                "User_Tag TEXT, " +
                "Notification_Title TEXT NOT NULL, " +
                "Notification_Content TEXT NOT NULL, " +
                "Notification_Date TEXT NOT NULL, " +
                "Notification_Time TEXT NOT NULL, " +
                "Notification_Type TEXT NOT NULL, " +
                "FOREIGN KEY(User_Name, User_Tag) REFERENCES User(User_Name, User_Tag))";
        db.execSQL(sql);
    }

    private void createTableQrCordAgency(@NonNull SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_QR_CORD_AGENCY + " (" +
                "QR_Cord_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Agency_Name TEXT NOT NULL, " +
                "QR_CODE_ORIGIN_Name INTEGER NOT NULL, " +
                "User_Custom_Name TEXT )";
        db.execSQL(sql);
    }

    private void createTableRecycleStation(@NonNull SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_RECYCLE_STATION + " (" +
                "RecycleStation_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "RecycleStation_Name TEXT NOT NULL, " +
                "RecycleStation_Address TEXT NOT NULL, " +
                "RecycleStation_Latitude REAL NOT NULL, " +
                "RecycleStation_Longitude REAL NOT NULL," +
                "Max_Weight REAL NOT NULL," +
                "Current_Weight REAL NOT NULL)";
        db.execSQL(sql);
    }

    private void createTableRemittanceRecord(@NonNull SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_REMITTANCE_RECORD + " (" +
                "Remittance_Record_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "User_Name TEXT NOT NULL, " +
                "User_Tag TEXT NOT NULL, " +
                "Remittance_Money REAL NOT NULL, " +
                "Remittance_Date TEXT NOT NULL, " +
                "FOREIGN KEY(User_Name, User_Tag) REFERENCES User(User_Name, User_Tag))";
        db.execSQL(sql);
    }

    private void createTableStationFixRecord(@NonNull SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_STATION_FIX_RECORD + " (" +
                "Station_Fix_Record_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "RecycleStation_ID INTEGER NOT NULL, " +
                "Fix_Date TEXT  NOT NULL, " +
                "Fix_Content TEXT NOT NULL, " +
                "FOREIGN KEY(RecycleStation_ID) REFERENCES RecycleStation(RecycleStation_ID))";
        db.execSQL(sql);
    }

    private void createStationStatusHistory(@NonNull SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_STATION_STATUS_HISTORY + "(" +
                "StatusHistory_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "RecycleStation_ID INTEGER NOT NULL," +
                "Cleaner_Name TEXT NOT NULL, " +
                "Cleaner_Tag TEXT NOT NULL, " +
                "Status TEXT NOT NULL, " +
                "Clean_Date TEXT NOT NULL, " +
                "FOREIGN KEY (Cleaner_Name, Cleaner_Tag) REFERENCES User (User_Name, User_Tag), " +
                "FOREIGN KEY (RecycleStation_ID) REFERENCES RecycleStation (RecycleStation_ID))";
        db.execSQL(sql);
    }

    private void createTableUserRecycleRecord(@NonNull SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_USER_RECYCLE_RECORD + " (" +
                "User_Recycle_Record_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "User_Name TEXT NOT NULL, " +
                "User_Tag TEXT NOT NULL, " +
                "RecycleStation_ID INTEGER NOT NULL, " +
                "Recycle_Date TEXT NOT NULL, " +
                "Recycle_Weight REAL NOT NULL, " +
                "FOREIGN KEY(User_Name, User_Tag) REFERENCES User(User_Name, User_Tag), " +
                "FOREIGN KEY(RecycleStation_ID) REFERENCES RecycleStation(RecycleStation_ID))";
        db.execSQL(sql);
    }

    private void createUserNotification(@NonNull SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_USER_NOTIFICATION + " (" +
                "User_Name TEXT, " +
                "User_Tag TEXT, " +
                "Notification_ID INTEGER, " +
                "PRIMARY KEY (User_Name, User_Tag, Notification_ID), " +
                "FOREIGN KEY(User_Name, User_Tag) REFERENCES User(User_Name, User_Tag), " +
                "FOREIGN KEY(Notification_ID) REFERENCES Notifications(Notification_ID))";
        db.execSQL(sql);
    }

    private void createActivityTable(@NonNull SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_ACTIVITY + " (" +
                "Activity_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Activity_Name TEXT NOT NULL, " +
                "Activity_Start_Time TEXT NOT NULL, " +
                "Activity_END_Time TEXT NOT NULL, " +
                "Activity_Goal INTEGER NOT NULL, " +
                "Activity_Description TEXT )";
        db.execSQL(sql);
    }

    private void createUserActivityRelationTable(@NonNull SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_USER_ACTIVITY + " (" +
                "User_Name TEXT NOT NULL, " +
                "User_Tag TEXT NOT NULL, " +
                "Activity_ID INTEGER, " +
                "Current_Achievement INTEGER NOT NULL, " +
                "Goal_Achievement INTEGER NOT NULL, " +
                "PRIMARY KEY (User_Name, User_Tag, Activity_ID), " +
                "FOREIGN KEY(User_Name, User_Tag) REFERENCES User(User_Name, User_Tag), " +
                "FOREIGN KEY(Activity_ID) REFERENCES Activity(Activity_ID))";
        db.execSQL(sql);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // onUpgrade 則是如果資料庫結構有改變了就會觸發 onUpgrade
    }

    /**
     * 新增用戶
     *
     * @param user 用戶
     * @return 是否新增成功
     */
    public boolean addUser(@NonNull User user) {
        try (SQLiteDatabase db = getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put("User_Name", user.getUserName());
            values.put("User_Tag", user.getUserTag()); // 新增 User_Tag
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
     * @param userName 用戶名
     * @param userTag  識別標籤
     * @return 用戶
     */
    public User findUserByNameAndTag(String userName, String userTag) {
        String query = "SELECT * FROM " + TABLE_USER + " WHERE User_Name = ? AND User_Tag = ?";
        try (SQLiteDatabase db = getReadableDatabase();
             Cursor cursor = db.rawQuery(query, new String[]{userName, userTag})) {
            if (cursor != null && cursor.moveToFirst()) {
                return buildUserObject(cursor);
            }
        }
        return null;
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
        user.setUserName(cursor.getString(cursor.getColumnIndexOrThrow("User_Name")));
        user.setUserTag(cursor.getString(cursor.getColumnIndexOrThrow("User_Tag")));
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
            values.put("User_Tag", user.getUserTag()); // 新增 User_Tag
            values.put("E_mail", user.getEmail());
            values.put("Password", user.getPassword());
            values.put("Phone_Number", user.getPhoneNumber());
            values.put("Earn_Money", user.getEarnMoney());
            values.put("QR_Code", user.getQrCode());
            values.put("Donate_Money", user.getDonateMoney());
            values.put("Gender", user.getGender().toString());
            values.put("Role", user.getRole().toString());
            values.put("UserImage", user.getUserImage());
            return db.update(TABLE_USER, values, "User_Name = ? AND User_Tag = ?",
                    new String[]{user.getUserName(), user.getUserTag()}) > 0;
        }
    }


    /**
     * 檢查用戶是否唯一
     *
     * @param userName 用戶名
     * @param userTag  識別標籤
     * @return
     */
    public boolean isUnique(String userName, String userTag) {
        String query = "SELECT COUNT(1) FROM " + TABLE_USER + " WHERE User_Name = ? AND User_Tag = ?";
        try (SQLiteDatabase db = getReadableDatabase();
             Cursor cursor = db.rawQuery(query, new String[]{userName, userTag})) {
            return cursor != null && cursor.moveToFirst() && cursor.getInt(0) == 0;
        }
    }


    /**
     * 檢查郵箱是否唯一
     *
     * @param email 郵箱
     * @return 是否唯一
     */
    public boolean checkEmail(String email) {
        return isUnique("E_mail", email);
    }

    /**
     * 檢查手機號碼是否唯一
     *
     * @param phoneNumber 手機號碼
     * @return
     */
    public boolean checkPhoneNumber(String phoneNumber) {
        return isUnique("Phone_Number", phoneNumber);
    }

    /**
     * 獲取用戶數量
     *
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

    /**
     * 新增回收站
     *
     * @param recycleStation 回收站
     * @return 是否新增成功
     */
    public boolean addRecycleStation(@NonNull RecycleStation recycleStation) {
        try (SQLiteDatabase db = getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put("RecycleStation_Name", recycleStation.getName());
            values.put("RecycleStation_Address", recycleStation.getAddress());
            values.put("RecycleStation_Latitude", recycleStation.getLatitude());
            values.put("RecycleStation_Longitude", recycleStation.getLongitude());
            values.put("Max_Weight", recycleStation.getMaxWeight());
            values.put("Current_Weight", recycleStation.getCurrentWeight());
            return db.insert(TABLE_RECYCLE_STATION, null, values) != -1;
        }
    }

    /**
     * 根據用戶名和識別標籤獲取用戶的所有回收記錄
     *
     * @param userName 用戶名
     * @param userTag  識別標籤
     * @return 回收記錄列表
     */
    public List<RecycleRecord> getAllRecycleRecordsByUserId(String userName, String userTag) {
        // Update the query to filter by User_Name and User_Tag
        String query = "SELECT * FROM " + TABLE_USER_RECYCLE_RECORD +
                " WHERE User_Name = ? AND User_Tag = ?";

        try (SQLiteDatabase db = getReadableDatabase();
             Cursor cursor = db.rawQuery(query, new String[]{userName, userTag})) {
            if (cursor != null && cursor.moveToFirst()) {
                List<RecycleRecord> records = new ArrayList<>();
                do {
                    RecycleRecord record = new RecycleRecord();
                    record.setRecycleRecordId(cursor.getInt(cursor.getColumnIndexOrThrow("User_Recycle_Record_ID")));
                    record.setUserName(cursor.getString(cursor.getColumnIndexOrThrow("User_Name")));
                    record.setUserTag(cursor.getString(cursor.getColumnIndexOrThrow("User_Tag")));
                    record.setRecycleStationId(cursor.getInt(cursor.getColumnIndexOrThrow("RecycleStation_ID")));
                    record.setRecycleTime(cursor.getString(cursor.getColumnIndexOrThrow("Recycle_Date")));
                    record.setRecycleWeight(cursor.getDouble(cursor.getColumnIndexOrThrow("Recycle_Weight")));
                    records.add(record);
                } while (cursor.moveToNext());
                return records;
            }
        } catch (Exception e) {
            // Log or handle the exception as necessary
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 獲取所有回收站
     *
     * @return 回收站列表
     */
    public List<RecycleStation> findAllStations() {
        String query = "SELECT * FROM " + TABLE_RECYCLE_STATION;
        try (SQLiteDatabase db = getReadableDatabase();
             Cursor cursor = db.rawQuery(query, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                List<RecycleStation> stations = new ArrayList<>();
                do {
                    RecycleStation station = new RecycleStation();
                    station.setId(cursor.getInt(cursor.getColumnIndexOrThrow("RecycleStation_ID")));
                    station.setName(cursor.getString(cursor.getColumnIndexOrThrow("RecycleStation_Name")));
                    station.setAddress(cursor.getString(cursor.getColumnIndexOrThrow("RecycleStation_Address")));
                    station.setLatitude(cursor.getDouble(cursor.getColumnIndexOrThrow("RecycleStation_Latitude")));
                    station.setLongitude(cursor.getDouble(cursor.getColumnIndexOrThrow("RecycleStation_Longitude")));
                    station.setMaxWeight(cursor.getDouble(cursor.getColumnIndexOrThrow("Max_Weight")));
                    station.setCurrentWeight(cursor.getDouble(cursor.getColumnIndexOrThrow("Current_Weight")));
                    stations.add(station);
                } while (cursor.moveToNext());
                return stations;
            }
        }
        return null;
    }

    /**
     * 根據回收站 ID 查找回收站
     *
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
                station.setMaxWeight(cursor.getDouble(cursor.getColumnIndexOrThrow("Max_Weight")));
                station.setCurrentWeight(cursor.getDouble(cursor.getColumnIndexOrThrow("Current_Weight")));
                return station;
            }
        }
        return null;
    }

    /**
     * 新增回收記錄, 並同時更新回收站當前重量
     *
     * @param record 回收記錄
     * @return 是否新增成功
     */
    public boolean addRecycleRecord(@NonNull RecycleRecord record) {
        SQLiteDatabase db = getWritableDatabase();
        boolean isSuccess = false;

        // 開啟一個資料庫事務以保證原子性操作
        db.beginTransaction();
        try {
            // 新增回收記錄
            ContentValues recordValues = new ContentValues();
            recordValues.put("User_Name", record.getUserName());
            recordValues.put("User_Tag", record.getUserTag());
            recordValues.put("RecycleStation_ID", record.getRecycleStationId());
            recordValues.put("Recycle_Date", record.getRecycleTime());
            recordValues.put("Recycle_Weight", record.getRecycleWeight());

            long recordResult = db.insert(TABLE_USER_RECYCLE_RECORD, null, recordValues);
            if (recordResult == -1) {
                throw new Exception("新增回收記錄失敗");
            }

            // 同時更新回收站的當前重量
            RecycleStation station = findStationById(record.getRecycleStationId());
            if (station != null) {
                station.setCurrentWeight(station.getCurrentWeight() + record.getRecycleWeight());

                ContentValues stationValues = buildStationContentValues(station);
                int updateResult = db.update(TABLE_RECYCLE_STATION, stationValues,
                        "RecycleStation_ID = ?", new String[]{String.valueOf(record.getRecycleStationId())});
                if (updateResult <= 0) {
                    throw new Exception("更新回收站重量失敗");
                }
            } else {
                throw new Exception("找不到對應的回收站");
            }

            // 若一切操作成功，提交事務
            db.setTransactionSuccessful();
            isSuccess = true;
        } catch (Exception e) {
            Log.e("DBHelper", "Error adding recycle record: " + e.getMessage());
        } finally {
            db.endTransaction();
            db.close();
        }

        return isSuccess;
    }

    @NonNull
    private ContentValues buildStationContentValues(@NonNull RecycleStation station) {
        ContentValues values = new ContentValues();
        values.put("RecycleStation_Name", station.getName());
        values.put("RecycleStation_Address", station.getAddress());
        values.put("RecycleStation_Latitude", station.getLatitude());
        values.put("RecycleStation_Longitude", station.getLongitude());
        values.put("Max_Weight", station.getMaxWeight());
        values.put("Current_Weight", station.getCurrentWeight());
        return values;
    }


    /**
     * 獲取該user所有通知
     *
     * @param userName 用戶名
     * @param userTag  用戶標籤
     * @return 通知列表
     */
    public ArrayList<Notification> getUserNotifications(String userName, String userTag) {
        ArrayList<Notification> notifications = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT n.Notification_ID, n.Notification_Title, n.Notification_Content, " +
                "n.Notification_Date, n.Notification_Time, n.Notification_Type " +
                "FROM Notifications n " +
                "LEFT JOIN User_Notification un ON n.Notification_ID = un.Notification_ID " +
                "WHERE un.User_Name = ? AND un.User_Tag = ? OR n.Notification_Type = ?";

        try (Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userName), String.valueOf(userTag), Type.ALL_USERS.name()})) {
            if (cursor.moveToFirst()) {
                do {
                    int notificationId = cursor.getInt(cursor.getColumnIndexOrThrow("Notification_ID"));
                    String title = cursor.getString(cursor.getColumnIndexOrThrow("Notification_Title"));
                    String content = cursor.getString(cursor.getColumnIndexOrThrow("Notification_Content"));
                    String date = cursor.getString(cursor.getColumnIndexOrThrow("Notification_Date"));
                    String time = cursor.getString(cursor.getColumnIndexOrThrow("Notification_Time"));
                    String typeStr = cursor.getString(cursor.getColumnIndexOrThrow("Notification_Type"));
                    Type type = Type.valueOf(typeStr);

                    Notification notification = new Notification(notificationId, userName, userTag, title, content, date, time, type);
                    notifications.add(notification);
                } while (cursor.moveToNext());
            }
        } finally {
            db.close();
        }
        return notifications;
    }


    /**
     * 新增通知
     *
     * @param notification 通知
     * @return 是否新增成功
     */
    public boolean addNotification(@NonNull Notification notification) {
        SQLiteDatabase db = this.getWritableDatabase();
        long notificationId;
        try {
            // 新增通知到 Notifications 表
            ContentValues values = new ContentValues();
            values.put("Notification_Title", notification.getTitle());
            values.put("Notification_Content", notification.getContent());
            values.put("Notification_Date", notification.getDate());
            values.put("Notification_Time", notification.getTime());
            values.put("Notification_Type", notification.getType().toString());

            notificationId = db.insert(TABLE_NOTIFICATIONS, null, values);

            // 檢查通知是否成功插入
            if (notificationId == -1) {
                return false; // 插入失敗
            }

            // 如果是 SPECIFIC_USER，則在 User_Notification 表中插入關聯
            if (notification.getType() == Type.SPECIFIC_USER &&
                    (notification.getUserName() != null && notification.getUserTag() != null)) {
                ContentValues userNotificationValues = new ContentValues();
                userNotificationValues.put("User_Name", notification.getUserName());
                userNotificationValues.put("User_Tag", notification.getUserTag());
                userNotificationValues.put("Notification_ID", notificationId);
                long userNotificationId = db.insert(TABLE_USER_NOTIFICATION, null, userNotificationValues);

                // 檢查用戶-通知關聯是否成功插入
                if (userNotificationId == -1) {
                    return false; // 插入失敗
                }
            }
            return true; // 成功新增通知
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            db.close(); // 確保關閉資料庫
        }
    }

    /**
     * 檢查活動是否過期
     *
     * @param StartTime 開始時間
     * @param EndTime   結束時間
     * @return 是否過期
     */
    private boolean isActivityExpired(String StartTime, String EndTime) {
        long currentTime = System.currentTimeMillis();
        long startTime = Long.parseLong(StartTime);
        long endTime = Long.parseLong(EndTime);
        return currentTime < startTime || currentTime > endTime;
    }

    /**
     * 新增活動，並同時將所有用戶加入活動
     *
     * @param activity 活動
     * @return 是否新增成功
     */
    public boolean addActivity(@NonNull MyActivity activity) {
        SQLiteDatabase db = this.getWritableDatabase();
        long activityId;
        try {
            ContentValues values = new ContentValues();
            values.put("Activity_Name", activity.getActivityName());
            values.put("Activity_Description", activity.getActivityDescription());
            values.put("Activity_Start_Time", activity.getActivityStartTime());
            values.put("Activity_END_Time", activity.getActivityEndTime());
            values.put("Activity_Goal", activity.getActivityGoal());
            activityId = db.insert(TABLE_ACTIVITY, null, values);

            if (activityId == -1) {
                return false;
            } else {
                boolean isExpired = isActivityExpired(activity.getActivityStartTime(), activity.getActivityEndTime());
                addUsersToActivity(db, activityId, activity.getActivityGoal());
                return true;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            db.close();
        }
    }

    /**
     * 將所有用戶加入活動(Role 為 USER)
     *
     * @param db         資料庫
     * @param activityId 活動 ID
     * @param goal       目標
     */
    private void addUsersToActivity(SQLiteDatabase db, long activityId, int goal) {
        // 查詢獲取所有 USER 角色的用戶
        String query = "SELECT User_Name, User_Tag FROM " + TABLE_USER + " WHERE Role = 'USER'";

        try (Cursor cursor = db.rawQuery(query, null)) {
            if (cursor.moveToFirst()) {
                do {
                    String userName = cursor.getString(cursor.getColumnIndexOrThrow("User_Name"));
                    String userTag = cursor.getString(cursor.getColumnIndexOrThrow("User_Tag"));

                    // 將用戶添加到活動中
                    ContentValues userActivityValues = new ContentValues();
                    userActivityValues.put("User_Name", userName);
                    userActivityValues.put("User_Tag", userTag);
                    userActivityValues.put("Activity_ID", activityId);
                    userActivityValues.put("Current_Achievement", 0);
                    userActivityValues.put("Goal_Achievement", goal);

                    db.insert(TABLE_USER_ACTIVITY, null, userActivityValues);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 尋找所有尚未過期的活動ID及其目標
     *
     * @return 活動ID及其目標
     */
    public Map<Integer, Integer> findAllUnExpiredActivities() {
        Map<Integer, Integer> activities = new HashMap<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT Activity_ID, Activity_Goal, Activity_Start_Time, Activity_END_Time FROM Activity";

        try (Cursor cursor = db.rawQuery(query, null)) {
            if (cursor.moveToFirst()) {
                do {
                    int activityId = cursor.getInt(cursor.getColumnIndexOrThrow("Activity_ID"));
                    String startTime = cursor.getString(cursor.getColumnIndexOrThrow("Activity_Start_Time"));
                    String endTime = cursor.getString(cursor.getColumnIndexOrThrow("Activity_END_Time"));
                    int goal = cursor.getInt(cursor.getColumnIndexOrThrow("Activity_Goal"));

                    if (!isActivityExpired(startTime, endTime)) {
                        activities.put(activityId, goal);
                    }
                } while (cursor.moveToNext());
            }
        } finally {
            db.close();
        }
        return activities;
    }

    /**
     * 將指定的用戶加入所有未過期的活動
     *
     * @param userName 用戶名稱
     * @param userTag  用戶標籤
     * @return 是否加入成功
     */
    public boolean addUserToAllUnExpiredActivities(String userName, String userTag) {
        SQLiteDatabase db = this.getWritableDatabase();

        // 獲取所有未過期的活動
        Map<Integer, Integer> unExpiredActivities = findAllUnExpiredActivities();
        for (Map.Entry<Integer, Integer> entry : unExpiredActivities.entrySet()) {
            int activityId = entry.getKey();
            int goal = entry.getValue();

            try {
                ContentValues values = new ContentValues();
                values.put("User_Name", userName); // 將 User_Name 插入
                values.put("User_Tag", userTag);   // 將 User_Tag 插入
                values.put("Activity_ID", activityId);
                values.put("Current_Achievement", 0);
                values.put("Goal_Achievement", goal);
                db.insert(TABLE_USER_ACTIVITY, null, values); // 執行插入操作
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    }


    // 從 TABLE_USER_ACTIVITY 中取得指定用戶的所有活動
    public List<ActivityItem> getUserActivities(String userName, String userTag) {
        List<ActivityItem> userActivities = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // 查詢用戶的活動，根據 User_Name 和 User_Tag
        String activityQuery = "SELECT * FROM " + TABLE_USER_ACTIVITY +
                " WHERE User_Name = ? AND User_Tag = ?";
        try (Cursor cursor = db.rawQuery(activityQuery, new String[]{userName, userTag})) {
            if (cursor.moveToFirst()) {
                do {
                    ActivityItem userActivity = new ActivityItem();
                    userActivity.setUserName(cursor.getString(cursor.getColumnIndexOrThrow("User_Name")));
                    userActivity.setUserTag(cursor.getString(cursor.getColumnIndexOrThrow("User_Tag")));
                    userActivity.setActivityId(cursor.getInt(cursor.getColumnIndexOrThrow("Activity_ID")));
                    userActivity.setActivityAchievement(cursor.getInt(cursor.getColumnIndexOrThrow("Current_Achievement")));
                    userActivity.setActivityGoal(cursor.getInt(cursor.getColumnIndexOrThrow("Goal_Achievement")));
                    userActivities.add(userActivity);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return userActivities;
    }


    public MyActivity findActivityById(int activityId) {
        String query = "SELECT * FROM " + TABLE_ACTIVITY + " WHERE Activity_ID = ?";
        try (SQLiteDatabase db = getReadableDatabase();
             Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(activityId)})) {
            if (cursor != null && cursor.moveToFirst()) {
                MyActivity activity = new MyActivity();
                activity.setId(cursor.getInt(cursor.getColumnIndexOrThrow("Activity_ID")));
                activity.setActivityName(cursor.getString(cursor.getColumnIndexOrThrow("Activity_Name")));
                activity.setActivityDescription(cursor.getString(cursor.getColumnIndexOrThrow("Activity_Description")));
                activity.setActivityStartTime(cursor.getString(cursor.getColumnIndexOrThrow("Activity_Start_Time")));
                activity.setActivityEndTime(cursor.getString(cursor.getColumnIndexOrThrow("Activity_END_Time")));
                activity.setActivityGoal(cursor.getInt(cursor.getColumnIndexOrThrow("Activity_Goal")));
                return activity;
            }
        }
        return null;
    }

    /**
     * 計算指定用戶的總回收次數和回收重量
     *
     * @param userName 用戶名稱
     * @param userTag  用戶標籤
     * @return 回傳包含總回收次數和回收重量的 Map
     */
    public Map<String, Object> getUserRecycleStats(String userName, String userTag) {
        Map<String, Object> recycleStats = new HashMap<>();
        recycleStats.put("recycleCount", 0);    // 初始化為 0
        recycleStats.put("totalWeight", 0.0);   // 初始化為 0.0

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            String sql = "SELECT COUNT(*) AS recycleCount, SUM(Recycle_Weight) AS totalWeight " +
                    "FROM " + TABLE_USER_RECYCLE_RECORD + " " +
                    "WHERE User_Name = ? AND User_Tag = ?";
            cursor = db.rawQuery(sql, new String[]{userName, userTag});

            if (cursor.moveToFirst()) {
                recycleStats.put("recycleCount", cursor.getInt(cursor.getColumnIndexOrThrow("recycleCount")));
                recycleStats.put("totalWeight", cursor.getDouble(cursor.getColumnIndexOrThrow("totalWeight")));
            }
        } catch (Exception e) {
            Log.e("DBHelper", "計算用戶回收統計錯誤", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return recycleStats;
    }


}
