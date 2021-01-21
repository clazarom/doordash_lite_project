package com.catlaz.doordash_lit_cl.remote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * Class to instantiate the Request fields sent for a GET store_feed request
 *
 * @author Caterina Lazaro
 * @version 1.0 Jan 2021
 */
public class APIRestaurantsRequestMessage {
    @SerializedName("lat")
    @Expose
    double lat; //latitude
    @SerializedName("lng")
    @Expose
    double lng; //longitude
    @SerializedName("offset")
    @Expose
    int offset;
    @SerializedName("limit")
    @Expose
    int limit;

    @Override
    public String toString(){
        return "latitute: "+lat+",longitude: "+lng +", offset:"+offset +
                ", limit: "+limit+", limit"+limit;
    }
}
