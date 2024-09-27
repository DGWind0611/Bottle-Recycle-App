package com.fcu.android.bottlerecycleapp.recycle_record;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fcu.android.bottlerecycleapp.R;
import com.fcu.android.bottlerecycleapp.database.DBHelper;
import com.fcu.android.bottlerecycleapp.database.RecycleRecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecycleRecordActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecycleRecordAdapter adapter;
    private List<RecycleRecord> records;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_record);

        recyclerView = findViewById(R.id.rv_recycle_record);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new DBHelper(this);

        //TODO: 從資料庫中獲取數據
        int testUserId = 1; // 假設使用者 ID 為 1
        // 從資料庫中獲取數據
        records = dbHelper.getAllRecycleRecordsByUserId(testUserId); // 確保DBHelper有此方法來抓取所有紀錄
        Map<String, List<RecycleRecord>> groupedRecords = groupRecordsByMonth(records);

        adapter = new RecycleRecordAdapter(records, dbHelper);
        recyclerView.setAdapter(adapter);

    }

    /**
     * 將紀錄依照月份分組
     * @param records 紀錄列表
     * @return 以月份為 key 的紀錄分組
     */
    @NonNull
    private Map<String, List<RecycleRecord>> groupRecordsByMonth(@NonNull List<RecycleRecord> records) {
        Map<String, List<RecycleRecord>> groupedRecords = new HashMap<>();
        if (!records.isEmpty()) {
            for (RecycleRecord record : records) {
                // 提取紀錄中的月份，假設紀錄的日期格式為 "yyyy/MM/dd HH:mm"
                String month = record.getRecycleTime().substring(0, 7); // 取得 "yyyy/MM"
                if (!groupedRecords.containsKey(month)) {
                    groupedRecords.put(month, new ArrayList<>());
                }
                groupedRecords.get(month).add(record);
            }
        } else {
            // 若紀錄列表為空，則回傳空的分組
            groupedRecords = new HashMap<>();
            Log.e("RecycleRecordActivity", "No recycle records available");
        }
        return groupedRecords;
    }

}
