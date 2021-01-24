package com.catlaz.doordash_lit_cl.ui.main;


import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;


/**
 * Fragment state adapter to manage the fragment pages (on ViewPager2)
 *
 * @author Caterina Lazaro
 * @version Jan 2021
 */
public class FragmentPagesCollectionAdapter extends FragmentStateAdapter {
    final List<Fragment> pagesList;

    /**
     * Constructor
     * @param fragment fragment
     * @param pagesList list of fragment pages
     */
    public FragmentPagesCollectionAdapter(@NonNull Fragment fragment, List<Fragment> pagesList) {
        super(fragment);
        this.pagesList = pagesList;
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        //OPTION: Send some arguments to the fragment
//        Bundle bundle= new Bundle();
//        bundle.putInt(ARG_SECTION_NUMBER, position);
//        fragment.setArguments(bundle);

        return pagesList.get(position);
    }

    @Override
    public int getItemCount() {
        return pagesList.size();
    }
}
