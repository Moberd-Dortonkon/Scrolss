package com.example.koolguy.scroll;


import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.os.CountDownTimer;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.koolguy.scroll.VolonteersInfo.Volonteer;
import com.example.koolguy.scroll.serverInterfaces.ServerCreateGroup;
import com.example.koolguy.scroll.serverInterfaces.ServerGetCoordinates;
import com.example.koolguy.scroll.serverInterfaces.ServerGetMyInformation;
import com.example.koolguy.scroll.serverInterfaces.ServerVolonteerStatus;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

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
    Boolean rightCheck;
    LatLng user;
    String latlng;
    ImageView eaten;
    ImageView camen;
    Boolean boolEat;
    Boolean firstenable;
    Volonteer meVolonteer;
    Dialog dialog;
    String fromServer;
    Boolean boolCome;
    Boolean firstEatSet;
    private float distance;
    private SoundPool mySoundPool;
    private AssetManager myAssetManager;
    private int myButtonSound;
    private int myStreamID;
    private LatLng lastGroupcircle;
    private Gson gson;
    public static String EAT_PREFERENCES;
   // private Initation initation;

    public VolonteerStatus() {
        // Required empty public constructor
    }

    public void setBoolEat(Boolean boolEat) {
        this.boolEat = boolEat;

    }

    public void setBoolCome(Boolean boolCome) {
        this.boolCome = boolCome;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public void setDistance(float distance, LatLng latLng, LatLng user) {
        this.distance = distance;
//        if (distance < 400) camen.setImageResource(R.drawable.ic_thumup);
  //      if (distance > 400) camen.setImageResource(R.drawable.ic_thumdown);
        groupCircle = latLng;
        if (groupCircle!=lastGroupcircle) {
            try {

                map.clear();
            map.addCircle(new CircleOptions().center(groupCircle).radius(450).clickable(true).fillColor(0x220000FF));
            map.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_flag)).position(groupCircle).title("Ваша группа"));}catch (Exception e){}
            lastGroupcircle=groupCircle;
        }

        this.user = user;
        if(firstenable) {
            try {


            map.moveCamera(CameraUpdateFactory.newLatLng(user));}catch (Exception e){}
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
    public void onStart() {
        super.onStart();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_volonteer_status, container, false);

        createSoundPool();
        gson = new Gson();

        myAssetManager = getActivity().getAssets();
        myButtonSound = createSound("button_16.mp3");
        boolEat=false;

        firstenable=true;
        firstEatSet=true;
      //  come = (Button) v.findViewById(R.id.come);
        //  new getCoordinates().start();
        eat = (Button) v.findViewById(R.id.eat);
        eaten = (ImageView) v.findViewById(R.id.eaten);
      //  camen = (ImageView) v.findViewById(R.id.camen);
        eaten.setImageResource(R.drawable.ic_loading);
      //  camen.setImageResource(R.drawable.ic_loading);
        textView = (TextView) v.findViewById(R.id.distance);
        textView.setText("Connecting...");
       /* if(!v.getContext().getSharedPreferences(EAT_PREFERENCES,Context.MODE_PRIVATE).contains("eat"))boolEat=false;
        if(v.getContext().getSharedPreferences(EAT_PREFERENCES,Context.MODE_PRIVATE).contains("eat"))
        {
          if(v.getContext().getSharedPreferences(EAT_PREFERENCES,Context.MODE_PRIVATE).getBoolean("eat",false)){eaten.setImageResource(R.drawable.ic_thumup);boolEat = false; }
          else {eaten.setImageResource(R.drawable.ic_thumdown);boolEat=true;}
        }*/
        ImageButton imageButton = (ImageButton) v.findViewById(R.id.mapButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.volonteer_status_map);
                Window w = dialog.getWindow();
                w.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                dialog.show();
                MapView mapView = (MapView) dialog.findViewById(R.id.volonteerStatusMap);
                MapsInitializer.initialize(getActivity());
                mapView.onCreate(dialog.onSaveInstanceState());
                mapView.onResume();
                mapView.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        GoogleMap dialogMap = googleMap;
                        dialogMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(47.249365, 39.696464)));
                        dialogMap.moveCamera(CameraUpdateFactory.zoomTo(10));
                        map.addCircle(new CircleOptions().center(groupCircle).radius(450).clickable(true).fillColor(0x220000FF));
                        map.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_flag)).position(groupCircle).title("Ваша группа"));
                        if (ActivityCompat.checkSelfPermission(v.getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                                && ActivityCompat.checkSelfPermission(v.getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        }
                        map.setMyLocationEnabled(true);
                    }
                });

            }
        });
        eat.setEnabled(false);
        eat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSound(myButtonSound);
               // if(!boolEat){eaten.setImageResource(R.drawable.ic_thumup);}
               // else eaten.setImageResource(R.drawable.ic_thumdown);
               // boolEat=!boolEat;
                boolEat=true;
                if((Integer)eaten.getTag()==R.drawable.ic_thumup){rightCheck=true;}
                if ((Integer)eaten.getTag()==R.drawable.ic_thumdown){rightCheck=false;}
                eat.setEnabled(false);
                eaten.setImageResource(R.drawable.ic_loading);
                new EatMyAsyncTask().execute("");

            }
        });
        refresh = (Button) v.findViewById(R.id.refrsh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSound(myButtonSound);
                ref.refrsh();
            }
        });
        mapView = (MapView) v.findViewById(R.id.volonteerMap);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);


        return v;

    }

    public void initMe(float distance, LatLng latLng, LatLng user) {
            Retrofit retrofit = new Retrofit.Builder().baseUrl(MainActivity.SERVER).addConverterFactory(GsonConverterFactory.create()).build();
            ServerGetMyInformation doIt = retrofit.create(ServerGetMyInformation.class);
            String servName = name;
            String servlName = lName;

            Call<ResponseBody> call = doIt.getMyInfromation(servlName, servName);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        try {
                            fromServer = response.body().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        meVolonteer = gson.fromJson(fromServer, Volonteer.class);
                        firstEatSet=false;

                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
            if (meVolonteer != null) {
                if(!boolEat) {
                    if (meVolonteer.isEat()) {
                        eaten.setImageResource(R.drawable.ic_thumup);
                        //boolEat = false;
                        eaten.setTag(R.drawable.ic_thumup);
                        eat.setEnabled(true);
                        //v.getContext().getSharedPreferences(EAT_PREFERENCES,Context.MODE_PRIVATE).edit().putBoolean("eat",false).commit();
                    }
                    if (!meVolonteer.isEat()) {
                        eaten.setImageResource(R.drawable.ic_thumdown);
                        eaten.setTag(R.drawable.ic_thumdown);
                        // boolEat = true;
                        eat.setEnabled(true);
                        //v.getContext().getSharedPreferences(EAT_PREFERENCES,Context.MODE_PRIVATE).edit().putBoolean("eat",true).commit();
                    }
                }
                if(boolEat)
                {
                    if (meVolonteer.isEat()&&!rightCheck) {
                        eaten.setImageResource(R.drawable.ic_thumup);
                        eaten.setTag(R.drawable.ic_thumup);
                        //boolEat = false;
                        eat.setEnabled(true);
                        //v.getContext().getSharedPreferences(EAT_PREFERENCES,Context.MODE_PRIVATE).edit().putBoolean("eat",false).commit();
                    }
                    if (!meVolonteer.isEat()&&rightCheck) {
                        eaten.setImageResource(R.drawable.ic_thumdown);
                        eaten.setTag(R.drawable.ic_thumdown);
                        // boolEat = true;
                        eat.setEnabled(true);
                        //v.getContext().getSharedPreferences(EAT_PREFERENCES,Context.MODE_PRIVATE).edit().putBoolean("eat",true).commit();
                    }
                }
            }
            setDistance(distance,latLng,user);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(47.249365, 39.696464)));
        map.moveCamera(CameraUpdateFactory.zoomTo(9));
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
    class EatThread extends Thread
    {
        @Override
        public void run()
        {
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