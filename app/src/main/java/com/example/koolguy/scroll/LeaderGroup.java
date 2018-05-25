package com.example.koolguy.scroll;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.app.Fragment;
import android.os.CountDownTimer;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
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
import com.example.koolguy.scroll.serverInterfaces.ServerSetCoordinates;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

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
    LatLng groupLatLng;
    MyThread thread;
    Button copy;
    TextView textView;
    Button map;
    GoogleMap gMap;
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
        ref = (RefreshStatus) activity;
    }

    public void setlName(String key) {
        this.key = key;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    LatLng finaLng;
    String key;
    HashMap<String, Volonteer> vGroup;
    Activity activity;

    public LeaderGroup() {
        // Required empty public constructor
    }


    public void setPasswordView(String s) {
        //    textView = (TextView)v.findViewById(R.id.refreshGroup);
        //  textView.setText(s);

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_leader_group, container, false);

        createSoundPool();
        myAssetManager = getActivity().getAssets();
        myButtonSound=createSound("button_16.mp3");

        refresh = (Button) v.findViewById(R.id.reset);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ref.refrsh();
            }
        });
        setHasOptionsMenu(true);

        map = (Button) v.findViewById(R.id.MAP);
        createMapDialog(savedInstanceState);
        preferences = v.getContext().getSharedPreferences(MainActivity.APP_PREFERENCES, Context.MODE_PRIVATE);
        editor = preferences.edit();
        thread = new MyThread(this);
        viewHolder = (ViewGroup) v.findViewById(R.id.groupFromStart);

        if (!preferences.contains("groupExist")) {
            greetingview = LayoutInflater.from(v.getContext()).inflate(R.layout.group_greetings, null);
            viewHolder.addView(greetingview);
        }
        if (preferences.contains("groupExist")) {
            showGroup = LayoutInflater.from(v.getContext()).inflate(R.layout.group_show, null);
            viewHolder.addView(showGroup);
            groupView = (ListView) v.findViewById(R.id.groupView);
            refresh = (Button) v.findViewById(R.id.reset);
        }
        textView = (TextView) v.findViewById(R.id.refreshGroup);
        textView.setText(key);
        copy = (Button) v.findViewById(R.id.sendNudes);
        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSound(myButtonSound); //повставляй везде эту строчку где у тебя какие либо нажатия будут
                String send = key;
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                myIntent.putExtra(Intent.EXTRA_TEXT, send);//
                startActivity(Intent.createChooser(myIntent, "Share with"));
            }
        });
        return v;
    }

    private void createMapDialog(Bundle savedInstanceState) {
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                playSound(myButtonSound);
               /* AlertDialog.Builder alertDiaolog = new AlertDialog.Builder(v.getContext());
                alertDiaolog.setView(R.layout.gmap_diaolg_icon);
                alertDiaolog.setCancelable(true);
                AlertDialog realDialog=alertDiaolog.create();
                realDialog.show();
                MapView mapView =(MapView)realDialog.findViewById(R.id.mapView);
                MapsInitializer.initialize(getActivity());
                mapView.onCreate(realDialog.onSaveInstanceState());
                mapView.onResume();
                mapView.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        gMap=googleMap;
                    }
                });
         */


                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.gmap_diaolg_icon);
                Window w = dialog.getWindow();
                w.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                dialog.setTitle("Укажите ваше место сегодня");
                dialog.show();
                MapView mapView = (MapView) dialog.findViewById(R.id.mapView);
              LatLng groupLatLng;
                MapsInitializer.initialize(getActivity());

                mapView.onCreate(dialog.onSaveInstanceState());
                mapView.onResume();
                mapView.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        gMap = googleMap;
                        final boolean circle_exist=false;
                        gMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(47.236361,39.715731)));
                        gMap.moveCamera(CameraUpdateFactory.zoomTo(12));
                        if (ActivityCompat.checkSelfPermission(view.getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(view.getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        }
                        gMap.setMyLocationEnabled(true);
                        gMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                            @Override
                            public void onMapLongClick(LatLng latLng) {
                               gMap.clear();
                               gMap.addCircle(new CircleOptions().center(latLng).radius(450).clickable(true).fillColor(0x220000FF));
                               gMap.addMarker(new MarkerOptions().title("место вашей группы?").position(latLng).draggable(false).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_flag)));

                            }

                        });
                        gMap.setOnCircleClickListener(new GoogleMap.OnCircleClickListener() {
                            @Override
                            public void onCircleClick(Circle circle) {
                                gMap.addMarker(new MarkerOptions().position(circle.getCenter()).title("Место Вашей группы"));
                            }
                        });


                    }
                });
               Button dialogCheck = (Button)dialog.findViewById(R.id.dialogCheck);
               dialogCheck.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {

                   }
               });

            }
        });
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
            for (int i =0;i<99999;i++) {
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
                    } catch (Exception e) {
                    }
                } catch (IOException e) {
                }

                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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
    public void onStart() {
        super.onStart();
        thread.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        thread.interrupt();
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

