package com.fcu.android.bottlerecycleapp.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.fcu.android.bottlerecycleapp.MainActivity;
import com.fcu.android.bottlerecycleapp.R;
import com.fcu.android.bottlerecycleapp.database.DBHelper;
import com.fcu.android.bottlerecycleapp.database.entity.MyActivity;
import com.fcu.android.bottlerecycleapp.database.entity.RecycleStation;
import com.fcu.android.bottlerecycleapp.database.entity.Role;
import com.fcu.android.bottlerecycleapp.database.entity.User;
import com.fcu.android.bottlerecycleapp.database.test_data.TestData;
import com.fcu.android.bottlerecycleapp.ui.adminPage.AdminHomeActivity;
import com.fcu.android.bottlerecycleapp.ui.sign_up.SignUpActivity;

import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private EditText etUserEmail;
    private EditText etPassword;
    private TextView tvGoToSignUp;
    private Button btnLogin;
    private ImageButton btnLoginTogglePassword;
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
        etUserEmail = findViewById(R.id.et_user_email_login);
        etPassword = findViewById(R.id.et_login_password);
        btnLogin = findViewById(R.id.btn_login);
        tvGoToSignUp = findViewById(R.id.tv_go_to_sign_up);
        btnLoginTogglePassword = findViewById(R.id.btn_login_togglePassword);

        btnLoginTogglePassword.setOnClickListener(v -> {
            if (etPassword.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                // 顯示密碼
                etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                btnLoginTogglePassword.setImageResource(R.drawable.visibility_off); // 顯示密碼圖示
            } else {
                // 隱藏密碼
                etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                btnLoginTogglePassword.setImageResource(R.drawable.visibility); // 隱藏密碼圖示
            }
            // 移動光標至最後
            etPassword.setSelection(etPassword.getText().length());
        });

        // 測試自動填寫登入資訊
        testLogin();

        // 點擊登入按鈕
        btnLogin.setOnClickListener(v -> {
            String userEmail = etUserEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            // 檢查是否為空
            if (userEmail.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "請輸入電子郵件與密碼", Toast.LENGTH_SHORT).show();
                return;
            }

            // 檢查帳號是否存在
            User user = dbHelper.findUserByEmail(userEmail);
            Log.d("LoginActivity", "userName: " + user.getUserName() + "#" + user.getUserTag());
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
        etUserEmail.setText(USER_EMAIL);
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
