package com.moberd.koolguy.scroll.groups2;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moberd.koolguy.scroll.MainActivity;
import com.moberd.koolguy.scroll.R;
import com.moberd.koolguy.scroll.groups.ServerInterfaces.CreateVolonteer;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConnectDirectly extends Fragment {


    public ConnectDirectly() {
        // Required empty public constructor
    }
View view;
Button button;
boolean check;
EditText editText;
Retrofit retrofit;
FrameLayout frameLayout;
ShowAllGroups.DefineGroup listener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener=(ShowAllGroups.DefineGroup)activity;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_connect_directly, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        retrofit = new Retrofit.Builder()
                .baseUrl(MainActivity.TEST_SERVER)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        frameLayout=(FrameLayout)view.findViewById(R.id.LoadingFrame);
        check=true;
        editText = (EditText)view.findViewById(R.id.connect_direcltly);
        button =(Button)view.findViewById(R.id.connect_directly_buton);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                button.setEnabled(false);
                button.setVisibility(View.INVISIBLE);
                frameLayout.addView(inflater.inflate(R.layout.progress_view,null));
               // Toast.makeText(getActivity(),"Connecting...",Toast.LENGTH_SHORT).show();
                DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Groups").child(editText.getText().toString());
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(check)
                        if(dataSnapshot.exists()&&check){check =false; listener.defineGroup(editText.getText().toString());}
                        else{button.setEnabled(true);button.setVisibility(View.VISIBLE);frameLayout.setVisibility(View.INVISIBLE);check =false;}
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
