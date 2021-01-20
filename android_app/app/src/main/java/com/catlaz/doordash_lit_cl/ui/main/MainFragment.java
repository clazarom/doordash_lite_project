package com.catlaz.doordash_lit_cl.ui.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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

    //Fragments <TODO>
    //public static final int NUM_ITEMS = 2;
    private static final List<Fragment> fragmentPages = Collections.unmodifiableList(
            new ArrayList<Fragment>(){{add(new RestaurantsFragment()); add(new PlaceholderFragment()); }});
    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2}; // Tab names
    //Pager
    FragmentPagesCollectionAdapter fragmentPagesCollectionAdapter;
    ViewPager2 viewPager;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(_TAG, "View Created");


        //Initialize ViewPager
        //NOTE: Using ViewPager instead of ViewPager2 to be able to scroll listview inside viewpager
        //More about the issue and workarounds
        // -https://bladecoder.medium.com/fixing-recyclerview-nested-scrolling-in-opposite-direction-f587be5c1a04
        // -https://gist.github.com/cbeyls/b75d730795a4b4c2fcdce554b0b0782a
        fragmentPagesCollectionAdapter = new FragmentPagesCollectionAdapter(this, fragmentPages);
        viewPager = view.findViewById(R.id.view_pager);
        viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewPager.setAdapter(fragmentPagesCollectionAdapter);
        viewPager.registerOnPageChangeCallback(onPageChangeCallback);
        viewPager.setOnTouchListener(pagerOnTouchListener); //allow scrolling for the listview inside the viewpager

        //Tabs
        TabLayout tabLayout = view.findViewById(R.id.tabs);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(this.getResources().getString(TAB_TITLES[position]))).attach();

    }

    /* ****************************************
        LISTENERS
     */
    ViewPager2.OnPageChangeCallback onPageChangeCallback = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            //Adapt size to new child
            // nothing here for now...
        }

        @Override
        public void onPageSelected (int position){
            //Adapt size to new child
            fragmentPagesCollectionAdapter.notifyDataSetChanged();
        }
    };

    //Touch listener, to allow scrolling on the child view (list)

    @SuppressLint("ClickableViewAccessibility")
    View.OnTouchListener pagerOnTouchListener = (view, motionEvent) -> {
        Log.v(_TAG, "onItemTouch parent view");
        // Disallow VERTICAL touch request for parent scroll, when child onTouch
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.AXIS_VSCROLL:
                // Disallow interception for PageViewer to intercept touch events VERTICALLY.
                view.findViewById(R.id.list_restaurants).getParent().requestDisallowInterceptTouchEvent(false);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.AXIS_HSCROLL:
                // Allow interception for PageViewer to intercept touch events VERTICALLY.
                view.findViewById(R.id.list_restaurants).getParent()
                        .requestDisallowInterceptTouchEvent(true);
            break;
        }
        return false;
    };

}
