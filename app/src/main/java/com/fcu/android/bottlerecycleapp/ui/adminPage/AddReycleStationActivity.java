package com.fcu.android.bottlerecycleapp.ui.adminPage;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.fcu.android.bottlerecycleapp.R;
import com.fcu.android.bottlerecycleapp.database.DBHelper;
import com.fcu.android.bottlerecycleapp.database.RecycleStation;

import java.util.regex.Pattern;

public class AddReycleStationActivity extends AppCompatActivity {

    private EditText etStationName;
    private EditText etStationAddress;
    private EditText etStationLongitude;
    private EditText etStationLatitude;
    private Button btnAddRecycleStation;
    private DBHelper dbHelper;

    private static final Pattern LONGITUDE_PATTERN = Pattern.compile("^([-+]?(180(\\.0+)?|1[0-7]\\d(\\.\\d+)?|\\d{1,2}(\\.\\d+)?|\\d?\\d(\\.\\d+)?))$");
    private static final Pattern LATITUDE_PATTERN = Pattern.compile("^([-+]?(90(\\.0+)?|[1-8]\\d(\\.\\d+)?|\\d?\\d(\\.\\d+)?))$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_reycle_station);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new DBHelper(this);
        etStationName = findViewById(R.id.et_station_name);
        etStationAddress = findViewById(R.id.et_station_address);
        etStationLongitude = findViewById(R.id.et_longitude);
        etStationLatitude = findViewById(R.id.et_latitude);
        btnAddRecycleStation = findViewById(R.id.btn_add_Station);

        btnAddRecycleStation.setOnClickListener(v -> addRecycleStation());
    }

    private void addRecycleStation() {
        String stationName = etStationName.getText().toString().trim();
        String stationAddress = etStationAddress.getText().toString().trim();
        String longitudeString = etStationLongitude.getText().toString().trim();
        String latitudeString = etStationLatitude.getText().toString().trim();

        // 確保所有字段都有值
        if (stationName.isEmpty() || stationAddress.isEmpty() || longitudeString.isEmpty() || latitudeString.isEmpty()) {
            Toast.makeText(this, "請填寫所有字段", Toast.LENGTH_SHORT).show();
            return;
        }

        // 使用正則表達式檢查經緯度格式
        if (!LONGITUDE_PATTERN.matcher(longitudeString).matches()) {
            Toast.makeText(this, "經度格式不正確", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!LATITUDE_PATTERN.matcher(latitudeString).matches()) {
            Toast.makeText(this, "緯度格式不正確", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double longitude = Double.parseDouble(longitudeString);
            double latitude = Double.parseDouble(latitudeString);

            // 創建 RecycleStation 對象
            RecycleStation recycleStation = new RecycleStation();
            recycleStation.setName(stationName);
            recycleStation.setAddress(stationAddress);
            recycleStation.setLongitude(longitude);
            recycleStation.setLatitude(latitude);

            // 添加到數據庫
            dbHelper.addRecycleStation(recycleStation);
            Toast.makeText(this, "回收站添加成功", Toast.LENGTH_SHORT).show();
            finish(); // 完成活動
        } catch (NumberFormatException e) {
            Toast.makeText(this, "經度或緯度格式不正確", Toast.LENGTH_SHORT).show();
        }
    }
}
