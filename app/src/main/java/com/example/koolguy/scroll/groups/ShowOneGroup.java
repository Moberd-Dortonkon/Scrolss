package com.example.koolguy.scroll.groups;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.app.Fragment;
import android.os.TestLooperManager;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.koolguy.scroll.MainActivity;
import com.example.koolguy.scroll.R;
import com.example.koolguy.scroll.VolonteersInfo.Volonteer;
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
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowOneGroup extends Fragment implements OnMapReadyCallback {

    View view;
    ImageView imageView;
    Button button;
    MapView mapView;
    TextView textView;
    boolean booleat;
    GoogleMap map;
    Retrofit retrofit;
    SharedPreferences group_pref;
    public ShowOneGroup() {
        // Required empty public constructor
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
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
        Call<Volonteer>getInfo(@Query("groupid")String groupid,@Query("name")String name);
    }
    public interface SetEat
    {
        @GET("/set/eat")
        Call<ResponseBody>setEat(@Query("volonteerid")String volonteerid,@Query("groupid")String groupid,@Query("come")String come);
    }
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.fragment_show_one_group, container, false);
        imageView=(ImageView)view.findViewById(R.id.show_onegroup_imageview);
        button=(Button)view.findViewById(R.id.show_onegroup_button);
        button.setEnabled(false);
        mapView=(MapView)view.findViewById(R.id.show_onegroup_mapview);
        textView=(TextView)view.findViewById(R.id.show_onegroup_textview);
        textView.setText("Connecting");
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) view.findViewById(R.id.toolbarId);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.frames,new ChooseToDoVolonteer()).addToBackStack(null).commit();
            }
        });
        imageView.setImageResource(R.drawable.ic_loading);
        group_pref=view.getContext().getSharedPreferences(MainActivity.GROUP_PREFERENCES, Context.MODE_PRIVATE);
        retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(MainActivity.TEST_SERVER).build();
        Call<Volonteer>call =retrofit.create(ShowOneGroupInformation.class).getInfo(group_pref.getString("groupid",""),group_pref.getString("leaderid",""));
        call.enqueue(new Callback<Volonteer>() {
            @Override
            public void onResponse(Call<Volonteer> call, Response<Volonteer> response) {
                if(response.isSuccessful())
                {
                    Volonteer volonteer=response.body();
                    button.setEnabled(true);
                    if(volonteer.isEat()){imageView.setImageResource(R.drawable.ic_thumup);booleat=true;}
                    if(!volonteer.isEat()){imageView.setImageResource(R.drawable.ic_thumdown);booleat=false;}
                }
            }

            @Override
            public void onFailure(Call<Volonteer> call, Throwable t) {

            }
        });
       button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               if(booleat)
               {    Call<ResponseBody>setEat=retrofit.create(SetEat.class).setEat(group_pref.getString("leaderid",""),group_pref.getString("groupid",""),
                       "false");
                   setEat.enqueue(new Callback<ResponseBody>() {
                       @Override
                       public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                           if(response.isSuccessful()) {
                               try {
                                   Toast.makeText(getActivity(),response.body().string(),Toast.LENGTH_SHORT);
                               } catch (IOException e) {
                                   e.printStackTrace();
                               }
                           }
                       }

                       @Override
                       public void onFailure(Call<ResponseBody> call, Throwable t) {

                       }
                   });
                   imageView.setImageResource(R.drawable.ic_thumdown);
               }
               if(!booleat)
               {
                   Call<ResponseBody>setEat=retrofit.create(SetEat.class).setEat(group_pref.getString("leaderid",""),group_pref.getString("groupid",""),
                           "true");
                   setEat.enqueue(new Callback<ResponseBody>() {
                       @Override
                       public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                           if(response.isSuccessful()) {
                               try {
                                   Toast.makeText(getActivity(),response.body().string(),Toast.LENGTH_SHORT);
                               } catch (IOException e) {
                                   e.printStackTrace();
                               }
                           }
                       }

                       @Override
                       public void onFailure(Call<ResponseBody> call, Throwable t) {

                       }
                   });
                   imageView.setImageResource(R.drawable.ic_thumup);
               }

               booleat=!booleat;
           }
       });
       mapView.onCreate(savedInstanceState);
       mapView.getMapAsync(this);
       return view;
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
        map.addCircle(new CircleOptions().fillColor(0x42AB2B).radius(450).clickable(true).center(latLng));
        map.addMarker(new MarkerOptions().title("ваша группа сейчас здесь").position(latLng).draggable(false).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_flag)));}catch (Exception e){}

    }


}
