package com.catlaz.doordash_lit_cl.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.catlaz.doordash_lit_cl.Constant;
import com.catlaz.doordash_lit_cl.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

/**
 * Main Fragment of the Main Activity, displaying a viewPager. Currently, we have two page fragments:
 * - RestaurantsFragment: to display a list of restaurants
 * - Map Fragment: to show a map with the location of those fragments
 *
 * @author Caterina Lazaro
 * @version 1.0 Jan 2021
 */
public class MainFragment extends Fragment {
    private static final String _TAG = "MAIN_FRAGMENT";

    //Pager
    FragmentPagesCollectionAdapter fragmentPagesCollectionAdapter;
    ViewPager2 viewPager;

    /**
     * Get the fragment currently being displayed
     * @return current fragment
     */
    public Fragment getCurrentPage(){
        return Constant.fragmentPages.get(viewPager.getCurrentItem());
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(_TAG, "View Created");


        //Initialize ViewPager2
        fragmentPagesCollectionAdapter = new FragmentPagesCollectionAdapter(this, Constant.fragmentPages);
        viewPager = view.findViewById(R.id.view_pager);
        viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewPager.setAdapter(fragmentPagesCollectionAdapter);
        viewPager.registerOnPageChangeCallback(onPageChangeCallback);
        view.getParent().requestDisallowInterceptTouchEvent(false); //allow scrolling for the listview inside the viewpager
        //More about the issue and workarounds
        // -https://bladecoder.medium.com/fixing-recyclerview-nested-scrolling-in-opposite-direction-f587be5c1a04
        // -https://gist.github.com/cbeyls/b75d730795a4b4c2fcdce554b0b0782a

        //Tabs
        TabLayout tabLayout = view.findViewById(R.id.tabs);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(this.getResources().getString(Constant.TAB_TITLES[position]))).attach();


    }

    /* ****************************************
        LISTENERS
     */
    final ViewPager2.OnPageChangeCallback onPageChangeCallback = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            //Adapt size to new child
            // nothing here for now...
        }

        @Override
        public void onPageSelected (int position){
            //Adapt size to new child
            //fragmentPagesCollectionAdapter.notifyDataSetChanged();
            // nothing here for now...

        }
    };

}
