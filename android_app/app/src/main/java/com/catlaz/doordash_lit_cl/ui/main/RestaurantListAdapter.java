package com.catlaz.doordash_lit_cl.ui.main;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.catlaz.doordash_lit_cl.R;
import com.catlaz.doordash_lit_cl.data.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class RestaurantListAdapter extends BaseAdapter {
    private static final String _TAG = "RESTAURANT_LIST_ADAPTER";

    private List<Restaurant> restaurantsList;
    private Context mContext;

    public RestaurantListAdapter(Context context){
        mContext = context;
        restaurantsList = new ArrayList<Restaurant>();
    }

    public RestaurantListAdapter(Context context, List<Restaurant> list){
        mContext = context;
        restaurantsList = list;
    }

    public void updateRestaurantList (List<Restaurant> list){
        Log.d(_TAG, "Update list: "+list.size());
        //Add each value individually to notify the adapter when changed
        for (int i=0; i<list.size(); i++) {
            restaurantsList.add(list.get(i));
            notifyDataSetInvalidated(); //update change
        }
    }

    public void addItem(Restaurant item){
        Log.d(_TAG, "Add new restaurant: "+item.getId());
        restaurantsList.add(item);
        notifyDataSetInvalidated(); //update change

    }

    @Override
    public int getCount() {
        Log.d(_TAG, "Get count: "+restaurantsList.size());

        return restaurantsList.size();
    }

    @Override
    public Object getItem(int i) {
        Log.d(_TAG, "Get item: "+restaurantsList.get(i).getId());

        return restaurantsList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup container) {
        if (convertView == null) {
            //No view to get: build an empty one
            LayoutInflater  inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.restaurant_list_item, container, false);
        }

        //Add the name of the restaurant
        TextView restaurantName =  convertView.findViewById(R.id.restaurant_name);
        restaurantName.setText(restaurantsList.get(position).getName());


        return convertView;
    }
}

