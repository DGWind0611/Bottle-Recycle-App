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
import com.fcu.android.bottlerecycleapp.service.FirebaseSync;
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

        // FirebaseSync 監聽資料變更
        FirebaseSync firebaseSync = new FirebaseSync(requireContext());
        firebaseSync.setOnDataChangedListener(newRecordCount -> {
            Log.d("PersonalDataFragment", "New records detected: " + newRecordCount);

            // 更新回收狀態並刷新 UI
            requireActivity().runOnUiThread(this::refreshRecycleStatus);
        });
        firebaseSync.startListeningForUpdates();

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
        binding = FragmentPersonalDataBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final ImageButton btnSetting = binding.btnSetting;

        final TextView tvUserName = binding.tvUserName;
        final TextView tvPriceValue = binding.tvPriceValue;
        final ImageView ivAvatar = binding.ivAvatar;
        final LinearLayout btnRecycleRecord = binding.btnRecycleRecord;
        final ImageButton btnNotification = binding.btnNotification;

        sharedViewModel.getData().observe(getViewLifecycleOwner(), data -> {
            if (data != null) {
                String userName = data.getUserName();
                String userTag = data.getUserTag();

                // 嘗試獲取最新回收狀態
                refreshRecycleStatus();

                // 更新用戶頭像
                String avatarUrl = data.getUserImage();  // 儲存的圖片路徑
                tvUserName.setText(userName);

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
            Log.d("PersonalDataFragment", "userName: " + sharedViewModel.getData().getValue().getUserName() + ", userTag: " + sharedViewModel.getData().getValue().getUserTag());
            intent.putExtra("userName", sharedViewModel.getData().getValue().getUserName());
            intent.putExtra("userTag", sharedViewModel.getData().getValue().getUserTag());
            startActivity(intent);
        });

        btnNotification.setOnClickListener(v -> {
            // 跳轉到通知頁面
            Intent intent = new Intent(requireActivity(), NotificationActivity.class);
            startActivity(intent);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * 刷新回收狀態，避免數據重複累加
     */
    private void refreshRecycleStatus() {
        if (sharedViewModel.getData().getValue() != null) {
            String userName = sharedViewModel.getData().getValue().getUserName();
            String userTag = sharedViewModel.getData().getValue().getUserTag();

            // 更新狀態表
            dbHelper.recalculateRecycleStatus(userName, userTag);

            // 從狀態表中獲取最新狀態
            RecycleStatus recycleStatus = dbHelper.getUserRecycleStats(dbHelper.getReadableDatabase(), userName, userTag);

            if (recycleStatus != null) {
                double userPrice = Math.round(recycleStatus.getTotalMoney() * 100.0) / 100.0;
                binding.tvPriceValue.setText("$ " + userPrice);
            } else {
                binding.tvPriceValue.setText("$ 0");
            }
        }
    }
}
