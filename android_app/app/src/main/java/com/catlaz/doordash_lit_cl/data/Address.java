package com.catlaz.doordash_lit_cl.data;

public class Address {
    private String city;
    private String subpremise;
    private int id;
    private String printable_address;
    private String state;
    private String street;
    private String country;
    private String zip_code;
    private int lat;
    private int lng;
    private String shortname;

    @Override
    public String toString(){ return printable_address;}
}
