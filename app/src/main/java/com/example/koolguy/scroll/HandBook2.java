package com.example.koolguy.scroll;


import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.ListView;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 */
public class HandBook2 extends Fragment {
    View view;
    Activity activity;
    MyMap map;
    HandBookFragment.HandbookListener listener;
    String[] FirstHelp;
    ListView listView;
    private SoundPool mySoundPool;
    private AssetManager myAssetManager;
    private int myButtonSound;
    private int myStreamID;

    public HandBook2() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = activity;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_hand_book2, container, false);
        createSoundPool();
        myAssetManager = view.getContext().getAssets();
        myButtonSound=createSound("button_16.mp3");
        Resources resources = view.getResources();
        map = new MyMap(getActivity(),view.getContext());
        ScrollView sv = (ScrollView) view.findViewById(R.id.handbook);
        final Resources res=getResources();
        FirstHelp = res.getStringArray(R.array.FirstHelp);
        listView = (ListView)view.findViewById(R.id.lv);
        ArrayAdapter<String> BookAdapter=new ArrayAdapter<String>(inflater.getContext(), android.R.layout.simple_list_item_1,FirstHelp);
        listView.setAdapter(BookAdapter);


        Button schBtn=(Button)view.findViewById(R.id.schdBtn);
        schBtn.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSound(myButtonSound);
                TeamList2 mapFragment = new TeamList2();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frames, mapFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        }));

        Button mapBtn=(Button)view.findViewById(R.id.mapBtn);
        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSound(myButtonSound);
                map.makeMap();

            }
        });

        Button helpBtn=(Button) view.findViewById(R.id.helpBtn);
        helpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSound(myButtonSound);
                FirstHelpFragment mapFragment = new FirstHelpFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frames, mapFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        Button myBtn=(Button) view.findViewById(R.id.my_schd);
        myBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSound(myButtonSound);
                getFragmentManager().beginTransaction().replace(R.id.frames,new CalendarFragment()).addToBackStack(null).commit();
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i==0){
                    playSound(myButtonSound);
                    extraNumbersClick();

                }
                else {
                    playSound(myButtonSound);
                    Resources res = getResources();
                    String[] FirstHelp = res.getStringArray(R.array.FirstHelp);
                    String s = FirstHelp[i];
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + s));
                    startActivity(intent);
                }
            }
        });

        ImageButton settings=(ImageButton) view.findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsClick();

            }
        });


        return view;
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
            Toast.makeText(view.getContext(), "Не смог загрузить звук " + fileName,
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
    public void extraNumbersClick(){
        ExtraNumbersFragment mapFragment = new ExtraNumbersFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frames, mapFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void SettingsClick(){
        SettingsFragment mapFragment = new SettingsFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frames, mapFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }



}

