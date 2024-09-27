package com.fcu.android.bottlerecycleapp.ui.adminPage;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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

import com.fcu.android.bottlerecycleapp.DatePicker;
import com.fcu.android.bottlerecycleapp.R;
import com.fcu.android.bottlerecycleapp.database.DBHelper;
import com.fcu.android.bottlerecycleapp.database.MyActivity;

import java.util.Calendar;

public class AddActivityActivity extends AppCompatActivity {

    private EditText etActivityName, etActivityDescription, etActivityStartTime, etActivityEndTime, etActivityGoal;
    private ImageButton btnCalenderStart, btnCalenderEnd;
    private Button btnAddActivity;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_activity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new DBHelper(this);
        etActivityName = findViewById(R.id.et_activity_name);
        etActivityDescription = findViewById(R.id.et_activity_description);
        etActivityStartTime = findViewById(R.id.et_activity_start_time);
        etActivityEndTime = findViewById(R.id.et_activity_end_time);
        etActivityGoal = findViewById(R.id.et_activity_goal);
        btnAddActivity = findViewById(R.id.btn_add_activity);
        btnCalenderStart = findViewById(R.id.btn_calender_annoncement_record);
        btnCalenderEnd = findViewById(R.id.btn_calender_end);

        btnAddActivity.setOnClickListener(v -> {
            String activityName = etActivityName.getText().toString();
            String activityDescription = etActivityDescription.getText().toString();
            String activityStartTime = etActivityStartTime.getText().toString();
            String activityEndTime = etActivityEndTime.getText().toString();
            String activityGoal = etActivityGoal.getText().toString();

            MyActivity activity = new MyActivity(); // You may need to set other properties for the activity object
            dbHelper.addActivity(activity);
            Toast.makeText(this, "活動已新增", Toast.LENGTH_SHORT).show();
        });

        btnCalenderStart.setOnClickListener(v -> DatePicker.showDatePickerDialog(AddActivityActivity.this,etActivityStartTime));
        btnCalenderEnd.setOnClickListener(v -> DatePicker.showDatePickerDialog(AddActivityActivity.this,etActivityEndTime));
    }

}
