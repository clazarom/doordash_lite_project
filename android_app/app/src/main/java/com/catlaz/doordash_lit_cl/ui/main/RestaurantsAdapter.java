package com.catlaz.doordash_lit_cl.ui.main;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

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
public class RestaurantsAdapter extends RecyclerView.Adapter<RestaurantsAdapter.ViewHolder> {
    private static final String _TAG = "RESTAURANT_LIST_ADAPTER";

    private final List<Integer> restaurantIdsList;
    private final Map<Integer, Restaurant> restaurantMap;

    //Open a new fragment when clicked
    private FragmentManager fragmentManager;


    /**
     * Constructor
     */
    public RestaurantsAdapter(){
        Log.d(_TAG, "Create");
        //Initialize data lists
        restaurantIdsList = new ArrayList<>();
        restaurantMap = new HashMap<>();
        //Set the fragment manager
    }
    /** FragmentManager Setter **/
    public void setFragmentManager(FragmentManager fragmentManager){this.fragmentManager = fragmentManager;  }


    /**
     * Update values in the restaurant list and its images map
     * @param list restaurants list
     * @param map images hashMap
     */
    public void updateRestaurantList (List<Restaurant> list, Map<Integer, Restaurant> map){
        Log.d(_TAG, "Update list: "+list.size());
        if (list.size() > 0) {
            for (Restaurant restaurant: list)
                restaurantIdsList.add(restaurant.getId());
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
        restaurantIdsList.clear();
        restaurantMap.clear();
        notifyDataSetChanged(); //update change

    }

    /**
     * Method to update a restaurant's item view with the right information
     * @param holderView holderView
     * @param i position
     */
    private void updateItemView(ViewHolder holderView, int i){
        Restaurant restaurant = restaurantMap.get(restaurantIdsList.get(i));

        if (restaurant != null) {
            //Add the thumbnail image -- user Bitmap
            holderView.imageView.setImageBitmap(restaurant.getBitmap_img());
            //Add the name of the restaurant
            holderView.restaurantName.setText(restaurant.getName());
            //Add description of the restaurant
            holderView.restaurantDescription.setText(restaurant.getDescription());
            //Update id too
            holderView.id = restaurantIdsList.get(i);
        }
    }

    @Override
    public int getItemCount() {
        Log.d(_TAG, "Get count: "+ restaurantIdsList.size());

        return restaurantIdsList.size();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_list_item,
                parent,
                false),
                fragmentManager);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Update view holder with restaurant info
        updateItemView(holder, position);

    }


    @Override
    public long getItemId(int i) {
        return restaurantIdsList.get(i);
    }

    /**
     * Get a restaurant item given the position
     * @param i position
     * @return retaurant
     */
    public Restaurant getItem(int i){return restaurantMap.get(restaurantIdsList.get(i));}


    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        protected ImageView imageView;
        protected TextView restaurantName;
        protected TextView restaurantDescription;

        protected int id;

        //Open a new fragment
        private static FragmentManager fragmentManager;

        /**
         * Constructor
         * @param itemView itemView
         */
        ViewHolder(final View itemView, FragmentManager fm)  {
            super(itemView);

            //Update HolderView's internal elements
            imageView = itemView.findViewById(R.id.restaurant_image_thumb);
            restaurantName = itemView.findViewById(R.id.restaurant_name);
            restaurantDescription = itemView.findViewById(R.id.restaurant_description);

            //Set OnClickListener
            itemView.setOnClickListener(this);

            //Fragment Manager
            fragmentManager = fm;


        }

        @Override
        public void onClick(View view) {
            //Build a fragment transaction for the Restaurant Detail Fragment
            FragmentTransaction ft =  fragmentManager.beginTransaction();

            RestaurantDetailFragment.makeFragmentTransaction(ft, id,
                    restaurantName.getText().toString(),
                    restaurantDescription.getText().toString())
                    .commit();  //Commit fragment transaction
        }


    }
}

