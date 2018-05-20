package com.example.koolguy.scroll;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import java.util.Calendar;
import java.util.Date;

import sun.bob.mcalendarview.MCalendarView;
import sun.bob.mcalendarview.vo.DateData;
import sun.bob.mcalendarview.vo.MarkedDates;


/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarFragment extends Fragment {
    MCalendarView calendarView;
    View v;
    public CalendarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v =inflater.inflate(R.layout.fragment_calendar, container, false);
        calendarView =(MCalendarView)v.findViewById(R.id.calendar);











         return v;
    }

}
