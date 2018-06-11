package com.moberd.koolguy.scroll.groups2;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.v7.widget.ButtonBarLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moberd.koolguy.scroll.MainActivity;
import com.moberd.koolguy.scroll.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LeaderConfirm extends Fragment {

    Button button;
    EditText name;
    EditText password;
    public LeaderConfirm() {
        // Required empty public constructor
    }
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final DatabaseReference ref= FirebaseDatabase.getInstance().getReference("GroupPassword");
        view=inflater.inflate(R.layout.fragment_leader_confirm, container, false);
        button=(Button)view.findViewById(R.id.confirm);
        name =(EditText)view.findViewById(R.id.login);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        password=(EditText)view.findViewById(R.id.password); 
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String login=dataSnapshot.child("Login").getValue(String.class);
                        String passwordServ=dataSnapshot.child("Password").getValue(String.class);
                        if(login.equals(name.getText().toString())&&passwordServ.equals(password.getText().toString()))
                        {
                            view.getContext().getSharedPreferences(MainActivity.GROUP_PREFERENCES, Context.MODE_PRIVATE).edit().putString("role","leader").apply();
                            getFragmentManager().beginTransaction().replace(R.id.frames,new ChooseToDoLeader()).disallowAddToBackStack().commit();

                        }
                        else Toast.makeText(getActivity(), "Неверный логин или пароль", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });


        return view;
    }

}
