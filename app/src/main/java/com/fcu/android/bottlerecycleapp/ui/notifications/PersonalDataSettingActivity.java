package com.fcu.android.bottlerecycleapp.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.fcu.android.bottlerecycleapp.Gender;
import com.fcu.android.bottlerecycleapp.R;
import com.fcu.android.bottlerecycleapp.database.DBHelper;
import com.fcu.android.bottlerecycleapp.database.User;

public class PersonalDataSettingActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private Button btnConfirm;
    private EditText etUserName, etUserEmail, etGender, etUserPhone, etPassword;
    private DBHelper dbHelper = new DBHelper(this, "bottle_recycle.db", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data_setting);

        // 初始化 UI 元件
        btnBack = findViewById(R.id.btn_back_to_personal_data);
        btnConfirm = findViewById(R.id.btn_confirm_setting);
        etUserName = findViewById(R.id.et_username_setting);
        etUserEmail = findViewById(R.id.et_email_setting);
        etGender = findViewById(R.id.et_gender_setting);
        etUserPhone = findViewById(R.id.et_phone_setting);
        etPassword = findViewById(R.id.et_password_setting);

        User user = (User) getIntent().getSerializableExtra("userData");

        // 設置可編輯切換的邏輯
        setupEditableField(findViewById(R.id.btn_edit_username), etUserName);
        setupEditableField(findViewById(R.id.btn_edit_email), etUserEmail);
        setupEditableField(findViewById(R.id.btn_edit_gender), etGender);
        setupEditableField(findViewById(R.id.btn_edit_phoneNumber), etUserPhone);
        setupEditableField(findViewById(R.id.btn_edit_password), etPassword);

        // 確認按鈕點擊事件
        btnConfirm.setOnClickListener(v -> {
            user.setEmail(etUserEmail.getText().toString());
            user.setUserName(etUserName.getText().toString());
            user.setGender(Gender.valueOf(etGender.getText().toString()));
            user.setPhoneNumber(etUserPhone.getText().toString());
            user.setPassword(etPassword.getText().toString());
            dbHelper.updateUser(user);
        });

        // 返回按鈕點擊事件
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(PersonalDataSettingActivity.this, PersonalDataFragment.class);
            intent.putExtra("userData", user);
            finish();
        });

        // 邊緣到邊緣處理
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // 設置每個欄位的編輯模式切換
    private void setupEditableField(@NonNull ImageButton editButton, EditText editText) {
        editButton.setOnClickListener(v -> {
            boolean isEditable = editText.isEnabled();
            editText.setEnabled(!isEditable);
            editText.requestFocus();
            editButton.setImageResource(isEditable ? R.drawable.pen : R.drawable.confirmation);
        });
    }
}
