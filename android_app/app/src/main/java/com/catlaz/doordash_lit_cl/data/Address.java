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

    /**
     * Constructor
     * @param city city
     * @param subpremise subpremise
     * @param id id
     * @param state state
     * @param street street
     * @param country country
     * @param zip_code zip_code
     * @param lat latitude
     * @param lng longitude
     * @param shortname short_name
     */
    public Address(String city, String subpremise, int id, String state, String street,
                   String country, String zip_code, double lat, double lng, String shortname) {
        this.city = city;
        this.subpremise = subpremise;
        this.id = id;
        this.state = state;
        this.street = street;
        this.country = country;
        this.zip_code = zip_code;
        this.lat = lat;
        this.lng = lng;
        this.shortname = shortname;
        //Build printable adderss
        StringBuilder sb = new StringBuilder();
        sb.append (street+" \n");
        sb.append (city+", ");
        sb.append (state+", ");
        sb.append (zip_code+" \n");
        sb.append(country);
        printable_address = sb.toString();

    }

    @Override
    public String toString(){ return printable_address;}

    public String getAllString(){
        return shortname+"{city: "+city+", subpremise: "+subpremise+", id: "+id+
                ", street: "+street+", state: "+state+", zip_code: "+zip_code+
                ", country: "+country+", latitude:"+lat+", longitude: "+lng+"}";
    }
}
