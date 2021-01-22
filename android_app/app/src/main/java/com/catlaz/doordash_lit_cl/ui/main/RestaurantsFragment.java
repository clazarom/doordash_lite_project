package com.catlaz.doordash_lit_cl.ui.main;

import android.annotation.SuppressLint;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.catlaz.doordash_lit_cl.R;
import com.catlaz.doordash_lit_cl.data.Restaurant;
import com.catlaz.doordash_lit_cl.data.UpdatedValues;
import com.catlaz.doordash_lit_cl.remote.RestClient;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

/**
 * A fragment containing a list of restaurants close to DoorDash HQ (37.422740, -122.139956)
 *
 * @author Caterina lazaro
 * @version 1.0 Jan 2021
 */
public class RestaurantsFragment extends Fragment {
    private static final String _TAG = "RESTAURANTS_FRAGMENT";

    //Restaurants list adapter
    private RestaurantListAdapter rListAdapter;


    //Connect to DoorDash server
    private RestClient restClient;
    private UpdatesBroadcastReceiver receiver;

    /**
     * Getter for the list adapter
     * @return restaurant list adapter
     */
    public RestaurantListAdapter getrListAdapter(){return rListAdapter;}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d(_TAG, "onCreate");

        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_page_restaurants, container, false);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(_TAG, "onViewCreated");

        //Set up the restaurants list
        rListAdapter = new RestaurantListAdapter(getActivity());
        ListView restaurantsListView = view.findViewById(R.id.list_restaurants);
        restaurantsListView.setAdapter(rListAdapter);
        restaurantsListView.setOnItemClickListener(listOnItemClickListener);
        restaurantsListView.setOnTouchListener(rListOnTouchListener);
        rListAdapter.notifyDataSetChanged();

        //Refresh button
        Button refreshButton = view.findViewById(R.id.refresh_button);
        refreshButton.setOnClickListener(buttonOnClickListener);

        //RestClient
        restClient= new RestClient(getContext());
        receiver = new UpdatesBroadcastReceiver(new Handler(), rListAdapter); // Create the receiver
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(receiver, new IntentFilter(RestClient._BROADCAST_API_UPDATE)); // Register

        //Update contents of listview automatically
        List<Restaurant> restaurantList = UpdatedValues.Instance().getRestaurantList();
        if (restaurantList.size()>0)
            //If there is already data available...  update it!
            rListAdapter.updateRestaurantList(UpdatedValues.Instance().getRestaurantList(),
                    UpdatedValues.Instance().getRestaurantImageMap());
        else
            //If not, request from server
            restClient.getRestaurantsListByDoorDashHQ();

    }

    @Override
    public void onPause(){
        super.onPause();

        Log.d(_TAG, "onPause");
        //Clean remote disposables
        if (restClient != null)
            restClient.destroyDisposables();

        //Unregister Receiver
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(receiver); // Unregister
    }

    /* ************************************************
       LISTENERS
     */
    // Restaurants list on item click listener
    AdapterView.OnItemClickListener listOnItemClickListener = (adapterView, view, i, l) -> {
        Log.v(_TAG, "onItemClick view restaurants_list");
        //Open another screen with the restaurant's details
        Bundle bundle = new Bundle();
        bundle.putString("name", ((Restaurant) rListAdapter.getItem(i)).getName());
        bundle.putInt("id", ((Restaurant) rListAdapter.getItem(i)).getId());
        bundle.putString("description", ((Restaurant) rListAdapter.getItem(i)).getDescription());


        //Fragment holder initialize
        final FragmentTransaction ft = getParentFragment().getParentFragmentManager().beginTransaction();
        DetailFragment mFragment = new DetailFragment();
        mFragment.setArguments(bundle);
        ft.replace(R.id.fragemt_placeholder, mFragment, "Detail");
        ft.addToBackStack(null);
        ft.commit();

    };

    //Refresh button on click listener: refresh list
    View.OnClickListener buttonOnClickListener = view -> {
        //Get restaurants from Doordash server: async call
        restClient.getRestaurantsListByDoorDashHQ();
        
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
