package com.catlaz.doordash_lit_cl.data;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Synchronous data interface to exchange values between the Internet Thread and UI Thread
 *
 * @author Caterina Lazaro
 * @version 1.0 Jan 2021
 */
public class UpdatedValues {
    private static final String _TAG = "UPDATED_VALUES";

    //Field to instantiate
    private static UpdatedValues updatedValues;

    //Last downloaded restaurant values
    private final List<Restaurant> newDownloadedRestaurantList;
    private final Map<Integer, Restaurant> restaurantMap;
    private final Map<Integer, RestaurantDetail> restaurantDetailsMap;


    /**
     * Private constructor
     */
    private UpdatedValues() {
        //Init restaurant list
        newDownloadedRestaurantList = new ArrayList<>();
        restaurantDetailsMap = new HashMap<>();
        restaurantMap = new HashMap<>();
    }

    /**
     * Access to the data interface
     */
    public static UpdatedValues Instance(){
        //Check if initialization is needed
        if (updatedValues == null)
            synchronized (UpdatedValues.class){
                if (updatedValues==null){
                    updatedValues = new UpdatedValues();
                }
            }

        //Return instance
        return updatedValues;
    }

    /**
     * Update restaurant's list and compute each restaurants image thumbnail
     * @param restaurantList list of restaurants
     */
    public void updateRestaurants(List<Restaurant> restaurantList, Map<Integer, Restaurant> restaurantMap){
        Log.d(_TAG, "Update restaurant list: "+restaurantList.size());
        this.newDownloadedRestaurantList.addAll(restaurantList);
        this.restaurantMap.putAll(restaurantMap);
    }

    /**
     * Update the map with restaurant's detail info
     * @param restaurantDetail restaurant detail
     */
    public void addRestaurantDetail(RestaurantDetail restaurantDetail){
        Log.d(_TAG, "Add restaurant detail: "+restaurantDetail.getId());
        restaurantDetailsMap.put(restaurantDetail.getId(), restaurantDetail);
    }

    //Getters
    public List<Restaurant> getNewDownloadedRestaurantList(){ return newDownloadedRestaurantList;}
    public Map<Integer,Restaurant> getRestaurantMap(){ return restaurantMap;}
    public Map<Integer,RestaurantDetail> getRestaurantDetailMap(){ return restaurantDetailsMap;}


    /**
     *     Consume items in restaurant list and its image map
     */
    public void cleanRestaurants(){
        newDownloadedRestaurantList.clear();
    }
}
