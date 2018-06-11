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

public class ChooseToDoVolonteer extends Fragment {


    View view;
    Button show_groups;
    Button conntect_directly;
    private SoundPool mySoundPool;
    private AssetManager myAssetManager;
    private int myButtonSound;
    private int myStreamID;
    public ChooseToDoVolonteer() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        view = inflater.inflate(R.layout.fragment_chooset_to_do__volonteer, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        show_groups=(Button)view.findViewById(R.id.choose_to_do_showgroups);
        conntect_directly=(Button)view.findViewById(R.id.chooset_to_do_coonnect_directly);
        show_groups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getChildFragmentManager().beginTransaction().replace(R.id.choose_to_do_volonteer_framelayout,new ShowAllGroups()).commit();
            }
        });
        conntect_directly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getChildFragmentManager().beginTransaction().replace(R.id.choose_to_do_volonteer_framelayout,new ConnectDirectly()).commit();

            }
        });

        return view;
    }
}
