package com.fcu.android.bottlerecycleapp.ui.adminPage;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
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

import com.fcu.android.bottlerecycleapp.DatePicker;
import com.fcu.android.bottlerecycleapp.R;
import com.fcu.android.bottlerecycleapp.database.DBHelper;
import com.fcu.android.bottlerecycleapp.database.RecycleRecord;

import java.util.Calendar;

public class AddRecycleRecordsActivity extends AppCompatActivity {

    private EditText etMachineName;
    private EditText etRecycleTime;
    private EditText etRecycleWeight;
    private EditText etUserId;
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
        etUserId = findViewById(R.id.et_recycle_user_id);
        btnAddRecord = findViewById(R.id.btn_add_recycle_record);
        btnCalender = findViewById(R.id.btn_calender_annoncement_record);

        btnAddRecord.setOnClickListener(v -> {
            String machineName = etMachineName.getText().toString();
            String recycleTime = etRecycleTime.getText().toString();
            String recycleWeight = etRecycleWeight.getText().toString();
            String userId = etUserId.getText().toString();

            if (machineName.isEmpty() || recycleTime.isEmpty() || recycleWeight.isEmpty() || userId.isEmpty()) {
                return;
            }
            RecycleRecord record = new RecycleRecord();
            record.setRecycleStationId(Integer.parseInt(machineName));
            record.setRecycleTime(recycleTime);
            record.setRecycleWeight(Double.parseDouble(recycleWeight));
            record.setUserId(Integer.parseInt(userId));
            boolean isAddedRecord = dbHelper.addRecycleRecord(record);
            if (isAddedRecord) {
                Toast.makeText(this, "Record added successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed to add record", Toast.LENGTH_SHORT).show();
            }
        });

        btnCalender.setOnClickListener(v -> DatePicker.showDatePickerDialog(AddRecycleRecordsActivity.this,etRecycleTime));

    }

}