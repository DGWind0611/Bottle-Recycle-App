package com.fcu.android.bottlerecycleapp.ui.recycle_map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.fcu.android.bottlerecycleapp.databinding.FragmentRecycleMapBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

public class RecycleMapFragment extends Fragment implements OnMapReadyCallback {

    private FragmentRecycleMapBinding binding;
    private GoogleMap mMap;
    private MapView mapView;
    private FusedLocationProviderClient fusedLocationClient;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        RecycleMapViewModel recycleMapViewModel =
                new ViewModelProvider(this).get(RecycleMapViewModel.class);

        binding = FragmentRecycleMapBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.tvRecycleMapTitle;
        recycleMapViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

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

        //自訂義三種顏色 紅 橙 綠 代表回收站的當前收容量
        List<BitmapDescriptor> colors = new ArrayList<>();
        BitmapDescriptor red = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
        BitmapDescriptor orange = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE);
        BitmapDescriptor green = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);

        colors.add(red);
        colors.add(orange);
        colors.add(green);

        // 回收站
        //TODO: 這裡要改成從資料庫讀取回收站的位置
        List<LatLng> locations = new ArrayList<>();
        locations.add(new LatLng(25.0330, 121.5654)); // Taipei 101
        locations.add(new LatLng(25.0375, 121.5637)); // Sun Yat-sen Memorial Hall

        //在地圖上標記回收站
        for (LatLng location : locations) {
            mMap.addMarker(new MarkerOptions()
                    .position(location)
                    .title("Recycle Station")
                    .icon(colors.get((int) (Math.random() * 3))));
        }
        LatLng sydney = new LatLng(25.0330, 121.5654);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
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
