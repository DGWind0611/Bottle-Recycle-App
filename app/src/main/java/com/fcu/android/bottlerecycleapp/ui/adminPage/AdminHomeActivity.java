package com.fcu.android.bottlerecycleapp.ui.adminPage;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.fcu.android.bottlerecycleapp.R;

public class AdminHomeActivity extends AppCompatActivity {

    private Button btnAddUser;
    private Button btnAddRecycleStation;
    private Button btnAddRecycleRecord;
    private Button btnAddNotification;
    private Button btnAddActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnAddUser = findViewById(R.id.btn_admin_add_station);
        btnAddRecycleStation = findViewById(R.id.btn_admin_add_station);
        btnAddRecycleRecord = findViewById(R.id.btn_admin_add_recycle_records);
        btnAddNotification = findViewById(R.id.btn_admin_add_notification);
        btnAddActivity = findViewById(R.id.btn_admin_add_activity);

        btnAddUser.setOnClickListener(v -> {
            Intent intent = new Intent(AdminHomeActivity.this, AddUserActivity.class);
            startActivity(intent);
            finish();
        });

        btnAddRecycleStation.setOnClickListener(v -> {
            Intent intent = new Intent(AdminHomeActivity.this, AddReycleStationActivity.class);
            startActivity(intent);
            finish();
        });

        btnAddRecycleRecord.setOnClickListener(v -> {
            Intent intent = new Intent(AdminHomeActivity.this, AddRecycleRecordsActivity.class);
            startActivity(intent);
            finish();
        });

        btnAddNotification.setOnClickListener(v -> {
            Intent intent = new Intent(AdminHomeActivity.this, AddNotificationActivity.class);
            startActivity(intent);
            finish();
        });

        btnAddActivity.setOnClickListener(v -> {
            Intent intent = new Intent(AdminHomeActivity.this, AddActivityActivity.class);
            startActivity(intent);
            finish();
        });
    }
}