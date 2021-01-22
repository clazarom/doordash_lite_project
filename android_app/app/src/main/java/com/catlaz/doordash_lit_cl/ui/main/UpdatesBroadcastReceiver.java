package com.catlaz.doordash_lit_cl.ui.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import com.catlaz.doordash_lit_cl.data.Restaurant;
import com.catlaz.doordash_lit_cl.data.RestaurantDetail;
import com.catlaz.doordash_lit_cl.data.UpdatedValues;
import com.catlaz.doordash_lit_cl.remote.RestClient;

import org.w3c.dom.Text;

import java.util.List;

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

    //UI elements
    private final RestaurantListAdapter restaurantListAdapter; //listview to update
    private TextView telephone;
    private TextView address;
    private int id;

    /**
     * Constructor default
     */
    public UpdatesBroadcastReceiver() {
        handler = new Handler();
        restaurantListAdapter = null;
        id = -1;
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
        id = -1;

    }

    /**
     * Constructor for the Restaurant Detail
     *
     * @param handler handler
     */
    public UpdatesBroadcastReceiver(Handler handler, TextView telephone, TextView address, int id) {
        this.handler = handler;
        this.restaurantListAdapter = null;
        this.telephone = telephone;
        this.address = address;
        this.id = id;
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
        List<Restaurant> restaurantsToUpdate = UpdatedValues.Instance().getRestaurantList();
        if (updated && restaurantListAdapter != null && restaurantsToUpdate.size()>0)
            handler.post(() -> {
                //Update UI
                restaurantListAdapter.updateRestaurantList(restaurantsToUpdate,
                        UpdatedValues.Instance().getRestaurantImageMap());
                //Consume updated values
                UpdatedValues.Instance().cleanRestaurants();
            });

        //Update Details
        // Update ListView
        updated = intent.getBooleanExtra(RestClient._UPDATE_DETAIL_KEY, false);
        if (updated && telephone != null && address != null && id>0)
            handler.post(() -> {
                //Update UI
                RestaurantDetail rDetail = UpdatedValues.Instance().getRestaurantDetailMap().get(id);
                telephone.setText(rDetail.getPhone_number());
                address.setText(rDetail.getAddress().toString());
            });
    }

}
