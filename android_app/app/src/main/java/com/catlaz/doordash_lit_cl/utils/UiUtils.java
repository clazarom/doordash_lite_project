package com.catlaz.doordash_lit_cl.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import androidx.annotation.DrawableRes;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.catlaz.doordash_lit_cl.Constant;
import com.catlaz.doordash_lit_cl.MainActivity;

/**
 * Utils class to manage updates on the System UI:
 * - Status bar notifications: https://developer.android.com/training/notify-user/channels
 *
 * @author Caterina Lazaro
 * @version 1.0 Feb 2021
 */
public class UiUtils {

    private static final String _TAG = "UI_UTILS";


    /* ****************************
     * *****  NOTIFICATIONS  ******
     * ****************************/

    /**
     * Update status bar with notifications from application
     * @param ctx context
     */
    public static void updateStatusBarNotifications(Context ctx, String title,
                                                    @DrawableRes int iconId, int notificationId){
        //Check if a notification channel needs to be created (Oreo or higher)
        if (ApplicationConfigInformation.getBuildVersion() >= Build.VERSION_CODES.O){
            createNotificationChannel(ctx);
        }

        //Build the notification to display: text & icon
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(ctx, Constant.CHANNEL_ID)
                .setSmallIcon(iconId)
                .setContentTitle(title);

        //Create an intent to be called when clicked on the notification and wrapping as pending
        Intent intent = new Intent(ctx, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(ctx, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT); //modify current pending intent associated with 'intent'
        notificationBuilder.setContentIntent(pendingIntent);

        //Build notification
        Notification notification = notificationBuilder.build();
        //Flags for consecutive notification updates to replace existing one & user not clear
        //notification.flags |= Notification.FLAG_NO_CLEAR | Notification.FLAG_ONGOING_EVENT;

        //Request notification update
        NotificationManager notificationManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId, notification);

    }

    /**
     * Delete the notification channel
     * @param ctx context
     */
    public static void deleteStatusBarNotification(Context ctx){
        NotificationManager notificationManager =
                (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        // The id of the channel.
        notificationManager.deleteNotificationChannel(Constant.CHANNEL_ID);
    }

    /**
     * Create a notification channel
     * Require per API >= 26
     *
     * @param ctx context
     */
    @RequiresApi(Build.VERSION_CODES.O)
    private static void createNotificationChannel (Context ctx){
        //Create the notification manager
        NotificationManager notificationManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);

        //Verify the notification channel is not in use
        if (notificationManager.getNotificationChannel(Constant.CHANNEL_ID) == null) {
            Log.d(_TAG, "createNotificationChannel: create new channel");
            //Define a new notification channel - importance_low
            NotificationChannel notificationChannel = new NotificationChannel ( Constant.CHANNEL_ID,
                                                        Constant.CHANNEL_NAME,
                                                        NotificationManager.IMPORTANCE_LOW);
            notificationChannel.setDescription(Constant.CHANNEL_DESCRIPTION);
            notificationChannel.setLightColor(Color.DKGRAY);
            notificationChannel.enableVibration(true);

            //Create notification channel
            notificationManager.createNotificationChannel(notificationChannel);

        } else{
            Log.d(_TAG, "createNotificationChannel: channel reuse");
        }


    }
}
