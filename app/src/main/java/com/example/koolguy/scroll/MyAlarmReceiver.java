package com.example.koolguy.scroll;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by User on 03.06.2018.
 */

public class MyAlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Alarm worked.", Toast.LENGTH_LONG).show();


    }
}
