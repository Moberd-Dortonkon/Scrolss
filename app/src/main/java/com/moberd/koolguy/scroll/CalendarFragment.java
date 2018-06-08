package com.moberd.koolguy.scroll;


import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;


import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.TreeSet;


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
    private SoundPool mySoundPool;
    private AssetManager myAssetManager;
    private int myButtonSound;
    private int myStreamID;
    Resources res;
    String text;
    public CalendarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        v =inflater.inflate(R.layout.fragment_calendar, container, false);
        createSoundPool();
        myAssetManager = v.getContext().getAssets();
        myButtonSound=createSound("button_16.mp3");
        button = (Button)v.findViewById(R.id.calendarButton);
        textView=(TextView)v.findViewById(R.id.calendarTextView);
        count=0;
        res=v.getContext().getResources();
        text=res.getString(R.string.your_days);
        textView.setText(text);
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
                playSound(myButtonSound);



                v.getContext().getSharedPreferences(MainActivity.CALENDAR_PREFERENCES,Context.MODE_PRIVATE).edit().putString("Time",time[selectedItemPosition]).apply();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(myButtonSound);
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
