package com.catlaz.doordash_lit_cl.ui.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.catlaz.doordash_lit_cl.data.Restaurant;
import com.catlaz.doordash_lit_cl.data.RestaurantDetail;
import com.catlaz.doordash_lit_cl.data.UpdatedValues;
import com.catlaz.doordash_lit_cl.remote.RestClient;

import java.util.List;

import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * Broadcast Receiver to Update downloaded values
 *
 * @author Caterina lazaro
 * @version 1.0 Jan 2021
 */
public class APIUpdateBroadcastReceiver extends BroadcastReceiver {
    private static final String _TAG = "API_UPDATE_BROADCAST_RECEIVER";

    //Interface listener for UI
    private APIBroadcastListener apiListener;


    /**
     * Constructor default
     */
    public APIUpdateBroadcastReceiver() {
    }

    /**
     * Constructor  to update UI
     * @param apiListener apiBroadcastListener
     */
    public APIUpdateBroadcastReceiver(APIBroadcastListener apiListener){
        this.apiListener = apiListener;
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
        if (updated && apiListener!=null)
            apiListener.updateUI();

        //Update Details
        // Update ListView
        updated = intent.getBooleanExtra(RestClient._UPDATE_DETAIL_KEY, false);
        if (updated && apiListener!=null)
            apiListener.updateUI();
    }

}
