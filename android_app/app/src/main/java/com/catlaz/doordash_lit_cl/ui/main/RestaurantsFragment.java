package com.catlaz.doordash_lit_cl.ui.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;

import com.catlaz.doordash_lit_cl.R;
import com.catlaz.doordash_lit_cl.data.Restaurant;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * A fragment containing a list of restaurants close to DoorDash HQ (37.422740, -122.139956)
 *
 * @author Caterina lazaro
 * @version 1.0 Jan 2020
 */
public class RestaurantsFragment extends Fragment {
    private static final String _TAG = "RESTAURANTS_FRAGMENT";

    //Restaurants list adapter
    private RestaurantListAdapter rListAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_page_restaurants, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Set up the restaurants list
        ListView restaurantsListView = view.findViewById(R.id.list_restaurants);
        rListAdapter = new RestaurantListAdapter(getContext());
        restaurantsListView.setAdapter(rListAdapter);
        restaurantsListView.setOnItemClickListener(listOnItemClickListener);
        restaurantsListView.setOnTouchListener(rListOnTouchListener);

        //Refresh button
        Button refreshButton = view.findViewById(R.id.refresh_button);
        refreshButton.setOnClickListener(buttonOnClickListener);

    }

    /* ************************************************
       LISTENERS
     */
    // Restaurants list on item click listener
    AdapterView.OnItemClickListener listOnItemClickListener = (adapterView, view, i, l) -> {
        //<TODO>
    };

    //Refresh button on click listener: refresh list
    View.OnClickListener buttonOnClickListener = view -> {
        //<TODO>
        //Update with MOCK Restaurants list
        List<Restaurant> restaurantList = new ArrayList<Restaurant>();
        restaurantList.add(new Restaurant(1, "katsu burguer", "burguers, korean", "url"));
        restaurantList.add(new Restaurant(2, "howl at the moon", "italian, pasta", "url"));
        restaurantList.add(new Restaurant(3, "pagliaci", "italian, pizza", "url"));

        //Update list
        rListAdapter.updateRestaurantList(restaurantList);

    };

    //Touch listener, to allow scrolling on the list
    @SuppressLint("ClickableViewAccessibility")
    View.OnTouchListener rListOnTouchListener = (view, motionEvent) -> {
        Log.v(_TAG, "onTouch child view restaurants_list");
        // Disallow horizontal touch request for parent scroll, when child onTouch
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.AXIS_VSCROLL:
                // Disallow interception for ListView, to intercept touch events VERTICALLY.
                view.getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.AXIS_HSCROLL:
                // Allow interception for ListView, to intercept touch events VERTICALLY.
                view.getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }
        return false;
    };
}
