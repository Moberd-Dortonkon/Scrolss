package com.example.koolguy.scroll;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

/**
 * Created by User on 03.06.2018.
 */

public class MyAlarmReceiver extends BroadcastReceiver {
    MyNotification myNotification;


    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "EHFF", Toast.LENGTH_SHORT).show();
        MyNotification.notify(context,"Привет",1);




    }
}
