package com.catlaz.doordash_lit_cl.data;

public class RestaurantDetail {

    //Restaurant key details
    private String phone_number;
    private Address address;
    private int delivery_radius;
    private int price_range;
    private boolean offers_pickup;
    private int id;

    /**
     * Constructor
     * @param phone_number phone_number
     * @param address address
     * @param delivery_radius delivery_radius
     * @param price_range price_range
     * @param offers_pickup offers_pickup
     * @param id id
     */
    public RestaurantDetail(String phone_number, Address address, int delivery_radius, int price_range, boolean offers_pickup, int id) {
        this.phone_number = phone_number;
        this.address = address;
        this.delivery_radius = delivery_radius;
        this.price_range = price_range;
        this.offers_pickup = offers_pickup;
        this.id = id;
    }

    //Getter and setter for offers_pickup
    public boolean isOffers_pickup() { return offers_pickup; }
    public void setOffers_pickup(boolean offers_pickup) { this.offers_pickup = offers_pickup; }

    //Getter and setter for price_range
    public int getPrice_range() {  return price_range; }
    public void setPrice_range(int price_range) { this.price_range = price_range; }

    //Getter and setter for delivery_radius
    public int getDelivery_radius() { return delivery_radius; }
    public void setDelivery_radius(int delivery_radius) { this.delivery_radius = delivery_radius; }

    //Getter and setter for address
    public Address getAddress() { return address; }
    public void setAddress(Address address) { this.address = address; }

    //Getter and setter for phone number
    public String getPhone_number() { return phone_number; }
    public void setPhone_number(String phone_number) {  this.phone_number = phone_number; }

    //Getter and setter for id
    public int getId() {  return id; }
    public void setId(int id) {  this.id = id; }
}
