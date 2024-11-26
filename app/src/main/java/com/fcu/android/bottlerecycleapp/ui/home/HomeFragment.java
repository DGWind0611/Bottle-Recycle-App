package com.fcu.android.bottlerecycleapp.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fcu.android.bottlerecycleapp.SharedViewModel;
import com.fcu.android.bottlerecycleapp.database.entity.ActivityItem;
import com.fcu.android.bottlerecycleapp.database.DBHelper;
import com.fcu.android.bottlerecycleapp.database.entity.RecycleStatus;
import com.fcu.android.bottlerecycleapp.databinding.FragmentHomeBinding;

import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private List<ActivityItem> activityList;
    private DBHelper dbHelper;
    private String userName;
    private String userTag;

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        dbHelper = new DBHelper(requireContext());

        final LinearLayout llTransferMoney = binding.llTransferMoney;
        final TextView textView = binding.tvTitle;
        final LinearLayout llDonate = binding.llDonate;
        final LinearLayout llPlantTree = binding.llPlantTree;
        final LinearLayout llRewards = binding.llRewards;
        final RecyclerView rvActivity = binding.rvActivities;
        final TextView tvEarnValue = binding.tvEarnValue;
        final TextView tvDonateValue = binding.tvDonateValue;
        final TextView tvRecycleTime = binding.tvRecycleTime;
        final TextView tvCarbonReductionValue = binding.tvCarbonReductionValue;

        // 從 SharedViewModel 取得userName和userTag
        SharedViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        sharedViewModel.getData().observe(getViewLifecycleOwner(), data -> {
            if (data != null) {
                userName = data.getUserName();
                userTag = data.getUserTag();
                Log.d("HomeFragment", "userName: " + userName + ", userTag: " + userTag);

                // 獲取用戶活動
                activityList = dbHelper.getUserActivities(userName, userTag);
                for (ActivityItem activityItem : activityList) {
                    activityItem.setActivityName(dbHelper.findActivityById(activityItem.getActivityId()).getActivityName());
                }
                RecycleStatus recycleStats = dbHelper.getUserRecycleStats(userName, userTag);
                if (recycleStats != null) {
                    tvEarnValue.setText(String.valueOf("$ " + recycleStats.getTotalMoney()));
                    tvDonateValue.setText(String.valueOf("$ " + recycleStats.getTotalDonation()));
                    tvRecycleTime.setText(recycleStats.getRecycleTimes() + " 次");
                    tvCarbonReductionValue.setText(recycleStats.getTotalCarbonReduction() + " g");
                } else {
                    Log.d("HomeFragment", "RecycleStatus is null for user: " + userName + ", tag: " + userTag);
                    // 可以設定預設值
                    tvEarnValue.setText("$ 0");
                    tvDonateValue.setText("$ 0");
                    tvRecycleTime.setText("0 次");
                    tvCarbonReductionValue.setText("0 g");
                }

                ActivityAdapter activityAdapter = new ActivityAdapter(getContext(), activityList);
                rvActivity.setAdapter(activityAdapter);
                rvActivity.setLayoutManager(new LinearLayoutManager(getContext()));
            } else {
                Log.d("HomeFragment", "User data is null");
            }
        });


//        llTransferMoney.setOnClickListener(v -> {
//            Intent intent = new Intent(getActivity(), TransferMoneyActivity.class);
//            startActivity(intent);
//        });
//
//        llDonate.setOnClickListener(v -> {
//            Intent intent = new Intent(getActivity(), DonateActivity.class);
//            startActivity(intent);
//        });
//        llPlantTree.setOnClickListener(v -> {
//            Intent intent = new Intent(getActivity(), PlantTreeActivity.class);
//            startActivity(intent);
//        });
//        llRewards.setOnClickListener(v -> {
//            Intent intent = new Intent(getActivity(), RewardsActivity.class);
//            startActivity(intent);
//        });

        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}