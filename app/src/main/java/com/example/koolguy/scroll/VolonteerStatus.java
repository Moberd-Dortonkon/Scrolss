package com.example.koolguy.scroll;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import com.example.koolguy.scroll.serverInterfaces.ServerCreateGroup;
import com.example.koolguy.scroll.serverInterfaces.ServerGetCoordinates;
import com.example.koolguy.scroll.serverInterfaces.ServerVolonteerStatus;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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
public class VolonteerStatus extends Fragment {
    View v;
    String lName;
    String name;
    Button come;
    Button eat;
    MapView mapView;
    GoogleMap map;
    String latlng;
    ImageView eaten;
    ImageView camen;
    Boolean boolEat;
    Boolean boolCome;


    public VolonteerStatus() {
        // Required empty public constructor
    }


    public void setlName(String lName) {
        this.lName = lName;
    }

    public void setName(String name) {
        this.name = name;
    }
    Button refresh;
    RefreshStatus ref;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ref=(RefreshStatus)activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_volonteer_status, container, false);
        come = (Button) v.findViewById(R.id.come);
      //  new getCoordinates().start();
        eat = (Button) v.findViewById(R.id.eat);
        eaten = (ImageView)v.findViewById(R.id.eaten);
        camen = (ImageView)v.findViewById(R.id.camen);
        final SharedPreferences sharedPreferences = v.getContext().getSharedPreferences("VolonteerStatus",Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor=sharedPreferences.edit();
        if(sharedPreferences.contains("come"))
        {
            boolCome=sharedPreferences.getBoolean("come",false);
            if(!boolCome){camen.setImageResource(R.drawable.ic_thumup);}
            if(boolCome){camen.setImageResource(R.drawable.ic_thumdown);
            }
        }
        if(!sharedPreferences.contains("come"))boolCome = true;
        if(sharedPreferences.contains("eat"))
        {
            boolEat=sharedPreferences.getBoolean("eat",true);
            if(!boolEat)eaten.setImageResource(R.drawable.ic_thumup);
            if(boolEat)eaten.setImageResource(R.drawable.ic_thumdown);

        }
        if(!sharedPreferences.contains("eat"))boolEat = true;


        come.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        });
        eat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!boolEat)
                {
                    eaten.setImageResource(R.drawable.ic_thumdown);

                }
                if(boolEat)
                {
                    eaten.setImageResource(R.drawable.ic_thumup);

                }

                boolEat = !boolEat;
                editor.putBoolean("eat",boolEat);
                editor.commit();
               new EatMyAsyncTask().execute("");

            }
        });
        refresh=(Button)v.findViewById(R.id.refrsh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.clear().commit();
                ref.refrsh();
            }
        });
        //mapView = (MapView)v.findViewById(R.id.mapView);
        //mapView.onCreate(savedInstanceState);
        //mapView.getMapAsync(this);
        return v;

    }
/*
    @Override
    public void onMapReady(GoogleMap googleMap) {
       // map = googleMap;
        Retrofit retrofit = new Retrofit.Builder().baseUrl(MainActivity.SERVER).addConverterFactory(GsonConverterFactory.create()).build();
        ServerGetCoordinates status = retrofit.create(ServerGetCoordinates.class);
        Call<ResponseBody> call =status.getCoordinates(lName);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful())
                {
                    if(latlng!=null)
                        if(!latlng.equals("not complete"))
                        {
                    String[]latlngs = latlng.split(",");
                    Double lat = Double.parseDouble(latlngs[0]);
                    Double lng = Double.parseDouble(latlngs[1]);
                   // map.addMarker(new MarkerOptions()
                     //       .position(new LatLng(lat,lng))
                     //       .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_flag)));
                        }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

        if(latlng!=null)
        if(!latlng.equals("not complete"))
        {
            String[]latlngs = latlng.split(",");
            Double lat = Double.parseDouble(latlngs[0]);
            Double lng = Double.parseDouble(latlngs[1]);
            map.addMarker(new MarkerOptions()
            .position(new LatLng(lat,lng))
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_flag)));

        }
        //mapView.onResume();


    }
*/

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
}