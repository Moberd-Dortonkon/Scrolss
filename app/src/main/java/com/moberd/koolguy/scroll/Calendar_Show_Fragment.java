package com.moberd.koolguy.scroll;


import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.SoundPool;
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

import java.io.IOException;
import java.util.Calendar;
import java.util.Set;
import java.util.TreeSet;


/**
 * A simple {@link Fragment} subclass.
 */
public class Calendar_Show_Fragment extends Fragment {
    MaterialCalendarView calendarView;
    View v;
    TextView textView;
    private SoundPool mySoundPool;
    private AssetManager myAssetManager;
    private int myButtonSound;
    private int myStreamID;

    public Calendar_Show_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_calendar__show_, container, false);
        createSoundPool();
        myAssetManager = v.getContext().getAssets();
        myButtonSound=createSound("button_16.mp3");
        final SharedPreferences sharedPreferences=v.getContext().getSharedPreferences(MainActivity.CALENDAR_PREFERENCES,Context.MODE_PRIVATE);
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
            public void onClick(final View v) {
                        playSound(myButtonSound);
                        AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext());
                        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                playSound(myButtonSound);
                                v.getContext().getSharedPreferences(MainActivity.CALENDAR_PREFERENCES, Context.MODE_PRIVATE).edit().clear().apply();
                                getFragmentManager().beginTransaction().replace(R.id.frames,new CalendarFragment()).disallowAddToBackStack().commit();
                            }
                        }).setTitle(R.string.reset_calendar).setCancelable(true).show();
                    }
                });

        return v;
    }

    private void createSoundPool() {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        mySoundPool = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();
    }

    private int createSound(String fileName) {
        AssetFileDescriptor AsFileDesc;
        try {
            AsFileDesc = myAssetManager.openFd(fileName);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Не смог загрузить звук " + fileName,
                    Toast.LENGTH_SHORT).show();
            return -1;
        }
        return mySoundPool.load(AsFileDesc, 1);
    }

    private int playSound(int sound) {
        if (sound > 0) {
            myStreamID = mySoundPool.play(sound, 1, 1, 1, 0, 1);
        }
        return myStreamID;
    }
}
