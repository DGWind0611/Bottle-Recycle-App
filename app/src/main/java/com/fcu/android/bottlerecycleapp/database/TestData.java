package com.fcu.android.bottlerecycleapp.database;

import com.fcu.android.bottlerecycleapp.Gender;

import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestData {

    public List<User> testUser() {
        List<User> users = new ArrayList<>();
        Date date = new Date();
        users.add(new User(1, "測試使用者1", "test@test.com", BCrypt.hashpw("Test123456", BCrypt.gensalt())
                , "0911000001", 0.0, "1" + date.getTime(), 0.0, Gender.UNDEFINED, Role.USER, null));
        users.add(new User(2, "測試使用者2", "test2@test.com", BCrypt.hashpw("Test123456", BCrypt.gensalt())
                , "0911000002", 0.0, "2" + date.getTime(), 0.0, Gender.UNDEFINED, Role.USER, null));
        users.add(new User(3, "測試使用者3", "test3@test.com", BCrypt.hashpw("Test123456", BCrypt.gensalt())
                , "0911000003", 0.0, "3" + date.getTime(), 0.0, Gender.UNDEFINED, Role.USER, null));
        users.add(new User(4, "測試使用者4", "test4@test.com", BCrypt.hashpw("Test123456", BCrypt.gensalt())
                , "0911000004", 0.0, "4" + date.getTime(), 0.0, Gender.UNDEFINED, Role.USER, null));
        users.add(new User(5, "測試使用者5", "測試使用5", BCrypt.hashpw("Test123456", BCrypt.gensalt())
                , "0911000005", 0.0, "5" + date.getTime(), 0.0, Gender.UNDEFINED, Role.USER, null));
        users.add(new User(6, "測試管理員", "admin@test.com", BCrypt.hashpw("Test123456", BCrypt.gensalt())
                , "0911000006", 0.0, "6" + date.getTime(), 0.0, Gender.UNDEFINED, Role.ADMIN, null));
        return users;
    }

    public List<RecycleStation> testStation() {
        List<RecycleStation> stations = new ArrayList<>();
        stations.add(new RecycleStation(1, "測試站點1", "地址1",123.00000, 123.00000));
        stations.add(new RecycleStation(2, "測試站點2", "地址2",123.00000, 123.00000));
        stations.add(new RecycleStation(3, "測試站點3", "地址3",123.00000, 123.00000));
        stations.add(new RecycleStation(4, "測試站點4", "地址4",123.00000, 123.00000));
        stations.add(new RecycleStation(5, "測試站點5", "地址5",123.00000, 123.00000));
        return stations;
    }

    public List<MyActivity> testActivity() {
        List<MyActivity> activities = new ArrayList<>();
        Date date = new Date();
        //假定目標時間為當前時間的一個月後

        activities.add(new MyActivity(1, "測試活動1", "活動內容1",date.getTime() + "",new Date (date.getTime() + 2592000000L).getTime() + "", 100));
        activities.add(new MyActivity(2, "測試活動2", "活動內容2",date.getTime() + "",new Date (date.getTime() + 2592000000L).getTime() + "", 1));
        activities.add(new MyActivity(3, "測試活動3", "活動內容3",date.getTime() + "",new Date (date.getTime() + 2592000000L).getTime() + "", 1));
        activities.add(new MyActivity(4, "測試活動4", "活動內容4",date.getTime() + "",new Date (date.getTime() + 2592000000L).getTime() + "", 100));
        activities.add(new MyActivity(5, "測試活動5", "活動內容5",date.getTime() + "",new Date (date.getTime() + 2592000000L).getTime() + "", 100));
        return activities;
    }
}
