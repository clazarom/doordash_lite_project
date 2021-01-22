package com.catlaz.doordash_lit_cl.remote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * POJO (Plain Old Java Object) Class to instantiate the Request fields sent for a GET store_feed request
 *
 * @author Caterina Lazaro
 * @version 1.0 Jan 2021
 */
@SuppressWarnings("ALL")
public class APIRestaurantsRequestMessage {
    @SerializedName("lat")
    @Expose
    private double lat; //latitude
    @SerializedName("lng")
    @Expose
    private double lng; //longitude
    @SerializedName("offset")
    @Expose
    private int offset;
    @SerializedName("limit")
    @Expose
    private int limit;

    @Override
    public String toString(){
        return "latitude: "+lat+",longitude: "+lng +", offset:"+offset +
                ", limit: "+limit+", limit"+limit;
    }
}
