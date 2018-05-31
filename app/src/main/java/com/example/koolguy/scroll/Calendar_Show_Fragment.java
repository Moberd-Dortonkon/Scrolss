package com.example.koolguy.scroll;


import android.content.Context;
import android.content.SharedPreferences;
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
        calendarView.state().edit().setMinimumDate(CalendarDay.from(2018,5,1)).setMaximumDate(CalendarDay.from(2018,6,19))
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





        return v;
    }

}
