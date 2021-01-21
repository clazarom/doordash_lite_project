package com.catlaz.doordash_lit_cl.remote;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Retrofit API Interface to interact with the server REST API's
 * Tutorial on Retrofit and type of calls:
 * https://futurestud.io/tutorials/retrofit-synchronous-and-asynchronous-requests
 *
 * API available url are served in https://host46.ph.iit.edu/api-docs/
 * @author Caterina Lazaro
 * @version 1.0 Jan 2020
 */
public interface ApiInterface {

    //Get list of restaurants close to the location
    //Ex: "https://api.doordash.com/v1/store_feed/?lat=37.422740&lng=-122.139956&offset=0&limit=50"
    //@Headers(RestClient.TOKEN_HEADER+": {token}")
    @GET("v1/store_feed/")
    Call<APIRestaurantsResponseMessage> getRestaurants(
            @Query("lat") double lat,
            @Query("lng") double lng,
            @Query("offset") int offset,
            @Query("limit") int limit   );
}
