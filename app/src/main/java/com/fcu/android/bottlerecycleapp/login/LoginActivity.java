package com.fcu.android.bottlerecycleapp.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.fcu.android.bottlerecycleapp.Gender;
import com.fcu.android.bottlerecycleapp.MainActivity;
import com.fcu.android.bottlerecycleapp.R;
import com.fcu.android.bottlerecycleapp.database.DBHelper;
import com.fcu.android.bottlerecycleapp.database.Role;
import com.fcu.android.bottlerecycleapp.database.User;
import com.fcu.android.bottlerecycleapp.sign_up.SignUpActivity;
import com.fcu.android.bottlerecycleapp.ui.adminPage.AdminHomeActivity;

import org.mindrot.jbcrypt.BCrypt;

import java.util.Date;

public class LoginActivity extends AppCompatActivity {

    private EditText etUserName;
    private EditText etPassword;
    private TextView tvGoToSignUp;
    private Button btnLogin;
    private DBHelper dbHelper;
    private final String USER_NAME = "test";
    private final String ADMIN_NAME = "admin";
    private final String USER_EMAIL = "test@test.com";
    private final String TEST_PASSWORD = "Test123456";
    private final String ADMIN_EMAIL = "admin@test.com";
    private final String USER_PHONE = "0911222333";
    private final String ADMIN_PHONE = "0987878787";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        dbHelper = new DBHelper(this); // 確保 dbHelper 在任何使用之前已初始化


        // 設置視窗邊距
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.loginPage), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 檢查USER_EMAIL, 如果不存在則新增
        if (dbHelper.findUserByEmail(USER_EMAIL) == null) {
            Log.d("LoginActivity", "testUser");
            testUser();
        }
        // 檢查ADMIN_EMAIL, 如果不存在則新增
        if (dbHelper.findUserByEmail(ADMIN_EMAIL) == null) {
            Log.d("LoginActivity", "testAdmin");
            testAdmin();
        }

        etUserName = findViewById(R.id.et_user_name);
        etPassword = findViewById(R.id.et_login_password);
        btnLogin = findViewById(R.id.btn_login);
        tvGoToSignUp = findViewById(R.id.tv_go_to_sign_up);
        testLogin();
        // 點擊登入按鈕
        btnLogin.setOnClickListener(v -> {
            String userEmail = etUserName.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (userEmail.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "請輸入帳號與密碼", Toast.LENGTH_SHORT).show();
                return;
            }

            // 檢查帳號是否存在
            User user = dbHelper.findUserByEmail(userEmail);
            if (user != null) {
                String storedPassword = user.getPassword();
                // 使用 BCrypt 進行密碼驗證
                if (BCrypt.checkpw(password, storedPassword)) {
                    if (user.getRole() == Role.ADMIN) {
                        Intent intent = new Intent(LoginActivity.this, AdminHomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else if(user.getRole() == Role.USER){
                    // 密碼正確，進入主頁面
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    //傳送user至 其他Activity
                    intent.putExtra("user", user);
                    startActivity(intent);
                    finish();
                    }
                } else {
                    Toast.makeText(this, "密碼錯誤", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "該帳號尚未註冊", Toast.LENGTH_SHORT).show();
            }
        });


        // 點擊前往註冊頁面
        tvGoToSignUp.setOnClickListener(v1 -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void testLogin() {
        etUserName.setText(USER_EMAIL);
        etPassword.setText(TEST_PASSWORD);
    }

    /**
     * 測試用戶
     */
    private void testUser() {
        User user = new User();
        user.setUserName(USER_NAME);
        user.setEmail(USER_EMAIL);
        user.setPassword(BCrypt.hashpw(TEST_PASSWORD, BCrypt.gensalt()));
        user.setPhoneNumber(USER_PHONE);
        user.setEarnMoney(0.0);
        user.setQrCode(String.valueOf(new Date().getTime() + dbHelper.getUserCount()));
        user.setDonateMoney(0.0);
        user.setGender(Gender.UNDEFINED);
        user.setRole(Role.USER);
        user.setUserImage(null);
        dbHelper.addUser(user);
    }

    /**
     * 測試管理員
     */
    private void testAdmin() {
        User user = new User();
        user.setUserName(ADMIN_NAME);
        user.setEmail(ADMIN_EMAIL);
        user.setPassword(BCrypt.hashpw(TEST_PASSWORD, BCrypt.gensalt()));
        user.setPhoneNumber(ADMIN_PHONE);
        user.setEarnMoney(0.0);
        user.setQrCode(String.valueOf(new Date().getTime() + dbHelper.getUserCount()));
        user.setDonateMoney(0.0);
        user.setGender(Gender.UNDEFINED);
        user.setRole(Role.ADMIN);
        user.setUserImage(null);
        dbHelper.addUser(user);
    }
}
