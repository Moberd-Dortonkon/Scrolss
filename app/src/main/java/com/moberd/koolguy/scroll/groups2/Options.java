package com.moberd.koolguy.scroll.groups2;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.database.FirebaseDatabase;
import com.moberd.koolguy.scroll.MainActivity;
import com.moberd.koolguy.scroll.R;

public class Options extends Fragment {

    public Options(){}

    View settingsView;

    public void setContext(Context context) {
        this.context = context;
    }
    SharedPreferences group_pref;
    Context context;
    EditText name,family;
    Button confirm;
    ImageButton become_leader;
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        group_pref=context.getSharedPreferences(MainActivity.GROUP_PREFERENCES,Context.MODE_PRIVATE);
        if(context.getSharedPreferences(MainActivity.GROUP_PREFERENCES,Context.MODE_PRIVATE).getString("role","").equals("leader")){
        settingsView =  inflater.inflate(R.layout.leader_settings, container, false);
            name =(EditText)settingsView.findViewById(R.id.name);
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
                }
            });
        }


        if(context.getSharedPreferences(MainActivity.GROUP_PREFERENCES,Context.MODE_PRIVATE).getString("role","").equals("leader")) {
            settingsView = inflater.inflate(R.layout.volonteer_settings, container, false);



        }

         return  settingsView;
        }

}
