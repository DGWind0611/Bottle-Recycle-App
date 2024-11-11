package com.fcu.android.bottlerecycleapp.ui.recycle_record;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fcu.android.bottlerecycleapp.R;
import com.fcu.android.bottlerecycleapp.SharedViewModel;
import com.fcu.android.bottlerecycleapp.database.DBHelper;
import com.fcu.android.bottlerecycleapp.database.entity.RecycleRecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecycleRecordActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ImageButton backButton;
    private RecycleRecordAdapter adapter;
    private List<RecycleRecord> records;
    private DBHelper dbHelper;
    private SharedViewModel sharedViewModel;
    private String userName;
    private String userTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_record);

        // 從 Intent 中取得 userName 和 userTag
        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");
        userTag = intent.getStringExtra("userTag");
        Log.d("RecycleRecordActivity", "Received userName: " + userName + ", userTag: " + userTag);

        backButton = findViewById(R.id.btn_back_to_personal_data_from_recycle_record);
        backButton.setOnClickListener(v -> finish());

        recyclerView = findViewById(R.id.rv_recycle_record);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new DBHelper(this);

        // 確保在取得 userName 和 userTag 後再加載記錄
        if (userName != null && userTag != null) {
            loadRecycleRecords();
        } else {
            Log.e("RecycleRecordActivity", "userName or userTag is null");
        }
    }


    // 將獲取數據的邏輯移到一個方法中
    private void loadRecycleRecords() {
        // 從資料庫中獲取數據
        records = dbHelper.getAllRecycleRecordsByUserId(userName, userTag); // 確保DBHelper有此方法來抓取所有紀錄
        Map<String, List<RecycleRecord>> groupedRecords = groupRecordsByMonth(records);

        // 這裡的adapter使用的是records，所以確保不為null
        if (records != null) {
            adapter = new RecycleRecordAdapter(records, dbHelper);
            recyclerView.setAdapter(adapter);
        } else {
            Log.e("RecycleRecordActivity", "Records list is null");
        }
    }

    /**
     * 將紀錄依照月份分組
     *
     * @param records 紀錄列表
     * @return 以月份為 key 的紀錄分組
     */
    @NonNull
    private Map<String, List<RecycleRecord>> groupRecordsByMonth(@NonNull List<RecycleRecord> records) {
        Map<String, List<RecycleRecord>> groupedRecords = new HashMap<>();
        if (records != null && !records.isEmpty()) { // 加入null檢查
            for (RecycleRecord record : records) {
                // 提取紀錄中的月份，日期格式為 "yyyy/MM/dd HH:mm:ss"
                String month = record.getRecycleTime().substring(0, 7); // 取得 "yyyy/MM"
                if (!groupedRecords.containsKey(month)) {
                    groupedRecords.put(month, new ArrayList<>());
                }
                groupedRecords.get(month).add(record);
            }
        } else {
            // 若紀錄列表為空或為null，則回傳空的分組
            Log.e("RecycleRecordActivity", "No recycle records available");
        }
        return groupedRecords;
    }
}
