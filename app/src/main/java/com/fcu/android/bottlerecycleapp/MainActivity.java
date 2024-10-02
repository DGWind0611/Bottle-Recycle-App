package com.fcu.android.bottlerecycleapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.fcu.android.bottlerecycleapp.database.DBHelper;
import com.fcu.android.bottlerecycleapp.database.entity.User;
import com.fcu.android.bottlerecycleapp.ui.login.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.fcu.android.bottlerecycleapp.databinding.ActivityMainBinding;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private User user;
    private DBHelper dbHelper = new DBHelper(this);

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
        //TODO 將ActionBar 清除
        Objects.requireNonNull(getSupportActionBar()).hide();

        // 接收 Email
        user = getIntent().getSerializableExtra("user") != null ? (User) getIntent().getSerializableExtra("user") : null;
        assert user != null;
        Log.d("MainActivity", "user: " + user.getGender());
        if (user != null) {
            SharedViewModel sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);
            sharedViewModel.setData(user);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("錯誤");
            builder.setMessage("請重新登入");
            builder.setPositiveButton("確定", (dialog, which) -> {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            });
            builder.show();
        }
    }
}
