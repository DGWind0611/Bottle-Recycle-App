package com.fcu.android.bottlerecycleapp.ui.recycle_map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        RecycleMapViewModel recycleMapViewModel =
                new ViewModelProvider(this).get(RecycleMapViewModel.class);

        binding = FragmentRecycleMapBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

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

        // Check for location permission
        if (ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(requireContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        mMap.setMyLocationEnabled(true);

        fusedLocationClient.getLastLocation().addOnSuccessListener(requireActivity(), new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
                    addRecycleMarkers();
                }
            }
        });
    }

    private void addRecycleMarkers() {

        // 自訂義五種顏色代表回收站的當前收容量
        Map<StationStatus, BitmapDescriptor> statusColorMap = new HashMap<>();
        statusColorMap.put(StationStatus.FULL, BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)); // 滿
        statusColorMap.put(StationStatus.ALMOST_FULL, BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)); // 快滿
        statusColorMap.put(StationStatus.HALF_FULL, BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)); // 半滿
        statusColorMap.put(StationStatus.ALMOST_EMPTY, BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)); // 三成滿
        statusColorMap.put(StationStatus.EMPTY, BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)); // 空

        // 回收站
        List<RecycleStation> stations = dbHelper.findAllStations();

        // 在地圖上標記回收站
        for (RecycleStation station : stations) {
            LatLng stationLocation = new LatLng(station.getLatitude(), station.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(stationLocation)
                    .title(station.getName())
                    .snippet(station.getAddress());

            // 根據回收站的狀態來設定 icon 顏色
            BitmapDescriptor color = statusColorMap.get(station.getStatus());
            if (color != null) {
                Log.d("RecycleMapFragment", "Station: " + station.getName() + " Status: " + station.getStatus());
                markerOptions.icon(color);
            }

            mMap.addMarker(markerOptions);
        }

        //TODO: 預設為顯示當前位置 目前先以虎尾科大當作中心
        LatLng defaultLocation = new LatLng(23.70265710296463, 120.42952217510486);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(defaultLocation));
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
