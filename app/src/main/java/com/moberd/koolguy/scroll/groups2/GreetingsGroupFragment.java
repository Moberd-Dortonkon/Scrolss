package com.moberd.koolguy.scroll.groups2;


import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.moberd.koolguy.scroll.MainActivity;
import com.moberd.koolguy.scroll.R;
import com.moberd.koolguy.scroll.groups.ServerInterfaces.CreateLeader;

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
public class GreetingsGroupFragment extends Fragment {



    public GreetingsGroupFragment() {
        // Required empty public constructor
    }
    EditText name;
    SharedPreferences resources;
    EditText second_name;
    String leaderid;
    Button leader;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    Button volonteer;
    private SoundPool mySoundPool;
    private AssetManager myAssetManager;
    private int myButtonSound;
    private int myStreamID;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view =inflater.inflate(R.layout.fragment_greetings_group, container, false);
        auth=FirebaseAuth.getInstance();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        FirebaseUser user=auth.getCurrentUser();
        String name1 = user.getDisplayName();

        name=(EditText)view.findViewById(R.id.greetingf_name);
        second_name=(EditText)view.findViewById(R.id.family);
        try {


        name.setText(name1.split(" ")[0]);
        second_name.setText(name1.split(" ")[1]);}
        catch (Exception e){}
        leader=(Button)view.findViewById(R.id.greetings_button_leader);
        volonteer=(Button)view.findViewById(R.id.greetings_button_volonteer);
        leader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.getContext().getSharedPreferences(MainActivity.GROUP_PREFERENCES,Context.MODE_PRIVATE).edit().putString("name",name.getText().toString()+" "+second_name.getText().toString()).apply();
                getFragmentManager().beginTransaction().replace(R.id.frames,new LeaderConfirm()).addToBackStack(null).commit();
            }
        });
        volonteer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.getContext().getSharedPreferences(MainActivity.GROUP_PREFERENCES,Context.MODE_PRIVATE).edit().putString("role","volonteer").apply();
                view.getContext().getSharedPreferences(MainActivity.GROUP_PREFERENCES,Context.MODE_PRIVATE).edit().putString("name",name.getText().toString()+" "+second_name.getText().toString()).apply();
                getFragmentManager().beginTransaction().replace(R.id.frames,new ChooseToDoVolonteer()).disallowAddToBackStack().commit();
            }
        });





        return view;
    }
}
