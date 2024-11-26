package com.fcu.android.bottlerecycleapp.service;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.fcu.android.bottlerecycleapp.R;
import com.fcu.android.bottlerecycleapp.database.DBHelper;
import com.fcu.android.bottlerecycleapp.database.entity.RecycleRecord;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseSync {

    private final DBHelper dbHelper;
    private final Context context;
    private final DatabaseReference databaseReference;

    public FirebaseSync(Context context) {
        this.context = context;
        this.dbHelper = new DBHelper(context);
        this.databaseReference = FirebaseDatabase.getInstance().getReference("recycle_records");
    }

    public void startListeningForUpdates() {
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // 當有新數據時觸發
                processRecycleRecord(snapshot);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // 當數據被更改時觸發
                processRecycleRecord(snapshot);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                // 可選：處理數據刪除情況
                Log.d("FirebaseSync", "Data removed: " + snapshot.getKey());
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // 可選：處理數據移動情況
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
            String userName = snapshot.child("user_name").getValue(String.class);
            String userTag = snapshot.child("user_tag").getValue(String.class);
            String recycleStationIdStr = snapshot.child("recycle_station_id").getValue(String.class);
            String recycleDate = snapshot.child("recycle_date").getValue(String.class);
            Double recycleWeight = snapshot.child("recycle_weight").getValue(Double.class);
            Double earnMoney = snapshot.child("earn_money").getValue(Double.class);
            Boolean isNotified = snapshot.child("is_notified").getValue(Boolean.class);

            if (recordId == null || userName == null || userTag == null || recycleStationIdStr == null ||
                    recycleDate == null || recycleWeight == null || earnMoney == null) {
                Log.e("FirebaseSync", "Invalid record, skipping");
                return;
            }

            int recycleStationId = Integer.parseInt(recycleStationIdStr);

            // 檢查是否需要通知
            if (isNotified != null && isNotified) {
                Log.d("FirebaseSync", "Record already notified: " + recordId);
                return;
            }

            // 創建 RecycleRecord
            RecycleRecord record = new RecycleRecord(recordId, userName, userTag, recycleStationId, recycleDate, recycleWeight, earnMoney, 0);

            // 新增或更新資料
            if (dbHelper.addRecycleRecord(record)) {
                Log.d("FirebaseSync", "Record successfully synced: " + recordId);
                showSuccessDialog("您以成功回收寶特瓶！！");
                updateFirebaseRecordNotified(recordId); // 更新 Firebase 中的 is_notified 標記
            } else {
                Log.e("FirebaseSync", "Failed to sync record: " + recordId);
            }
        } catch (Exception e) {
            Log.e("FirebaseSync", "Error processing record", e);
        }
    }

    private void updateFirebaseRecordNotified(String recordId) {
        if (recordId == null) return;

        DatabaseReference recordRef = databaseReference.child(recordId);
        recordRef.child("is_notified").setValue(true)
                .addOnSuccessListener(aVoid -> Log.d("FirebaseSync", "Record marked as notified: " + recordId))
                .addOnFailureListener(e -> Log.e("FirebaseSync", "Failed to update is_notified for record: " + recordId, e));
    }


    private void showSuccessDialog(String message) {
        new Handler(Looper.getMainLooper()).post(() -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("通知")
                    .setMessage(message)
                    .setPositiveButton("確定", (dialog, which) -> dialog.dismiss())
                    .setCancelable(false)
                    .show();
        });
    }

    private void showNotification(String title, String message) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = "recycle_update_channel";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Recycle Updates", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        notificationManager.notify(1, builder.build());
    }
}
