package com.example.koolguy.scroll;


import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.koolguy.scroll.serverInterfaces.ServerCreateGroup;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class LeaderCreateGroup extends Fragment {

    View v;
    EditText leaderName;
    Button createGroup;
    TextView test;
    String string;
    LeaderCreateGroupNext listener;

    private SoundPool mySoundPool;
    private AssetManager myAssetManager;
    private int myButtonSound;
    private int myStreamID;

    public interface LeaderCreateGroupNext {
        void leaderCreateClick(String lName, String key);
    }

    public LeaderCreateGroup() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (LeaderCreateGroupNext) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        createSoundPool();
        myAssetManager = getActivity().getAssets();
        myButtonSound=createSound("button_16.mp3");
        v = inflater.inflate(R.layout.fragment_leader_create_group, container, false);
        leaderName = (EditText) v.findViewById(R.id.leadername);
        test = (TextView) v.findViewById(R.id.test);
        createGroup = (Button) v.findViewById(R.id.createGroup);
        createGroup.setEnabled(true);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        createGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSound(myButtonSound);
                Toast.makeText(v.getContext(), "Succses", Toast.LENGTH_LONG);
                new MyThread().start();
                //  if(string!=null)listener.leaderCreateClick(leaderName.getText().toString(),string);
                createGroup.setEnabled(false);
            }
        });

        Toast.makeText(v.getContext(), "Succses", Toast.LENGTH_LONG);
    }

    class MyThread extends Thread {
        String s;

        synchronized
        @Override
        public void run() {
            Retrofit retrofit = new Retrofit.Builder().baseUrl(MainActivity.SERVER).addConverterFactory(GsonConverterFactory.create()).build();
            ServerCreateGroup group = retrofit.create(ServerCreateGroup.class);
            String lName = leaderName.getText().toString();
                Call<ResponseBody> call = group.createGroup(lName);
                try {
                    Response<ResponseBody> response = call.execute();
                    string = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (string != null) {
                    listener.leaderCreateClick(leaderName.getText().toString(), string);
                }


            }


    }


    private void createSoundPool() {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        mySoundPool = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();
    }

    private int createSound(String fileName) {
        AssetFileDescriptor AsFileDesc;
        try {
            AsFileDesc = myAssetManager.openFd(fileName);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Не смог загрузить звук " + fileName,
                    Toast.LENGTH_SHORT).show();
            return -1;
        }
        return mySoundPool.load(AsFileDesc, 1);
    }

    private int playSound(int sound) {
        if (sound > 0) {
            myStreamID = mySoundPool.play(sound, 1, 1, 1, 0, 1);
        }
        return myStreamID;
    }


}



