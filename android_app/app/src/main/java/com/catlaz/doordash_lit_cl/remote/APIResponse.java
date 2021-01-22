package com.catlaz.doordash_lit_cl.remote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.LinkedTreeMap;

import retrofit2.Response;

@SuppressWarnings("unused")
public class APIResponse {
    @SuppressWarnings("unused")
    @SerializedName("body")
    @Expose
    private LinkedTreeMap<Object, Object> body;

    @SuppressWarnings("unused")
    @SerializedName("errorBody")
    @Expose
    private String errorBody;

    @SuppressWarnings("unused")
    @SerializedName("rawResponse")
    @Expose
    private Response<Object> rawResponse;


    //Body getter
    @SuppressWarnings("unused")
    public LinkedTreeMap<Object, Object> getBody(){ return body;}


}
