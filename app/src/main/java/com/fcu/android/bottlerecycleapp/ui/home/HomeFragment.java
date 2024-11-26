package com.fcu.android.bottlerecycleapp.ui.home;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.fcu.android.bottlerecycleapp.SharedViewModel;
import com.fcu.android.bottlerecycleapp.database.DBHelper;
import com.fcu.android.bottlerecycleapp.database.entity.ActivityItem;
import com.fcu.android.bottlerecycleapp.database.entity.RecycleStatus;
import com.fcu.android.bottlerecycleapp.databinding.FragmentHomeBinding;
import com.fcu.android.bottlerecycleapp.service.FirebaseSync;

import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private List<ActivityItem> activityList;
    private DBHelper dbHelper;
    private String userName;
    private String userTag;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        dbHelper = new DBHelper(requireContext());

        // 從 SharedViewModel 獲取當前用戶資料
        SharedViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        sharedViewModel.getData().observe(getViewLifecycleOwner(), data -> {
            if (data != null) {
                userName = data.getUserName();
                userTag = data.getUserTag();
                Log.d("HomeFragment", "UserName: " + userName + ", UserTag: " + userTag);

                // 更新用戶的活動列表
                updateUserActivities();

                // 更新回收狀態
                updateRecycleStatus();
            } else {
                Log.d("HomeFragment", "No user data found");
            }
        });

        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 初始化 FirebaseSync
        FirebaseSync firebaseSync = new FirebaseSync(requireContext());
        firebaseSync.setOnDataChangedListener(newRecordCount -> {
            // 當有新的回收紀錄時顯示通知
            if (newRecordCount > 0) {
                showNewRecordsDialog(newRecordCount);
            }
        });
        firebaseSync.startListeningForUpdates();
    }

    private void showNewRecordsDialog(int newRecordCount) {
        new AlertDialog.Builder(requireContext())
                .setTitle("回收通知")
                .setMessage(String.format(Locale.getDefault(), "成功新增了 %d 筆回收紀錄", newRecordCount))
                .setPositiveButton("確定", (dialog, which) -> {
                    dialog.dismiss();
                    refreshUI();
                })
                .setCancelable(false)
                .show();
    }

    private void refreshUI() {
        if (userName != null && userTag != null) {
            // 更新用戶的活動列表
            updateUserActivities();

            // 更新回收狀態
            updateRecycleStatus();
        }
    }


    private void updateUserActivities() {
        // 獲取用戶活動列表
        activityList = dbHelper.getUserActivities(userName, userTag);
        for (ActivityItem activityItem : activityList) {
            activityItem.setActivityName(dbHelper.findActivityById(activityItem.getActivityId()).getActivityName());
        }

        // 設置 RecyclerView 的 Adapter
        ActivityAdapter activityAdapter = new ActivityAdapter(getContext(), activityList);
        binding.rvActivities.setAdapter(activityAdapter);
        binding.rvActivities.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void updateRecycleStatus() {
        // 重新計算回收狀態
        RecycleStatus recycleStatus = dbHelper.recalculateRecycleStatus(userName, userTag);

        if (recycleStatus != null) {
            binding.tvEarnValue.setText(String.format(Locale.getDefault(), "$ %.2f", recycleStatus.getTotalMoney()));
            binding.tvDonateValue.setText(String.format(Locale.getDefault(), "$ %.2f", recycleStatus.getTotalDonation()));
            binding.tvRecycleTime.setText(String.format(Locale.getDefault(), "%d 次", recycleStatus.getRecycleTimes()));
            binding.tvCarbonReductionValue.setText(String.format(Locale.getDefault(), "%.2f g", recycleStatus.getTotalCarbonReduction()));
        } else {
            Log.d("HomeFragment", "RecycleStatus is null for user: " + userName + ", UserTag: " + userTag);
            // 設定預設值
            binding.tvEarnValue.setText("$ 0");
            binding.tvDonateValue.setText("$ 0");
            binding.tvRecycleTime.setText("0 次");
            binding.tvCarbonReductionValue.setText("0 g");
        }

        Log.d("HomeFragment", "RecycleStatus updated in UI");
    }

}
