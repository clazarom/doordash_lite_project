package com.catlaz.doordash_lit_cl.remote;


import android.util.Log;
import javax.net.ssl.HostnameVerifier;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import static com.catlaz.doordash_lit_cl.remote.RestClient.SERVER_HOST_NAME;

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
     * Initialize the HTTPS clinet by creating an OkHttpClient linked with the
     * - messages interceptor
     * - sslSocketFactory
     * - server's host name verifier
     *
     */
    private void initializeHttpClient(){
        //Interceptor for logging  <TODO> use on debug only
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClient = new OkHttpClient();
        okHttpClient = okHttpClient.newBuilder()
                .addInterceptor(interceptor)
                .hostnameVerifier(hostnameVerifier).build();

    }

    /**
     * Getter for the OkHttpClient
     * @return
     */
    protected OkHttpClient getOkHttpClient(){ return okHttpClient; }


    // HOSTNAME VERIFIER - perform a simple comparison of remote hostname and local value
    private final HostnameVerifier hostnameVerifier = (hostname, session) -> {
        Log.d(_TAG, "Host name verifier: " + hostname + " - " + session.getPeerHost());
        //TODO:Some logic to verify your host and set value
        return SERVER_HOST_NAME.equals(hostname)
                && SERVER_HOST_NAME.equals(session.getPeerHost());
    };


}


