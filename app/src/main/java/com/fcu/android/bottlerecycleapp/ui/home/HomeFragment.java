package com.fcu.android.bottlerecycleapp.ui.home;

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
import com.fcu.android.bottlerecycleapp.databinding.FragmentHomeBinding;

import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private List<ActivityItem> activityList;
    private DBHelper dbHelper;
    private String userName;
    private String userTag;

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