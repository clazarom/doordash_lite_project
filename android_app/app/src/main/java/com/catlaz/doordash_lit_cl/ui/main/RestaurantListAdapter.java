package com.catlaz.doordash_lit_cl.ui.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.catlaz.doordash_lit_cl.R;
import com.catlaz.doordash_lit_cl.data.Restaurant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestaurantListAdapter extends BaseAdapter {
    private static final String _TAG = "RESTAURANT_LIST_ADAPTER";

    private final List<Restaurant> restaurantsList;
    private final Map<Integer, Bitmap> restaurantImagesMap;

    private final Context mContext;

    /**
     * Constructor
     * @param context context
     */
    public RestaurantListAdapter(Context context){
        Log.d(_TAG, "Create");
        mContext = context;
        restaurantsList = new ArrayList<>();
        restaurantImagesMap = new HashMap<>();
    }


    /**
     * Update values in the restaurant list and its images map
     * @param list restaurants list
     * @param map images hashmap
     */
    public void updateRestaurantList (List<Restaurant> list, Map<Integer, Bitmap> map){
        Log.d(_TAG, "Update list: "+list.size());
        if (list.size() > 0) {
            restaurantsList.addAll(list);
            restaurantImagesMap.putAll(map);
        }
        notifyDataSetChanged(); //update change


    }

    /**
     * Getter for restaurant list
     * @return restaurant list
     */
    public List<Restaurant> getRestaurantsList(){return restaurantsList;}

    /**
     * Reset the contents of the list adapter
     */
    public void clearRestaurantList(){
        restaurantsList.clear();
        restaurantImagesMap.clear();
        notifyDataSetChanged(); //update change

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
        Log.d(_TAG, "Get View");

        if (convertView == null) {
            //No view to get: build an empty one
            LayoutInflater  inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.restaurant_list_item, container, false);
        }

        //UPDATE DATA WITH RESTAURANT INFO
        Restaurant restaurant = restaurantsList.get(position);
        //Add the thumbnail image -- user Bitmap
        ImageView image = convertView.findViewById(R.id.restaurant_image_thumb);
        image.setImageBitmap(restaurantImagesMap.get(restaurant.getId()));
        //Add the name of the restaurant
        TextView restaurantName =  convertView.findViewById(R.id.restaurant_name);
        restaurantName.setText(restaurant.getName());
        //Add description of the restaurant
        TextView restauranDescription =  convertView.findViewById(R.id.restaurant_description);
        restauranDescription.setText(restaurant.getDescription());


        return convertView;
    }
}

