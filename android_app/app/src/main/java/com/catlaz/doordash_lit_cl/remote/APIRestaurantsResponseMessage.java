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
    @SerializedName("num_results")
    @Expose
    private Double num_results; //num of stores

    @SerializedName("is_first_time_user")
    @Expose
    private Boolean is_first_time_user;

    @SerializedName("sort_order")
    @Expose
    private String sort_order;

    @SerializedName("next_offset")
    @Expose
    private Double next_offset;

    @SerializedName("show_list_as_pickup")
    @Expose
    private Boolean show_list_as_pickup;

    @SerializedName("stores")
    @Expose
    private List<Restaurant> stores;

    //Getter
    public List<Restaurant> getStores(){ return stores;}

}
