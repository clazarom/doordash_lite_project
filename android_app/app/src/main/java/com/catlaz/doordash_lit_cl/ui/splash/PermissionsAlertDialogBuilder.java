package com.catlaz.doordash_lit_cl.ui.splash;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Process;
import android.provider.Settings;

import com.catlaz.doordash_lit_cl.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;

public class PermissionsAlertDialogBuilder extends MaterialAlertDialogBuilder {

    //Request permissions
    public static final int REQUEST_PERMISSION_ACCESS_COARSE_LOCATION = 1;

    /**
     * Constructor: set up the messages and title to be shown
     * @param context context
     * @param message message
     * @param title title
     */
    public PermissionsAlertDialogBuilder(Context context, String message, String title, boolean needRationale){
        super(context, R.style.RequestPermissionDialogTheme);
        //Update fields
        setTitle(title);
        setMessage(message);
        //Positive button = RETRY
        setPositiveButton("Retry", (dialog, which) -> {
            // Try again
            if (needRationale) {
                // the "never ask again" flash is not set, try again with permission request
                requestPermissions(context);
            } else {
                // the "never ask again" flag is set so the permission requests is disabled, try open app settings to enable the permission
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                intent.setData(uri);
                context.startActivity(intent);
            }
        });

        //Negative button: EXIT APP
        setNegativeButton("Exit application", (dialog, which) -> {
            // without permission exit is the only way
            ((Activity)context).finish();
            Process.killProcess(Process.myPid());
        });
    }

    /**
     * Request permission dynamically:
     *  - Maps (COARSE_LOCATION and FINE_LOCATION)
     * @param context context
     * @return permissionsGranted
     */
    public boolean requestPermissions(Context context){
        //Runtime request permissions -  for Android 6.0 or more
        //if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
        //Access coarse location and Notification policy
        if(ContextCompat.checkSelfPermission(context, ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity)context, new String[]{ACCESS_COARSE_LOCATION}, REQUEST_PERMISSION_ACCESS_COARSE_LOCATION);
            return false;
        }else
            return true;

    }


}
