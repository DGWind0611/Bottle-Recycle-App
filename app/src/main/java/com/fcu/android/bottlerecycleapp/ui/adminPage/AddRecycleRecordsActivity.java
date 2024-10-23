package com.fcu.android.bottlerecycleapp.ui.adminPage;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.fcu.android.bottlerecycleapp.custom_fuction_class.DatePicker;
import com.fcu.android.bottlerecycleapp.R;
import com.fcu.android.bottlerecycleapp.database.DBHelper;
import com.fcu.android.bottlerecycleapp.database.entity.RecycleRecord;

public class AddRecycleRecordsActivity extends AppCompatActivity {

    private EditText etMachineName;
    private EditText etRecycleTime;
    private EditText etRecycleWeight;
    private EditText etUserName;
    private Button btnAddRecord;
    private ImageButton btnCalender;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_recycle_records);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new DBHelper(this);
        etMachineName = findViewById(R.id.et_machine_name);
        etRecycleTime = findViewById(R.id.et_recycle_time);
        etRecycleWeight = findViewById(R.id.et_recycle_weight);
        etUserName = findViewById(R.id.et_recycle_user_name);
        btnAddRecord = findViewById(R.id.btn_add_recycle_record);
        btnCalender = findViewById(R.id.btn_calender_annoncement_record);

        btnAddRecord.setOnClickListener(v -> {
            String machineName = etMachineName.getText().toString().trim();
            String recycleTime = etRecycleTime.getText().toString().trim();
            String recycleWeight = etRecycleWeight.getText().toString().trim();
            String userInput = etUserName.getText().toString().trim();

            // 驗證輸入
            if (machineName.isEmpty() || recycleTime.isEmpty() || recycleWeight.isEmpty() || userInput.isEmpty()) {
                Toast.makeText(this, "請填寫所有欄位", Toast.LENGTH_SHORT).show();
                return;
            }

            // 分割用戶名稱和標籤
            String userName = "";
            String userTag = "";
            if (userInput.contains("#")) {
                userName = userInput.substring(0, userInput.indexOf('#')).trim();
                userTag = userInput.substring(userInput.indexOf('#') + 1).trim();
            } else {
                Toast.makeText(this, "用戶名格式錯誤，請使用 'name#tag'", Toast.LENGTH_SHORT).show();
                return;
            }

            // 轉換數據
            int recycleStationId;
            double recycleWeightValue;
            try {
                recycleStationId = Integer.parseInt(machineName);
                recycleWeightValue = Double.parseDouble(recycleWeight);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "機器名稱或重量格式不正確", Toast.LENGTH_SHORT).show();
                return;
            }

            // 創建回收紀錄物件
            RecycleRecord record = new RecycleRecord();
            record.setRecycleStationId(recycleStationId);
            record.setRecycleTime(recycleTime);
            record.setRecycleWeight(recycleWeightValue);
            record.setUserName(userName);
            record.setUserTag(userTag);

            // 將紀錄加入資料庫
            boolean isAddedRecord = dbHelper.addRecycleRecord(record);
            if (isAddedRecord) {
                Toast.makeText(this, "紀錄已成功添加", Toast.LENGTH_SHORT).show();
                clearInputs(); // 可以考慮在成功後清空輸入欄位
            } else {
                Toast.makeText(this, "添加紀錄失敗", Toast.LENGTH_SHORT).show();
            }
        });

        btnCalender.setOnClickListener(v -> DatePicker.showDatePickerDialog(AddRecycleRecordsActivity.this, etRecycleTime));
    }

    // 可選擇的清空輸入欄位方法
    private void clearInputs() {
        etMachineName.setText("");
        etRecycleTime.setText("");
        etRecycleWeight.setText("");
        etUserName.setText("");
    }
}
