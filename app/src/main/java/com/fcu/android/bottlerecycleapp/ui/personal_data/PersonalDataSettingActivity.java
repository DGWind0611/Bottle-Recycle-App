package com.fcu.android.bottlerecycleapp.ui.personal_data;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.fcu.android.bottlerecycleapp.database.entity.Gender;
import com.fcu.android.bottlerecycleapp.R;
import com.fcu.android.bottlerecycleapp.database.DBHelper;
import com.fcu.android.bottlerecycleapp.database.entity.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class PersonalDataSettingActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private Button btnConfirm;
    private ImageButton btnSetupPassword;
    private EditText etUserName, etUserEmail, etUserPhone, etPassword;
    private DBHelper dbHelper;
    private Spinner spGender;
    private ImageView ivAvatar;
    private ImageView ivEditIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data_setting);

        // 初始化 UI 元件
        btnBack = findViewById(R.id.btn_back_to_personal_data);
        btnConfirm = findViewById(R.id.btn_confirm_setting);
        btnSetupPassword = findViewById(R.id.btn_edit_password);
        etUserName = findViewById(R.id.et_username_setting);
        etUserEmail = findViewById(R.id.et_email_setting);
        etUserPhone = findViewById(R.id.et_phone_setting);
        etPassword = findViewById(R.id.et_password_setting);
        spGender = findViewById(R.id.sp_gender_setting);
        ivAvatar = findViewById(R.id.ivAvatar);
        ivEditIcon = findViewById(R.id.ivEditIcon);


        ivEditIcon.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("是否開啟相簿選擇大頭貼？")
                    .setPositiveButton("是", (dialog, which) -> {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        startActivityForResult(intent, 1);
                    })
                    .setNegativeButton("否", (dialog, which) -> dialog.dismiss())
                    .create()
                    .show();
        });


        dbHelper = new DBHelper(this);

        User user = (User) getIntent().getSerializableExtra("userData");
        assert user != null;
        etUserName.setText(user.getUserName());
        etUserEmail.setText(user.getEmail());
        etUserPhone.setText(user.getPhoneNumber());
        etPassword.setText("********"); // 避免顯示真實密碼

        String avatarUrl = user.getUserImage();
        setAvatar(avatarUrl);

        // 你可以選擇不顯示密碼，或者在按下編輯按鈕時才顯示
        // etPassword.setText(user.getPassword());
        // Log.d("PersonalDataSettingActivity", "userGender: " + user.getGender());

        // 設置可編輯切換的邏輯
        setupEditableField(findViewById(R.id.btn_edit_username), etUserName);
        setupEditableField(findViewById(R.id.btn_edit_email), etUserEmail);
        setupEditableField(findViewById(R.id.btn_edit_password), etPassword);
        setupGenderField(spGender, Objects.requireNonNull(user));

        // 確認按鈕點擊事件
        btnConfirm.setOnClickListener(v -> {
            user.setEmail(etUserEmail.getText().toString());
            user.setUserName(etUserName.getText().toString());
            user.setPhoneNumber(etUserPhone.getText().toString());

            // 更新用戶性別，將 Spinner 的選擇轉回 Gender 枚舉
            String selectedChineseGender = (String) spGender.getSelectedItem();
            Gender selectedGender = Gender.fromChineseValue(selectedChineseGender);
            if (selectedGender != null) {
                user.setGender(selectedGender);
            }

            dbHelper.updateUser(user);  // 更新資料庫中的用戶資料

            // 返回更新後的用戶資料
            Intent resultIntent = new Intent();
            resultIntent.putExtra("userData", user);
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        });

        // 密碼修改按鈕點擊事件
        btnSetupPassword.setOnClickListener(v -> dialog(user));

        // 返回按鈕點擊事件
        btnBack.setOnClickListener(v -> {
            setResult(Activity.RESULT_CANCELED);
            finish();
        });

        // 邊緣到邊緣處理
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    /**
     * 處理從相簿選擇大頭貼的結果
     *
     * @param requestCode 請求代碼
     * @param resultCode  結果代碼
     * @param data        更新後User資料
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            // 獲取用戶選擇的圖片的 URI
            Uri selectedImageUri = data.getData();

            // 儲存圖片到應用程式的 Avatar 資料夾
            try {
                assert selectedImageUri != null;
                InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);
                if (inputStream != null) {
                    // 定義保存大頭貼的路徑
                    File avatarDir = new File(getFilesDir(), "Avatar");
                    if (!avatarDir.exists()) {
                        avatarDir.mkdirs();  // 建立 Avatar 資料夾
                    }

                    // 使用 TimeStamp 生成唯一的檔案名稱
                    String timeStamp = String.valueOf(System.currentTimeMillis());
                    File avatarFile = new File(avatarDir, "user_avatar_" + timeStamp + ".png");

                    // 寫入圖片
                    FileOutputStream outputStream = new FileOutputStream(avatarFile);
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = inputStream.read(buffer)) > 0) {
                        outputStream.write(buffer, 0, length);
                    }

                    // 關閉流
                    outputStream.close();
                    inputStream.close();

                    // 更新資料庫中對應的路徑
                    User user = (User) getIntent().getSerializableExtra("userData");
                    if (user != null) {
                        user.setUserImage(avatarFile.getAbsolutePath());
                        dbHelper.updateUser(user);
                    }
                    Toast.makeText(this, "大頭貼已更新", Toast.LENGTH_SHORT).show();
                    setAvatar(avatarFile.getAbsolutePath());
                }
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "儲存大頭貼失敗", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 設置大頭貼
     *
     * @param avatarUrl 大頭貼的路徑
     */
    private void setAvatar(String avatarUrl) {
        if (avatarUrl == null) {
            ivAvatar.setImageResource(R.drawable.avatar);
        } else {
            File avatarFile = new File(avatarUrl);
            if (avatarFile.exists()) {
                // 使用 Glide 載入並裁切成圓形圖片
                Glide.with(this)
                        .load(avatarFile)
                        .circleCrop()  // 將圖片裁切成圓形
                        .into(ivAvatar);
            } else {
                // 若圖片不存在，使用預設的頭像
                ivAvatar.setImageResource(R.drawable.avatar);
            }
        }
    }

    /**
     * 當點擊 EditButton, 設置EditText進入可編輯模式 再次點擊 EditText退出編輯模式
     *
     * @param editButton 編輯按鈕
     * @param editText   用於顯示用戶資料的 EditText
     */
    private void setupEditableField(@NonNull ImageButton editButton, EditText editText) {
        editButton.setOnClickListener(v -> {
            boolean isEditable = editText.isEnabled();
            if (!isEditable) {
                // 進入編輯模式
                editText.setEnabled(true);
                editText.requestFocus();
                editText.setBackground(getDrawable(R.drawable.enable_edit_text));
                editButton.setImageResource(R.drawable.confirmation);
                setButtonsEnabled(false);  // 禁用確認和返回按鈕
            } else {
                // 退出編輯模式
                editText.setEnabled(false);
                editText.setBackground(getDrawable(R.drawable.edit_text_background));
                editButton.setImageResource(R.drawable.pen);
                setButtonsEnabled(true);  // 啟用確認和返回按鈕
            }
        });
    }

    /**
     * 根據EditButton是否被啟用, 設置確認和返回按鈕是否可用
     *
     * @param enabled 是否啟用
     */
    private void setButtonsEnabled(boolean enabled) {
        btnConfirm.setEnabled(enabled);  // 控制「確認」按鈕
        btnBack.setEnabled(enabled);     // 控制「返回」按鈕
    }


    /**
     * 設置性別選擇欄位
     *
     * @param spinner 用於顯示性別選擇的 Spinner
     * @param user    用戶的資料
     */
    private void setupGenderField(@NonNull Spinner spinner, @NonNull User user) {
        // 創建一個 ArrayAdapter，將 Gender 的中文值放入陣列
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new String[]{
                        Gender.MALE.getChineseValue(),
                        Gender.FEMALE.getChineseValue(),
                        Gender.UNDEFINED.getChineseValue()
                });

        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(genderAdapter);

        // 設置用戶當前的性別選擇
        Gender userGender = user.getGender();
        if (userGender != null) {
            int position = genderAdapter.getPosition(userGender.getChineseValue());
            if (position >= 0) {
                spinner.setSelection(position);
            } else {
                spinner.setSelection(genderAdapter.getPosition(Gender.UNDEFINED.getChineseValue()));
            }
        } else {
            spinner.setSelection(genderAdapter.getPosition(Gender.UNDEFINED.getChineseValue()));
        }

        // 監聽用戶的選擇變化
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedChineseGender = (String) parent.getItemAtPosition(position);
                // 將中文值轉換回 Gender 枚舉
                Gender selectedGender = Gender.fromChineseValue(selectedChineseGender);
                // 根據選擇更新 user 的性別
                if (selectedGender != null) {
                    user.setGender(selectedGender);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 不進行任何操作
            }
        });
    }

    /**
     * 顯示密碼修改對話框
     *
     * @param user 用戶的資料
     */
    private void dialog(User user) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("密碼修改");

        // 創建三個 EditText 並設置輸入類型和提示
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        final EditText oldPassword = createPasswordEditText("請輸入舊密碼");
        final EditText newPassword = createPasswordEditText("請輸入新密碼");
        final EditText confirmPassword = createPasswordEditText("請確認新密碼");
        layout.addView(oldPassword);
        layout.addView(newPassword);
        layout.addView(confirmPassword);
        builder.setView(layout);

        // 設置確定按鈕的邏輯
        builder.setPositiveButton("確定", (dialogInterface, which) -> {
            String inputOldPassword = oldPassword.getText().toString();
            String inputNewPassword = newPassword.getText().toString();
            String inputConfirmPassword = confirmPassword.getText().toString();

            // 驗證舊密碼是否正確
            if (inputOldPassword.equals(user.getPassword())) {
                // 檢查密碼格式
                if (!inputNewPassword.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,20}$")) {
                    Toast.makeText(this, "密碼須包含大小寫英文字母和數字，長度為8-20位", Toast.LENGTH_SHORT).show();
                    return;
                }
                // 檢查新密碼是否與確認密碼一致
                if (inputNewPassword.equals(inputConfirmPassword)) {
                    // 更新密碼邏輯
                    user.setPassword(inputNewPassword);
                    dbHelper.updateUser(user);  // 更新資料庫中的用戶信息
                    Toast.makeText(this, "密碼已更新", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "新密碼與確認密碼不一致，請重新輸入", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "舊密碼不正確，請重新輸入", Toast.LENGTH_SHORT).show();
            }
        });

        // 設置取消按鈕的邏輯
        builder.setNegativeButton("取消", (dialogInterface, which) -> {
            Toast.makeText(this, "取消密碼更新", Toast.LENGTH_SHORT).show();
        });

        // 顯示對話框
        builder.show();
    }

    /**
     * 創建一個用於輸入密碼的 EditText
     *
     * @param hint 提示文字
     * @return 創建的 EditText
     */
    @NonNull
    private EditText createPasswordEditText(String hint) {
        EditText editText = new EditText(this);
        editText.setHint(hint);
        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        return editText;
    }


}
