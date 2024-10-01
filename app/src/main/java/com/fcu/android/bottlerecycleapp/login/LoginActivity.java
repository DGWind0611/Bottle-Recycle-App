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
import com.fcu.android.bottlerecycleapp.database.MyActivity;
import com.fcu.android.bottlerecycleapp.database.RecycleStation;
import com.fcu.android.bottlerecycleapp.database.Role;
import com.fcu.android.bottlerecycleapp.database.TestData;
import com.fcu.android.bottlerecycleapp.database.User;
import com.fcu.android.bottlerecycleapp.sign_up.SignUpActivity;
import com.fcu.android.bottlerecycleapp.ui.adminPage.AdminHomeActivity;

import org.mindrot.jbcrypt.BCrypt;

import java.util.Date;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private EditText etUserName;
    private EditText etPassword;
    private TextView tvGoToSignUp;
    private Button btnLogin;
    private DBHelper dbHelper;
    private final String USER_EMAIL = "test@test.com";
    private final String TEST_PASSWORD = "Test123456";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // 初始化 DBHelper
        dbHelper = new DBHelper(this);
        if (dbHelper == null) {
            Log.e("LoginActivity", "DBHelper 初始化失敗！");
            Toast.makeText(this, "資料庫初始化失敗，請重新啟動應用程式", Toast.LENGTH_LONG).show();
            return;
        }

        // 設置視窗邊距
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.loginPage), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 插入測試資料，先檢查是否已存在
        insertTestData();

        // 取得視圖
        etUserName = findViewById(R.id.et_user_name);
        etPassword = findViewById(R.id.et_login_password);
        btnLogin = findViewById(R.id.btn_login);
        tvGoToSignUp = findViewById(R.id.tv_go_to_sign_up);

        // 測試自動填寫登入資訊
        testLogin();

        // 點擊登入按鈕
        btnLogin.setOnClickListener(v -> {
            String userEmail = etUserName.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            // 檢查是否為空
            if (userEmail.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "請輸入帳號與密碼", Toast.LENGTH_SHORT).show();
                return;
            }

            // 檢查帳號是否存在
            User user = dbHelper.findUserByEmail(userEmail);
            if (user != null) {
                String storedPassword = user.getPassword();
                // 檢查密碼
                if (BCrypt.checkpw(password, storedPassword)) {
                    if (user.getRole() == Role.ADMIN) {
                        navigateToAdminPage();
                    } else if (user.getRole() == Role.USER) {
                        navigateToMainPage(user);
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

    /**
     * 測試自動填寫登入資訊
     */
    private void testLogin() {
        etUserName.setText(USER_EMAIL);
        etPassword.setText(TEST_PASSWORD);
    }

    /**
     * 插入測試數據
     */
    private void insertTestData() {
        TestData testData = new TestData();
        List<User> users = testData.testUser();
        for (User user : users) {
            if (dbHelper.findUserByEmail(user.getEmail()) == null) {
                dbHelper.addUser(user);
            }
        }
        List<RecycleStation> stations = testData.testStation();
        for (RecycleStation station : stations) {
            if (dbHelper.findStationById(station.getId()) == null) {
                dbHelper.addRecycleStation(station);
            }
        }
        List<MyActivity> activities = testData.testActivity();
        for (MyActivity activity : activities) {
            if (dbHelper.findActivityById(activity.getId()) == null) {
                dbHelper.addActivity(activity);
            }
        }
    }

    /**
     * 導向管理員頁面
     */
    private void navigateToAdminPage() {
        Intent intent = new Intent(LoginActivity.this, AdminHomeActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 導向主頁面
     */
    private void navigateToMainPage(User user) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }
}
