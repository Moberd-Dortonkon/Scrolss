package com.example.koolguy.scroll;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

/**
 * Created by User on 03.06.2018.
 */

public class MyAlarmReceiver extends BroadcastReceiver {
    MyNotification myNotification;
    NotificationUtils mNotificationUtils;


    public void onReceive(Context context, Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mNotificationUtils = new NotificationUtils(context);
            Notification.Builder nb = mNotificationUtils.
                    getAndroidChannelNotification("Volunteer Helper", "У Вас скоро рабочая смена");
            mNotificationUtils.getManager().notify(1, nb.build());

        }



        else MyNotification.notify(context,"Привет",1);




    }
}
