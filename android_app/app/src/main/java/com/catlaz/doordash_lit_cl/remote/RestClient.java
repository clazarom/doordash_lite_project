package com.catlaz.doordash_lit_cl.remote;

import android.util.Log;

import com.catlaz.doordash_lit_cl.BuildConfig;
import com.catlaz.doordash_lit_cl.data.Restaurant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    //HTTP codes
    private static final int _OK = 200;
    private static final int _CONFLICT = 409;

    //API interface
    private final ApiInterface restAPI;

    //Manage disposables
    private final CompositeDisposable compositeDisposable;

    //DEBUG fields
    private static final int LOGCAT_MAX_LENGTH = 3950;
    /**
     * Default constructor
     * Setup the httpsClientManager, the Retrofit and API interface
     */
    public RestClient(){
        Log.d(_TAG, "Create rest client");
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

    public List<Restaurant> mockReqCallToDoordash(){
        Log.d(_TAG, "Call doordash request stores");

        //Send request
        //Example: "https://api.doordash.com/v1/store_feed/?lat=37.422740&lng=-122.139956&offset=0&limit=50";
        Call<APIRestaurantsResponseMessage> apiResponse = restAPI.getRestaurants(37.422740, -122.139956, 0, 50);
        //Call server API - asynchronous
        // apiResponse.enqueue(this);

        try {
            //{body, errorBody, rawResponse, shadow$_klass, shadow$_monitor}
            Response<APIRestaurantsResponseMessage> response = apiResponse.execute();
            if (response.body() != null) {
                //Log body DEBUG
                if (BuildConfig.DEBUG) {
                    Log.d(_TAG, "Print message body");
                    String body = response.body().toString();
                    Log.d(_TAG, "(body_beginning) "+ body);
                    while (body.length() > LOGCAT_MAX_LENGTH) {
                        int substringIndex = body.lastIndexOf(",", LOGCAT_MAX_LENGTH);
                        if (substringIndex == -1)
                            substringIndex = LOGCAT_MAX_LENGTH;
                        Log.d(_TAG, body.substring(0, substringIndex));
                        body = body.substring(substringIndex).trim();
                    }
                    Log.d(_TAG, "(body_end)"+ body);
                }
                //Process body's response
                return processRestaurantsRequest(response);
            }


        }catch (IOException e){
            Log.e(_TAG, "Test error: "+e);
        }
        //If this line is reached, no answer was process
        return null;
    }

    /**
     * Extract the list of restaurants for a GET stores request
     * Validate the HTTP code too
     * @param response received response [APIRestaurantsResponseMessage]
     * @return list of restaurants
     */
    private List<Restaurant> processRestaurantsRequest (Response<APIRestaurantsResponseMessage> response){
        Log.d(_TAG, "Process GET restaurant response");

        List<Restaurant> rList = new ArrayList<>();
        switch (response.code()) {
            case _OK:
                Log.i(_TAG, "OK ("+_OK+") response from Doordash server: stores");
                Log.d(_TAG, "Print stores numbers: "+response.body().getStores().size());
                rList = response.body().getStores();
                break;
            case _CONFLICT:
                Log.i(_TAG, "CONFLICT ("+_CONFLICT+") response from Doordash server: stores");
                break;
            default:
                Log.i(_TAG, "OTHER ("+response.code()+") response from Doordash server: stores");

        }

        return rList;
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
