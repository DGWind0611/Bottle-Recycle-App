package com.fcu.android.bottlerecycleapp.login;

import android.content.Intent;
import android.database.Cursor;
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

import com.fcu.android.bottlerecycleapp.MainActivity;
import com.fcu.android.bottlerecycleapp.R;
import com.fcu.android.bottlerecycleapp.database.DBHelper;
import com.fcu.android.bottlerecycleapp.database.User;
import com.fcu.android.bottlerecycleapp.sign_up.SignUpActivity;

import org.mindrot.jbcrypt.BCrypt;

public class LoginActivity extends AppCompatActivity {

    private EditText etUserName;
    private EditText etPassword;
    private TextView tvGoToSignUp;
    private Button btnLogin;
    private DBHelper dbHelper;
    private final String TestEmail = "test@test.com";
    private final String TestPassword = "Test123456";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // 設置視窗邊距
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.loginPage), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 初始化資料庫與視圖
        dbHelper = new DBHelper(this, null, null, 1);
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
                Log.d("LoginActivity", "storedPassword: " + storedPassword);
                // 使用 BCrypt 進行密碼驗證
                if (BCrypt.checkpw(password, storedPassword)) {
                    // 密碼正確，進入主頁面
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    //傳送user至 其他Activity
                    intent.putExtra("userEmail", userEmail);
                    startActivity(intent);
                    finish();
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
        etUserName.setText(TestEmail);
        etPassword.setText(TestPassword);
    }
}
