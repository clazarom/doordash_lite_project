package com.catlaz.doordash_lit_cl.ui.main;

import android.content.Context;
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

/**
 * ListView Adapter for the Restaurants List
 *
 * Stores items in a restaurantList - List<Restaurant>, which can be updated, retrieved and clean
 * It keeps the restaurants images (as bitmaps) in a separate Map, that can be access with the
 * restaurant's id as key
 *
 * @author Caterina Lazaro
 * @version 1.0 Jan 2021
 */
public class RestaurantListAdapter extends BaseAdapter {
    private static final String _TAG = "RESTAURANT_LIST_ADAPTER";

    private final List<Integer> restaurantsList;
    private final Map<Integer, Restaurant> restaurantMap;

    private final Context mContext;

    /**
     * Constructor
     * @param context context
     */
    public RestaurantListAdapter(Context context){
        Log.d(_TAG, "Create");
        mContext = context;
        restaurantsList = new ArrayList<>();
        restaurantMap = new HashMap<>();
    }


    /**
     * Update values in the restaurant list and its images map
     * @param list restaurants list
     * @param map images hashMap
     */
    public void updateRestaurantList (List<Restaurant> list, Map<Integer, Restaurant> map){
        Log.d(_TAG, "Update list: "+list.size());
        if (list.size() > 0) {
            for (Restaurant restaurant: list)
                restaurantsList.add(restaurant.getId());
        }
        if(map!=null && map.size() > 0)
            restaurantMap.putAll(map);
        notifyDataSetChanged(); //update change
    }

    /**
     * Getter for restaurant list
     * @return restaurant list
     */
    public Map<Integer, Restaurant> getRestaurantsMap(){return restaurantMap;}

    /**
     * Reset the contents of the list adapter
     */
    public void clearRestaurantList(){
        restaurantsList.clear();
        restaurantMap.clear();
        notifyDataSetChanged(); //update change

    }

    /**
     * Method to update a restaurant's item view with the right information
     * @param itemView view
     * @param i position
     */
    private void updateItemView(View itemView, int i){
        Restaurant restaurant = restaurantMap.get(restaurantsList.get(i));
        //Add the thumbnail image -- user Bitmap
        ImageView image = itemView.findViewById(R.id.restaurant_image_thumb);
        image.setImageBitmap(restaurant.getBitmap_img());
        //Add the name of the restaurant
        TextView restaurantName =  itemView.findViewById(R.id.restaurant_name);
        restaurantName.setText(restaurant.getName());
        //Add description of the restaurant
        TextView restaurantDescription =  itemView.findViewById(R.id.restaurant_description);
        restaurantDescription.setText(restaurant.getDescription());
    }

    @Override
    public int getCount() {
        Log.d(_TAG, "Get count: "+restaurantsList.size());

        return restaurantsList.size();
    }

    @Override
    public Object getItem(int i) {
//        Log.d(_TAG, "Get item: "+restaurantsList.get(i).getId());

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

        //Update data with restaurant info
        updateItemView(convertView, position);


        return convertView;
    }
}

