package com.catlaz.doordash_lit_cl.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.catlaz.doordash_lit_cl.Constant;
import com.catlaz.doordash_lit_cl.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * A placeholder fragment containing a simple map fragment.
 * https://developer.android.com/training/maps
 * @author Caterina lazaro
 * @version 1.0 Jan 2021
 */
public class MapFragment extends Fragment implements OnMapReadyCallback  {
    private static final String _TAG = "MAP_FRAGMENT";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);


        return inflater.inflate(R.layout.fragment_page_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(_TAG, "View Created");
        //<TODO>
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    /* *********************************************
        MAPS CALLBACKS
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(_TAG, "Map ready");
        LatLng ddHeadquarters = new LatLng(Constant._DD_HQ_LAT, Constant._DD_HQ_LONG);
        drawMainMarker(googleMap, ddHeadquarters);
    }

    private void drawMainMarker(GoogleMap googleMap, LatLng position){
        googleMap.addMarker(new MarkerOptions()
                .position(position)
                .title("Marker in Sydney"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(position));

    }
}