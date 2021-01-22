package com.catlaz.doordash_lit_cl.ui.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import com.catlaz.doordash_lit_cl.data.UpdatedValues;
import com.catlaz.doordash_lit_cl.remote.RestClient;

/**
 * Broadcast Receiver to Update downloaded values
 *
 * @author Caterina lazaro
 * @version 1.0 Jan 2021
 */
public class UpdatesBroadcastReceiver extends BroadcastReceiver {
    private static final String _TAG = "UPDATES_BROADCAST_RECEIVER";
    //Handler
    private final Handler handler; // to execute code on the UI thread
    private final RestaurantListAdapter restaurantListAdapter; //listview to update

    /**
     * Constructor default
     */
    public UpdatesBroadcastReceiver() {
        handler = new Handler();
        restaurantListAdapter = null;
    }

    /**
     * Constructor
     *
     * @param handler  handler
     * @param restaurantListAdapter restaurant list adapter
     */
    public UpdatesBroadcastReceiver(Handler handler, RestaurantListAdapter restaurantListAdapter) {
        this.handler = handler;
        this.restaurantListAdapter = restaurantListAdapter;
    }

    /**
     * Constructor
     *
     * @param handler handler
     */
    public UpdatesBroadcastReceiver(Handler handler) {
        this.handler = handler;
        this.restaurantListAdapter = null;
    }

    /**
     * Callback when an intent is received
     *
     * @param context context
     * @param intent  intent
     */
    @Override
    public void onReceive(final Context context, Intent intent) {
        Log.d(_TAG, "Message received");
        // Update ListView
        boolean updated = intent.getBooleanExtra(RestClient._UPDATE_LIST_KEY, false);
        if (updated && restaurantListAdapter != null)
            handler.post(() -> {
                //Update UI
                restaurantListAdapter.updateRestaurantList(UpdatedValues.Instance().getRestaurantList(),
                        UpdatedValues.Instance().getRestaurantImageMap());
                //Consume updated values
                UpdatedValues.Instance().cleanRestaurants();
            });

        //Update Details
        // Update ListView
        updated = intent.getBooleanExtra(RestClient._UPDATE_DETAIL_KEY, false);
        if (updated && restaurantListAdapter != null)
            handler.post(() -> {
                //Update UI
                //UpdatedValues.Instance().getRestaurantDetailMap(id);
                //TODO
            });
    }

}
