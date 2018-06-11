package com.moberd.koolguy.scroll.groups2;


import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.moberd.koolguy.scroll.HandBook2;
import com.moberd.koolguy.scroll.MainActivity;
import com.moberd.koolguy.scroll.R;

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


        view = inflater.inflate(R.layout.fragment_choose_to_do_leader, container, false);
        creategroup=(Button)view.findViewById(R.id.choose_to_do_leader_creategroup);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        creategroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupCreator groupCreator = new GroupCreator();
                groupCreator.setFragmentManager(getFragmentManager());
                getChildFragmentManager().beginTransaction().replace(R.id.choose_to_do_leader_framelayout,groupCreator).commit();
            }
        });
        final MyGroups myGroups = new MyGroups();
        myGroups.setFragmentManager(getFragmentManager());
        Button showGroups = (Button)view.findViewById(R.id.choosetodoleadergroups);
        showGroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getChildFragmentManager().beginTransaction().replace(R.id.choose_to_do_leader_framelayout,myGroups).commit();
            }
        });
        return view;
    }
}
