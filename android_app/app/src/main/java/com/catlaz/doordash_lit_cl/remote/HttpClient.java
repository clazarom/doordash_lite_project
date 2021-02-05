package com.catlaz.doordash_lit_cl.remote;


import android.util.Log;

import com.catlaz.doordash_lit_cl.utils.ApplicationConfigInformation;

import javax.net.ssl.HostnameVerifier;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import static com.catlaz.doordash_lit_cl.Constant.SERVER_HOST_NAME;

/**
 * Class to manage http connections
 * Additionally, https ssl connections can be implemented to deal with servers certificates
 * (Retrofit tutorial to add ssl certificates:
 * https://proandroiddev.com/configuring-retrofit-2-client-in-android-130455eaccbd)
 *
 * @author Caterina Lazaro
 * @version 1.0 - Jan 2021
 */
public class HttpClient{
    private static final String _TAG = "HTTP_CLIENT";

    //Field: the OkHttpClient
    private OkHttpClient okHttpClient;

    /**
     * Constructor: initialize the okHttpClient
     */
    HttpClient(){
        initializeHttpClient();
    }


    /**
     * Initialize the HTTPS client by creating an OkHttpClient linked with the
     * - messages interceptor
     * - sslSocketFactory
     * - server's host name verifier
     *
     */
    private void initializeHttpClient(){
        Log.d(_TAG, "Create http client");
        okHttpClient = new OkHttpClient();

        //1. Set up the client's parameters
        OkHttpClient.Builder httpBuilder = okHttpClient.newBuilder()
                .hostnameVerifier(hostnameVerifier);
        //Interceptor for logging  <TODO> use on debug only
        if (ApplicationConfigInformation.isDebugging()) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.level(HttpLoggingInterceptor.Level.BODY);
            httpBuilder.addInterceptor(interceptor);
        }
        //2. Build http client
        okHttpClient =  httpBuilder.build();

    }

    /**
     * Getter for the OkHttpClient
     * @return httpClient
     */
    protected OkHttpClient getOkHttpClient(){ return okHttpClient; }


    // HOSTNAME VERIFIER - perform a simple comparison of remote hostname and local value
    private final HostnameVerifier hostnameVerifier = (hostname, session) -> {
        Log.d(_TAG, "Host name verifier: " + hostname + " - " + session.getPeerHost());
        //<TODO> Some logic to verify your host and set value
        return SERVER_HOST_NAME.equals(hostname)
                && SERVER_HOST_NAME.equals(session.getPeerHost());
    };


}


