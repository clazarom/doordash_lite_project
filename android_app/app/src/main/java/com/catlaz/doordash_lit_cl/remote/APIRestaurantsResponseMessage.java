package com.catlaz.doordash_lit_cl.remote;

import com.catlaz.doordash_lit_cl.data.Restaurant;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Class to instantiate the  Response fields from a GET store_feed request
 *
 * @author Caterina Lazaro
 * @version 1.0 Jan 2021
 */
public class APIRestaurantsResponseMessage {
    @SerializedName("stores”")
    @Expose
    List<Restaurant> stores; //latitude

}
