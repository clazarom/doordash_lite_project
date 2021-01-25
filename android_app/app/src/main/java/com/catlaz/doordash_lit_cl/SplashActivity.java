package com.catlaz.doordash_lit_cl;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.catlaz.doordash_lit_cl.remote.RestClient;
import com.catlaz.doordash_lit_cl.utils.NetworkStateManager;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

/**
 * Splash screen to load before the application starts
 *
 * @author Caterina Lazaro
 * @version 1.0 Jan 2021
 */

public class SplashActivity extends AppCompatActivity {

    Context mContext;
    private static final int _SPLASH_DISPLAY_LENGTH = 5; //seconds
    public static final int _INIT_REQ_NUM = 50;

    //Network connectivity
    public NetworkStateManager networkStateManager;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mContext = this;
        View decorView = getWindow().getDecorView();
        // Hide both the navigation bar and the status bar.
        // SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        //Check network availability
        networkStateManager = new NetworkStateManager((ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE));
        if (networkStateManager.isNetworkAvailable()) {

            //Show SPLASH screen with a timer
            new Handler().postDelayed(() -> {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(getBaseContext(), MainActivity.class);
                i.addFlags(FLAG_ACTIVITY_CLEAR_TOP); // never navigate back here
               mContext.startActivity(i);

                // close this activity
                this.finish();
            }, _SPLASH_DISPLAY_LENGTH*1000);

            //Internet Downloads in advance
            new RestClient(this).getRestaurantsListByDoorDashHQ(0, _INIT_REQ_NUM);

        } else {
            Toast.makeText(SplashActivity.this,
                    R.string.no_network_message_string,
                    Toast.LENGTH_SHORT).show();

            showNotification(getString(R.string.no_network_message_string));
        }
    }



    /**
     * Show a snackbar notification at the bottom of the activity
     * @param message notification-message
     */
    public void showNotification(String message){
        if (this.findViewById(android.R.id.content) != null)
            Snackbar.make(this.findViewById(android.R.id.content), message, Snackbar.LENGTH_INDEFINITE).show();
    }


}