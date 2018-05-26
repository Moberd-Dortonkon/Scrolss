package com.example.koolguy.scroll;


import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.koolguy.scroll.serverInterfaces.ServerCreateGroup;
import com.example.koolguy.scroll.serverInterfaces.ServerGetCoordinates;
import com.example.koolguy.scroll.serverInterfaces.ServerVolonteerStatus;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

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
public class VolonteerStatus extends Fragment implements OnMapReadyCallback {
    View v;
    TextView textView;
    String lName;
    String name;
    Button come;
    Button eat;
    LatLng groupCircle;
    MapView mapView;
    GoogleMap map;
    LatLng user;
    String latlng;
    ImageView eaten;
    ImageView camen;
    Boolean boolEat;
    Boolean firstenable;
    Boolean boolCome;
    private float distance;
    private SoundPool mySoundPool;
    private AssetManager myAssetManager;
    private int myButtonSound;
    private int myStreamID;
    private LatLng lastGroupcircle;


    public VolonteerStatus() {
        // Required empty public constructor
    }


    public void setlName(String lName) {
        this.lName = lName;
    }

    public void setDistance(float distance, LatLng latLng, LatLng user) {
        this.distance = distance;
        if (distance < 400) camen.setImageResource(R.drawable.ic_thumup);
        if (distance > 400) camen.setImageResource(R.drawable.ic_thumdown);
        groupCircle = latLng;
        if (groupCircle!=lastGroupcircle) {
            map.clear();
            map.addCircle(new CircleOptions().center(groupCircle).radius(450).clickable(true).fillColor(0x220000FF));
            map.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_flag)).position(groupCircle).title("Ваша группа"));
            lastGroupcircle=groupCircle;
        }

        this.user = user;
        if(firstenable) {
            map.moveCamera(CameraUpdateFactory.newLatLng(user));
            firstenable=false;
        }
        if(distance>450)
        {
        float realDistance=distance-450;
        textView.setText("До вашей группы:" + String.format("%d",(long)realDistance) + " м.");
        }
        if(distance<450)
        {
         textView.setText("Вы на месте");
        }



    }

    public void setName(String name) {
        this.name = name;
    }

    Button refresh;
    RefreshStatus ref;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ref = (RefreshStatus) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_volonteer_status, container, false);

        createSoundPool();
        myAssetManager = getActivity().getAssets();
        myButtonSound = createSound("button_16.mp3");

        firstenable=true;
        come = (Button) v.findViewById(R.id.come);
        //  new getCoordinates().start();
        eat = (Button) v.findViewById(R.id.eat);
        eaten = (ImageView) v.findViewById(R.id.eaten);
        textView = (TextView) v.findViewById(R.id.distance);
        camen = (ImageView) v.findViewById(R.id.camen);
        Button calendar = (Button) v.findViewById(R.id.calendarButton);
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSound(myButtonSound);
                CalendarFragment calendarFragment = new CalendarFragment();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frames, new CalendarFragment()).commit();

            }
        });
        if (distance < 400) camen.setImageResource(R.drawable.ic_thumup);
        if (distance > 400) camen.setImageResource(R.drawable.ic_thumdown);
        final SharedPreferences sharedPreferences = v.getContext().getSharedPreferences("VolonteerStatus", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
       /* if(sharedPreferences.contains("come"))
        {
            boolCome=sharedPreferences.getBoolean("come",false);
            if(!boolCome){camen.setImageResource(R.drawable.ic_thumup);}
            if(boolCome){camen.setImageResource(R.drawable.ic_thumdown);
            }
        }*/
        //if(!sharedPreferences.contains("come"))boolCome = true;
        if (sharedPreferences.contains("eat")) {
            boolEat = sharedPreferences.getBoolean("eat", true);
            if (!boolEat) eaten.setImageResource(R.drawable.ic_thumup);
            if (boolEat) eaten.setImageResource(R.drawable.ic_thumdown);

        }
        if (!sharedPreferences.contains("eat")) boolEat = true;


        /*come.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSound(myButtonSound);
                if(!boolCome)
                {
                    camen.setImageResource(R.drawable.ic_thumdown);


                }
                if(boolCome)
                {
                    camen.setImageResource(R.drawable.ic_thumup);

                }

                boolCome = !boolCome;
                editor.putBoolean("come",boolCome);
                editor.commit();

              new ComeMyAsyncTask().execute("");

            }
        });*/
        eat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSound(myButtonSound);
                if (!boolEat) {
                    eaten.setImageResource(R.drawable.ic_thumdown);

                }
                if (boolEat) {
                    eaten.setImageResource(R.drawable.ic_thumup);

                }

                boolEat = !boolEat;
                editor.putBoolean("eat", boolEat);
                editor.commit();
                new EatMyAsyncTask().execute("");

            }
        });
        refresh = (Button) v.findViewById(R.id.refrsh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSound(myButtonSound);
                editor.clear().commit();
                ref.refrsh();
            }
        });
        mapView = (MapView) v.findViewById(R.id.volonteerMap);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        return v;

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(47.249365, 39.696464)));
        map.moveCamera(CameraUpdateFactory.zoomTo(12));
        if (ActivityCompat.checkSelfPermission(v.getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(v.getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }
        map.setMyLocationEnabled(true);
       // map.moveCamera(CameraUpdateFactory.newLatLngZoom(user,12));
       // if(groupCircle!=null) {
         //   map.addCircle(new CircleOptions().center(groupCircle).radius(450).clickable(true).fillColor(0x220000FF));
           // map.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_flag)).position(groupCircle).title("Ваша группа"));
      //  }
        mapView.onResume();


    }


    class getCoordinates extends Thread
    {
        @Override
        public void run()
        {

            Retrofit retrofit = new Retrofit.Builder().baseUrl(MainActivity.SERVER).addConverterFactory(GsonConverterFactory.create()).build();
            ServerGetCoordinates status = retrofit.create(ServerGetCoordinates.class);
            String key =lName;
            Call<ResponseBody>call =status.getCoordinates(key);
            try {
                Response<ResponseBody>response=call.execute();
                latlng=response.body().string();


            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }
    class ComeMyAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {


            Retrofit retrofit = new Retrofit.Builder().baseUrl(MainActivity.SERVER).addConverterFactory(GsonConverterFactory.create()).build();
            ServerVolonteerStatus status = retrofit.create(ServerVolonteerStatus.class);
            String servName = name;
            String servlName = lName;

            Call<String> call = status.volonteerStatus("come", servlName, servName);
            try {
                Response<String> response = call.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

    }
    class EatMyAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {


            Retrofit retrofit = new Retrofit.Builder().baseUrl(MainActivity.SERVER).addConverterFactory(GsonConverterFactory.create()).build();
            ServerVolonteerStatus status = retrofit.create(ServerVolonteerStatus.class);
            String servName = name;
            String servlName = lName;

            Call<String> call = status.volonteerStatus("eat", servlName, servName);
            try {
                Response<String> response = call.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //save name

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