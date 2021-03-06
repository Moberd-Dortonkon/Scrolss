package com.moberd.koolguy.scroll.groups;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
    Button volonteer;
    private SoundPool mySoundPool;
    private AssetManager myAssetManager;
    private int myButtonSound;
    private int myStreamID;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_greetings_group, container, false);
        createSoundPool();
        myAssetManager = view.getContext().getAssets();
        myButtonSound=createSound("button_16.mp3");
        // Inflate the layout for this fragment
       // ViewGroup viewGroup = new ViewGroup();
        //viewGroup.e
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        resources =view.getContext().getSharedPreferences(MainActivity.GROUP_PREFERENCES, Context.MODE_PRIVATE);
        name = view.findViewById(R.id.greetingf_name);
        volonteer=(Button)view.findViewById(R.id.greetings_button_volonteer);
        leader=(Button)view.findViewById(R.id.greetings_button_leader);
        second_name=view.findViewById(R.id.family);
        volonteer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(myButtonSound);
                if(!name.getText().toString().isEmpty()&&!second_name.getText().toString().isEmpty()){
                    resources.edit().putString("name",name.getText().toString()+" "+second_name.getText().toString()).apply();
                    Retrofit retrofit = new Retrofit.Builder().baseUrl(MainActivity.TEST_SERVER)
                            .addConverterFactory(GsonConverterFactory.create()).build();
                    CreateLeader createLeader = retrofit.create(CreateLeader.class);
                    Call<ResponseBody>call=createLeader.createLeader(name.getText().toString()+" "+second_name.getText().toString());
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if(response.isSuccessful())
                            {
                                try {
                                    leaderid=response.body().string();
                                  //  Toast.makeText(getActivity(),leaderid,Toast.LENGTH_SHORT).show();
                                    resources.edit().putString("leaderid",leaderid).apply();
                                    resources.edit().putString("type","volonteer").apply();
                                   // getFragmentManager().beginTransaction().replace(R.id.frames,new ChooseToDo()).disallowAddToBackStack().commit();
                                    getFragmentManager().beginTransaction().replace(R.id.frames,new ChooseToDoVolonteer()).disallowAddToBackStack().commit();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });
                    // Toast.makeText(getActivity(),leaderid,Toast.LENGTH_SHORT).show();
                    //getFragmentManager().beginTransaction().replace(R.id.frames,new ChooseToDo()).disallowAddToBackStack().commit();
                }
            }
        });
        leader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(myButtonSound);
                if(!name.getText().toString().isEmpty()&&!second_name.getText().toString().isEmpty()){
                    resources.edit().putString("name",name.getText().toString()+" "+second_name.getText().toString()).apply();
                    Retrofit retrofit = new Retrofit.Builder().baseUrl(MainActivity.TEST_SERVER)
                            .addConverterFactory(GsonConverterFactory.create()).build();
                    CreateLeader createLeader = retrofit.create(CreateLeader.class);
                    Call<ResponseBody>call=createLeader.createLeader(name.getText().toString()+" "+second_name.getText().toString());
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if(response.isSuccessful())
                            {
                                try {
                                    leaderid=response.body().string();
                                   // Toast.makeText(getActivity(),leaderid,Toast.LENGTH_SHORT).show();
                                    resources.edit().putString("leaderid",leaderid).apply();
                                    resources.edit().putString("type","leader").apply();
                                   // getFragmentManager().beginTransaction().replace(R.id.frames,new ChooseToDo()).disallowAddToBackStack().commit();
                                    getFragmentManager().beginTransaction().replace(R.id.frames,new ChooseToDoLeader()).disallowAddToBackStack().commit();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });
                    // Toast.makeText(getActivity(),leaderid,Toast.LENGTH_SHORT).show();
                    //getFragmentManager().beginTransaction().replace(R.id.frames,new ChooseToDo()).disallowAddToBackStack().commit();
                }
            }
        });

       /* Button apply=view.findViewById(R.id.greetings_button);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!name.getText().toString().isEmpty()&&!second_name.getText().toString().isEmpty()){
                    resources.edit().putString("name",name.getText().toString()+" "+second_name.getText().toString()).apply();
                    Retrofit retrofit = new Retrofit.Builder().baseUrl(MainActivity.TEST_SERVER)
                            .addConverterFactory(GsonConverterFactory.create()).build();
                    CreateLeader createLeader = retrofit.create(CreateLeader.class);
                    Call<ResponseBody>call=createLeader.createLeader(name.getText().toString()+" "+second_name.getText().toString());
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if(response.isSuccessful())
                            {
                                try {
                                    leaderid=response.body().string();
                                    Toast.makeText(getActivity(),leaderid,Toast.LENGTH_SHORT).show();
                                    resources.edit().putString("leaderid",leaderid).apply();
                                    getFragmentManager().beginTransaction().replace(R.id.frames,new ChooseToDo()).disallowAddToBackStack().commit();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });
                   // Toast.makeText(getActivity(),leaderid,Toast.LENGTH_SHORT).show();
                //getFragmentManager().beginTransaction().replace(R.id.frames,new ChooseToDo()).disallowAddToBackStack().commit();
                    }
            }
        });*/


        return view;
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
