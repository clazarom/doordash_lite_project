package com.catlaz.doordash_lit_cl.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.catlaz.doordash_lit_cl.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * A placeholder fragment containing a simple view.
 *
 * @author Caterina lazaro
 * @version 1.0 Jan 2020
 */
public class PlaceholderFragment extends Fragment {
    private static final String _TAG = "PH_COLLECTION_FRAGMENT";

    protected static final String ARG_SECTION_NUMBER = "section_number";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
       return inflater.inflate(R.layout.fragment_page_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(_TAG, "View Created");

        Bundle args = getArguments();
        int section = -1;
        if (args != null && args.size()>0)
            section = args.getInt(ARG_SECTION_NUMBER, 0);

        //Update the view label
        String sb = "" + (section+1);
        TextView sectionLabel = view.findViewById(R.id.section_label);
        sectionLabel.setText(sb);
    }

}