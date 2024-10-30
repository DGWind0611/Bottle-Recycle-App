package com.fcu.android.bottlerecycleapp.ui.notification;

import android.media.Image;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fcu.android.bottlerecycleapp.R;
import com.fcu.android.bottlerecycleapp.database.DBHelper;
import com.fcu.android.bottlerecycleapp.database.entity.Notification;

import java.util.ArrayList;

public class NotificationActivity extends AppCompatActivity {


    private RecyclerView rvNotification;
    private NotificationAdapter adapter;
    private ArrayList<Notification> notificationItems;
    private ImageButton btnBackToPersonalData;
    private DBHelper dbHelper;
    private String userName;
    private String userTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_notification);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        btnBackToPersonalData = findViewById(R.id.btn_back_to_personal_data_from_notification);

        btnBackToPersonalData.setOnClickListener(v -> {
            finish();
        });

        rvNotification = findViewById(R.id.recyclerView_notifications);
        rvNotification.setLayoutManager(new LinearLayoutManager(this));

        notificationItems = new ArrayList<>();
        dbHelper = new DBHelper(this);
        //TODO: 完成通知功能
//        userName = getIntent().getIntExtra("userId", 0);
//        notificationItems = dbHelper.getUserNotifications(userId);

        adapter = new NotificationAdapter(notificationItems);
        rvNotification.setAdapter(adapter);
    }

}