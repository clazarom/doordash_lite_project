package com.catlaz.doordash_lit_cl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.catlaz.doordash_lit_cl.ui.main.MainFragment;
import com.catlaz.doordash_lit_cl.utils.ApplicationConfigInformation;
import com.catlaz.doordash_lit_cl.utils.UiUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * Main Activity, to host the frame holder and a floating action button
 *
 * @author Caterina Lazaro
 * @version 1.1 Feb 2021
 */
public class MainActivity extends AppCompatActivity {
    //Debug logging tag
    private static final String _TAG = "MAIN_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Keep screen ON for debug mode
        if(ApplicationConfigInformation.isDebugging()){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }

        //Fragment holder initialize
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_placeholder, new MainFragment(), "main_restaurants_fragment"); // Replace the contents of the container with the new fragment
        ft.addToBackStack(null);
        ft.commit();

        //Back button navigation
        getOnBackPressedDispatcher().addCallback(this, onBackPressedCallback);

        //Floating  action button <TODO>
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, getResources().getString(R.string.app_my_info), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

        //Notification bar - initialize
        UiUtils.updateStatusBarNotifications(this, "DD Lit is Running and focused", R.drawable.ic_stat_restaurant, Constant.NOTIFICATION_ID );

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        //If on onStop() is called, also delete notification
        UiUtils.deleteStatusBarNotification(this);
    }

    /**
     * Make the MainActivity Start Intent
     * @param ctx context
     * @return start intent
     */
    public static Intent makeStartIntent(Context ctx){
        //Build MainActivity intent
        return new Intent(ctx, MainActivity.class);
    }

    // Back navigation callback
    final OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true /* enabled by default */) {
        /**
         * Handle onBackPressed events:
         * If there are fragments on the backStack, pop last up
         * If not, exit the app
         */
        @Override
        public void handleOnBackPressed() {
            Log.d(_TAG, "On back button pressed");
            // Handle navigation through fragments
            FragmentManager fm = getSupportFragmentManager();
            int lastFragEntry = fm.getBackStackEntryCount()-1;
            if (lastFragEntry > 0) {
                fm.popBackStackImmediate(); // go to previous fragment
            }else {
                //Exit app
                moveTaskToBack(true); //change focus out
            }
        }
    };

}