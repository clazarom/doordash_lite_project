package com.catlaz.doordash_lit_cl.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
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
    private final List<Restaurant> restaurantList;
    private final Map<Integer, Bitmap> restaurantImagesMap;
    private final Map<Integer, RestaurantDetail> restaurantDetailsMap;


    /**
     * Private constructor
     */
    private UpdatedValues() {
        //Init restaurant list
        restaurantList = new ArrayList<>();
        restaurantImagesMap = new HashMap<>();
        restaurantDetailsMap = new HashMap<>();
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
    public void updateRestaurants(List<Restaurant> restaurantList, Map<Integer, Bitmap> restaurantImagesMap){
        this.restaurantList.addAll(restaurantList);
        this.restaurantImagesMap.putAll(restaurantImagesMap);
    }

    /**
     * Update the map with restaurant's detail info
     * @param restaurantDetail restaurant detail
     */
    public void addRestaurantDetail(RestaurantDetail restaurantDetail){
        restaurantDetailsMap.put(restaurantDetail.getId(), restaurantDetail);
    }

    //Getters
    public List<Restaurant> getRestaurantList(){ return restaurantList;}
    public Map<Integer,Bitmap> getRestaurantImageMap(){ return restaurantImagesMap;}
    public Map<Integer,Bitmap> getRestaurantDetailMap(){ return restaurantImagesMap;}


    /**
     *     Consume items in restaurant list and its image map
     */
    public void cleanRestaurants(){
        restaurantList.clear();
        restaurantImagesMap.clear();
    }
}
