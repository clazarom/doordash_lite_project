package com.catlaz.doordash_lit_cl.ui.main;

import android.os.Bundle;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import static com.catlaz.doordash_lit_cl.ui.main.PlaceholderCollectionFragment.ARG_SECTION_NUMBER;

public class PageCollectionAdapter extends FragmentStateAdapter {
    List<Fragment> pagesList;

    public PageCollectionAdapter(@NonNull Fragment fragment, List<Fragment> pagesList) {
        super(fragment);
        this.pagesList = pagesList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = pagesList.get(position);
        //Send some arguments for the fragment
        Bundle bundle= new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, position);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public int getItemCount() {
        return pagesList.size();
    }
}
