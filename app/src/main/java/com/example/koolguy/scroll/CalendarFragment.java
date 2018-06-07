package com.example.koolguy.scroll;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;


import com.google.android.gms.common.SignInButton;
import com.haibin.calendarview.CalendarView;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;


import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import sun.bob.mcalendarview.MCalendarView;
import sun.bob.mcalendarview.MarkStyle;
import sun.bob.mcalendarview.listeners.OnDateClickListener;
import sun.bob.mcalendarview.vo.DateData;
import sun.bob.mcalendarview.vo.MarkedDates;


/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarFragment extends Fragment {
    MaterialCalendarView calendarView;
    View v;
    TextView textView;
    Button button;
    Spinner spinner;
    int count;
    String[] time;
    static boolean ready;
    public CalendarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        v =inflater.inflate(R.layout.fragment_calendar, container, false);
        // Inflate the layout for this fragment
        button = (Button)v.findViewById(R.id.calendarButton);
        textView=(TextView)v.findViewById(R.id.calendarTextView);
        count=0;
        textView.setText("Выберите дни ваших смен");
        calendarView = (MaterialCalendarView) v.findViewById(R.id.calendar);
        calendarView.state().edit().setMinimumDate(CalendarDay.from(2018,5,1)).setMaximumDate(CalendarDay.from(2018,6,19))
                .setFirstDayOfWeek(Calendar.MONDAY).setCalendarDisplayMode(CalendarMode.MONTHS).commit();
        calendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_MULTIPLE);
        spinner=(Spinner)v.findViewById(R.id.spinner);
        time = v.getContext().getResources().getStringArray(R.array.time);
        ArrayAdapter<String>spinnerAdapter=new ArrayAdapter<String>(v.getContext(),android.R.layout.simple_spinner_dropdown_item,time);
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {



                v.getContext().getSharedPreferences(MainActivity.CALENDAR_PREFERENCES,Context.MODE_PRIVATE).edit().putString("Time",time[selectedItemPosition]).apply();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // v.getContext().getSharedPreferences(MainActivity.CALENDAR_PREFERENCES,Context.MODE_PRIVATE).edit().
                TreeSet<String>dates=new TreeSet<>();
                List<CalendarDay>datesList=calendarView.getSelectedDates();
                for (CalendarDay day:datesList)
                {
                 dates.add(day.getYear()+":"+day.getMonth()+":"+day.getDay());        //yyyy:mm:dd
                }
                v.getContext().getSharedPreferences(MainActivity.CALENDAR_PREFERENCES,Context.MODE_PRIVATE).edit().putStringSet("Days",dates).apply();
                //FragmentReplace
                getFragmentManager().beginTransaction().replace(R.id.frames,new Calendar_Show_Fragment()).commit();

            }
        });
         return v;
    }

}
