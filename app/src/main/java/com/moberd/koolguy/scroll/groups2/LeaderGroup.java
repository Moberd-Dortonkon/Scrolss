package com.moberd.koolguy.scroll.groups2;


import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.moberd.koolguy.scroll.MainActivity;
import com.moberd.koolguy.scroll.R;
import com.moberd.koolguy.scroll.RefreshStatus;
import com.moberd.koolguy.scroll.Tools.GroupAdapter;
import com.moberd.koolguy.scroll.Tools.GroupListener;
import com.moberd.koolguy.scroll.VolonteersInfo.Group;
import com.moberd.koolguy.scroll.VolonteersInfo.Volonteer;
import com.moberd.koolguy.scroll.groups.ServerInterfaces.GetMyVolonteers;
import com.moberd.koolguy.scroll.serverInterfaces.ServerSetCoordinates;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
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

    String testString;
    LatLng groupLatLng;
    //MyThread thread;
    Button copy;
    TextView textView;
    Button map;
    Thread sendCoordinates;
    GoogleMap gMap;
    RefreshStatus ref;
    SharedPreferences.Editor editor;
    Button refresh;
    SharedPreferences preferences;
    CountDownTimer timer;
    View greetingview;
    View showGroup;
    Group group;
    ViewGroup viewHolder;
    private LatLng created;
    private FirebaseDatabase db;
    private SoundPool mySoundPool;
    private AssetManager myAssetManager;
    private int myButtonSound;
    private int myStreamID;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ref = (RefreshStatus) activity;
    }
    public void setGroup(Group group){this.group=group;}
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
    LayoutInflater layoutInflater;

    public LeaderGroup() {
        // Required empty public constructor
    }


    public void setPasswordView(String s) {
        //    textView = (TextView)v.findViewById(R.id.refreshGroup);
        //  textView.setText(s);

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_leader_group, container, false);
        vGroup = new HashMap<>();
        db=FirebaseDatabase.getInstance();
        layoutInflater=LayoutInflater.from(v.getContext());
        viewHolder = (ViewGroup) v.findViewById(R.id.groupFromStart);
        //showGroup = LayoutInflater.from(v.getContext()).inflate(R.layout.group_show, null);
        //viewHolder.addView(showGroup);
        TextView textView = (TextView)v.findViewById(R.id.refreshGroup);
        textView.setText(key);
        map =(Button)v.findViewById(R.id.MAP);
        createMapDialog(savedInstanceState);
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
        groupView = (ListView) v.findViewById(R.id.groupView);
        DatabaseReference ref = db.getReference("Groups").child(key).child("Volonteers");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Volonteer volonteer = new Volonteer();
                volonteer.setName(dataSnapshot.getKey());
                volonteer.setCome(dataSnapshot.child("Come").getValue(Boolean.class));
                volonteer.setEattime(dataSnapshot.child("EatTime").getValue(String.class));
                vGroup.put(volonteer.getName(),volonteer);
                updateUi(vGroup);


            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Volonteer volonteer = new Volonteer();
                volonteer.setName(dataSnapshot.getKey());
                volonteer.setCome(dataSnapshot.child("Come").getValue(Boolean.class));
                volonteer.setEattime(dataSnapshot.child("EatTime").getValue(String.class));

                vGroup.put(volonteer.getName(),volonteer);
                updateUi(vGroup);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot test:dataSnapshot.getChildren())
                {
                    Volonteer volonteer = new Volonteer();
                    volonteer.setName(test.getKey());
                    volonteer.setCome(test.getValue(Boolean.class));
                    volonteer.setEattime(test.getValue(String.class));
                    vGroup.put(volonteer.getName(),volonteer);
                    updateUi(vGroup);
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return v;
    }
    private void updateUi(HashMap<String,Volonteer> volonteers)
    {
        if(vGroup.isEmpty()){viewHolder.removeAllViews();viewHolder.addView(layoutInflater.inflate(R.layout.group_greetings,null));}
        else{
            showGroup = LayoutInflater.from(v.getContext()).inflate(R.layout.group_show, null);
            viewHolder.addView(showGroup);
            groupView = (ListView) v.findViewById(R.id.groupView);
           groupView.setAdapter(new GroupAdapter(v.getContext(), volonteers));
            }
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


                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.gmap_diaolg_icon);
                Window w = dialog.getWindow();
                w.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                dialog.setTitle("Укажите ваше место сегодня");
                dialog.show();
                MapView mapView = (MapView) dialog.findViewById(R.id.mapView);

                MapsInitializer.initialize(getActivity());

                mapView.onCreate(dialog.onSaveInstanceState());
                mapView.onResume();
                mapView.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        gMap = googleMap;
                        final boolean circle_exist=false;
                        if(group.getGroupcoordinates()!=null){
                          if(!group.getGroupcoordinates().isEmpty()){
                            LatLng lng = new LatLng(Double.parseDouble(group.getGroupcoordinates().split(",")[0]),Double.parseDouble(group.getGroupcoordinates().split(",")[1]));
                            gMap.addCircle(new CircleOptions().fillColor(0x42AB2B).radius(450).clickable(true).center(lng));
                            gMap.addMarker(new MarkerOptions().title("ваша группа сейчас здесь").position(lng).draggable(false).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_flag)));}
                        }
                        if(created!=null&&group.getGroupcoordinates()==null)
                        {
                            gMap.addCircle(new CircleOptions().fillColor(0x42AB2B).radius(450).clickable(true).center(created));
                            gMap.addMarker(new MarkerOptions().title("ваша группа сейчас здесь").position(created).draggable(false).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_flag)));}
                        gMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(47.236361,39.715731)));
                        gMap.moveCamera(CameraUpdateFactory.zoomTo(12));
                        if (ActivityCompat.checkSelfPermission(view.getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(view.getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        }
                        gMap.setMyLocationEnabled(true);
                        gMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                            @Override
                            public void onMapLongClick(LatLng latLng) {
                               gMap.clear();
                               Circle circle =gMap.addCircle(new CircleOptions().center(latLng).radius(450).clickable(true).fillColor(0x220000FF));
                              // gMap.addCircle(new CircleOptions().center(latLng).radius(450).clickable(true).fillColor(0x220000FF));

                               gMap.addMarker(new MarkerOptions().title("место вашей группы?").position(latLng).draggable(false).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_flag)));
                               groupLatLng=latLng;



                            }

                        });



                    }
                });
               ImageButton dialogCheck = (ImageButton)dialog.findViewById(R.id.dialogCheck);
               dialogCheck.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       if(groupLatLng!=null) {
                           //String latlngForServer=Double.toString(groupLatLng.latitude)+","+Double.toString(groupLatLng.longitude);
                           //String key = v.getContext().getSharedPreferences(MainActivity.APP_PREFERENCES,Context.MODE_PRIVATE).getString("groupPassword","");
                           // try{
                           // sendCoordinates.start();}catch (Exception e){Toast.makeText(getActivity(),"Try again",Toast.LENGTH_LONG).show();}
                           if (groupLatLng != null) {
                               if(group!=null){
                                   DatabaseReference reference =db.getReference("Groups").child(key).child("Coordinates");
                                   reference.setValue(groupLatLng);
                                   group.setGroupcoordinates((String.valueOf(groupLatLng.latitude)+","+String.valueOf(groupLatLng.longitude)));}
                               created=groupLatLng;
                               String latlngForServer = Double.toString(groupLatLng.latitude) + "," + Double.toString(groupLatLng.longitude);
                               DatabaseReference reference =db.getReference("Groups").child(key).child("Coordinates");
                               reference.setValue(latlngForServer);
                               dialog.cancel();


                           }
                       }
                   }
               });

            }
        });
    }
    class sendCoordinates extends Thread
    {

        @Override
        public void run()
        {
            Retrofit retrofit = new Retrofit.Builder().baseUrl(MainActivity.TEST_SERVER).addConverterFactory(GsonConverterFactory.create()).build();
            ServerSetCoordinates setCoordinates = retrofit.create(ServerSetCoordinates.class);
            if(groupLatLng!=null){
            String latlngForServer=Double.toString(groupLatLng.latitude)+","+Double.toString(groupLatLng.longitude);
            testString="";
           // String key = v.getContext().getSharedPreferences(MainActivity.APP_PREFERENCES,Context.MODE_PRIVATE).getString("groupPassword","");
            Call<ResponseBody>call = setCoordinates.setCoordinates(key,latlngForServer);
                try {
                    Response<ResponseBody>response= call.execute();
                    if(response.isSuccessful()){
                        testString=response.body().string().toString();}
                        else{testString="hi therre";}

                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(!testString.equals(""))
                {activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity,"коорд:"+testString,Toast.LENGTH_LONG).show();
                    }
                });

                }

            }

        }
    }


    @Override
    public void listener(HashMap<String, Volonteer> vGroup) {
      groupView.setAdapter(new GroupAdapter(v.getContext(),vGroup));

    }
    private void test()
    {

    }

    private void firstGroupTake() {
        viewHolder = (ViewGroup) v.findViewById(R.id.groupFromStart);
        viewHolder.removeAllViews();
        showGroup = LayoutInflater.from(v.getContext()).inflate(R.layout.group_show, null);
        viewHolder.addView(showGroup);
        groupView = (ListView) v.findViewById(R.id.groupView);
        // refresh=(Button)v.findViewById(R.id.reset);}
    }
   /* class MyThread extends Thread {
        GroupListener listener;
        public MyThread(GroupListener listener){this.listener=listener;}
        synchronized
        @Override
        public void run() {
            for (int i =0;i<8;i++) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(MainActivity.TEST_SERVER)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
               // ServerDisplayGroup group = retrofit.create(ServerDisplayGroup.class);
                Call<List<Volonteer>> call =retrofit.create(GetMyVolonteers.class).getMyVolonteer(group.getGroupid());
                List<Volonteer>volonteers = new ArrayList<>();

                try {
                    Response<List<Volonteer>> response = call.execute();
                    volonteers=response.body();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if(volonteers!=null&&!volonteers.isEmpty())
                {
                    String s="";
                    final HashMap<String,Volonteer>vgroup=new HashMap<>();
                    for(Volonteer v:volonteers)
                    {
                        vgroup.put(v.getName(),v);
                    }
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(!preferences.contains("groupExist")){editor.putString("groupExist","yes");editor.commit();firstGroupTake();}
                            try { groupView.setAdapter(new GroupAdapter(v.getContext(),vgroup));}catch (Exception e){}
                        }
                    });}
                try {
                    sleep(15000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }



        }
    }*/

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onDetach() {
        super.onDetach();

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

