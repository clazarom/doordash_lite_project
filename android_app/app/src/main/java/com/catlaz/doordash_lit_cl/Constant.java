package com.catlaz.doordash_lit_cl;

import com.catlaz.doordash_lit_cl.ui.main.MapFragment;
import com.catlaz.doordash_lit_cl.ui.main.RestaurantsFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;

public class Constant {

    //************ DOORDASH  API SPECIFIC VALUES *************************//
    //DoorDash URL constants
    public static final String SERVER_HOST_NAME = "api.doordash.com";
    public static final String _DD_URL = "https://api.doordash.com" ;
    //Headquarters location
    public static final double _DD_HQ_LAT = 37.422740;
    public static final double _DD_HQ_LONG = -122.139956;

    //************ REMOTE VALUES *************************//
    //API requests
    public static final int _REQ_NUM = 10;
    public static final int _INIT_REQ_NUM = 50;
    //HTTP codes
    public static final int _OK = 200;
    public static final int _CONFLICT = 409;


    //************ BROADCAST MESSAGES *************************//
    //Broadcast messages
    public static final String _BROADCAST_API_UPDATE = "API_UPDATE";
    public static final String _UPDATE_LIST_KEY = "update_list";
    public static final String _UPDATE_DETAIL_KEY = "update_detail";

    //************ SPLASH ACTIVITY *************************//
    public static final int _SPLASH_DISPLAY_LENGTH = 5; //seconds

    //************ MAIN ACTIVITY *************************//
    //MainFragment: View Pager fragments
    //public static final int NUM_ITEMS = 2;
    public static final List<Fragment> fragmentPages = Collections.unmodifiableList(
            new ArrayList<Fragment>(){{add(new RestaurantsFragment()); add(new MapFragment()); }});
    @StringRes
    public static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2}; // Tab names

}
