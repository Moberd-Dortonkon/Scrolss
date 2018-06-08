package com.moberd.koolguy.scroll;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.moberd.koolguy.scroll.VolonteersInfo.Volonteer;


/**
 * A simple {@link Fragment} subclass.
 */
public class InDevelopment extends Fragment {


    public InDevelopment() {
        // Required empty public constructor
    }
   View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_in_development, container, false);
        view.getContext().getSharedPreferences(MainActivity.CALENDAR_PREFERENCES, Context.MODE_PRIVATE).edit().clear().apply();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref=database.getReference("message");
        ref.setValue("Hello world2");
        ref.setValue("volonteer",new Volonteer("test",false,"19.12.2018/12:12:34"));
        return view;
    }

}
