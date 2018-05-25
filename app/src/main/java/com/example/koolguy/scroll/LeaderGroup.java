package com.example.koolguy.scroll;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.app.Fragment;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.koolguy.scroll.Tools.GroupAdapter;
import com.example.koolguy.scroll.Tools.GroupListener;
import com.example.koolguy.scroll.VolonteersInfo.DisplayVolonteers;
import com.example.koolguy.scroll.VolonteersInfo.Volonteer;
import com.example.koolguy.scroll.serverInterfaces.ServerDisplayGroup;

import java.io.IOException;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class LeaderGroup extends Fragment implements GroupListener {
    ListView groupView;
    Button button;
    EditText editText;
    View v;
    MyThread thread;
    Button copy;
    TextView textView;
    RefreshStatus ref;
    SharedPreferences.Editor editor;
    Button refresh;
    SharedPreferences preferences;
    CountDownTimer timer;
    View greetingview;
    View showGroup;
    ViewGroup viewHolder;
    private SoundPool mySoundPool;
    private AssetManager myAssetManager;
    private int myButtonSound;
    private int myStreamID;




    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ref=(RefreshStatus)activity;
    }
    public void setlName(String key) {
        this.key = key;
    }
    public void setActivity(Activity activity){this.activity = activity;}
    String key;
    HashMap<String,Volonteer> vGroup;
    Activity activity;

    public LeaderGroup() {
        // Required empty public constructor
    }


    public void setPasswordView(String s)
    {
    //    textView = (TextView)v.findViewById(R.id.refreshGroup);
      //  textView.setText(s);

    }
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.fragment_leader_group, container, false);

        createSoundPool();

        myAssetManager = getActivity().getAssets();
        myButtonSound=createSound("button_16.mp3");

        refresh=(Button)v.findViewById(R.id.reset);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSound(myButtonSound);
                ref.refrsh();
            }
        });
        setHasOptionsMenu(true);
        preferences = v.getContext().getSharedPreferences(MainActivity.APP_PREFERENCES, Context.MODE_PRIVATE);
         editor=preferences.edit();
        thread = new MyThread(this);
        viewHolder = (ViewGroup) v.findViewById(R.id.groupFromStart);
        thread.start();
            if(!preferences.contains("groupExist")) {
                greetingview = LayoutInflater.from(v.getContext()).inflate(R.layout.group_greetings, null);
                viewHolder.addView(greetingview);
            }
        if(preferences.contains("groupExist")) {
            showGroup= LayoutInflater.from(v.getContext()).inflate(R.layout.group_show, null);
            viewHolder.addView(showGroup);
            groupView =(ListView)v.findViewById(R.id.groupView);
            refresh=(Button)v.findViewById(R.id.reset);
        }
        textView = (TextView)v.findViewById(R.id.refreshGroup);
        textView.setText(key);
        copy = (Button)v.findViewById(R.id.sendNudes);
        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSound(myButtonSound);
                String send = key;
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                myIntent.putExtra(Intent.EXTRA_TEXT, send);//
                startActivity(Intent.createChooser(myIntent, "Share with"));
            }
        });
        return v;
    }

    @Override
    public void listener(HashMap<String, Volonteer> vGroup) {
      groupView.setAdapter(new GroupAdapter(v.getContext(),vGroup));

    }
    private void test()
    {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.group_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void firstGroupTake(){
        viewHolder = (ViewGroup) v.findViewById(R.id.groupFromStart);
        viewHolder.removeAllViews();
        showGroup= LayoutInflater.from(v.getContext()).inflate(R.layout.group_show, null);
        viewHolder.addView(showGroup);
        groupView =(ListView)v.findViewById(R.id.groupView);
        refresh=(Button)v.findViewById(R.id.reset);}
    class MyThread extends Thread {
        GroupListener listener;
        public MyThread(GroupListener listener){this.listener=listener;}
        synchronized
        @Override
        public void run() {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(MainActivity.SERVER)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            ServerDisplayGroup group = retrofit.create(ServerDisplayGroup.class);

            Call<DisplayVolonteers> call = group.getVolonteers(key);
            try {
                Response<DisplayVolonteers> response = call.execute();
                DisplayVolonteers d = response.body();
                try {
                    vGroup = d.getVolonteers();
                }catch (Exception e){}
            } catch (IOException e) {
            }


            if (vGroup != null) {
                  String s="";
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(!preferences.contains("groupExist")){editor.putString("groupExist","yes");editor.commit();firstGroupTake();}
                        try {


                         groupView.setAdapter(new GroupAdapter(v.getContext(),vGroup));}catch (Exception e){}
                    }
                });}


        }
    }
    @Override
    public void onPause() {
        super.onPause();
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
            Toast.makeText(v.getContext(), "Не смог загрузить звук " + fileName,
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
