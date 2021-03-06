package com.moberd.koolguy.scroll.groups2;


import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moberd.koolguy.scroll.MainActivity;
import com.moberd.koolguy.scroll.R;
import com.moberd.koolguy.scroll.VolonteersInfo.Volonteer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowOneGroup extends Fragment implements OnMapReadyCallback {

    View view;
    ImageView imageView;
    ImageButton thum_up,thump_down;
    Button button;
    Date date;
    TextView didyoueat;
    MapView mapView;
    TextView textView;
    FrameLayout frameLayout;
    boolean booleat;
    GoogleMap map;
    String name;
    Retrofit retrofit;
    SharedPreferences group_pref;
    private SoundPool mySoundPool;
    private AssetManager myAssetManager;
    private int myButtonSound;
    private int myStreamID;
    String groupid;
    DatabaseReference reference;
    public ShowOneGroup() {
        // Required empty public constructor
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(47.249365, 39.696464)));
        map.moveCamera(CameraUpdateFactory.zoomTo(9));

        if (ActivityCompat.checkSelfPermission(view.getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(view.getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }
        map.setMyLocationEnabled(true);
        mapView.onResume();
    }

    public interface ShowOneGroupInformation
    {
        @GET("/display/me")
        Call<Volonteer>getInfo(@Query("groupid") String groupid, @Query("name") String name);
    }
    public interface SetEat
    {
        @GET("/set/eat")
        Call<ResponseBody>setEat(@Query("volonteerid") String volonteerid, @Query("groupid") String groupid, @Query("come") String come);
    }
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       // view=inflater.inflate(R.layout.fragment_choose_to_do_leader, container, false);
        //createSoundPool();
       // myAssetManager = view.getContext().getAssets();
//        myButtonSound=createSound("button_16.mp3");
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.fragment_show_one_group, container, false);
        //imageView=(ImageView)view.findViewById(R.id.show_onegroup_imageview);
        // imageView.setImageResource(R.drawable.ic_loading);
        retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(MainActivity.TEST_SERVER).build();
        //button=(Button)view.findViewById(R.id.show_onegroup_button);
        //button.setEnabled(false);
        didyoueat=(TextView)view.findViewById(R.id.did_you_eat);
        group_pref=view.getContext().getSharedPreferences(MainActivity.GROUP_PREFERENCES, Context.MODE_PRIVATE);
        frameLayout=(FrameLayout)view.findViewById(R.id.show_one_group);
        frameLayout.removeAllViews();
        frameLayout.addView(inflater.inflate(R.layout.progress_view,null));
        //frameLayout.addView(inflater.inflate(R.layout.group_eat_check_view,null));
       // initializedate(inflater);
        mapView=(MapView)view.findViewById(R.id.show_onegroup_mapview);
        textView=(TextView)view.findViewById(R.id.show_onegroup_textview);
        textView.setText("Connecting");
        FrameLayout chat =(FrameLayout) view.findViewById(R.id.bubble_chat);
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(),"lol",Toast.LENGTH_SHORT).show();
                getFragmentManager().beginTransaction().replace(R.id.frames,new GroupChat()).addToBackStack(null).commit();
            }
        });
        TextView textView = (TextView)view.findViewById(R.id.message_count);
        textView.setVisibility(View.INVISIBLE);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) view.findViewById(R.id.toolbarId);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(myButtonSound);
                getFragmentManager().beginTransaction().replace(R.id.frames,new ChooseToDoVolonteer()).addToBackStack(null).commit();
            }
        });

        groupid=view.getContext().getSharedPreferences(MainActivity.GROUP_PREFERENCES,Context.MODE_PRIVATE).getString("groupid","");
        name=view.getContext().getSharedPreferences(MainActivity.GROUP_PREFERENCES,Context.MODE_PRIVATE).getString("name","");
        reference = FirebaseDatabase.getInstance().getReference("Groups").child(groupid).child("Volonteers").child(name);
        /*reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               String eattime= dataSnapshot.child("EatTime").getValue(String.class);
               Boolean come =  dataSnapshot.child("Come").getValue(Boolean.class);
               String name = dataSnapshot.getKey();
               Volonteer volonteer = new Volonteer();
               volonteer.setEattime(eattime);
               volonteer.setCome(come);
               volonteer.setName(name);
                try {
                    initializedate(inflater,volonteer);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    initializedate(inflater,dataSnapshot.child("EatTime").getValue(String.class));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



       mapView.onCreate(savedInstanceState);
       mapView.getMapAsync(this);
       return view;
    }
    SimpleDateFormat format2;
    String dateFormated;
    private void initializedate(final LayoutInflater inflater,String eattime) throws ParseException {


        SimpleDateFormat format2 = new SimpleDateFormat("dd.MM.yyyy/HH:mm:ss");
        String fromServer = eattime;
        TextView textView = (TextView) view.findViewById(R.id.did_you_eat);
        Date date = Calendar.getInstance().getTime();
        dateFormated = format2.format(date);
        long test = format2.parse(dateFormated).getTime();
        long serverTime = format2.parse(fromServer).getTime();
        //Toast.makeText(getActivity(),""+(test-serverTime),Toast.LENGTH_SHORT).show();
        if (test - serverTime < 25200 * 1000) {
            frameLayout.removeAllViews();
            frameLayout.addView(inflater.inflate(R.layout.group_check_today_eated, null));
            didyoueat.setText("Вы сегодня кушали:3");
        } else {
            didyoueat.setText("Вы кушали?");
            frameLayout.removeAllViews();
            View view = inflater.inflate(R.layout.group_eat_check_view, null);
            thump_down = (ImageButton) view.findViewById(R.id.thum_down);
            thum_up = (ImageButton) view.findViewById(R.id.thum_up);
            thum_up.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    frameLayout.removeAllViews();
                    frameLayout.addView(inflater.inflate(R.layout.group_check_today_eated, null));
                    didyoueat.setText("Вы сегодня кушали:3");
                    reference.child("EatTime").setValue(dateFormated);

                }
            });

            thump_down.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "Покушайте и возвращайтесь", Toast.LENGTH_LONG).show();
                }
            });
            frameLayout.addView(view);

        }
    }
                    //Toast.makeText(getActivity(),""+dateFormated,Toast.LENGTH_LONG).show();


    private void firstTimeOpen()
    {
        final TextView textView=(TextView)view.findViewById(R.id.did_you_eat);
        final LayoutInflater inflater=LayoutInflater.from(view.getContext());
        textView.setText("Вы кушали?");
        frameLayout.removeAllViews();
        View view=inflater.inflate(R.layout.group_eat_check_view,null);
        thump_down=(ImageButton)view.findViewById(R.id.thum_down);
        thum_up=(ImageButton)view.findViewById(R.id.thum_up);
        thum_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frameLayout.addView(inflater.inflate(R.layout.group_check_today_eated,null));
                textView.setText("Вы сегодня кушали:3");
                Call<ResponseBody> setEat = retrofit.create(SetEat.class).setEat(group_pref.getString("leaderid", ""), group_pref.getString("groupid", ""),
                        "true");
                setEat.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }});
        thump_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Покушайте и возвращайтесь",Toast.LENGTH_LONG).show();
            }
        });


    }


    public void noGroup()
    {
        textView.setText("Лидер не назначил место");
    }
    public void initmap(float distance,LatLng latLng)
    {
        try{
        String show = String.format("%.0f",distance);
        if(distance<450)textView.setText("Вы на месте");
        if(distance>450)textView.setText("До вашей группы: "+show+" м.");
        map.clear();
        map.addCircle(new CircleOptions().fillColor(0x42AB2B).radius(450).clickable(true).center(latLng));
        map.addMarker(new MarkerOptions().title("ваша группа сейчас здесь").position(latLng).draggable(false).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_flag)));}catch (Exception e){}

    }

 /*   private void createSoundPool() {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        mySoundPool = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();
    }
*/
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
