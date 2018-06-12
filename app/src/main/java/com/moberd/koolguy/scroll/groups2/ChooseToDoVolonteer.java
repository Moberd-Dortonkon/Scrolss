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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class ChooseToDoVolonteer extends Fragment {


    View view;
    Button show_groups;
    Button conntect_directly;
    private SoundPool mySoundPool;
    private AssetManager myAssetManager;
    private int myButtonSound;
    private int myStreamID;
    Dialog dialog;
    SharedPreferences group_pref;
    EditText name;
    EditText family;
    public ChooseToDoVolonteer() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        view = inflater.inflate(R.layout.fragment_chooset_to_do__volonteer, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        group_pref = view.getContext().getSharedPreferences(MainActivity.GROUP_PREFERENCES,Context.MODE_PRIVATE);
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
        ImageButton imageButton = (ImageButton) view.findViewById(R.id.settings);
        imageButton.setVisibility(View.VISIBLE);
        /*imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(view.getContext(),R.style.Theme_AppCompat_Light_Dialog_Alert);
                View settingsView = inflater.inflate(R.layout.volonteer_settings,null);

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
                        getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frames,new LeaderConfirm()).commit();
                        dialog.dismiss();
                    }
                });
                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!group_pref.getString("name","").equals(name.getText().toString()+" "+family.getText().toString()))
                        {
                            try {
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Groups").child(group_pref.getString("groupid","")).child("Volonteers");
                            reference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    try {
                                    String eattime= dataSnapshot.child(group_pref.getString("name","")).child("EatTime").getValue(String.class);
                                    boolean come =dataSnapshot.child(group_pref.getString("name","")).child("Come").getValue(Boolean.class);
                                    dataSnapshot.getRef().child(name.getText().toString()+" "+family.getText().toString()).child("EatTime").setValue(eattime);
                                    dataSnapshot.getRef().child(name.getText().toString()+" "+family.getText().toString()).child("Come").setValue(come);
                                    dataSnapshot.getRef().child((group_pref.getString("name",""))).removeValue();
                                    }catch (Exception e ){}
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });}catch (Exception e){}
                        }
                        group_pref.edit().putString("name",name.getText().toString()+" "+family.getText().toString()).apply();
                        dialog.dismiss();
                    }
                });
                dialog.setContentView(settingsView);
                dialog.show();
                dialog.getWindow().setLayout(1000,1400);



            }
        });*/
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setCancelable(true).setTitle(R.string.change_status).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(group_pref.contains("groupid"))FirebaseDatabase.getInstance().getReference("Groups").child(group_pref.getString("groupid",""))
                        .child("Volonteers").child(group_pref.getString("name","")).removeValue();
                     getFragmentManager().beginTransaction().replace(R.id.frames,new LeaderConfirm()).addToBackStack(null).commit();
                    }
                }).show();
            }
        });

        return view;
    }
}
