package com.catlaz.doordash_lit_cl.utils;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Class to manage network connectivity (Internet)
 *
 * @author Caterina Lazaro
 * @version 1.0 Jan 2021
 */
public class NetworkStateManager {

    private final ConnectivityManager connectivityManager;

    /**
     * Constructor
     * Initialize the system connectivityManager
     * @param connectivityManager connectivityManager
     */
    public NetworkStateManager(ConnectivityManager connectivityManager){
        this.connectivityManager = connectivityManager;
    }

    /**
     * Test network connectivity upon start
     * @return available
     */
    public boolean isNetworkAvailable() {
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        //Check availability
        if (networkInfo != null && networkInfo.isConnected() && networkInfo.isAvailable()) {
            Log.i("Network Testing", "***Available***");

            return true;
        }else {
            Log.e("Network Testing", "***Not Available***");
            return false;
        }
    }
}
