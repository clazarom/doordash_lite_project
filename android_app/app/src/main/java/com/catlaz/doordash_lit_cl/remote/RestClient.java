package com.catlaz.doordash_lit_cl.remote;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {
    private static final String _TAG = "REST_CLIENT";

    //Doordash URL constants
    public static final String SERVER_HOST_NAME = "api.doordash.com";

    private static final String _DD_URL = "https://api.doordash.com" ;
    private static final String _REQ_RESTAURANTS_LOCATION = "v1/store_feed/?lat=37.422740&"+
            "lng=-122.139956&offset=0&limit=50";
    private static final String _DEBUG_EXAMPLE = "https://api.doordash.com/v1/store_feed/?lat="+
            "37.422740&lng=-122.139956&offset=0&limit=50";

    //API interface
    private final ApiInterface restAPI;

    //Manage disposables
    private final CompositeDisposable compositeDisposable;

    /**
     * Default constructor
     * Setup the httpsClientManager, the Retrofit and API interface
     */
    public RestClient(){
        //1.Initialize Retrofilt
        //HTTP client
        HttpClient httpClient = new HttpClient();
        //Gson
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();



        //Retrofit fields
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(_DD_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient.getOkHttpClient())
                .build();

        //API Interface
        restAPI = retrofit.create(ApiInterface.class);
        compositeDisposable = new CompositeDisposable();
    }

    public void mockReqCallToDoordash(){
        //Send request
        //Example: "https://api.doordash.com/v1/store_feed/?lat=37.422740&lng=-122.139956&offset=0&limit=50";
        Call<APIRestaurantsResponseMessage> apiResponse = restAPI.getRestaurants(37.422740, 122.139956, 0, 50);
        //Call server API - asynchronous
        // apiResponse.enqueue(this);

        try {
            Response<APIRestaurantsResponseMessage> response = apiResponse.execute();
            assert response.body() != null;
            Log.d(_TAG, "Response: " + response.body().toString());

        }catch (IOException e){
            Log.e(_TAG, "Test error: "+e);
        }
    }

    /**
     * Method to "clean" up disposables
     */
    public void destroyDisposables(){
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

}
