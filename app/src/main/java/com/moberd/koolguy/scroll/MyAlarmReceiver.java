package com.moberd.koolguy.scroll;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;

/**
 * Created by User on 03.06.2018.
 */

public class MyAlarmReceiver extends BroadcastReceiver {
    MyNotification myNotification;
    NotificationUtils mNotificationUtils;
    Resources res;


    public void onReceive(Context context, Intent intent) {
        res=context.getResources();
        final String title =res.getString(R.string.app_name);
        final String text = res.getString(R.string.soon_work);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mNotificationUtils = new NotificationUtils(context);
            Notification.Builder nb = mNotificationUtils.
                    getAndroidChannelNotification(title, text);
            mNotificationUtils.getManager().notify(1, nb.build());

        }



        else MyNotification.notify(context,title,1);




    }
}
