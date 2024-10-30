package com.fcu.android.bottlerecycleapp.ui.recycle_map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.fcu.android.bottlerecycleapp.R;
import com.fcu.android.bottlerecycleapp.database.DBHelper;
import com.fcu.android.bottlerecycleapp.database.entity.RecycleStation;
import com.fcu.android.bottlerecycleapp.database.entity.StationStatus;
import com.fcu.android.bottlerecycleapp.databinding.FragmentRecycleMapBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecycleMapFragment extends Fragment implements OnMapReadyCallback {

    private FragmentRecycleMapBinding binding;
    private GoogleMap mMap;
    private MapView mapView;
    private FusedLocationProviderClient fusedLocationClient;
    private DBHelper dbHelper;
    private LinearLayout llTips;
    private ImageButton btnMapInfo;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        RecycleMapViewModel recycleMapViewModel =
                new ViewModelProvider(this).get(RecycleMapViewModel.class);

        binding = FragmentRecycleMapBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        llTips = binding.llTips;
        llTips.setVisibility(View.GONE);

        btnMapInfo = binding.btnMapInfo;

        btnMapInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (llTips.getVisibility() == View.GONE) {
                    llTips.setVisibility(View.VISIBLE);
                } else {
                    llTips.setVisibility(View.GONE);
                }
            }
        });

        final TextView textView = binding.tvRecycleMapTitle;
        recycleMapViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        dbHelper = new DBHelper(requireContext());
        mapView = binding.mapView;
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        return root;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // 檢查位置權限
        if (ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(requireContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        // 啟用使用者位置顯示
        mMap.setMyLocationEnabled(true);

        fusedLocationClient.getLastLocation().addOnSuccessListener(requireActivity(), new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    // 當成功取得使用者位置時，將地圖移動至當前位置
                    LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
                } else {
                    // 無法取得位置時顯示預設座標
                    LatLng defaultLocation = new LatLng(23.70265710296463, 120.42952217510486);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 15));
                }
                addRecycleMarkers();
            }
        });
    }


    private void addRecycleMarkers() {
        List<RecycleStation> stations = dbHelper.findAllStations();

        for (RecycleStation station : stations) {
            LatLng stationLocation = new LatLng(station.getLatitude(), station.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(stationLocation)
                    .title(station.getName())
                    .snippet(station.getAddress());

            // 計算當前重量和最大重量的比例
            double currentWeight = station.getCurrentWeight();
            double maxWeight = station.getMaxWeight();
            double weightRatio = currentWeight / maxWeight;

            // 根據比例設置顏色區間
            int color;
            if (weightRatio > 0.95) {
                color = getResources().getColor(R.color.map_full_red); // 滿
            } else if (weightRatio >= 0.7 && weightRatio < 0.95) {
                color = getResources().getColor(R.color.map_almost_full_orange); // 快滿
            } else if (weightRatio >= 0.45 && weightRatio < 0.7) {
                color = getResources().getColor(R.color.map_half_full_yellow); // 半滿
            } else if (weightRatio >= 0.25 && weightRatio < 0.45) {
                color = getResources().getColor(R.color.map_almost_empty_blue); // 三成滿
            } else {
                color = getResources().getColor(R.color.map_empty_green); // 空
            }

            float hue = getHueFromColor(color); // 將顏色轉換為色相值
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(hue));

            mMap.addMarker(markerOptions);
        }

        // 預設顯示位置
        LatLng defaultLocation = new LatLng(23.70265710296463, 120.42952217510486);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(defaultLocation));
    }



    @NonNull
    private BitmapDescriptor createColoredMarker(int color) {
        int markerSize = 100; // 設置標記大小（像素）
        Bitmap bitmap = Bitmap.createBitmap(markerSize, markerSize, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(markerSize / 2, markerSize / 2, markerSize / 2, paint);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    private float getHueFromColor(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        return hsv[0]; // 取得色相值
    }



    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mapView.onDestroy();
        binding = null;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

}
