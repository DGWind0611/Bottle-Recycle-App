package com.fcu.android.bottlerecycleapp.ui.qrcode;

import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;
import com.fcu.android.bottlerecycleapp.R;
import com.fcu.android.bottlerecycleapp.databinding.FragmentQrCodeBinding;
import java.util.Arrays;
import java.util.List;

public class QrCodeFragment extends Fragment {

    private FragmentQrCodeBinding binding;
    private ViewPager2 viewPager2;
    private ImageButton btnLeft, btnRight;
    private boolean isExpanded1 = true; // 初始狀態為展開

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentQrCodeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        viewPager2 = binding.vp2Qrcode;
        btnLeft = binding.btnLeft;
        btnRight = binding.btnRight;

        viewPager2.setPageTransformer(new SlowPageTransformer());

        List<String> qrCodes = Arrays.asList("Test QR Code 1", "Test QR Code 2", "Test QR Code 3");

        QrCodeAdapter adapter = new QrCodeAdapter(qrCodes);
        viewPager2.setAdapter(adapter);

        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int previousItem = viewPager2.getCurrentItem() - 1;
                if (previousItem >= 0) {
                    viewPager2.setCurrentItem(previousItem, true);
                }
            }
        });

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int nextItem = viewPager2.getCurrentItem() + 1;
                if (nextItem < adapter.getItemCount()) {
                    viewPager2.setCurrentItem(nextItem, true);
                }
            }
        });

        final TextView textView = binding.tvQRCODE;

        final ImageView ivUserCode = binding.ivUserCode;
        final ImageButton btnExpand1 = binding.btnExpand1;
        final View line1 = binding.viewLine;

        final TextView tvCustomizedName = binding.tvCustomizedName;
        final ViewPager2 vp2Qrcode = binding.vp2Qrcode;
        final ImageButton btnExpand2 = binding.btnExpand2;
        final ImageButton btnLeft = binding.btnLeft;
        final ImageButton btnRight = binding.btnRight;

        btnExpand1.setOnClickListener(v -> {
            if (isExpanded1) {
                ivUserCode.setVisibility(View.GONE);  // 隱藏圖片
                line1.setVisibility(View.GONE);
                btnExpand1.setImageResource(android.R.drawable.arrow_up_float);  // 更改按鈕圖片為向上箭頭
            } else {
                ivUserCode.setVisibility(View.VISIBLE);  // 顯示圖片
                line1.setVisibility(View.VISIBLE);
                btnExpand1.setImageResource(android.R.drawable.arrow_down_float);  // 更改按鈕圖片為向下箭頭
            }
            isExpanded1 = !isExpanded1;  // 切換展開狀態
        });

        btnExpand2.setOnClickListener(v -> {
            if (vp2Qrcode.getVisibility() == View.VISIBLE) {
                vp2Qrcode.setVisibility(View.GONE);  // 隱藏ViewPager2
                btnLeft.setVisibility(View.GONE);
                btnRight.setVisibility(View.GONE);
                tvCustomizedName.setVisibility(View.GONE);
                btnExpand2.setImageResource(android.R.drawable.arrow_up_float);  // 更改按鈕圖片為向上箭頭
            } else {
                vp2Qrcode.setVisibility(View.VISIBLE);  // 顯示ViewPager2
                btnLeft.setVisibility(View.VISIBLE);
                btnRight.setVisibility(View.VISIBLE);
                tvCustomizedName.setVisibility(View.VISIBLE);
                btnExpand2.setImageResource(android.R.drawable.arrow_down_float);  // 更改按鈕圖片為向下箭頭
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
