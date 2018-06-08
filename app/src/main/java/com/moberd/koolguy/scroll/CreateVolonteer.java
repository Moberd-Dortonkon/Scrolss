package com.example.koolguy.scroll;


import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Bundle;
        import android.app.Fragment;
import android.os.Looper;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.koolguy.scroll.serverInterfaces.ServerCreateGroup;
import com.example.koolguy.scroll.serverInterfaces.ServerCreateVolonteer;

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
public class CreateVolonteer extends Fragment {
    EditText lName;
    EditText name1;
    Button join;
    View v;
    String string;


    private SoundPool mySoundPool;
    private AssetManager myAssetManager;
    private int myButtonSound;
    private int myStreamID;

    interface createVolonteer {
        void createVolonteerCLick(String lName, String name);

    }

    public CreateVolonteer() {
        // Required empty public constructor
    }

    createVolonteer listener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (createVolonteer) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        createSoundPool();
        myAssetManager = getActivity().getAssets();
        myButtonSound=createSound("button_16.mp3");

        v = inflater.inflate(R.layout.fragment_create_volonteer, container, false);
        lName = (EditText) v.findViewById(R.id.leaderName);
        name1 = (EditText) v.findViewById(R.id.yourName);
        join = (Button) v.findViewById(R.id.join);
        string="";
        join.setEnabled(true);
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSound(myButtonSound);
                new MyThread().start();
                join.setEnabled(false);
                if(string.equals("complete")){

                    listener.createVolonteerCLick(lName.getText().toString(),name1.getText().toString());}
            }
        });

        return v;

    }

    class MyThread extends Thread
    {


        @Override
        public void run() {
            Retrofit retrofit = new Retrofit.Builder().baseUrl(MainActivity.SERVER).addConverterFactory(GsonConverterFactory.create()).build();
            ServerCreateVolonteer group = retrofit.create(ServerCreateVolonteer.class);
            String lNam = lName.getText().toString();
            String name2 = name1.getText().toString();
            Call<ResponseBody>  call = group.createVolonteer(name2,lNam);
                try {
                    Response<ResponseBody>response=call.execute();
                    string =response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            String s=string;

            if(s!=null)
            {
                if(s.equals("complete")){
                     v.getContext().getSharedPreferences(MainActivity.APP_PREFERENCES,Context.MODE_PRIVATE).edit().putString("groupPassword",lNam).commit();
                    listener.createVolonteerCLick(lName.getText().toString(),name1.getText().toString());}
                if(s.equals("try another name")){
                    Toast.makeText(v.getContext(),"try another name",Toast.LENGTH_LONG);
                    }
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
