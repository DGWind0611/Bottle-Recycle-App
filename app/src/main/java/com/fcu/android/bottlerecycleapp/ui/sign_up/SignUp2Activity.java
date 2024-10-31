package com.fcu.android.bottlerecycleapp.ui.sign_up;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.fcu.android.bottlerecycleapp.R;
import com.fcu.android.bottlerecycleapp.database.DBHelper;
import com.fcu.android.bottlerecycleapp.database.entity.User;
import com.fcu.android.bottlerecycleapp.ui.login.LoginActivity;

import org.mindrot.jbcrypt.BCrypt;

import java.util.Date;

public class SignUp2Activity extends AppCompatActivity {

    private User user;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private CheckBox cbAgree;
    private TextView tvPolicy;
    private Button btnSignUp;
    private ImageButton ibBack;
    private ImageButton btnSignUpTogglePassword;
    private ImageButton btnSignUpToggleConfirmPassword;
    private DBHelper dbHelper;
    private ActivityResultLauncher<Intent> policyActivityLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);

        // 接收從第一頁傳來的 User 物件
        user = (User) getIntent().getSerializableExtra("user");

        // 初始化資料庫輔助類
        dbHelper = new DBHelper(this);

        // 初始化 UI 元件
        etPassword = findViewById(R.id.et_signup_password);
        etConfirmPassword = findViewById(R.id.et_signup_confirm_password);
        cbAgree = findViewById(R.id.cb_agree);
        btnSignUp = findViewById(R.id.btn_sing_up);
        ibBack = findViewById(R.id.btn_back_to_signup_from_signup2);
        tvPolicy = findViewById(R.id.tv_policy);

        btnSignUpTogglePassword = findViewById(R.id.btn_sing_up_togglePassword);
        btnSignUpToggleConfirmPassword = findViewById(R.id.signup_confirm_togglePassword);

        // 初始化 ActivityResultLauncher 以替代 startActivityForResult
        policyActivityLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        boolean agreed = result.getData().getBooleanExtra("agree", false);
                        cbAgree.setChecked(agreed); // 自動勾選 CheckBox
                    }
                }
        );

        View.OnClickListener toggleListener = v -> {
            if (v.getId() == R.id.btn_sing_up_togglePassword) {
                if (etPassword.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    btnSignUpTogglePassword.setImageResource(R.drawable.visibility);
                } else {
                    etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    btnSignUpTogglePassword.setImageResource(R.drawable.visibility_off);
                }
            } else {
                if (etConfirmPassword.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    etConfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    btnSignUpToggleConfirmPassword.setImageResource(R.drawable.visibility);
                } else {
                    etConfirmPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    btnSignUpToggleConfirmPassword.setImageResource(R.drawable.visibility_off);
                }
            }
        };

        btnSignUpTogglePassword.setOnClickListener(toggleListener);
        btnSignUpToggleConfirmPassword.setOnClickListener(toggleListener);

        // 設定使用者政策和條款
        setupPolicyAndTerms();

        ibBack.setOnClickListener(v -> {
            Intent intent = new Intent(SignUp2Activity.this, SignUpActivity.class);
            startActivity(intent);
        });

        btnSignUp.setText("註冊");
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
                // 建立個人 QR Code
                int userLength = dbHelper.getUserCount();
                String qrcode = createQRCode(userLength);
                user.setQrCode(qrcode);
                // 將加密的密碼設置到 User 物件
                user.setPassword(hashedPassword);
                Log.d("SignUp2Activity", "User Tag: " + user.getUserTag());
                // 儲存 User 到資料庫
                try {
                    boolean isCreate = dbHelper.addUser(user);
                    if (isCreate) {
                        user.setQrCode(qrcode);
                        Toast.makeText(this, "註冊成功", Toast.LENGTH_SHORT).show();

                        //將可加入的活動加入到資料庫
                        dbHelper.addUserToAllUnExpiredActivities(user.getUserName(), user.getUserTag());

                        // 註冊成功後，導向登入頁面
                        Intent intent = new Intent(SignUp2Activity.this, LoginActivity.class);
                        startActivity(intent);
                        finish(); // 結束註冊頁面，返回到登入頁面
                    } else {
                        Toast.makeText(this, "註冊失敗，請重試", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.e("SignUp2Activity", "資料庫操作錯誤", e);
                    Toast.makeText(this, "發生錯誤，請稍後再試", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 新增 QR CODE
     */
    public String createQRCode(int userLength) {
        Date date = new Date();
        return String.valueOf(date.getTime()) + userLength + 1;
    }

    private void setupPolicyAndTerms() {
        String fullText = "我已了解使用者政策 & 規範";
        SpannableString spannableString = new SpannableString(fullText);

        // 點擊「政策」
        ClickableSpan policyClick = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent = new Intent(SignUp2Activity.this, PolicyActivity.class);
                policyActivityLauncher.launch(intent); // 使用 ActivityResultLauncher 啟動 PolicyActivity
            }
        };

        // 設定「政策&規範」的可點擊範圍
        int policyStart = fullText.indexOf("政");
        int policyEnd = policyStart + "政策 & 規範".length();
        spannableString.setSpan(policyClick, policyStart, policyEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // 設定點擊後的文字顏色
        spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.green)),
                policyStart, policyEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // 將文字設置到 TextView 並啟用可點擊功能
        tvPolicy.setText(spannableString);
        tvPolicy.setMovementMethod(LinkMovementMethod.getInstance());
        tvPolicy.setHighlightColor(ContextCompat.getColor(this, android.R.color.transparent));
    }
}
