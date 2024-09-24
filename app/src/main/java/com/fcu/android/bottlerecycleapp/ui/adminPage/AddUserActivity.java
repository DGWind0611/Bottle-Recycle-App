package com.fcu.android.bottlerecycleapp.ui.adminPage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.fcu.android.bottlerecycleapp.R;
import com.fcu.android.bottlerecycleapp.database.DBHelper;
import com.fcu.android.bottlerecycleapp.database.Role;
import com.fcu.android.bottlerecycleapp.database.User;
import com.fcu.android.bottlerecycleapp.login.LoginActivity;

import org.mindrot.jbcrypt.BCrypt;

import java.util.Date;

//TODO 建立RegisterManger 來處理註冊的事情 強化程式碼的可讀性、可維護性
public class AddUserActivity extends AppCompatActivity {

    private EditText etUserName;
    private EditText etPassword;
    private EditText etEmail;
    private EditText etPhone;
    private Spinner etRole;
    private Button btnAddUser;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        // 初始化資料庫輔助類
        dbHelper = new DBHelper(this);

        // 初始化 UI 元件
        etUserName = findViewById(R.id.et_add_username);
        etPassword = findViewById(R.id.et_add_password);
        etEmail = findViewById(R.id.et_add_email);
        etPhone = findViewById(R.id.et_add_phone);
        etRole = findViewById(R.id.et_add_role);
        btnAddUser = findViewById(R.id.btn_add_user);

        User user = new User();

        // 設定角色選擇下拉選單
        setupRoleField(etRole, user);

        btnAddUser.setOnClickListener(v -> {
            String userName = etUserName.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();

            // 資料驗證
            if (userName.isEmpty() || password.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, "請填寫所有欄位", Toast.LENGTH_SHORT).show();
            } else if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                etEmail.setError("請輸入正確的電子郵件格式");
            } else if (!phone.matches("^09\\d{8}$")) {
                etPhone.setError("請輸入正確的手機號碼格式");
            } else if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,20}$")) {
                etPassword.setError("密碼須包含大小寫英文字母和數字，長度為8-20位");
            } else if (!dbHelper.checkEmail(email)) {
                etEmail.setError("此電子郵件已經註冊過");
            } else if (!dbHelper.checkPhoneNumber(phone)) {
                etPhone.setError("此手機號碼已經註冊過");
            } else {
                // 密碼加密
                String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
                // 建立個人 QR Code
                int userLength = dbHelper.getUserCount();
                String qrcode = createQRCode(userLength);

                // 設置 User 物件資料
                user.setUserName(userName);
                user.setPassword(hashedPassword);
                user.setEmail(email);
                user.setPhoneNumber(phone);
                user.setQrCode(qrcode);

                // 將 User 新增到資料庫
                try {
                    boolean isCreated = dbHelper.addUser(user);
                    if (isCreated) {
                        Toast.makeText(this, "用戶新增成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddUserActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();  // 結束當前 Activity，回到登入頁面
                    } else {
                        Toast.makeText(this, "新增失敗，請重試", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.e("AddUserActivity", "資料庫操作錯誤", e);
                    Toast.makeText(this, "發生錯誤，請稍後再試", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setupRoleField(@NonNull Spinner spinner, @NonNull User user) {
        ArrayAdapter<String> RoleAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new String[]{
                        Role.USER.getChineseValue(),
                        Role.ADMIN.getChineseValue(),
                        Role.WORKER.getChineseValue()
                });

        RoleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(RoleAdapter);

        // 設置默認角色為 USER
        spinner.setSelection(RoleAdapter.getPosition(Role.USER.getChineseValue()));

        // 監聽用戶選擇的變化
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedRole = (String) parent.getItemAtPosition(position);
                Role role = Role.fromChineseValue(selectedRole);
                if (role != null) {
                    user.setRole(role);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 無操作
            }
        });
    }

    /**
     * 創建 QR Code
     */
    public String createQRCode(int userLength) {
        Date date = new Date();
        return String.valueOf(date.getTime()) + userLength + 1;
    }
}
