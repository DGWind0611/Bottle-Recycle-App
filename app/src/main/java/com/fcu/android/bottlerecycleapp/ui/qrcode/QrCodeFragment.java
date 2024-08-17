package com.fcu.android.bottlerecycleapp.ui.qrcode;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fcu.android.bottlerecycleapp.R;
import com.fcu.android.bottlerecycleapp.databinding.FragmentDashboardBinding;
import com.fcu.android.bottlerecycleapp.databinding.FragmentQrCodeBinding;
import com.fcu.android.bottlerecycleapp.ui.dashboard.DashboardViewModel;


public class QrCodeFragment extends Fragment {

    private FragmentQrCodeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        QrCodeViewModel qrCodeViewModel =
                new ViewModelProvider(this).get(QrCodeViewModel.class);

        binding = FragmentQrCodeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.tvQRCODE;
        qrCodeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}