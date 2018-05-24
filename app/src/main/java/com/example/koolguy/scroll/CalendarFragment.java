package com.example.koolguy.scroll;


import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.haibin.calendarview.CalendarView;

import java.util.Calendar;
import java.util.Date;

import sun.bob.mcalendarview.MCalendarView;
import sun.bob.mcalendarview.MarkStyle;
import sun.bob.mcalendarview.listeners.OnDateClickListener;
import sun.bob.mcalendarview.vo.DateData;
import sun.bob.mcalendarview.vo.MarkedDates;


/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarFragment extends Fragment {
     CalendarView calendarView;
    View v;
    public CalendarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v =inflater.inflate(R.layout.fragment_calendar, container, false);
        // Inflate the layout for this fragment
        calendarView = (CalendarView) v.findViewById(R.id.calendar);
        calendarView.setWeekStarWithMon();
        calendarView.setRange(2018,5,2018,6);









         return v;
    }

}
