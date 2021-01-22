package com.catlaz.doordash_lit_cl.data;

@SuppressWarnings("unused")
public class Restaurant {

    //Key fields
    private int id;
    private String name;
    private String description; // type of food
    private String cover_img_url; //restaurant thumbnail url

    //Other fields
    private String display_delivery_fee;
    private int yelp_review_count;
    private double average_rating;
    private double number_of_ratings;

    private boolean offers_pickup;
    private double delivery_fee;

    private boolean is_consumer_subscription_eligible;
    private double promotion_delivery_fee;
    private int num_ratings;
    private Address address;
    private int price_range;


    /**
     * Constructor
     * @param id rest id
     * @param name name
     * @param description description
     * @param cover_img_url image
     */
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
