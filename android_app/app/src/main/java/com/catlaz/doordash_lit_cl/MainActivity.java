package com.catlaz.doordash_lit_cl;

import android.os.Bundle;
import android.util.Log;

import com.catlaz.doordash_lit_cl.ui.main.DetailFragment;
import com.catlaz.doordash_lit_cl.ui.main.MainFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * Main Activity, to host the frame holder and a floating action button
 *
 * @author Caterina Lazaro
 * @version 1.0 Jan 2021
 */
public class MainActivity extends AppCompatActivity {
    private static final String _TAG = "MAIN_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Fragment holder initialize
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragemt_placeholder, new MainFragment(), "Restaurants"); // Replace the contents of the container with the new fragment
        ft.addToBackStack(null);
        ft.commit();

        //Back button navigation
        getOnBackPressedDispatcher().addCallback(this, callback);

        //Floating  action button <TODO>
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
    }

    // This callback will only be called when MyFragment is at least Started.
    OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
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
                moveTaskToBack(true);
            }
        }
    };

}