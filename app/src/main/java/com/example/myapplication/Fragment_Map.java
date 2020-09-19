package com.example.myapplication;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


import java.util.ArrayList;


public class Fragment_Map extends Fragment implements OnMapReadyCallback {

    protected View view;
    private GoogleMap mgoogleMap;
    MapView mMapView;

    ArrayList<Winner> scores;


    private CallBack_TopTen callBack_topTen;

    public void setListCallBack(CallBack_TopTen callBack_topTen) {
        this.callBack_topTen = callBack_topTen;
    }

    public static Fragment_Map newInstance() {

        Fragment_Map fragment = new Fragment_Map();
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_map, container, false);

        if (callBack_topTen != null) {
            callBack_topTen.GetTopsFromSP();
        }

        return view;
    }

    // Set locations on map
    private void showTopTenOnMap(GoogleMap googleMap) {
        int counter = 1;
        for (Winner current : scores) {
            if (counter <= 10) {
                googleMap.addMarker(new MarkerOptions().position(new LatLng(current.getLat(), current.getLon())).title("place " + counter).snippet(current.getName()));
                CameraPosition current_pos = CameraPosition.builder().target(new LatLng(current.getLat(), current.getLon())).build();
                googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(current_pos));
                //increase counter
                counter++;
            } else return;
        }
    }

    protected void setScores(ArrayList<Winner> topten) {
        scores = topten;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMapView = (MapView) view.findViewById(R.id.Map_map_view);
        if (mMapView != null) {
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());

        mgoogleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        showTopTenOnMap(googleMap);
    }
}