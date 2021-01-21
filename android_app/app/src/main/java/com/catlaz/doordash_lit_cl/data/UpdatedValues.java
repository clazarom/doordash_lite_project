package com.catlaz.doordash_lit_cl.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Syncrhonous data interface to exchange values between the Internet Thread and UI Thread
 *
 * @author Caterina Lazaro
 * @version 1.0 Jan 2021
 */
public class UpdatedValues {
    private static final String _TAG = "UPDATED_VALUES";

    //Field to instantiate
    private static UpdatedValues updatedValues;

    //Last downloaded restaurant values
    private List<Restaurant> restaurantList;


    /**
     * Private constructor
     */
    private UpdatedValues() {
        //Init restaurant list
        restaurantList = new ArrayList<>();
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

    //Getters and Setters
    public void setRestaurantList (List<Restaurant> restaurantList){this.restaurantList.addAll(restaurantList);}
    public List<Restaurant> getRestaurantList(){ return restaurantList;}
    //Consume items in retaurant list
    public void cleanRestaurantList(){ restaurantList.clear();}
}
