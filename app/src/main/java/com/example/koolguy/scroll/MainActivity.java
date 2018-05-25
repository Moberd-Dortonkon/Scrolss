package com.example.koolguy.scroll;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.TypefaceSpan;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.darwindeveloper.horizontalscrollmenulibrary.custom_views.HorizontalScrollMenuView;
import com.darwindeveloper.horizontalscrollmenulibrary.extras.MenuItem;
import com.example.koolguy.scroll.ServerRequest.CreateGroup;
import com.example.koolguy.scroll.Tools.Json.JsonData;
import com.example.koolguy.scroll.Tools.Json.Place;
import com.example.koolguy.scroll.VolonteersInfo.Volonteer;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.Set;


public class MainActivity extends AppCompatActivity implements
        Check.Listener,DictionaryFragment.DictionaryListener,HandBookFragment.HandbookListener,LeaderCreateGroup.LeaderCreateGroupNext
        ,ChooseStatus.ChooseStatusClick,CreateVolonteer.createVolonteer,RefreshStatus {
    BottomNavigationView menu;
    TextView textView;
    MyMap map;
    SharedPreferences.Editor editor;
    public static final String APP_PREFERENCES = "mysettings";
    SharedPreferences preferences;
    public static final String SERVER = "https://immense-wave-82247.herokuapp.com";
    CameraPosition saveCamera;

    private SoundPool mySoundPool;
    private AssetManager myAssetManager;
    private int myButtonSound;
    private int myStreamID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        menu = (BottomNavigationView) findViewById(R.id.menu);
        textView = (TextView) findViewById(R.id.text);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 9999);
        int i = 0;
       // map = new MyMap(this, this);
        Gson gson = new Gson();
        //Place[] places = gson.fromJson(String.valueOf(R.raw.data),Place.class);
        Toast.makeText(this, " ", Toast.LENGTH_LONG).show();
        initSharedPreferences();
        initMenu();

        createSoundPool();

        myAssetManager = getAssets();
        myButtonSound=createSound("button_16.mp3");




    }


    private void initSharedPreferences() {

        preferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        editor = preferences.edit();
       /* if(preferences.getString("role","").equals("leader"))
        {
            CreateGroup createGroup=new CreateGroup();
            createGroup.setlName(preferences.getString("lName",""));
            //createGroup.execute("");
        }//здесь идем к сервер
        if(preferences.getString("role","").equals("volonteer"))
        {
            CreateGroup createGroup=new CreateGroup();
            createGroup.setlName(preferences.getString("lName",""));
          //  createGroup.execute("");
            com.example.koolguy.scroll.ServerRequest.CreateVolonteer createVolonteer = new com.example.koolguy.scroll.ServerRequest.CreateVolonteer();
            createVolonteer.setlNam(preferences.getString("lName",""));
            createVolonteer.setName2(preferences.getString("name",""));
            //createVolonteer.execute("");

        }*/
    }


    private void mateToast(String position) {
//        Toast.makeText(this, "" + position, Toast.LENGTH_SHORT).show();
    }

    private void anotherFragment() {
        playSound(myButtonSound);
        if (!preferences.contains("role")) {
            ChooseStatus mapFragment = new ChooseStatus();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.frames, mapFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        if (preferences.getString("role", "").equals("leader")) {

            LeaderGroup mapFragment = new LeaderGroup();
            mapFragment.setActivity(this);
           // mapFragment.setPasswordView(preferences.getString("groupPassword", ""));
            mapFragment.setlName(preferences.getString("groupPassword", ""));
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.frames, mapFragment);
            transaction.addToBackStack(null);
            transaction.commit();

        }
        if (preferences.getString("role", "").equals("volonteer")) {
            VolonteerStatus mapFragment = new VolonteerStatus();
            mapFragment.setlName(preferences.getString("lName", ""));
            mapFragment.setName(preferences.getString("name", ""));
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.frames, mapFragment);
            transaction.addToBackStack(null);
            transaction.commit();

        }
    }


    private void initMenu() {

        handBookFragment();
        menu.setSelectedItemId(R.id.book);
        menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull android.view.MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.book:
                        handBookFragment(); //создать метод который вызывает справочник
                        break;
                    // case R.id.map:
                    //  map.makeMap(new ArrayList<LatLng>());break;
                    case R.id.account:
                        anotherFragment(); // для сервера
                        break;
                    case R.id.dictionary:
                        dictionaryFragment(); //Создать метод который вызывыет словарь
                        break;
                }


                return true;
            }
        });

    }

    private void dictionaryFragment() //создание
    {
        playSound(myButtonSound);
        DictionaryFragment mapFragment = new DictionaryFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frames, mapFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void handBookFragment() //создание
    {
        playSound(myButtonSound);
        HandBookFragment mapFragment = new HandBookFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frames, mapFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override

    public void click() {
        ArrayList<LatLng> latLngs = new ArrayList<LatLng>();
        latLngs.add(new LatLng(47.277424, 39.707281));
        latLngs.add(new LatLng(47.213866, 39.711912));
        latLngs.add(new LatLng(47.219229, 39.704359));
        latLngs.add(new LatLng(47.221792, 39.723543));
        latLngs.add(new LatLng(47.225965, 39.746229));
        latLngs.add(new LatLng(47.226343, 39.739019));
        map.makeMap();

    }


    public void DictionaryClick(String [] phrase) {
        playSound(myButtonSound);
        ListDictFragment dict = new ListDictFragment();
        dict.setI(phrase);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frames, dict); //если
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void extraNumbersClick() {
        playSound(myButtonSound);
        ExtraNumbersFragment mapFragment = new ExtraNumbersFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frames, mapFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void handBookClick(int position) {
        if (position == 0) {
            playSound(myButtonSound);
            map.makeMap();



        }
        if (position == 1) {
            playSound(myButtonSound);
            FirstHelpFragment mapFragment = new FirstHelpFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.frames, mapFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        if (position == 2) {
            playSound(myButtonSound);
            TeamList2 mapFragment = new TeamList2();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.frames, mapFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }


    @Override
    public void leaderCreateClick(String lName, String pass) {
        playSound(myButtonSound);
        LeaderGroup mapFragment = new LeaderGroup();
        editor.putString("role", "leader");
        editor.putString("lName", lName);
        editor.putString("groupPassword", pass);
        editor.commit();
        //mateToast(pass);
        mapFragment.setlName(preferences.getString("groupPassword", ""));
        mapFragment.setActivity(this);
      //  mapFragment.setPasswordView(preferences.getString("groupPassword", ""));
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frames, mapFragment);
        //transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void chooseStatusClick(int id) {
        switch (id) {
            case 1:
                playSound(myButtonSound);
                LeaderCreateGroup mapFragment = new LeaderCreateGroup();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frames, mapFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;


            case 2:
                playSound(myButtonSound);
                CreateVolonteer volonteerStatus = new CreateVolonteer();
                FragmentTransaction trans = getFragmentManager().beginTransaction();
                trans.replace(R.id.frames, volonteerStatus);
                trans.addToBackStack(null);
                trans.commit();
                break;

        }
    }

    @Override
    public void createVolonteerCLick(String lName, String name) {
        VolonteerStatus volonteerStatus = new VolonteerStatus();
        editor.putString("role", "volonteer");
        editor.putString("lName", lName);
        editor.putString("name", name);
        editor.commit();
       //l Toast.makeText(this, "" + lName + name, Toast.LENGTH_LONG).show();
        volonteerStatus.setlName(lName);
        volonteerStatus.setName(name);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frames, volonteerStatus);
        //transaction.addToBackStack(null);
        transaction.commit();


    }

    @Override
    public void refrsh() {
        ChooseStatus dict = new ChooseStatus();

        editor.clear().commit();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frames, dict); //если
       // transaction.addToBackStack(null);
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
            Toast.makeText(getApplicationContext(), "Не смог загрузить звук " + fileName,
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