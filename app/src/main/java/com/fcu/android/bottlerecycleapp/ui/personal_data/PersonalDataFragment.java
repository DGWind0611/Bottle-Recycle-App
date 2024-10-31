package com.fcu.android.bottlerecycleapp.ui.personal_data;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.fcu.android.bottlerecycleapp.R;
import com.fcu.android.bottlerecycleapp.SharedViewModel;
import com.fcu.android.bottlerecycleapp.database.DBHelper;
import com.fcu.android.bottlerecycleapp.database.entity.RecycleStatus;
import com.fcu.android.bottlerecycleapp.database.entity.User;
import com.fcu.android.bottlerecycleapp.databinding.FragmentPersonalDataBinding;
import com.fcu.android.bottlerecycleapp.ui.notification.NotificationActivity;
import com.fcu.android.bottlerecycleapp.ui.recycle_record.RecycleRecordActivity;

import java.io.File;

public class PersonalDataFragment extends Fragment {

    private FragmentPersonalDataBinding binding;
    private SharedViewModel sharedViewModel;
    private ActivityResultLauncher<Intent> settingActivityLauncher;
    private DBHelper dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbHelper = new DBHelper(requireContext());

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        settingActivityLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            User updatedUser = (User) data.getSerializableExtra("userData");
                            if (updatedUser != null) {
                                sharedViewModel.setData(updatedUser);
                            }
                        }
                    }
                }
        );
    }

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PersonalDataViewModel personalDataViewModel =
                new ViewModelProvider(this).get(PersonalDataViewModel.class);

        binding = FragmentPersonalDataBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final ImageButton btnSetting = binding.btnSetting;

        final TextView tvUserName = binding.tvUserName;
        final TextView tvPriceValue = binding.tvPriceValue;
        final ImageView ivAvatar = binding.ivAvatar;
        final LinearLayout btnRecycleRecord = binding.btnRecycleRecord;
        final LinearLayout btnTransferRecord = binding.btnTransferRecord;
        final ImageButton btnNotification = binding.btnNotification;

        sharedViewModel.getData().observe(getViewLifecycleOwner(), data -> {
            if (data != null) {
                String userName = data.getUserName();
                String userTag = data.getUserTag();

                // 嘗試獲取 RecycleStatus 資料
                RecycleStatus recycleStatus = dbHelper.getUserRecycleStats(userName, userTag);
                if (recycleStatus != null) {
                    double userPrice = recycleStatus.getTotalMoney();
                    tvPriceValue.setText("$ " + userPrice);
                } else {
                    Log.d("PersonalDataFragment", "RecycleStatus is null for user: " + userName + ", tag: " + userTag);
                    tvPriceValue.setText("$ 0");
                }

                String avatarUrl = data.getUserImage();  // 這是儲存的圖片路徑
                tvUserName.setText(userName);

                if (avatarUrl == null) {
                    ivAvatar.setImageResource(R.drawable.avatar);
                } else {
                    File avatarFile = new File(avatarUrl);
                    Log.d("PersonalDataFragment", "avatarUrl: " + avatarUrl);
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
        });


        btnSetting.setOnClickListener(v -> {
            // 跳轉到個人資料設定頁面
            Intent intent = new Intent(requireActivity(), PersonalDataSettingActivity.class);
            intent.putExtra("userData", sharedViewModel.getData().getValue());
            settingActivityLauncher.launch(intent);
        });

        btnRecycleRecord.setOnClickListener(v -> {
            // 跳轉到回收記錄頁面
            Intent intent = new Intent(requireActivity(), RecycleRecordActivity.class);
            startActivity(intent);
        });

        //TODO: 轉帳記錄功能尚未實作
//        btnTransferRecord.setOnClickListener(v -> {
//            // 跳轉到轉帳記錄頁面
//            Intent intent = new Intent(requireActivity(), TransferRecordActivity.class);
//            intent.putExtra("userId", userId);
//            startActivity(intent);
//        });
        //TODO: 個人化通知實作
        btnNotification.setOnClickListener(v -> {
            // 跳轉到通知頁面
            Intent intent = new Intent(requireActivity(), NotificationActivity.class);
//            intent.putExtra("userName", userName);
//            intent.putExtra("userTag", userTag);
            startActivity(intent);
        });

        personalDataViewModel.getText().observe(getViewLifecycleOwner(), tvUserName::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
