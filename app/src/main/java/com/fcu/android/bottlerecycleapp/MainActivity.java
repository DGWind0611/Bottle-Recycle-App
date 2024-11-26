package com.fcu.android.bottlerecycleapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.fcu.android.bottlerecycleapp.database.DBHelper;
import com.fcu.android.bottlerecycleapp.database.entity.RecycleRecord;
import com.fcu.android.bottlerecycleapp.database.entity.User;
import com.fcu.android.bottlerecycleapp.databinding.ActivityMainBinding;
import com.fcu.android.bottlerecycleapp.ui.login.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private User user;
    private DatabaseReference databaseReference;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 初始化 DBHelper
        dbHelper = new DBHelper(this);
        // 初始化 Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference();

        // 設置 BottomNavigationView
        setupNavigation();

        // 處理用戶數據
        handleUserSession();

        // 測試從 Firebase 獲取回收記錄
        fetchRecycleRecords();
    }

    private void setupNavigation() {
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_recycle_map, R.id.navigation_PersonalData, R.id.navigation_qrcode)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        // 隱藏 ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    private void handleUserSession() {
        user = (User) getIntent().getSerializableExtra("user");
        if (user == null || user.getUserName() == null || user.getUserTag() == null) {
            showLoginErrorDialog();
            return;
        }
        Log.d("MainActivity", "User: " + user.getUserName() + ", Tag: " + user.getUserTag());
        SharedViewModel sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        sharedViewModel.setData(user);
    }

    private void showLoginErrorDialog() {
        new AlertDialog.Builder(this)
                .setTitle("錯誤")
                .setMessage("請重新登入")
                .setPositiveButton("確定", (dialog, which) -> {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish(); // 確保返回鍵不會回到此頁面
                })
                .show();
    }

    private void fetchRecycleRecords() {
        databaseReference.child("recycle_records").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean isSuccess = true;
                for (DataSnapshot recordSnapshot : dataSnapshot.getChildren()) {
                    try {
                        // 從 Firebase 提取資料
                        String recordId = recordSnapshot.getKey(); // 使用 Firebase 的自動生成 key 作為唯一 ID
                        String userName = recordSnapshot.child("user_name").getValue(String.class);
                        String userTag = recordSnapshot.child("user_tag").getValue(String.class);
                        String recycleStationIdStr = recordSnapshot.child("recycle_station_id").getValue(String.class);
                        String recycleDate = recordSnapshot.child("recycle_date").getValue(String.class);
                        Double recycleWeight = recordSnapshot.child("recycle_weight").getValue(Double.class);
                        Double earnMoney = recordSnapshot.child("earn_money").getValue(Double.class);

                        if (recordId == null || userName == null || userTag == null || recycleStationIdStr == null ||
                                recycleDate == null || recycleWeight == null || earnMoney == null) {
                            Log.e("FirebaseToSQLite", "Invalid record detected, skipping");
                            continue;
                        }

                        // 將 recycleStationId 轉換為整數
                        int recycleStationId = Integer.parseInt(recycleStationIdStr);

                        // 創建 RecycleRecord 物件
                        RecycleRecord record = new RecycleRecord(
                                recordId,
                                userName,
                                userTag,
                                recycleStationId,
                                recycleDate,
                                recycleWeight,
                                earnMoney
                        );

                        // 使用 addRecycleRecord 方法覆蓋或新增紀錄
                        if (!dbHelper.addRecycleRecord(record)) {
                            Log.e("FirebaseToSQLite", "Failed to add or replace record for user: " + userName);
                            isSuccess = false;
                        }

                    } catch (Exception e) {
                        Log.e("FirebaseToSQLite", "Error processing record", e);
                        isSuccess = false;
                    }
                }

                if (isSuccess) {
                    Log.d("FirebaseToSQLite", "所有資料已成功同步到 SQLite");
                    Toast.makeText(MainActivity.this, "回收紀錄同步成功", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("FirebaseToSQLite", "部分資料同步失敗");
                    Toast.makeText(MainActivity.this, "部分資料同步失敗，請檢查日誌", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("FirebaseToSQLite", "從 Firebase 獲取資料失敗", databaseError.toException());
                Toast.makeText(MainActivity.this, "從 Firebase 獲取資料失敗", Toast.LENGTH_SHORT).show();
            }
        });


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
