package com.catlaz.doordash_lit_cl.ui.main;

import android.annotation.SuppressLint;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.catlaz.doordash_lit_cl.Constant;
import com.catlaz.doordash_lit_cl.R;
import com.catlaz.doordash_lit_cl.data.Restaurant;
import com.catlaz.doordash_lit_cl.data.UpdatedValues;
import com.catlaz.doordash_lit_cl.remote.RestClient;
import com.catlaz.doordash_lit_cl.receivers.APIBroadcastListener;
import com.catlaz.doordash_lit_cl.receivers.APIUpdateBroadcastReceiver;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A fragment containing a list of restaurants close to DoorDash HQ (37.422740, -122.139956)
 * It request the data from DoorDash APIs and automatically update the contents of the list.
 * Main UI functions:
 *  REFRESH the list - load a new set of values
 *  LOAD MORE - request and add new values at the end of the list
 *
 * @author Caterina lazaro
 * @version 1.0 Jan 2021
 */
public class RestaurantsFragment extends Fragment  implements APIBroadcastListener {
    private static final String _TAG = "RESTAURANTS_FRAGMENT";

    //Restaurants list adapter
    private RestaurantsAdapter restaurantsAdapter;
    private RecyclerView restaurantsListView;
    private ConstraintLayout loadingView;
    private ImageView loadingImageGif;

    //Connect to DoorDash server
    private RestClient restClient;
    private APIUpdateBroadcastReceiver receiver;

    /**
     * Getter for the list adapter
     * @return restaurant list adapter
     */
    public RestaurantsAdapter getRestaurantListAdapter(){return restaurantsAdapter;}

    /**
     * On create view, return the new fragment
     * @param inflater inflater
     * @param container container[ViewGroup]
     * @param savedInstanceState savedInstanceState[Bundle]
     * @return fragment view
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        Log.d(_TAG, "onCreate");
        return inflater.inflate(R.layout.fragment_page_restaurants, container, false);
    }

    /**
     * onViewCreated, initialize:
     * - ViewList of restaurants, with its listAdapter
     * - Buttons refresh and load more
     * - RestClient, to send server requests
     * - BroadcastReceiver, to receive notification of internet events
     *
     * @param view view
     * @param savedInstanceState savedInstanceState[Bundle]
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(_TAG, "onViewCreated");
        //Set up the restaurants recycler view
        //1. Restaurant Adapter
        restaurantsAdapter = new RestaurantsAdapter();
        restaurantsAdapter.setFragmentManager(requireParentFragment().getParentFragmentManager());
        //2. Restaurants recycler view
        restaurantsListView = view.findViewById(R.id.list_restaurants);
        restaurantsListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        restaurantsListView.setAdapter(restaurantsAdapter);
        restaurantsListView.setOnTouchListener(rListOnTouchListener); //allow scroll vertically
        //More about the issue and workarounds with scrolling inside ViewPager2
        // -https://bladecoder.medium.com/fixing-recyclerview-nested-scrolling-in-opposite-direction-f587be5c1a04
        // -https://gist.github.com/cbeyls/b75d730795a4b4c2fcdce554b0b0782a
        restaurantsAdapter.notifyDataSetChanged();

        //REFRESH and load MORE button
        Button refreshButton = view.findViewById(R.id.refresh_button);
        refreshButton.setOnClickListener(buttonOnClickListener);
        Button moreButton = view.findViewById(R.id.more_button);
        moreButton.setOnClickListener(buttonOnClickListener);
        //Loading video
        loadingView = view.findViewById(R.id.loading_image_layout);
        loadingImageGif = view.findViewById(R.id.spin_video);
        startLoadingImage(loadingImageGif);

        //RestClient
        restClient= new RestClient(getContext());
        receiver = new APIUpdateBroadcastReceiver(this); // Create the receiver
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(receiver, new IntentFilter(Constant._BROADCAST_API_UPDATE)); // Register


        //Update contents of listview automatically
        List<Restaurant> restaurantList = UpdatedValues.Instance().getNewDownloadedRestaurantList();
        if (restaurantList.size()>0) {
            //If there is already data available...  update it!
            restaurantsAdapter.updateRestaurantList(UpdatedValues.Instance().getNewDownloadedRestaurantList(),
                    UpdatedValues.Instance().getRestaurantMap());
            showRestaurantsList(true);
        }else
            //If not, request from server
            restClient.getRestaurantsListByDoorDashHQ(0, Constant._REQ_NUM);

    }

    /**
     * onResume, register broadcastReceiver
     */
    @Override
    public void onResume(){
        super.onResume();
        Log.d(_TAG, "onResume");

        //Register broadcast receiver
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(receiver, new IntentFilter(Constant._BROADCAST_API_UPDATE)); // Register
    }

    /**
     * onPause, unregister broadcastReceiver and clean restClient
     */
    @Override
    public void onPause(){
        super.onPause();
        Log.d(_TAG, "onPause");
        //Clean remote disposables
        if (restClient != null)
            restClient.destroyDisposables();

        //Unregister Receiver
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(receiver); // Unregister
    }

    @Override
    public void updateUI(){
        List<Restaurant> restaurantsToUpdate = UpdatedValues.Instance().getNewDownloadedRestaurantList();
        if (restaurantsToUpdate.size()>0) {
            //Update restaurant list
            restaurantsAdapter.updateRestaurantList(restaurantsToUpdate,
                    UpdatedValues.Instance().getRestaurantMap());
            //Consume updated values
            UpdatedValues.Instance().cleanRestaurants();
            //Visible
            showRestaurantsList(true);
        }
    }

    /**
     * Initialize the GIF image LOADING
     * @param gifImageView rootView
     *
     */
    private void startLoadingImage(ImageView gifImageView){
        Log.d(_TAG, "start GIF image");
        //Load gif into ImageView using DrawableImageVieTarget
        gifImageView.setVisibility(View.VISIBLE);
        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(gifImageView);
        Glide.with(getActivity())
                .load(R.raw.dyn_spin)
                .placeholder(R.raw.dyn_spin)
                .centerCrop()
                .crossFade()
                .into(imageViewTarget);
    }

    /**
     * Transition between loading screen and restaurants list screen
     *
     * @param show show_restaurant_list
     */
    private void showRestaurantsList(boolean show){
        if (show) {
            restaurantsListView.setVisibility(View.VISIBLE);
            loadingView.setVisibility(View.GONE);
        }else{
            restaurantsListView.setVisibility(View.GONE);
            loadingView.setVisibility(View.VISIBLE);
            startLoadingImage(loadingImageGif); //... gif does not restart <TODO>
        }
    }


    /**
     * Click refresh button: clear current list and get a new one
     */
    private void onClickRefresh(){
        Log.d(_TAG, "onClickRefresh");
        //Get restaurants from Doordash server: async call
        restClient.getRestaurantsListByDoorDashHQ(0,Constant._REQ_NUM);
        //Clear the listview
        if (restaurantsAdapter != null)
            restaurantsAdapter.clearRestaurantList();
        else
            Log.e(_TAG,"restaurant list adapter is NULL!!!");

        showRestaurantsList(false);
    }

    /**
     * Click load more button: get new stores and add them at the end of the list
     */
    private void onClickMore(){
        Log.d(_TAG, "onClickLoadMore");
        int offset = restaurantsAdapter.getRestaurantsMap().size();
        //Get restaurants from DoorDash server: async call
        restClient.getRestaurantsListByDoorDashHQ(offset, Constant._REQ_NUM); // NEW METHOD TO DEVELOP

    }

    //Refresh button on click listener: refresh list
    final View.OnClickListener buttonOnClickListener = view -> {
        //Select the action to perform based on the button id
        if (view.getId() == R.id.refresh_button)
                onClickRefresh();
        else if (view.getId() == R.id.more_button)
                onClickMore();

        //Clicked on nothing
        Log.d(_TAG, "onClick nothing");
    };

    //Touch listener, to allow scrolling on the list
    final View.OnTouchListener rListOnTouchListener = (view, motionEvent) -> {
        Log.v(_TAG, "onTouch child view restaurants_list: "+motionEvent.getAction());
        // Disallow horizontal touch request for parent scroll, when child onTouch
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.AXIS_VSCROLL:
                Log.d(_TAG, "Disallow interception "+motionEvent.getAction());
                // Disallow interception for ListView, to intercept touch events VERTICALLY.
                view.getParent().requestDisallowInterceptTouchEvent(true);
                view.performClick();
                return true;
            case MotionEvent.AXIS_HSCROLL:
                Log.d(_TAG, "Allow interception "+motionEvent.getAction());
                // Allow interception for ListView, to intercept touch events VERTICALLY.
                view.getParent().requestDisallowInterceptTouchEvent(false);
                return false;
        }
        return false;
    };



}
