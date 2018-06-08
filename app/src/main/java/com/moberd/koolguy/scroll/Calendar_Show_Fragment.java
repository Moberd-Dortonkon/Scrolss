package com.moberd.koolguy.scroll;


import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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
        final SharedPreferences sharedPreferences=v.getContext().getSharedPreferences(MainActivity.CALENDAR_PREFERENCES,Context.MODE_PRIVATE);
        textView=(TextView)v.findViewById(R.id.time_show);
        textView.setText(sharedPreferences.getString("Time","Время не назначено"));
        ImageButton imageButton = (ImageButton)v.findViewById(R.id.settings);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext());
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getFragmentManager().beginTransaction().replace(R.id.frames,new CalendarFragment()).addToBackStack(null).commit();
                    }
                }).setTitle(R.string.reset_calendar).setCancelable(true).show();
            }
        });
        Set<String>days=sharedPreferences.getStringSet("Days",new TreeSet<String>());
        calendarView=(MaterialCalendarView)v.findViewById(R.id.calendarShow);
        calendarView.state().edit().setMinimumDate(CalendarDay.from(2018,5,1)).setMaximumDate(CalendarDay.from(2018,6,31))
                .setFirstDayOfWeek(Calendar.MONDAY).setCalendarDisplayMode(CalendarMode.MONTHS).commit();
        calendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_NONE);
        calendarView.setSelectionColor(Color.GREEN);
       for (String d:days)
        {
           // Toast.makeText(getActivity(),d,Toast.LENGTH_SHORT).show();
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
            calendar.set(Calendar.HOUR_OF_DAY, 8);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            PendingIntent pi = PendingIntent.getBroadcast(getActivity(), 0 , new Intent(getActivity(), MyAlarmReceiver.class),PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager amAm = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
            amAm.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),pi);
            int dateYesterday=date-1;
            calendar.set(Calendar.DAY_OF_MONTH, dateYesterday);
            calendar.set(Calendar.HOUR_OF_DAY, 20);
            AlarmManager amPm = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
            amPm.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),pi);

        }

        ImageButton settings=(ImageButton) v.findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });




        return v;
    }

}
