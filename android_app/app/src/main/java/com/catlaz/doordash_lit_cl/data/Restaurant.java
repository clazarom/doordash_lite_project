package com.catlaz.doordash_lit_cl.data;

public class Restaurant {

    //Key fields
    private int id;
    private String name;
    private String description; // type of food
    private String cover_img_url; //restaurant thumbnail url
    public static final String[] keyFields = {"id", "name", "description", "cover_img_url"};

    //Other key fields
    private boolean is_consumer_subscription_elegible;
    private double promotion_delivery_fee;
    private double delivery_fee;
    private DeliveryFeeMonetaryFields delivery_fee_monetary_field;
    private int num_ratings;


    public Restaurant (int id, String name, String description, String cover_img_url){
        this.id = id;
        this.name = name;
        this.description = description;
        this.cover_img_url = cover_img_url;
    }

    //ID getter and setter
    public int getId() { return id; }
    public void setId(int id) {   this.id = id;     }

    //Name getter and setter
    public String getName() {  return name;  }
    public void setName(String name) {  this.name = name;  }

    //Description getter and setter
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    //Cover_img_url getter and setter
    public String getCover_img_url() { return cover_img_url; }
    public void setCover_img_url(String cover_img_url) { this.cover_img_url = cover_img_url;  }
}
