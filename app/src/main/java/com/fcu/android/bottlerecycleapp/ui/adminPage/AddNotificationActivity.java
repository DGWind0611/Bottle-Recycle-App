package com.fcu.android.bottlerecycleapp.ui.adminPage;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.fcu.android.bottlerecycleapp.custom_fuction_class.DatePicker;
import com.fcu.android.bottlerecycleapp.R;
import com.fcu.android.bottlerecycleapp.database.DBHelper;
import com.fcu.android.bottlerecycleapp.database.entity.Notification;
import com.fcu.android.bottlerecycleapp.database.entity.Role;
import com.fcu.android.bottlerecycleapp.database.entity.Type;

public class AddNotificationActivity extends AppCompatActivity {

    private EditText etNotificationContent, etNotificationDate, etNotificationTime, etSpecificUser;
    private TextView tvSpecificUser;
    private Spinner spNotificationType;
    private Button btnAddNotification;
    private ImageButton btnCalender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notification);

        initializeViews();
        setUpNotificationType(spNotificationType);

        btnAddNotification.setOnClickListener(v -> addNotification());
        btnCalender.setOnClickListener(v -> DatePicker.showDateTimePickerDialog(
                AddNotificationActivity.this, etNotificationDate, etNotificationTime));
    }

    // 初始化視圖
    private void initializeViews() {
        etNotificationContent = findViewById(R.id.et_notification_content);
        etNotificationDate = findViewById(R.id.et_notification_date);
        etNotificationTime = findViewById(R.id.et_notification_time);
        etSpecificUser = findViewById(R.id.et_specific_user);
        tvSpecificUser = findViewById(R.id.tv_specific_user);
        spNotificationType = findViewById(R.id.sp_notification_type);
        btnAddNotification = findViewById(R.id.btn_add_notification);
        btnCalender = findViewById(R.id.btn_calender_annoncement_notification);
    }

    private void addNotification() {
        if (!isInputValid()) {
            Toast.makeText(this, "請填寫所有欄位", Toast.LENGTH_SHORT).show();
            return;
        }

        Notification notification = createNotificationFromInput();
        int addResult = saveNotificationToDatabase(notification);

        handleAddResult(addResult);
    }

    // 驗證輸入是否合法
    private boolean isInputValid() {
        return !etNotificationContent.getText().toString().isEmpty() &&
                !etNotificationDate.getText().toString().isEmpty();
    }

    // 從用戶輸入創建通知對象
    private Notification createNotificationFromInput() {
        Notification notification = new Notification();
        notification.setContent(etNotificationContent.getText().toString());
        notification.setDate(etNotificationDate.getText().toString());
        notification.setTime(etNotificationTime.getText().toString()); // 考慮合併日期和時間
        notification.setType(Type.valueOf(spNotificationType.getSelectedItem().toString()));

        if (notification.getType() == Type.SPECIFIC_USER) {
            notification.setUserId(etSpecificUser.getId());
        }

        return notification;
    }

    // 將通知保存到資料庫
    private int saveNotificationToDatabase(Notification notification) {
        DBHelper dbHelper = new DBHelper(this);
        return dbHelper.addNotification(notification) ? 1 : 0; // 1=添加成功, 0=添加失敗
    }

    // 處理添加結果
    private void handleAddResult(int addResult) {
        switch (addResult) {
            case 1:
                Toast.makeText(this, "通知新增成功", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case 0:
                Toast.makeText(this, "新增失敗，請稍後再試", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(this, "該使用者不存在", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    // 設定通知類型 Spinner
    private void setUpNotificationType(@NonNull Spinner spinner) {
        ArrayAdapter<String> NotificationTypeAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new String[]{
                        Type.ALL_USERS.name(),
                        Type.SPECIFIC_USER.name(),
                        Type.GROUP_NOTIFICATION.name()
                });

        NotificationTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(NotificationTypeAdapter);

        // 設定默認選擇為 ALL_USERS
        spinner.setSelection(NotificationTypeAdapter.getPosition(Type.ALL_USERS.name()));
        spinner.setOnItemSelectedListener(notificationTypeSelectedListener);
    }

    // Spinner 的選擇監聽器
    private final AdapterView.OnItemSelectedListener notificationTypeSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String selectedType = (String) parent.getItemAtPosition(position);
            Type type = Type.valueOf(selectedType);

            if (type == Type.SPECIFIC_USER) {
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
    };
}
