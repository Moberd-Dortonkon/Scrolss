package com.moberd.koolguy.scroll.groups2;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
    Dialog dialog ;
    EditText family;
    EditText name;
    SharedPreferences group_pref;
    public ChooseToDoLeader() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        view = inflater.inflate(R.layout.fragment_choose_to_do_leader, container, false);
        creategroup=(Button)view.findViewById(R.id.choose_to_do_leader_creategroup);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        ImageButton imageButton = (ImageButton) view.findViewById(R.id.settings);
        imageButton.setVisibility(View.GONE);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(view.getContext(),R.style.Theme_AppCompat_Light_Dialog_Alert);
                View settingsView = inflater.inflate(R.layout.leader_settings,null);
                group_pref = view.getContext().getSharedPreferences(MainActivity.GROUP_PREFERENCES,Context.MODE_PRIVATE);
                name =(EditText)settingsView.findViewById(R.id.name);
                name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                name.setText(group_pref.getString("name","").split(" ")[0]);
                family = (EditText)settingsView.findViewById(R.id.family);
                family.setText(group_pref.getString("name","").split(" ")[1]);
                ImageButton become_leader=(ImageButton)settingsView.findViewById(R.id.become_leader);
                Button confirm=(Button) settingsView.findViewById(R.id.confirm);
                become_leader.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        group_pref.edit().putString("role","volonteer").apply();
                        getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frames,new ChooseToDoVolonteer()).commit();
                        dialog.dismiss();
                    }
                });
                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!group_pref.getString("name","").equals(name.getText().toString()+" "+family.getText().toString()))
                        {
                          FirebaseDatabase.getInstance().getReference("Groups").child(group_pref.getString("groupid","")).child("LeaderName")
                                    .setValue(name.getText().toString()+" "+family.getText().toString());
                            group_pref.edit().putString("name",name.getText().toString()+" "+family.getText().toString());

                        }
                        dialog.dismiss();
                    }
                });
                dialog.setContentView(settingsView);
                dialog.show();
                dialog.getWindow().setLayout(1000,1400);



            }
        });

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
