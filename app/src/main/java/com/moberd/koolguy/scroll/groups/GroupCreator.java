package com.moberd.koolguy.scroll.groups;


import android.app.FragmentManager;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.moberd.koolguy.scroll.MainActivity;
import com.moberd.koolguy.scroll.R;
import com.moberd.koolguy.scroll.VolonteersInfo.Group;
import com.moberd.koolguy.scroll.groups.ServerInterfaces.CreateGroup;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupCreator extends Fragment {





    public GroupCreator() {
        // Required empty public constructor
    }

    View view;
       String responsem;
    EditText name,description;
    Button button;
    private SoundPool mySoundPool;
    private AssetManager myAssetManager;
    private int myButtonSound;
    private int myStreamID;

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    FragmentManager fragmentManager;
    FrameLayout frameLayout;
    String leadernid;
    String StringDate;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

         view =inflater.inflate(R.layout.fragment_group_creator, container, false);
        createSoundPool();
        myAssetManager = view.getContext().getAssets();
        myButtonSound=createSound("button_16.mp3");
         name = view.findViewById(R.id.group_creator_name);
         description=view.findViewById(R.id.group_creator_description);
         frameLayout=(FrameLayout)view.findViewById(R.id.group_create_loading);
         button=view.findViewById(R.id.group_creator_create);
         button.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 playSound(myButtonSound);
                 Retrofit retrofit = new Retrofit.Builder().baseUrl(MainActivity.TEST_SERVER)
                         .addConverterFactory(GsonConverterFactory.create()).build();
                 frameLayout.addView(inflater.inflate(R.layout.progress_view,null));
                 String leaderid=view.getContext().getSharedPreferences(MainActivity.GROUP_PREFERENCES,Context.MODE_PRIVATE).getString("leaderid","");
                 String name_leader=view.getContext().getSharedPreferences(MainActivity.GROUP_PREFERENCES,Context.MODE_PRIVATE).getString("name","");
                 //Date date = Calendar.getInstance().getTime();
                 StringDate = DateFormat.getDateInstance(DateFormat.SHORT).format(Calendar.getInstance().getTime());

                // Toast.makeText(getActivity(),StringDate,Toast.LENGTH_SHORT).show();
                 String grouptype="testgroup";
                 CreateGroup createGroup=retrofit.create(CreateGroup.class);
                 Call<ResponseBody>call=createGroup.createGroup
                         (leaderid,StringDate,name_leader,name.getText().toString(),description.getText().toString());
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()) {
                            try {
                                responsem=response.body().string();
                                String myName=view.getContext().getSharedPreferences(MainActivity.GROUP_PREFERENCES,Context.MODE_PRIVATE).getString("name","");
                                Group group = new Group(StringDate,myName,responsem,name.getText().toString(),description.getText().toString(),"");
                                LeaderGroupFragmentShow lg=new LeaderGroupFragmentShow();
                                lg.setGroupid(responsem);
                                lg.setGroup(group);
                               // Toast.makeText(getActivity(),"hi",Toast.LENGTH_SHORT).show();
                                fragmentManager.beginTransaction().replace(R.id.frames,lg).disallowAddToBackStack().commit();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });

                // Toast.makeText(getActivity(),responsem,Toast.LENGTH_SHORT).show();
             }
         });


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
