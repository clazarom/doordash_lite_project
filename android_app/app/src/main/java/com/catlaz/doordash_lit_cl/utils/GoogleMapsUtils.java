package com.catlaz.doordash_lit_cl.utils;

import android.util.Log;

import com.catlaz.doordash_lit_cl.Constant;
import com.catlaz.doordash_lit_cl.data.Restaurant;
import com.catlaz.doordash_lit_cl.data.UpdatedValues;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Map;

public class GoogleMapsUtils implements OnMapReadyCallback {

    private static final String _TAG = "GOOGLE_MAPS_UTIL";

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(_TAG, "Map ready");
        LatLng ddHeadquarters = new LatLng(Constant._DD_HQ_LAT, Constant._DD_HQ_LONG);
        drawMainMarker(googleMap, ddHeadquarters,"DoorDash");
    }

    /**
     * Method to draw the main marker value and center the map view around it
     * @param googleMap googleMap
     * @param position position [Latlng]
     */
    private void drawMainMarker(GoogleMap googleMap, LatLng position, String title){
        //Move camera to show market
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(position));
        //Zoom in
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(Constant._ZOOM_CITY));

        //Place market
        drawMarker(googleMap, position, title);
        //Draw all restaurants
        drawRestaurantList(googleMap);
    }

    /**
     * Method to add a new market to the map
     * @param googleMap googleMap
     * @param position position [LatLng
     */
    private void drawMarker(GoogleMap googleMap, LatLng position, String title){
        //Place market
        googleMap.addMarker(new MarkerOptions()
                .position(position)
                .title(title));
    }

    /**
     * Draw markers for all restaurants in UpdatedValues
     * @param googleMap googleMap
     */
    private void drawRestaurantList(GoogleMap googleMap){
        //Get restaurant list: from UpdatedValues
        Map<Integer, Restaurant> restaurantMap = UpdatedValues.Instance().getRestaurantMap();

        //Iterate through all values
        for (Map.Entry<Integer, Restaurant> restaurantEntry: restaurantMap.entrySet()){
            //Get restaurants position:
            LatLng position = new LatLng(restaurantEntry.getValue().getLocation().getLat(),
                    restaurantEntry.getValue().getLocation().getLng());
            //Draw restaurant marker
            drawMarker(googleMap, position, restaurantEntry.getValue().getName());
        }
    }
}
