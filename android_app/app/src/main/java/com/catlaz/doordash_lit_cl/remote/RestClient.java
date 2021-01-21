package com.catlaz.doordash_lit_cl.remote;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.catlaz.doordash_lit_cl.BuildConfig;
import com.catlaz.doordash_lit_cl.data.Restaurant;
import com.catlaz.doordash_lit_cl.data.UpdatedValues;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;


public class RestClient {
    private static final String _TAG = "REST_CLIENT";
    public static final String _BROADCAST_API_UPDATE = "API_UPDATE";

    //Doordash URL constants
    public static final String SERVER_HOST_NAME = "api.doordash.com";
    private static final String _DD_URL = "https://api.doordash.com" ;

    //HTTP codes
    private static final int _OK = 200;
    private static final int _CONFLICT = 409;

    //API interface
    private final ApiInterface restAPI;

    //Manage disposables
    private final CompositeDisposable compositeDisposable;

    //DEBUG fields
    private static final int LOGCAT_MAX_LENGTH = 3950;

    //Interact with UI
    private Context mContext;
    /**
     * Default constructor
     * Setup the httpsClientManager, the Retrofit and API interface
     */
    public RestClient(Context ctx){
        Log.d(_TAG, "Create rest client");
        mContext =ctx;
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

    /**
     * "GET restaurants by Doordash"
     * API request to obtain a list of restaurants around DoorDash HG
     */
    public void getRestaurantsListByDoordashHQ() {
        Log.d(_TAG, "Call doordash request stores");
        getRestaurantsLis("DoorDash", 37.422740, -122.139956, 0, 50);

    }

    /**
     * "GET restaurants"
     * API request to obtain a list of restaurants around a location
     * @param home home name [String]
     * @param lat latitude [double]
     * @param lng longitude [double]
     * @param offset offset [int]
     * @param limit limit of results [int]
     */
    public void getRestaurantsLis(String home, double lat, double lng, int offset, int limit) {
        Log.d(_TAG, "Call request stores by "+home+": { lat: "+lat+", lng: "+lng+", offset: "+offset+
                ", limit"+limit+"}");

        //Send request
        //Example: "https://api.doordash.com/v1/store_feed/?lat=37.422740&lng=-122.139956&offset=0&limit=50";
        Call<APIRestaurantsResponseMessage> apiResponse = restAPI.getRestaurants(lat, lng, offset, limit);
        //Call server API - synchronous
        // try{ apiResponse.call(this); }catch(IOException ioe){ Log.e(_TAG, "Test error: "+e);}

        //Call server API - asynchronous
        //{body, errorBody, rawResponse, shadow$_klass, shadow$_monitor}
            apiResponse.enqueue(new Callback<APIRestaurantsResponseMessage>() {
                @Override
                public void onResponse(Call<APIRestaurantsResponseMessage> call, Response<APIRestaurantsResponseMessage> response) {
                    Log.d(_TAG, "Successful response from server: "+response.code());
                    //Process the response
                    List<Restaurant> rList = successfulResponse(response);

                    //UPDATE UI
                    //1. Update data interface
                    UpdatedValues.Instance().setRestaurantList(rList);
                    //2. Notify UI of changes
                    broadcastUpdateUI();
                }

                @Override
                public void onFailure(Call<APIRestaurantsResponseMessage> call, Throwable t) {

                }
            });

    }

    /**
     * Extract the list of restaurants for a GET stores request
     * Validate the HTTP code too
     * @param response received response [APIRestaurantsResponseMessage]
     * @return list of restaurants
     */
    private List<Restaurant> successfulResponse(Response<APIRestaurantsResponseMessage> response){
        Log.d(_TAG, "Process GET restaurant response");

        List<Restaurant> rList = new ArrayList<>();
        if (response.body() != null) {
            //Log body DEBUG
            if (BuildConfig.DEBUG_MODE)
                logBody(response.body().toString());
            //Process body's response
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
        }
        return rList;

    }

    /**
     * Print the body of an API response. Since the JSON string is usually too long for the console,
     * break it in smaller pieces
     * @param body body [String]
     */
    private void logBody(String body){
        Log.d(_TAG, "Print message body");
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

    /**
     * Method to "clean" up disposables
     */
    public void destroyDisposables(){
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    /**
     * Broadcast updated status messages
     */
    private void broadcastUpdateUI() {
        Log.i(_TAG,"Sending a Message to UI");
        Intent intent = new Intent(_BROADCAST_API_UPDATE);
        intent.putExtra("updated",true);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
    }

}
