package com.fcu.android.bottlerecycleapp.ui.notifications;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.fcu.android.bottlerecycleapp.R;
import com.fcu.android.bottlerecycleapp.databinding.FragmentPersonalDataBinding;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.ShapeAppearanceModel;

import org.w3c.dom.Text;

public class PersonalDataFragment extends Fragment {

    private FragmentPersonalDataBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PersonalDataViewModel personalDataViewModel =
                new ViewModelProvider(this).get(PersonalDataViewModel.class);

        binding = FragmentPersonalDataBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final ListView lvActivityTasks = binding.lvActivityTasks;
        final ImageView ivActivityChart = binding.ivActivityChart;
        final Button btnTransferRecord  = binding.btnTransferRecord;
        final Button btnRecycleRecord = binding.btnRecycleRecord;
        final ImageButton btnNotification = binding.btnNotification;
        final ImageButton btnSetting = binding.btnSetting;

        final TextView tvUserName = binding.tvUserName;
        final TextView tvPriceValue = binding.tvPriceValue;
        final ImageView ivAvaster = binding.ivAvaster;




        final TextView textView = binding.tvUserName;
        personalDataViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
