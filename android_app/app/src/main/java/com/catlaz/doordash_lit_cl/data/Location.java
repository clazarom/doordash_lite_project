package com.catlaz.doordash_lit_cl.data;

/**
 * Location data class for the Restaurants. Contains latitude and longitude information
 *
 * @author Caterina Lazaro
 * @version 1.0 Jan 2021
 */
public class Location {
    private double lat;
    private double lng;

    //Lat getter and setter
    public double getLat() { return lat; }
    public void setLat(double lat) { this.lat = lat; }

    //Lng getter and setter
    public double getLng() { return lng; }
    public void setLng(double lng) { this.lng = lng; }
}
