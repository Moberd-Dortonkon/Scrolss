package com.moberd.koolguy.scroll.Tools;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class VolonteerInitializator {

    DatabaseReference reference;

    public VolonteerInitializator()
    {
       reference = FirebaseDatabase.getInstance().getReference("Groups");

    }

    private void initVolonteer(String groupid)
    {
        DatabaseReference checkGroup = reference.child(groupid);
        checkGroup.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 dataSnapshot.exists();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private void groupExist(String groupid)
    {


    }

    private void deleteVolonteer()
    {


    }

}
