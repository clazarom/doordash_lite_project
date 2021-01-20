package com.catlaz.doordash_lit_cl;

import android.os.Bundle;

import com.catlaz.doordash_lit_cl.ui.main.MainFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

/**
 * Main Activity, to host the frame holder and a floating action button
 *
 * @author Caterina Lazaro
 * @version 1.0 Jan 2021
 */
public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Fragment holder initialize
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragemt_placeholder, new MainFragment()); // Replace the contents of the container with the new fragment
        ft.commit();

        //Floating  action button <TODO>
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
    }
}