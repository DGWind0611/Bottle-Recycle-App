package com.fcu.android.bottlerecycleapp.ui.sign_up;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fcu.android.bottlerecycleapp.database.entity.Gender;
import com.fcu.android.bottlerecycleapp.R;
import com.fcu.android.bottlerecycleapp.database.DBHelper;
import com.fcu.android.bottlerecycleapp.database.entity.User;
import com.fcu.android.bottlerecycleapp.ui.login.LoginActivity;

import java.util.Random;

public class SignUpActivity extends AppCompatActivity {

    private EditText etUserName;
    private EditText etEmail;
    private EditText etPhoneNumber;
    private TextView tvBackToLogin;
    private Button btnSignUpNext;
    private DBHelper dbHelper = new DBHelper(this);
    private ImageButton btnBackToLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize views
        etUserName = findViewById(R.id.et_user_email);
        etEmail = findViewById(R.id.et_email);
        etPhoneNumber = findViewById(R.id.et_phone_number);
        btnSignUpNext = findViewById(R.id.btn_sing_up_next_page);
        tvBackToLogin = findViewById(R.id.tv_back_to_login2);
        btnBackToLog = findViewById(R.id.btn_back_to_signup);

        btnSignUpNext.setOnClickListener(v -> {
            String userName = etUserName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String phoneNumber = etPhoneNumber.getText().toString().trim();

            if (userName.isEmpty() || email.isEmpty() || phoneNumber.isEmpty()) {
                Toast.makeText(SignUpActivity.this, "請填寫相關資料", Toast.LENGTH_SHORT).show();
            } else if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                etEmail.setError("請輸入正確的電子郵件格式");
            } else if (!phoneNumber.matches("^09\\d{8}$")) {
                etPhoneNumber.setError("請輸入正確的手機號碼格式");
            } else if (!dbHelper.checkEmail(email)) {
                etEmail.setError("此電子郵件已經註冊過");
            } else if (!dbHelper.checkPhoneNumber(phoneNumber)) {
                etPhoneNumber.setError("此手機號碼已經註冊過");
            } else {
                User user = new User();
                user.setUserName(userName);
                user.setEmail(email);
                user.setPhoneNumber(phoneNumber);
                user.setEarnMoney(0.0);
                user.setDonateMoney(0.0);
                user.setGender(Gender.UNDEFINED);
                Random random = new Random();
                String userTag = String.valueOf(10000 + random.nextInt() * 1000);
                //檢查同一個userName是否有相同的userTag
                while (dbHelper.findUserByNameAndTag(user.getUserName(), userTag) != null) {
                    userTag = String.valueOf(10000 + random.nextInt() * 1000);
                }
                user.setUserTag(userTag);
                // 將資料傳遞給 SignUp2Activity
                Intent intent = new Intent(SignUpActivity.this, SignUp2Activity.class);
                intent.putExtra("user", user);  // 傳遞 User 物件
                startActivity(intent);
            }
        });

        tvBackToLogin.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
        btnBackToLog.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
