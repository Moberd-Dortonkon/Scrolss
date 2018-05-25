package com.example.koolguy.scroll;


import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.media.AudioAttributes;
import android.media.Image;
import android.media.SoundPool;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 */
public class HandBookFragment extends Fragment {
    MyMap map;
    View view;
    Activity activity;
    private SoundPool mySoundPool;
    private AssetManager myAssetManager;
    private int myButtonSound;
    private int myStreamID;

    public HandBookFragment() {
        // Required empty public constructor
    }

    public static interface HandbookListener {
        void handBookClick(int position);

    }

    HandbookListener listener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (HandbookListener) activity;
        this.activity = activity;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        createSoundPool();

        myAssetManager = getActivity().getAssets();
        myButtonSound=createSound("button_16.mp3");
        view = inflater.inflate(R.layout.fragment_hand_book, container, false);
        Resources resources = view.getResources();

        map = new MyMap(activity,view.getContext(),resources.getIdentifier("frames","id",view.getContext().getPackageName()));
        ScrollView sv = (ScrollView) view.findViewById(R.id.handbook);
        ImageButton schBtn=(ImageButton)view.findViewById(R.id.schdBtn);
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

        ImageButton mapBtn=(ImageButton)view.findViewById(R.id.mapBtn);
        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSound(myButtonSound);
                map.makeMap();

            }
        });

        ImageButton helpBtn=(ImageButton) view.findViewById(R.id.helpBtn);
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

//    @Override
//
//    public void onListItemClick(ListView l, View v, int position, long id) {
//        super.onListItemClick(l, v, position, id);
//        listener.handBookClick(position);
//
//
//    }
//}
