package com.fcu.android.bottlerecycleapp.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "bottle_recycle.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "bottle_recycle";

    public DBHelper(Context context, String name, CursorFactory factory,
                    int version) {
        super(context, name, factory, version);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 建立資料表 "User"
        String User = "CREATE TABLE IF NOT EXISTS User ("
                + "ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "User_Name TEXT NOT NULL,"
                + "E_mail TEXT NOT NULL,"
                + "Password TEXT NOT NULL,"
                + "Phone_Number TEXT,"
                + "Earn_Money REAL,"
                + "Donate_Money REAL"
                + ")";
        db.execSQL(User);

        // 建立資料表 "Staff"
        String Staff = "CREATE TABLE IF NOT EXISTS Staff ("
                + "Staff_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "Staff_Work_Area TEXT NOT NULL,"
                + "First_Name TEXT NOT NULL,"
                + "Last_Name TEXT NOT NULL"
                + ")";
        db.execSQL(Staff);

        // 建立資料表 "Donation_Record"
        String Donate_Record = "CREATE TABLE IF NOT EXISTS Donation_Record ("
                + "ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "money INTEGER NOT NULL,"
                + "Date TEXT NOT NULL,"
                + "Text TEXT,"
                + "User_ID INTEGER NOT NULL,"
                + "FOREIGN KEY(User_ID) REFERENCES User(ID)"
                + ")";
        db.execSQL(Donate_Record);

        // 建立資料表 "Notifications"
        String Notifications = "CREATE TABLE IF NOT EXISTS Notifications ("
                + "ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "User_ID INTEGER,"
                + "Tittle TEXT NOT NULL,"
                + "Text TEXT NOT NULL,"
                + "FOREIGN KEY(User_ID) REFERENCES User(ID)"
                + ")";
        db.execSQL(Notifications);

        // 建立資料表 "QR_Cord"
        String QR_Cord = "CREATE TABLE IF NOT EXISTS QR_Cord ("
                + "QR_Cord_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "Agency_Name TEXT NOT NULL,"
                + "QR_Cord_Origin_Data TEXT NOT NULL,"
                + "User_Custom_name TEXT"
                + ")";
        db.execSQL(QR_Cord);

        // 建立資料表 "RecycleStation"
        String RecycleStation = "CREATE TABLE IF NOT EXISTS RecycleStation ("
                + "Station_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "Location TEXT NOT NULL,"
                + "Status INTEGER NOT NULL,"
                + "Traffic INTEGER NOT NULL,"
                + "Set_Up_Date TEXT NOT NULL,"
                + "Fix_Date TEXT"
                + ")";
        db.execSQL(RecycleStation);

        // 建立資料表 "Remittance_Record"
        String Remittance_Record = "CREATE TABLE IF NOT EXISTS Remittance_Record ("
                + "Remittance_Record_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "Date TEXT NOT NULL,"
                + "Money REAL NOT NULL,"
                + "Text TEXT,"
                + "User_ID INTEGER NOT NULL,"
                + "FOREIGN KEY(User_ID) REFERENCES User(ID)"
                + ")";
        db.execSQL(Remittance_Record);

        // 建立資料表 "Station_Fix_Record"
        String Station_Fix_Record = "CREATE TABLE IF NOT EXISTS Station_Fix_Record ("
                + "Record_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "Fix_Date TEXT NOT NULL,"
                + "Fix_Reason INTEGER NOT NULL,"
                + "Fix_Staff INTEGER NOT NULL,"
                + "FOREIGN KEY(Fix_Staff) REFERENCES Staff(Staff_ID)"
                + ")";
        db.execSQL(Station_Fix_Record);

        // 建立資料表 "User_recycle_Record"
        String User_recycle_Record = "CREATE TABLE IF NOT EXISTS User_recycle_Record ("
                + "Record_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "Date TEXT NOT NULL,"
                + "Weight TEXT NOT NULL,"
                + "Price INTEGER NOT NULL,"
                + "User_ID INTEGER NOT NULL,"
                + "FOREIGN KEY(User_ID) REFERENCES User(ID)"
                + ")";
        db.execSQL(User_recycle_Record);
    }
    // User Table Add Data
    public void addUser(String User_Name, String E_mail, String Password, String Phone_Number, double Earn_Money, double Donate_Money) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO User (User_Name, E_mail, Password, Phone_Number, Earn_Money, Donate_Money) VALUES ('" + User_Name + "', '" + E_mail + "', '" + Password + "', '" + Phone_Number + "', '" + Earn_Money + "', '" + Donate_Money + "')");
    }
    // Staff Table Add Data
    public void addStaff(String Staff_Work_Area, String First_Name, String Last_Name) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO Staff (Staff_Work_Area, First_Name, Last_Name) VALUES ('" + Staff_Work_Area + "', '" + First_Name + "', '" + Last_Name + "')");
    }
    // Donation_Record Table Add Data
    public void addDonation_Record(int money, String Date, String Text, int User_ID) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO Donation_Record (money, Date, Text, User_ID) VALUES ('" + money + "', '" + Date + "', '" + Text + "', '" + User_ID + "')");
    }
    // Notifications Table Add Data
    public void addNotifications(int User_ID, String Tittle, String Text) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO Notifications (User_ID, Tittle, Text) VALUES ('" + User_ID + "', '" + Tittle + "', '" + Text + "')");
    }
    // QR_Cord Table Add Data
    public void addQR_Cord(String Agency_Name, String QR_Cord_Origin_Data, String User_Custom_name) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO QR_Cord (Agency_Name, QR_Cord_Origin_Data, User_Custom_name) VALUES ('" + Agency_Name + "', '" + QR_Cord_Origin_Data + "', '" + User_Custom_name + "')");
    }
    // Recycle_Station Table Add Data
    public void addRecycleStation(String Location, int Status, int Traffic, String Set_Up_Date, String Fix_Date) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO RecycleStation (Location, Status, Traffic, Set_Up_Date, Fix_Date) VALUES ('" + Location + "', '" + Status + "', '" + Traffic + "', '" + Set_Up_Date + "', '" + Fix_Date + "')");
    }
    // Remittance_Record Table Add Data
    public void addRemittance_Record(String Date, double Money, String Text, int User_ID) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO Remittance_Record (Date, Money, Text, User_ID) VALUES ('" + Date + "', '" + Money + "', '" + Text + "', '" + User_ID + "')");
    }
    // Station_Fix_Record Table Add Data
    public void addStation_Fix_Record(String Fix_Date, int Fix_Reason, int Fix_Staff) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO Station_Fix_Record (Fix_Date, Fix_Reason, Fix_Staff) VALUES ('" + Fix_Date + "', '" + Fix_Reason + "', '" + Fix_Staff + "')");
    }
    // User_recycle_Record Table Add Data
    public void addUser_recycle_Record(String Date, String Weight, int Price, int User_ID) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO User_recycle_Record (Date, Weight, Price, User_ID) VALUES ('" + Date + "', '" + Weight + "', '" + Price + "', '" + User_ID + "')");
    }
    // User Table Update Data
    public void updateUser(int ID, String User_Name, String E_mail, String Password, String Phone_Number, double Earn_Money, double Donate_Money) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE User SET User_Name = '" + User_Name + "', E_mail = '" + E_mail + "', Password = '" + Password + "', Phone_Number = '" + Phone_Number + "', Earn_Money = '" + Earn_Money + "', Donate_Money = '" + Donate_Money + "' WHERE ID = " + ID);
    }
    // Staff Table Update Data
    public void updateStaff(int Staff_ID, String Staff_Work_Area, String First_Name, String Last_Name) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE Staff SET Staff_Work_Area = '" + Staff_Work_Area + "', First_Name = '" + First_Name + "', Last_Name = '" + Last_Name + "' WHERE Staff_ID = " + Staff_ID);
    }
    // Donation_Record Table Update Data
    public void updateDonation_Record(int ID, int money, String Date, String Text, int User_ID) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE Donation_Record SET money = '" + money + "', Date = '" + Date + "', Text = '" + Text + "', User_ID = '" + User_ID + "' WHERE ID = " + ID);
    }
    // Notifications Table Update Data
    public void updateNotifications(int ID, int User_ID, String Tittle, String Text) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE Notifications SET User_ID = '" + User_ID + "', Tittle = '" + Tittle + "', Text = '" + Text + "' WHERE ID = " + ID);
    }
    // QR_Cord Table Update Data
    public void updateQR_Cord(int QR_Cord_ID, String Agency_Name, String QR_Cord_Origin_Data, String User_Custom_name) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE QR_Cord SET Agency_Name = '" + Agency_Name + "', QR_Cord_Origin_Data = '" + QR_Cord_Origin_Data + "', User_Custom_name = '" + User_Custom_name + "' WHERE QR_Cord_ID = " + QR_Cord_ID);
    }
    // RecycleStation Table Update Data
    public void updateRecycleStation(int Station_ID, String Location, int Status, int Traffic, String Set_Up_Date, String Fix_Date) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE RecycleStation SET Location = '" + Location + "', Status = '" + Status + "', Traffic = '" + Traffic + "', Set_Up_Date = '" + Set_Up_Date + "', Fix_Date = '" + Fix_Date + "' WHERE Station_ID = " + Station_ID);
    }
    // Remittance_Record Table Update Data
    public void updateRemittance_Record(int Remittance_Record_ID, String Date, double Money, String Text, int User_ID) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE Remittance_Record SET Date = '" + Date + "', Money = '" + Money + "', Text = '" + Text + "', User_ID = '" + User_ID + "' WHERE Remittance_Record_ID = " + Remittance_Record_ID);
    }
    // Station_Fix_Record Table Update Data
    public void updateStation_Fix_Record(int Record_ID, String Fix_Date, int Fix_Reason, int Fix_Staff) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE Station_Fix_Record SET Fix_Date = '" + Fix_Date + "', Fix_Reason = '" + Fix_Reason + "', Fix_Staff = '" + Fix_Staff + "' WHERE Record_ID = " + Record_ID);
    }
    // User_recycle_Record Table Update Data
    public void updateUser_recycle_Record(int Record_ID, String Date, String Weight, int Price, int User_ID) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE User_recycle_Record SET Date = '" + Date + "', Weight = '" + Weight + "', Price = '" + Price + "', User_ID = '" + User_ID + "' WHERE Record_ID = " + Record_ID);
    }
    // User Table Delete Data
    public void deleteUser(int ID) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM User WHERE ID = " + ID);
    }
    // Staff Table Delete Data
    public void deleteStaff(int Staff_ID) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM Staff WHERE Staff_ID = " + Staff_ID);
    }
    // Donation_Record Table Delete Data
    public void deleteDonation_Record(int ID) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM Donation_Record WHERE ID = " + ID);
    }
    // Notifications Table Delete Data
    public void deleteNotifications(int ID) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM Notifications WHERE ID = " + ID);
    }
    // QR_Cord Table Delete Data
    public void deleteQR_Cord(int QR_Cord_ID) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM QR_Cord WHERE QR_Cord_ID = " + QR_Cord_ID);
    }
    // RecycleStation Table Delete Data
    public void deleteRecycleStation(int Station_ID) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM RecycleStation WHERE Station_ID = " + Station_ID);
    }
    // Remittance_Record Table Delete Data
    public void deleteRemittance_Record(int Remittance_Record_ID) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM Remittance_Record WHERE Remittance_Record_ID = " + Remittance_Record_ID);
    }
    // Station_Fix_Record Table Delete Data
    public void deleteStation_Fix_Record(int Record_ID) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM Station_Fix_Record WHERE Record_ID = " + Record_ID);
    }
    // User_recycle_Record Table Delete Data
    public void deleteUser_recycle_Record(int Record_ID) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM User_recycle_Record WHERE Record_ID = " + Record_ID);
    }
    // TODO 查詢資料

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // onUpgrade 則是如果資料庫結構有改變了就會觸發 onUpgrade
    }
}
