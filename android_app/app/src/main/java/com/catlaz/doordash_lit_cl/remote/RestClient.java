package com.catlaz.doordash_lit_cl.remote;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import com.catlaz.doordash_lit_cl.Constant;
import com.catlaz.doordash_lit_cl.data.Restaurant;
import com.catlaz.doordash_lit_cl.data.RestaurantDetail;
import com.catlaz.doordash_lit_cl.data.UpdatedValues;
import com.catlaz.doordash_lit_cl.utils.ApplicationConfigInformation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import static com.catlaz.doordash_lit_cl.Constant._OK;
import static com.catlaz.doordash_lit_cl.Constant._CONFLICT;


public class RestClient {
    private static final String _TAG = "REST_CLIENT";

    //API interface
    private final ApiInterface restAPI;

    //Manage disposables
    private final CompositeDisposable compositeDisposable;

    //DEBUG fields
    private static final int LOGCAT_MAX_LENGTH = 3950;

    //Interact with UI
    private final Context mContext;

    /**
     * Default constructor
     * Setup the httpsClientManager, the Retrofit and API interface
     */
    public RestClient(Context ctx){
        Log.d(_TAG, "Create rest client");
        mContext =ctx;
        //1.Initialize Retrofit
        //HTTP client
        HttpClient httpClient = new HttpClient();
        //Gson builder
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        //Retrofit fields
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant._DD_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //receive Observable, Flowable, Single, Completable or Maybe <Call>
                .client(httpClient.getOkHttpClient())
                .build();

        //API Interface
        restAPI = retrofit.create(ApiInterface.class);
        compositeDisposable = new CompositeDisposable();
    }

    /**
     * Broadcast updated status messages
     * @param key message key
     */
    private void broadcastUpdateUI(String key) {
        Log.i(_TAG,"Sending a Message to UI");
        Intent intent = new Intent(Constant._BROADCAST_API_UPDATE);
        intent.putExtra(key,true);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
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
     * Extract the list of restaurants for a GET stores request
     * Validate the HTTP code too
     * @param response received response [APIRestaurantsResponseMessage]
     * @return list of restaurants
     */
    private List<Restaurant> successfulListResponse(Response<APIRestaurantsResponseMessage> response){
        Log.d(_TAG, "Process GET restaurant response");

        List<Restaurant> rList = new ArrayList<>();
        if (response.body() != null) {
            //Log body DEBUG
            if (ApplicationConfigInformation.isDebugging())
                logBody(response.body().toString());
            //Process body's response
            switch (response.code()) {
                case _OK:
                    Log.i(_TAG, "OK ("+_OK+") response from DoorDash server: stores");
                    Log.d(_TAG, "Print stores numbers: "+response.body().getStores().size());
                    rList = response.body().getStores();
                    break;
                case _CONFLICT:
                    Log.i(_TAG, "CONFLICT ("+_CONFLICT+") response from DoorDash server: stores");
                    break;
                default:
                    Log.i(_TAG, "OTHER ("+response.code()+") response from DoorDash server: stores");
            }
        }
        return rList;
    }

    /**
     * Extract the Restaurant Detail for a GET stores request
     * Validate the HTTP code too
     *
     * @param response received response [RestaurantDetail]
     * @return list of restaurants
     */
    private RestaurantDetail successfulDetailResponse(Response<RestaurantDetail> response){
        Log.d(_TAG, "Process GET restaurant response");

        RestaurantDetail rDetail = null;
        if (response.body() != null) {
            //Log body DEBUG
            if (ApplicationConfigInformation.isDebugging())
                logBody(response.body().toString());
            //Process body's response
            switch (response.code()) {
                case _OK:
                    Log.i(_TAG, "OK ("+_OK+") response from DoorDash server: stores");
                    Log.d(_TAG, "Print stores numbers: "+response.body().getId());
                    rDetail = response.body();
                    break;
                case _CONFLICT:
                    Log.i(_TAG, "CONFLICT ("+_CONFLICT+") response from DoorDash server: stores");
                    break;
                default:
                    Log.i(_TAG, "OTHER ("+response.code()+") response from DoorDash server: stores");
            }
        }
        return rDetail;
    }

    /**
     * Method pass the restaurants info to the ui
     * Additionally, to reduce computational cost later, it computes the bitmap of the images thumbnails
     * Working with a data interface (UpdatedValues) and Broadcast Receiver
     *
     * @param rList restaurants_list
     */
    private void updateRestaurantsListToUI(List<Restaurant> rList){
        Log.d(_TAG, "Updating values for the UI to update");
        //1. Process received images - to BitMap
        //Compute Image - NOTE !!! Network Operation cannot be in Main/ UI Thread
        AsyncTask.execute (() -> {
            Map<Integer, Restaurant> rMap = new HashMap<>();
            for (Restaurant restaurant : rList)
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(new URL(restaurant.getCover_img_url()).openConnection().getInputStream());
                    restaurant.setBitmap_img(bitmap);
                    rMap.put(restaurant.getId(), restaurant);
                } catch (IOException e) {
                    Log.e(_TAG, "Error processing restaurant image: " + e);
                }
            //2. Update Data Interface
            UpdatedValues.Instance().updateRestaurants(rList, rMap);
            //3. Notify UI of changes
            broadcastUpdateUI(Constant._UPDATE_LIST_KEY);
        });
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
    public void getRestaurantsList(String home, double lat, double lng, int offset, int limit) {
        Log.d(_TAG, "Call request stores by "+home+": { lat: "+lat+", lng: "+lng+", offset: "+offset+
                ", limit"+limit+"}");

        //Call server API asynchronous
        //Example: "https://api.doordash.com/v1/store_feed/?lat=37.422740&lng=-122.139956&offset=0&limit=50";
        Call<APIRestaurantsResponseMessage> apiResponse = restAPI.getRestaurants(lat, lng, offset, limit);

        //Call server API - asynchronous
        apiResponse.enqueue(new Callback<APIRestaurantsResponseMessage>() {
            @Override
            public void onResponse(@NotNull Call<APIRestaurantsResponseMessage> call, @NotNull Response<APIRestaurantsResponseMessage> response) {
                Log.d(_TAG, "Successful response from server: "+response.code());
                //Process the response
                List<Restaurant> rList = successfulListResponse(response);
                //Update UI
                updateRestaurantsListToUI( rList);
            }
            @Override
            public void onFailure(@NotNull Call<APIRestaurantsResponseMessage> call, @NotNull Throwable t) {
                Log.d(_TAG, "Failed response from server: "+t.toString());
            }
        });

    }

    /**
     * To test "GET restaurants", call synchronous
     * API request to obtain a list of restaurants around a location
     *
     * @param home home name [String]
     * @param lat latitude [double]
     * @param lng longitude [double]
     * @param offset offset [int]
     * @param limit limit of results [int]
     * @return list of restaurants
     */
    public List<Restaurant> getRestaurantsListSync(String home, double lat, double lng, int offset, int limit) {
        Log.d(_TAG, "Call test request stores by "+home+": { lat: "+lat+", lng: "+lng+", offset: "+offset+
                ", limit"+limit+"}");
        List<Restaurant> rList = null;
        //Send request
        //Example: "https://api.doordash.com/v1/store_feed/?lat=37.422740&lng=-122.139956&offset=0&limit=50";
        Call<APIRestaurantsResponseMessage> apiResponse = restAPI.getRestaurants(lat, lng, offset, limit);
        //Call server API - synchronous
        try{
            Response<APIRestaurantsResponseMessage> response=apiResponse.execute();
            Log.d(_TAG, "Successful response from server: "+response.code());
            //Process the response
            rList = successfulListResponse(response);

        }catch(IOException ioe){ Log.e(_TAG, "Test error: "+ioe);}

        return rList;

    }

    /**
     * "GET restaurants by DoorDash"
     * API request to obtain a list of restaurants around DoorDash HG
     * @param offset offset
     * @param limit limit
     */
    public void getRestaurantsListByDoorDashHQ(int offset, int limit) {
        Log.d(_TAG, "Call DoorDash request stores: [offset: ,"+offset+", limit: "+limit+"]");
        getRestaurantsList("DoorDash", Constant._DD_HQ_LAT, Constant._DD_HQ_LONG, offset, limit);
    }

    /**
     * To test "GET restaurants by DoorDash", call synchronous
     * API request to obtain a list of restaurants around DoorDash HG
     *
     * @param offset offset
     * @param limit limit
     * @return list of restaurants
     */
    public List<Restaurant> testRestaurantsListByDoorDashHQ(int offset, int limit) {
        Log.d(_TAG, "Call test DoorDash request stores");
        return getRestaurantsListSync("DoorDash", Constant._DD_HQ_LAT, Constant._DD_HQ_LONG, offset, limit);
    }

    /**
     * GET restaurant detail, making a sync call
     * @param id restaurant id
     * @return RestaurantDetail
     */
    public void getRestaurantDetail(int id) {
        Log.d(_TAG, "Call test request restaurant: " + id);
        if (UpdatedValues.Instance().getRestaurantDetailMap().get(id) == null) {
            // Only if the details were not previously requested
            Call<RestaurantDetail> apiResponse = restAPI.getRestaurantDetail(id);
            //Call server API - asynchronous
            apiResponse.enqueue(new Callback<RestaurantDetail>() {
                @Override
                public void onResponse(@NotNull Call<RestaurantDetail> call, @NotNull Response<RestaurantDetail> response) {
                    Log.d(_TAG, "Successful response from server: " + response.code());
                    //1. Process the response
                    RestaurantDetail rDetail = successfulDetailResponse(response);
                    Log.i(_TAG, "Received restaurant = "+rDetail.getAddress().getAllString());
                    //Update UI
                    //2. Update Data Interface
                    UpdatedValues.Instance().addRestaurantDetail(rDetail);
                    //3. Notify UI of changes
                    broadcastUpdateUI(Constant._UPDATE_DETAIL_KEY);
                }

                @Override
                public void onFailure(@NotNull Call<RestaurantDetail> call, @NotNull Throwable t) {
                    Log.d(_TAG, "Failed response from server: " + t.toString());
                }
            });
        }
    }

    /**
     * Test function GET restaurant detail, making a sync call
     * @param id id
     * @return RestaurantDetail
     */
    public RestaurantDetail getRestaurantDetailSync(int id){
        Log.d(_TAG, "Call test request restaurant: "+id);
        Call<RestaurantDetail> apiResponse = restAPI.getRestaurantDetail(id);
        //Call server API - synchronous
        try{
            Response<RestaurantDetail> response=apiResponse.execute();
            Log.d(_TAG, "Successful response from server: "+response.code());
            //Process the response
            return response.body();

        }catch(IOException ioe){ Log.e(_TAG, "Test error: "+ioe);}

        //Nothing was received
        return null;
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
