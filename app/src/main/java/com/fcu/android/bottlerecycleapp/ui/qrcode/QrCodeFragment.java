package com.fcu.android.bottlerecycleapp.ui.qrcode;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.fcu.android.bottlerecycleapp.R;
import com.fcu.android.bottlerecycleapp.SharedViewModel;
import com.fcu.android.bottlerecycleapp.databinding.FragmentQrCodeBinding;
import com.fcu.android.bottlerecycleapp.ui.qrcode.QrCodeAdapter;
import com.fcu.android.bottlerecycleapp.ui.qrcode.SlowPageTransformer;

import android.util.Pair;
import java.util.Arrays;
import java.util.List;

public class QrCodeFragment extends Fragment {

    private FragmentQrCodeBinding binding;
    private ViewPager2 viewPager2;
    private ImageButton btnLeft, btnRight;
    private ImageView ivUserCode;
    private boolean isExpanded1 = true; // 初始狀態為展開

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentQrCodeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        viewPager2 = binding.vp2Qrcode;
        btnLeft = binding.btnLeft;
        btnRight = binding.btnRight;
        ivUserCode = binding.ivUserCode;

        final ImageButton btnExpand1 = binding.btnExpand1;
        final View line1 = binding.viewLine;
        final TextView tvCustomizedName = binding.tvCustomizedName;
        final ImageButton btnExpand2 = binding.btnExpand2;

        // 從 SharedViewModel 取得資料
        SharedViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        sharedViewModel.getData().observe(getViewLifecycleOwner(), data -> {
            Log.d("QrCodeFragment", "Received data: " + data);
            if (data != null) {
                String qrCode = data.getQrCode();
                Log.d("QrCodeFragment", "QR Code: " + qrCode);
                if (qrCode != null && !qrCode.isEmpty()) {
                    QrCodeAdapter.createQrCode(qrCode, ivUserCode);
                } else {
                    ivUserCode.setImageResource(R.drawable.test_user);  // 替換為預設的佔位符圖片
                    Toast.makeText(getActivity(), "QR Code 無效", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 顯示自訂義QR Code
        viewPager2.setPageTransformer(new SlowPageTransformer());
        List<String> qrCodes = Arrays.asList("Test QR Code 1", "Test QR Code 2", "Test QR Code 3");

        QrCodeAdapter adapter = new QrCodeAdapter(qrCodes);
        viewPager2.setAdapter(adapter);

        // 左右按鈕邏輯
        btnLeft.setOnClickListener(v -> {
            int previousItem = viewPager2.getCurrentItem() - 1;
            if (previousItem >= 0) {
                viewPager2.setCurrentItem(previousItem, true);
            }
        });

        btnRight.setOnClickListener(v -> {
            int nextItem = viewPager2.getCurrentItem() + 1;
            if (nextItem < adapter.getItemCount()) {
                viewPager2.setCurrentItem(nextItem, true);
            }
        });

        // 展開/隱藏 QR Code 邏輯
        btnExpand1.setOnClickListener(v -> {
            if (isExpanded1) {
                ivUserCode.setVisibility(View.GONE);  // 隱藏圖片
                line1.setVisibility(View.GONE);
                btnExpand1.setImageResource(R.drawable.arrow_up);  // 更改按鈕圖片為向上箭頭
            } else {
                ivUserCode.setVisibility(View.VISIBLE);  // 顯示圖片
                line1.setVisibility(View.VISIBLE);
                btnExpand1.setImageResource(R.drawable.arrow_down);  // 更改按鈕圖片為向下箭頭
            }
            isExpanded1 = !isExpanded1;
        });

        // 展開/隱藏自訂義 QR Code 的邏輯
        btnExpand2.setOnClickListener(v -> {
            if (viewPager2.getVisibility() == View.VISIBLE) {
                viewPager2.setVisibility(View.GONE);
                btnLeft.setVisibility(View.GONE);
                btnRight.setVisibility(View.GONE);
                tvCustomizedName.setVisibility(View.GONE);
                btnExpand2.setImageResource(R.drawable.arrow_up);
            } else {
                viewPager2.setVisibility(View.VISIBLE);
                btnLeft.setVisibility(View.VISIBLE);
                btnRight.setVisibility(View.VISIBLE);
                tvCustomizedName.setVisibility(View.VISIBLE);
                btnExpand2.setImageResource(R.drawable.arrow_down);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
