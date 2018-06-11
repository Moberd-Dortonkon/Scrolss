package com.moberd.koolguy.scroll;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moberd.koolguy.scroll.VolonteersInfo.Volonteer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class InDevelopment extends Fragment implements DatabaseReference.CompletionListener {


    public InDevelopment() {
        // Required empty public constructor
    }
   View view;
    List<String>groups;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_in_development, container, false);
        view.getContext().getSharedPreferences(MainActivity.CALENDAR_PREFERENCES, Context.MODE_PRIVATE).edit().clear().apply();
        groups=new ArrayList<String>();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
      //  database.getReference("Leaders").push().getKey();
        //database.getReference("Leaders").child("test1").setValue("Leaderid");
       // database.getReference("Leaders").child("test1").child("Group1").setValue("GroupObj");
       // database.getReference("Leaders").child("test1").child("Group2").setValue("GroupObj");
        DatabaseReference groupCreate=database.getReference("Leaders").child("test1").push();
        final String groupId=groupCreate.getKey();
       // Toast.makeText(getActivity(),groupId,Toast.LENGTH_SHORT).show();
        DatabaseReference myGroup=database.getReference("Leaders").child("Test").child("Groups");
        DatabaseReference testi=database.getReference("Groups").child("-LEaM_BpLfU3XuJeMnZj");

        testi.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if(!dataSnapshot.hasChildren())Toast.makeText(getActivity(),dataSnapshot.getValue(String.class),Toast.LENGTH_SHORT).show();
               // Toast.makeText(getActivity(),dataSnapshot.child("Name").getValue(String.class),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            Toast.makeText(getActivity(),dataSnapshot.getValue(String.class),Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(),dataSnapshot.getValue(String.class),Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return view;
    }

    @Override
    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

    }
}
