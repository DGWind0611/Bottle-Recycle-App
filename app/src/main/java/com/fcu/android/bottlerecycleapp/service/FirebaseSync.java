package com.fcu.android.bottlerecycleapp.service;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fcu.android.bottlerecycleapp.database.DBHelper;
import com.fcu.android.bottlerecycleapp.database.entity.RecycleRecord;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashSet;
import java.util.Set;

public class FirebaseSync {

    public interface OnDataChangedListener {
        void onNewRecords(int newRecordCount);
    }

    private final DBHelper dbHelper;
    private final Context context;
    private final DatabaseReference databaseReference;
    private OnDataChangedListener onDataChangedListener;

    public FirebaseSync(Context context) {
        this.context = context;
        this.dbHelper = new DBHelper(context);
        this.databaseReference = FirebaseDatabase.getInstance().getReference("recycle_records");
    }

    public void setOnDataChangedListener(OnDataChangedListener listener) {
        this.onDataChangedListener = listener;
    }

    public void startListeningForUpdates() {
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                processRecycleRecord(snapshot);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                processRecycleRecord(snapshot);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Log.d("FirebaseSync", "Data removed: " + snapshot.getKey());
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.d("FirebaseSync", "Data moved: " + snapshot.getKey());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseSync", "DatabaseError: " + error.getMessage());
            }
        });
    }

    private void processRecycleRecord(DataSnapshot snapshot) {
        try {
            String recordId = snapshot.getKey();
            if (recordId == null) {
                Log.d("FirebaseSync", "Record ID is null, skipping.");
                return;
            }

            String userName = snapshot.child("user_name").getValue(String.class);
            String userTag = snapshot.child("user_tag").getValue(String.class);
            String recycleStationIdStr = snapshot.child("recycle_station_id").getValue(String.class);
            String recycleDate = snapshot.child("recycle_date").getValue(String.class);
            Double recycleWeight = snapshot.child("recycle_weight").getValue(Double.class);
            Double earnMoney = snapshot.child("earn_money").getValue(Double.class);

            if (userName == null || userTag == null || recycleStationIdStr == null ||
                    recycleDate == null || recycleWeight == null || earnMoney == null) {
                Log.e("FirebaseSync", "Invalid record, skipping");
                return;
            }

            int recycleStationId = Integer.parseInt(recycleStationIdStr);

            // 檢查紀錄是否已經同步過
            if (!dbHelper.isRecordSynced(recordId)) {
                RecycleRecord record = new RecycleRecord(recordId, userName, userTag, recycleStationId, recycleDate, recycleWeight, earnMoney, 1);

                if (dbHelper.addRecycleRecord(record)) {
                    Log.d("FirebaseSync", "Record successfully synced: " + recordId);

                    // 標記為已同步
                    dbHelper.markRecordAsSynced(recordId);

                    // 發送通知
                    if (onDataChangedListener != null) {
                        onDataChangedListener.onNewRecords(1);
                    }
                }
            }

        } catch (Exception e) {
            Log.e("FirebaseSync", "Error processing record", e);
        }
    }
}

