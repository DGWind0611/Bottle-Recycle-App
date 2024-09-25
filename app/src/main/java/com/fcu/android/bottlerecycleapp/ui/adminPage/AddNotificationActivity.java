package com.fcu.android.bottlerecycleapp.ui.adminPage;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.fcu.android.bottlerecycleapp.R;
import com.fcu.android.bottlerecycleapp.database.DBHelper;
import com.fcu.android.bottlerecycleapp.database.Notification;
import com.fcu.android.bottlerecycleapp.database.Role;
import com.fcu.android.bottlerecycleapp.database.Type;

public class AddNotificationActivity extends AppCompatActivity {

    private EditText etNotificationContent, etNotificationDate, etNotificationTime, etSpecificUser;
    private TextView tvSpecificUser;
    private Spinner spNotificationType;
    private Button btnAddNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notification);

        etNotificationContent = findViewById(R.id.et_notification_content);
        etNotificationDate = findViewById(R.id.et_notification_date);
        etNotificationTime = findViewById(R.id.et_notification_time);
        etSpecificUser = findViewById(R.id.et_specific_user);

        tvSpecificUser = findViewById(R.id.tv_specific_user);

        spNotificationType = findViewById(R.id.sp_notification_type);

        btnAddNotification = findViewById(R.id.btn_add_notification);
        spNotificationType = findViewById(R.id.sp_notification_type);

        btnAddNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNotification();
            }
        });
        setUpNotificationType(spNotificationType, new Notification());

    }

    private void addNotification() {
        String content = etNotificationContent.getText().toString();
        String date = etNotificationDate.getText().toString();
        String time = etNotificationTime.getText().toString();
        String type = spNotificationType.getSelectedItem().toString(); // 根據你的 Spinner 資料

        // 確認資料是否完整
        if (content.isEmpty() || date.isEmpty() || time.isEmpty()) {
            Toast.makeText(this, "請填寫所有欄位", Toast.LENGTH_SHORT).show();
            return;
        }

        Notification notification = new Notification();
        notification.setContent(content);
        notification.setDate(date);
        notification.setTime(time);
        notification.setType(Type.valueOf(type));

        DBHelper dbHelper = new DBHelper(this);
        boolean isAdded = dbHelper.addNotification(notification); // 添加到資料庫的方法

        if (isAdded) {
            Toast.makeText(this, "通知新增成功", Toast.LENGTH_SHORT).show();
            finish(); // 返回上一頁
        } else {
            Toast.makeText(this, "新增失敗，請稍後再試", Toast.LENGTH_SHORT).show();
        }
    }

    private void setUpNotificationType(@NonNull Spinner spinner, @NonNull Notification notification) {
        ArrayAdapter<String> NotificationTypeAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new String[]{
                        Type.ALL_USERS.name(),
                        Type.SPECIFIC_USER.name(),
                        Type.GROUP_NOTIFICATION.name()
                });

        NotificationTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(NotificationTypeAdapter);

        // 設置默認角色為 USER
        spinner.setSelection(NotificationTypeAdapter.getPosition(Role.USER.getChineseValue()));

        // 監聽用戶選擇的變化
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedType = (String) parent.getItemAtPosition(position);
                Type type = Type.valueOf(selectedType);
                if (type != null) {
                    notification.setType(type);
                }
                if (notification.getType() == Type.SPECIFIC_USER) {
                    tvSpecificUser.setVisibility(View.VISIBLE);
                    etSpecificUser.setVisibility(View.VISIBLE);
                } else {
                    tvSpecificUser.setVisibility(View.GONE);
                    etSpecificUser.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 無操作
            }
        });
    }
}
