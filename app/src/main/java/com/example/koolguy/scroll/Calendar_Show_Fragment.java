package com.example.koolguy.scroll;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;


/**
 * A simple {@link Fragment} subclass.
 */
public class Calendar_Show_Fragment extends Fragment {
  MaterialCalendarView calendarView;
  View v;
  TextView textView;

    public Calendar_Show_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_calendar__show_, container, false);
        SharedPreferences sharedPreferences=v.getContext().getSharedPreferences(MainActivity.CALENDAR_PREFERENCES,Context.MODE_PRIVATE);
        textView=(TextView)v.findViewById(R.id.time_show);
        textView.setText(sharedPreferences.getString("Time","Время не назначено"));
        Set<String>days=sharedPreferences.getStringSet("Days",new TreeSet<String>());
        calendarView=(MaterialCalendarView)v.findViewById(R.id.calendarShow);
        calendarView.state().edit().setMinimumDate(CalendarDay.from(2018,5,1)).setMaximumDate(CalendarDay.from(2018,6,31))
                .setFirstDayOfWeek(Calendar.MONDAY).setCalendarDisplayMode(CalendarMode.MONTHS).commit();
        calendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_NONE);
        calendarView.setSelectionColor(Color.GREEN);
       for (String d:days)
        {
            int y=Integer.valueOf(d.split(":")[0]);
            int m =Integer.valueOf(d.split(":")[1]);
            int date =Integer.valueOf(d.split(":")[2]);
            calendarView.setDateSelected(CalendarDay.from(y,m,date),true);
        }

        for (String d:days){
            int y=Integer.valueOf(d.split(":")[0]);
            int m =Integer.valueOf(d.split(":")[1]);
            int date =Integer.valueOf(d.split(":")[2]);
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, 2018);
            calendar.set(Calendar.MONTH, m);
            calendar.set(Calendar.DAY_OF_MONTH, date);
            calendar.set(Calendar.HOUR_OF_DAY, 2);
            calendar.set(Calendar.MINUTE, 18);
            calendar.set(Calendar.SECOND, 0);
            PendingIntent pi = PendingIntent.getBroadcast(getActivity(), 0 , new Intent(getActivity(), MyAlarmReceiver.class),PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager am = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
            am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),pi);

        }





        return v;
    }

}
