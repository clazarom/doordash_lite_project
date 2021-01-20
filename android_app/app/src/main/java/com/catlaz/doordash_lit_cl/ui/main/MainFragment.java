package com.catlaz.doordash_lit_cl.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.catlaz.doordash_lit_cl.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

public class MainFragment extends Fragment {
    private static final String _TAG = "MAIN_FRAGMENT";

    //Fragments
    public static final int NUM_ITEMS = 2;
    private static final List<Fragment> fragmentPages = Collections.unmodifiableList(
            new ArrayList<Fragment>(){{add(new PlaceholderCollectionFragment()); add(new PlaceholderCollectionFragment());}});

    //Pager
    PageCollectionAdapter pageCollectionAdapter;
    ViewPager2 viewPager;
    //Tabs information
    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2}; //TODO !!

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(_TAG, "View Created");

        //Get tabs names
        ArrayList<String> tabNames = new ArrayList<>();
        for (int i=0; i< TAB_TITLES.length; i++)
            tabNames.add(this.getResources().getString(TAB_TITLES[i]));

        //Initialize ViewPager
        pageCollectionAdapter = new PageCollectionAdapter(this, fragmentPages);
        viewPager = view.findViewById(R.id.view_pager);
        viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewPager.setAdapter(pageCollectionAdapter);

        //Tabs
        TabLayout tabLayout = view.findViewById(R.id.tabs);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(this.getResources().getString(TAB_TITLES[position]))).attach();

    }

}
