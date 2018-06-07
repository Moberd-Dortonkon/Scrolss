package com.example.koolguy.scroll.groups;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.koolguy.scroll.HandBook2;
import com.example.koolguy.scroll.MainActivity;
import com.example.koolguy.scroll.R;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChooseToDoLeader extends Fragment {
    Button creategroup;
    Button showGroups;
    View view;
    private SoundPool mySoundPool;
    private AssetManager myAssetManager;
    private int myButtonSound;
    private int myStreamID;
    public ChooseToDoLeader() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        view=inflater.inflate(R.layout.fragment_choose_to_do_leader, container, false);
        createSoundPool();
        myAssetManager = view.getContext().getAssets();
        myButtonSound=createSound("button_16.mp3");
        final MyGroups myGroups=new MyGroups();
        final GroupCreator groupCreator = new GroupCreator();
        groupCreator.setFragmentManager(getFragmentManager());
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        myGroups.setFragmentManager(getFragmentManager());
        ImageButton imageButton = (ImageButton)view.findViewById(R.id.settings);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(myButtonSound);
                AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        playSound(myButtonSound);
                        view.getContext().getSharedPreferences(MainActivity.GROUP_PREFERENCES, Context.MODE_PRIVATE).edit().clear().apply();
                        getFragmentManager().beginTransaction().replace(R.id.frames,new HandBook2()).disallowAddToBackStack().commit();
                    }
                }).setTitle(R.string.reset).setCancelable(true).show();
            }
        });
        creategroup = (Button)view.findViewById(R.id.choose_to_do_leader_creategroup);
        showGroups = (Button)view.findViewById(R.id.choosetodoleadergroups);
        showGroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(myButtonSound);
                getChildFragmentManager().beginTransaction().replace(R.id.choose_to_do_leader_framelayout,myGroups).disallowAddToBackStack().commit();
            }
        });

        creategroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(myButtonSound);
                getChildFragmentManager().beginTransaction().replace(R.id.choose_to_do_leader_framelayout,groupCreator).disallowAddToBackStack().commit();
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
