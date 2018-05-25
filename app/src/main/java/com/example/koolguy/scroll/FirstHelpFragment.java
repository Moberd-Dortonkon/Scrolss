package com.example.koolguy.scroll;


import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 */
public class FirstHelpFragment extends ListFragment {
    View view;
    String[] FirstHelp;
    private SoundPool mySoundPool;
    private AssetManager myAssetManager;
    private int myButtonSound;
    private int myStreamID;

    public FirstHelpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_first_help, container, false);
        createSoundPool();
        myAssetManager = view.getContext().getAssets();
        myButtonSound=createSound("button_16.mp3");

        Resources res=getResources();
        String[] FirstHelp = res.getStringArray(R.array.FirstHelp);
        ArrayAdapter<String> BookAdapter=new ArrayAdapter<String>(inflater.getContext(), android.R.layout.simple_list_item_1,FirstHelp);
        setListAdapter(BookAdapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    @Override

    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        if (position==0){
            playSound(myButtonSound);
            extraNumbersClick();

        }
        else {
            playSound(myButtonSound);
            Resources res = getResources();
            String[] FirstHelp = res.getStringArray(R.array.FirstHelp);
            String s = FirstHelp[position];
            Uri address = Uri.parse("https://www.google.ru/search?q=первая+помощь+при+" + s);
            Intent openlinkIntent = new Intent(Intent.ACTION_VIEW, address);
            startActivity(openlinkIntent);
        }
    }
    public void extraNumbersClick(){
        ExtraNumbersFragment mapFragment = new ExtraNumbersFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frames, mapFragment);
        transaction.addToBackStack(null);
        transaction.commit();
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
            Toast.makeText(view.getContext(), "Не смог загрузить звук " + fileName,
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

