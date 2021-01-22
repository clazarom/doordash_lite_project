package com.catlaz.doordash_lit_cl.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("ALL")
public class Address {
    @SerializedName("city")
    @Expose
    private String city;
    private String subpremise;
    private int id;
    private String printable_address;
    private String state;
    private String street;
    private String country;
    private String zip_code;
    private double lat;
    private double lng;
    private String shortname;

    @Override
    public String toString(){ return printable_address;}

    public String getAllString(){
        return shortname+"{city: "+city+", subpremise: "+subpremise+", id: "+id+
                ", street: "+street+", state: "+state+", zip_code: "+zip_code+
                ", country: "+country+", latitude:"+lat+", longitude: "+lng+"}";
    }
}
