package com.catlaz.doordash_lit_cl.remote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.LinkedTreeMap;

import org.json.JSONArray;

import retrofit2.Response;

public class APIResponse {
    @SerializedName("body")
    @Expose
    private LinkedTreeMap body;

    @SerializedName("errorBody")
    @Expose
    private String errorBody;

    @SerializedName("rawResponse")
    @Expose
    private Response rawResponse;

    //Body getter
    public LinkedTreeMap getBody(){ return body;}


}
