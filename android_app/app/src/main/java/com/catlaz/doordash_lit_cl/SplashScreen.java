package com.catlaz.doordash_lit_cl;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.catlaz.doordash_lit_cl.remote.RestClient;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

/**
 * Splash screen to load before the application starts
 *
 * @author Caterina Lazaro
 * @version 1.0 Jan 2021
 */

public class SplashScreen extends AppCompatActivity {

    Context mContext;
    private static final int _SPLASH_DISPLAY_LENGTH = 5; //seconds
    public static final int _INIT_REQ_NUM = 50;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        mContext = this;
        View decorView = getWindow().getDecorView();
        // Hide both the navigation bar and the status bar.
        // SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        if (isNetworkAvailable(mContext)) {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */
            new Handler().postDelayed(() -> {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(getBaseContext(), MainActivity.class);
                i.addFlags(FLAG_ACTIVITY_CLEAR_TOP);
               mContext.startActivity(i);

                // close this activity
                this.finish();
            }, _SPLASH_DISPLAY_LENGTH*1000);

            //Internet Downloads in advance
            new RestClient(this).getRestaurantsListByDoorDashHQ(0, _INIT_REQ_NUM);

        } else {
            Toast.makeText(com.catlaz.doordash_lit_cl.SplashScreen.this, "No Network Service!,Restart the application by switching on the Network Service",
                    Toast.LENGTH_SHORT).show();
            showNotification("No Network Service!,Restart the application by switching on the Network Service");
        }
    }

    /**
     * Test network connectivity upon start
     * @param ctx context
     * @return available
     */
    public  static boolean isNetworkAvailable(Context ctx) {
        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Log.e("Network Testing", "***Available***");
            return true;
        }
        Log.e("Network Testing", "***Not Available***");
        return false;
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
