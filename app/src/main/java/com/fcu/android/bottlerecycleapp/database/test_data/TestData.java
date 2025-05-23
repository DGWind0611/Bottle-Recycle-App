package com.fcu.android.bottlerecycleapp.database.test_data;

import com.fcu.android.bottlerecycleapp.database.entity.Gender;
import com.fcu.android.bottlerecycleapp.database.entity.MyActivity;
import com.fcu.android.bottlerecycleapp.database.entity.RecycleStation;
import com.fcu.android.bottlerecycleapp.database.entity.Role;
import com.fcu.android.bottlerecycleapp.database.entity.User;

import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestData {

    public List<User> testUser() {
        List<User> users = new ArrayList<>();
        Date date = new Date();
        users.add(new User("測試使用者1", "0001", "test@test.com", BCrypt.hashpw("Test123456", BCrypt.gensalt())
                , "0911000001", "1" + date.getTime(), 0.0, Gender.UNDEFINED, Role.USER, null));
        users.add(new User("測試使用者2", "0002", "test2@test.com", BCrypt.hashpw("Test123456", BCrypt.gensalt())
                , "0911000002", "2" + date.getTime(), 0.0, Gender.UNDEFINED, Role.USER, null));
        users.add(new User("測試使用者3", "4395", "test3@test.com", BCrypt.hashpw("Test123456", BCrypt.gensalt())
                , "0911000003", "3" + date.getTime(), 0.0, Gender.UNDEFINED, Role.USER, null));
        users.add(new User("測試使用者4", "5622", "test4@test.com", BCrypt.hashpw("Test123456", BCrypt.gensalt())
                , "0911000004", "4" + date.getTime(), 0.0, Gender.UNDEFINED, Role.USER, null));
        users.add(new User("測試使用者5", "2203", "測試使用5", BCrypt.hashpw("Test123456", BCrypt.gensalt())
                , "0911000005", "5" + date.getTime(), 0.0, Gender.UNDEFINED, Role.USER, null));
        users.add(new User("測試管理員", "1256", "admin@test.com", BCrypt.hashpw("Test123456", BCrypt.gensalt())
                , "0911000006", "6" + date.getTime(), 0.0, Gender.UNDEFINED, Role.ADMIN, null));
        return users;
    }

    public List<RecycleStation> testStation() {
        List<RecycleStation> stations = new ArrayList<>();
        stations.add(new RecycleStation(1, "測試站點1", "地址1", 23.701327108535494, 120.43032060792059, 100.50, 50.20));
        stations.add(new RecycleStation(2, "測試站點2", "地址2", 23.70265710296463, 120.42952217510486, 80, 78));
        stations.add(new RecycleStation(3, "測試站點3", "地址3", 23.702789275583562, 120.4281283008651, 125.25, 5.60));
        stations.add(new RecycleStation(4, "測試站點4", "地址4", 23.70063319300604, 120.42862450205226, 12, 11.2));
        stations.add(new RecycleStation(5, "測試站點5", "地址5", 23.699848403143218, 120.43112355166447, 100, 35));
        return stations;
    }

    public List<MyActivity> testActivity() {
        List<MyActivity> activities = new ArrayList<>();
        Date date = new Date();
        //假定目標時間為當前時間的一個月後

        activities.add(new MyActivity(1, "測試活動1", "活動內容1", date.getTime() + "", new Date(date.getTime() + 2592000000L).getTime() + "", 100));
        activities.add(new MyActivity(2, "測試活動2", "活動內容2", date.getTime() + "", new Date(date.getTime() + 2592000000L).getTime() + "", 1));
        activities.add(new MyActivity(3, "測試活動3", "活動內容3", date.getTime() + "", new Date(date.getTime() + 2592000000L).getTime() + "", 1));
        activities.add(new MyActivity(4, "測試活動4", "活動內容4", date.getTime() + "", new Date(date.getTime() + 2592000000L).getTime() + "", 100));
        activities.add(new MyActivity(5, "測試活動5", "活動內容5", date.getTime() + "", new Date(date.getTime() + 2592000000L).getTime() + "", 100));
        return activities;
    }
}
