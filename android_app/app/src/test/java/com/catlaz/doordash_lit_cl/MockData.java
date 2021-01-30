package com.catlaz.doordash_lit_cl;

import android.graphics.Bitmap;

import com.catlaz.doordash_lit_cl.data.Address;
import com.catlaz.doordash_lit_cl.data.Restaurant;
import com.catlaz.doordash_lit_cl.data.RestaurantDetail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MockData {
    public static final int _REST_NUM = 3;
    // Restaurants list and image map
    private static List<Restaurant> newRestaurantList;
    private static Map<Integer, Restaurant> restaurantMap;
    //RestaurantDetail list
    private static Map<Integer, RestaurantDetail> restaurantDetailMap;


    /**
     * Initialize MockData values
     */
    public static void initialize(Bitmap testLogoRestaurant) {
        //Restaurant List and Map
        newRestaurantList = new ArrayList<>();
        restaurantMap = new HashMap<>();

        addRestaurant(new Restaurant(1, "katsu burger", "burgers, korean", "url"), testLogoRestaurant);
        addRestaurant(new Restaurant(2, "howl at the moon", "italian, pasta", "url"), testLogoRestaurant);
        addRestaurant(new Restaurant(3, "pagliaci", "italian, pizza", "url"), testLogoRestaurant);


        //Restaurant Detail Map
        restaurantDetailMap = new HashMap<>();
        Address address = new Address("Chicago", "Cook", 0, "IL",
                "Belmont", "US", "60618",
                41.96846155307951, -87.69753608966684, "kumas");
        restaurantDetailMap.put(1, new RestaurantDetail("312 123 4645", address,
                70,1,  true, 1));
        restaurantDetailMap.put(2, new RestaurantDetail("314 444 9863", address,
                30,1,  true, 2));
        restaurantDetailMap.put(3, new RestaurantDetail("312 123 4645", address,
                0,1,  false, 3));


    }

    private static void addRestaurant (Restaurant restaurant, Bitmap image){
        newRestaurantList.add(restaurant);
        restaurant.setBitmap_img(image);
        restaurantMap.put(restaurant.getId(), restaurant);
    }

    /**
     * Getter for restaurantList
     * @return restaurantList
     */
    public static List<Restaurant> getNewRestaurantList(){ return newRestaurantList;}

    /**
     * Getter for restaurantLogoMap
     * @return restaurantList <Integer, Bitmap>
     */
    public static Map<Integer, Restaurant> getRestaurantMap(){ return restaurantMap;}

    /**
     * Getter for restaurantIntegerMap
     * @return restaurantList <Integer, RestaurantDetail>
     */
    public static Map<Integer, RestaurantDetail> getRestaurantDetailMap(){ return restaurantDetailMap;}


}
