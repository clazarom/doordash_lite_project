package com.catlaz.doordash_lit_cl.utils;

import android.os.Build;

import com.catlaz.doordash_lit_cl.BuildConfig;

/**
 * Class to access some of the application build configuration information and flags
 *
 * @author Caterina Lazaro
 * @version 1.0 Feb 2021
 */
public class ApplicationConfigInformation {

    /**
     * Find application version
     * @return versionName
     */
    public static String getApplicationVersionName(){
        return BuildConfig.VERSION_NAME;
    }

    /**
     * Check if build configuration is in DEBUG mode
     * @return debugging (boolean)
     */
    public static boolean isDebugging(){
        return BuildConfig.DEBUG;
        //return BuildConfig.DEBUG_MODE;
    }

    public static int getBuildVersion(){ return Build.VERSION.SDK_INT;}

}
