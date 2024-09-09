package com.fcu.android.bottlerecycleapp.sign_up;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import org.mindrot.jbcrypt.BCrypt;

import com.fcu.android.bottlerecycleapp.R;
import com.fcu.android.bottlerecycleapp.database.DBHelper;
import com.fcu.android.bottlerecycleapp.database.User;
import com.fcu.android.bottlerecycleapp.login.LoginActivity;

public class SignUp2Activity extends AppCompatActivity {

    private User user;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private CheckBox cbAgree;
    private Button btnSignUp;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);

        // 接收從第一頁傳來的 User 物件
        user = (User) getIntent().getSerializableExtra("user");

        // 初始化資料庫輔助類
        dbHelper = new DBHelper(this, null, null, 1);

        // 初始化 UI 元件
        etPassword = findViewById(R.id.et_singup_password);
        etConfirmPassword = findViewById(R.id.et_confirm_password);
        cbAgree = findViewById(R.id.cb_agree);
        btnSignUp = findViewById(R.id.btn_sing_up);

        // 註冊按鈕點擊事件
        btnSignUp.setOnClickListener(v -> {
            String password = etPassword.getText().toString().trim();
            String confirmPassword = etConfirmPassword.getText().toString().trim();

            // 檢查密碼格式
            if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,20}$")) {
                etPassword.setError("密碼須包含大小寫英文字母和數字，長度為8-20位");
            }
            // 檢查確認密碼是否匹配
            else if (!password.equals(confirmPassword)) {
                etConfirmPassword.setError("密碼不一致");
            }
            // 確認是否同意條款
            else if (!cbAgree.isChecked()) {
                Toast.makeText(this, "請同意條款", Toast.LENGTH_SHORT).show();
            } else {
                // 密碼有效，對密碼進行加密
                String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

                // 將加密的密碼設置到 User 物件
                user.setPassword(hashedPassword);

                // 儲存 User 到資料庫
                if (dbHelper.addUser(user)) {
                    Toast.makeText(this, "註冊成功", Toast.LENGTH_SHORT).show();
                    Log.d("SignUp2Activity", user.toString());
                    // 註冊成功後，導向登入頁面
                    Intent intent = new Intent(SignUp2Activity.this, LoginActivity.class);
                    startActivity(intent);
                    finish(); // 結束註冊頁面，返回到登入頁面
                } else {
                    Toast.makeText(this, "註冊失敗，請重試", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
