package com.moberd.koolguy.scroll;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChooseStatus extends Fragment {
    interface ChooseStatusClick
    {
        void chooseStatusClick(int id);

    }
    View view;
    Button volonteer,leader;
    ScrollView scrollView;
    TextView textView;
    private SoundPool mySoundPool;
    private AssetManager myAssetManager;
    private int myButtonSound;
    private int myStreamID;


    public ChooseStatus() {
        // Required empty public constructor
    }
    ChooseStatusClick click;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        click=(ChooseStatusClick) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_choose_status, container, false);
        createSoundPool();
        myAssetManager = view.getContext().getAssets();
        myButtonSound=createSound("button_16.mp3");
        scrollView=view.findViewById(R.id.scrollView);
        textView = (TextView)view.findViewById(R.id.chooseStatus);
        volonteer=(Button)view.findViewById(R.id.Volonteer);
        leader=(Button) view.findViewById(R.id.Leader);
        volonteer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSound(myButtonSound);
                textView.setText("Присоединиться к Группе");
                getChildFragmentManager().beginTransaction().replace(R.id.childChooseFragment,new CreateVolonteer()).commit();
            }
        });
        leader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSound(myButtonSound);
                SharedPreferences preferences = view.getContext().getSharedPreferences(MainActivity.APP_PREFERENCES,Context.MODE_PRIVATE);
                preferences.edit().clear().commit();

                textView.setText("Создать Группу");
                getChildFragmentManager().beginTransaction().replace(R.id.childChooseFragment,new LeaderCreateGroup()).commit();
            }
        });
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbarId);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSound(myButtonSound);
                getActivity().onBackPressed();
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
}
