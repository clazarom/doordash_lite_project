package com.catlaz.doordash_lit_cl;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.catlaz.doordash_lit_cl.remote.RestClient;
import com.catlaz.doordash_lit_cl.utils.NetworkStateManager;
import com.catlaz.doordash_lit_cl.ui.splash.PermissionsAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;


/**
 * Splash screen to load before the application starts
 *
 * @author Caterina Lazaro
 * @version 1.0 Jan 2021
 */

public class SplashActivity extends AppCompatActivity {

    private static final String _TAG = "SPLASH_ACTIVITY";

    //Request permissions
    PermissionsAlertDialogBuilder permissionsAlertDialogBuilder;

    //Network connectivity
    public NetworkStateManager networkStateManager;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set layout
        setContentView(R.layout.activity_splash);
        Log.d(_TAG, "On Create");

        //Initialize Activity
        initializeSplashActivity();

        //Request permissions
        final boolean needRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, ACCESS_COARSE_LOCATION);
        permissionsAlertDialogBuilder =  new PermissionsAlertDialogBuilder(this,
                "Without this permission Maps do not work, allow it in order to connect to the device.",
                "Permission required", needRationale);

        //Check network availability
        networkStateManager = new NetworkStateManager((ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE));
        if (networkStateManager.isNetworkAvailable()) {
            Log.d(_TAG, "Internet enabled");
            //If permissions are enabled, we run the application
            if(permissionsAlertDialogBuilder.requestPermissions(this))
                runSplashActions();
            else
                showNotification("Please enable permissions, Bluetooth connection to media devices");

        } else {
            Log.d(_TAG, "No Internet connection");
            //Show two notifications: Toast and Snackbar
            Toast.makeText(SplashActivity.this,
                    R.string.no_network_message_string,
                    Toast.LENGTH_SHORT).show();

            showNotification(getString(R.string.no_network_message_string));
        }
    }

    /**
     * Set up Splash Screen parameters
     */
    private void initializeSplashActivity(){
        // Hide both the navigation bar and the status bar.
        // SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    /**
     * Show a snackbar notification at the bottom of the activity
     * @param message notification-message
     */
    public void showNotification(String message){
        if (this.findViewById(android.R.id.content) != null)
            Snackbar.make(this.findViewById(android.R.id.content), message, Snackbar.LENGTH_INDEFINITE).show();
    }

    /**
     * Method to run the functions SPLASH performs: after a delaye to show the logo, do
     * - Start MainActivity
     * - Download info in advance
     */
    private void runSplashActions(){
        //Show SPLASH screen with a timer
        new Handler().postDelayed(() -> {
            // Start your app main activity
            Intent i = MainActivity.makeStartIntent(this);
            i.addFlags(FLAG_ACTIVITY_CLEAR_TOP); // never navigate back here
            this.startActivity(i);

            // close this activity
            this.finish();
        }, Constant._SPLASH_DISPLAY_LENGTH*1000);

        //Internet Downloads in advance
        new RestClient(this).getRestaurantsListByDoorDashHQ(0, Constant._INIT_REQ_NUM);
    }

    /* ******************************************************************
        REQUEST PERMISSIONS:
            - Location
     */

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PermissionsAlertDialogBuilder.REQUEST_PERMISSION_ACCESS_COARSE_LOCATION) {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission was granted
                Log.d(_TAG, "Permission granted!");
                //Continue with SPLASH execution
                runSplashActions();
            } else {
                // Permission denied, boo!
                //Show the PermissionsAlertDialogBuilder
                 permissionsAlertDialogBuilder.show();

            }
        } else {
            Log.w(_TAG, "Permission request not recognized");
        }
    }


}
