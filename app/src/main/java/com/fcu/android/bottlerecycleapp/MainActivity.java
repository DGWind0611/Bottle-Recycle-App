package com.fcu.android.bottlerecycleapp;

import android.content.Intent;
import android.os.Bundle;

import com.fcu.android.bottlerecycleapp.database.DBHelper;
import com.fcu.android.bottlerecycleapp.database.User;
import com.fcu.android.bottlerecycleapp.login.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.fcu.android.bottlerecycleapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private String userEmail;
    private User user;
    private DBHelper dbHelper = new DBHelper(this, "bottle_recycle.db", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_recycle_map, R.id.navigation_PersonalData, R.id.navigation_qrcode)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        // 接收 Email
        userEmail = getIntent().getStringExtra("userEmail");

        if (userEmail != null && !userEmail.isEmpty()) {
            user = dbHelper.findUserByEmail(userEmail);
            if (user != null) {
                SharedViewModel sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);
                sharedViewModel.setData(user);
            }
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("錯誤")
                    .setMessage("未能取得使用者資訊，請重新登入")
                    .setPositiveButton("返回登入頁", (dialog, which) -> {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    })
                    .setNegativeButton("關閉", (dialog, which) -> finish())
                    .setCancelable(false)
                    .show();
        }
    }
}
