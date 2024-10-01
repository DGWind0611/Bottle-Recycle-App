package com.fcu.android.bottlerecycleapp.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.fcu.android.bottlerecycleapp.SharedViewModel;
import com.fcu.android.bottlerecycleapp.database.ActivityItem;
import com.fcu.android.bottlerecycleapp.database.DBHelper;
import com.fcu.android.bottlerecycleapp.databinding.FragmentHomeBinding;

import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private List<ActivityItem> activityList;
    private DBHelper dbHelper;
    private int userId = 1;

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
        final ListView lvActivity = binding.lvActivities;

        // 從 SharedViewModel 取得uid
        SharedViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        sharedViewModel.getData().observe(getViewLifecycleOwner(), data -> {
            if (data != null) {
                userId = data.getId();
            }
        });
        Log.d("HomeFragment", "userId: " + userId);
        activityList = dbHelper.getUserActivities(userId);
        for(ActivityItem activityItem : activityList) {
            activityItem.setActivityName(dbHelper.findActivityById(activityItem.getActivityId()).getActivityName());
        }

        ActivityAdapter activityAdapter = new ActivityAdapter(getContext(), activityList);
        lvActivity.setAdapter(activityAdapter);



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