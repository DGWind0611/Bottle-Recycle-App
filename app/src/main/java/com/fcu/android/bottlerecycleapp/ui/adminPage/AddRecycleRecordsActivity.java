package com.fcu.android.bottlerecycleapp.ui.adminPage;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import com.fcu.android.bottlerecycleapp.custom_fuction_class.DatePicker;
import com.fcu.android.bottlerecycleapp.R;
import com.fcu.android.bottlerecycleapp.database.DBHelper;
import com.fcu.android.bottlerecycleapp.database.entity.RecycleRecord;

public class AddRecycleRecordsActivity extends AppCompatActivity {

    private EditText etMachineId, etRecycleTime, etRecycleWeight, etUserName, etUserTag, etRecycleValue;
    private TextView tvMachineName;
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

        etMachineId = findViewById(R.id.et_machine_id);
        tvMachineName = findViewById(R.id.tv_machine_name);
        etRecycleTime = findViewById(R.id.et_recycle_time);
        etRecycleWeight = findViewById(R.id.et_recycle_weight);
        etUserName = findViewById(R.id.et_recycle_user_name);
        etUserTag = findViewById(R.id.et_recycle_user_tag);
        etRecycleValue = findViewById(R.id.et_recycle_value);

        btnAddRecord = findViewById(R.id.btn_add_recycle_record);
        btnCalender = findViewById(R.id.btn_calender_annoncement_record);

        etMachineId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String machineIdStr = s.toString().trim();
                if (!machineIdStr.isEmpty()) {
                    try {
                        int machineId = Integer.parseInt(machineIdStr);
                        // 使用 AsyncTask 在非主執行緒中查詢資料庫
                        new GetMachineNameTask().execute(machineId);
                    } catch (NumberFormatException e) {
                        tvMachineName.setText("無效機台ID");
                    }
                } else {
                    tvMachineName.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().isEmpty()) {
                    tvMachineName.setText("機台名稱:"); // 檢查ID欄位是否為空，若空則顯示預設文字
                }
            }

        });

        btnAddRecord.setOnClickListener(v -> {
            String machineIdStr = etMachineId.getText().toString().trim();
            String recycleTime = etRecycleTime.getText().toString().trim();
            String recycleWeight = etRecycleWeight.getText().toString().trim();
            String userName = etUserName.getText().toString().trim();
            String userTag = etUserTag.getText().toString().trim();
            String recycleValue = etRecycleValue.getText().toString().trim();

            // 清除之前的錯誤提示
            etMachineId.setError(null);
            etRecycleTime.setError(null);
            etRecycleWeight.setError(null);
            etUserName.setError(null);
            etUserTag.setError(null);
            etRecycleValue.setError(null);

            // 驗證輸入
            boolean isValid = true;

            if (machineIdStr.isEmpty()) {
                etMachineId.setError("請填寫機台ID");
                isValid = false;
            }

            if (recycleTime.isEmpty()) {
                etRecycleTime.setError("請填寫回收時間");
                isValid = false;
            }

            if (recycleWeight.isEmpty()) {
                etRecycleWeight.setError("請填寫回收重量");
                isValid = false;
            }

            if (userName.isEmpty()) {
                etUserName.setError("請填寫用戶名稱");
                isValid = false;
            }

            if (userTag.isEmpty()) {
                etUserTag.setError("請填寫用戶標籤");
                isValid = false;
            }

            if (recycleValue.isEmpty()) {
                etRecycleValue.setError("請填寫回收金額");
                isValid = false;
            }

            // 若有任何欄位不符合，則不進行後續操作
            if (!isValid) return;

            // 檢查User是否存在
            if (dbHelper.findUserByNameAndTag(userName, userTag)==null) {
                Toast.makeText(this, "用戶不存在，請確認用戶名稱與標籤", Toast.LENGTH_SHORT).show();
                return;
            }

            // 創建回收紀錄物件並新增到資料庫
            Integer machineId = Integer.parseInt(machineIdStr);
            RecycleRecord record = new RecycleRecord();
            record.setUserName(userName);
            record.setUserTag(userTag);
            record.setRecycleStationId(machineId);
            record.setRecycleTime(recycleTime);
            record.setRecycleWeight(Double.parseDouble(recycleWeight));
            record.setEarnMoney(Double.parseDouble(recycleValue));

            boolean isAddedRecord = dbHelper.addRecycleRecord(record);
            if (isAddedRecord) {
                Toast.makeText(this, "紀錄已成功添加", Toast.LENGTH_SHORT).show();
                //clearInputs(); // 可以考慮在成功後清空輸入欄位
            } else {
                Toast.makeText(this, "添加紀錄失敗", Toast.LENGTH_SHORT).show();
            }
        });


        btnCalender.setOnClickListener(v -> DatePicker.showDatePickerDialog(AddRecycleRecordsActivity.this, etRecycleTime));
    }

    private class GetMachineNameTask extends AsyncTask<Integer, Void, String> {
        @Override
        protected String doInBackground(Integer... params) {
            int machineId = params[0];
            return dbHelper.getMachineNameById(machineId); // 假設您有這個方法
        }

        @Override
        protected void onPostExecute(String machineName) {
            if (machineName != null && !machineName.isEmpty()) {
                tvMachineName.setText("機台名稱: " + machineName);
            }
            else {
                tvMachineName.setText("找不到對應的機台名稱");
            }
        }
    }

    // 可選擇的清空輸入欄位方法
    private void clearInputs() {
        etMachineId.setText("");
        etRecycleTime.setText("");
        etRecycleWeight.setText("");
        etUserName.setText("");
        etRecycleValue.setText("");
        etUserTag.setText("");
    }


}
