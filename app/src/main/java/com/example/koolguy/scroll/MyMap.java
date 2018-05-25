package com.example.koolguy.scroll;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.koolguy.scroll.Tools.ExpandableAdpter;
import com.example.koolguy.scroll.Tools.ExpandableListListener;
import com.example.koolguy.scroll.serverInterfaces.ServerGetCoordinates;
import com.example.koolguy.scroll.serverInterfaces.ServerSetCoordinates;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.internal.IGoogleMapDelegate;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyMap implements OnMapReadyCallback,ExpandableListListener,LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    GoogleMap googleMap;
    FragmentTransaction ft;
    Activity activity;
    Context context;
    View view;
    Location location;
    CameraPosition saveCamera;
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    MapFragment gmap;
    LatLng lng;
    int layout_id;
    Resources res;
    LatLng testlng;
    AlertDialog dialog;
    boolean firstEnable;
    ArrayList<LatLng> list;

    public MyMap(Activity activity, Context context,int layout_id) {
        this.activity = activity;
        this.context = context;
        this.layout_id = layout_id;
        res=context.getResources();
        MapsInitializer.initialize(context);
       // ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 9999);

    }

    public void makeMap() { //
        if (googleServicesAvaliable()) {
             gmap = new MapFragment();
            firstEnable=true;
            testlng = new LatLng(47.236274, 39.713726);
            ft = activity.getFragmentManager().beginTransaction();
            ft.replace(layout_id, gmap); //Единственное что не могу автоматизировать так это framelayout,его надо вручную ставить(((
            ft.addToBackStack(null);
            ft.commit();

            gmap.getMapAsync(this);
        }
    }


    public void doFlags()
    {
     String[]places=res.getStringArray(
             R.array.places);
     for(String place:places) {

         ArrayList<LatLng>list=coordinate(place);
         if(place.equals("center"))
         for (LatLng lng : list)
         {
             googleMap.addMarker(new MarkerOptions()
                      .title("Your Home,Master")
                     .draggable(false)
                     .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_home_black))
                     .position(lng));
         }
         if(place.equals("eatPlace"))
             for (LatLng lng : list)
             {
                 googleMap.addMarker(new MarkerOptions()
                         .title("There you can eat,Master")
                         .draggable(false)
                         .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_restaurant_black))
                         .position(lng));
             }
     }


    }

    private ArrayList<LatLng> coordinate(String name)
    {
        int id =res.getIdentifier(name,"array",context.getPackageName());

        String[] strlatlng = context.getResources().getStringArray(id);
        ArrayList<LatLng> latLngs= new ArrayList<>();
        for(String coord:strlatlng)
        {
            String[]base =coord.split("!4d");
            latLngs.add(new LatLng(Double.parseDouble(base[0]),Double.parseDouble(base[1])));

        }
        return latLngs;

    }
    public boolean googleServicesAvaliable() {
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int isAvailable = api.isGooglePlayServicesAvailable(context);
        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (api.isUserResolvableError(isAvailable)) {
            Dialog dialog = api.getErrorDialog(activity, isAvailable, 0);
            dialog.show();
        } else {

        }
        return false;
    }


    public void saveCamera() {
        if (googleMap != null) saveCamera = googleMap.getCameraPosition();
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        this.googleMap = googleMap;
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        googleMap. moveCamera(CameraUpdateFactory.zoomTo(12));
        buildGoogleApiClient();

        googleMap.setMyLocationEnabled(true);
        doFlags();
      /*  googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);

                LayoutInflater inflater = LayoutInflater.from(context);
                String[] places = res.getStringArray(R.array.Places);
                List<String> p= Arrays.asList(places);
                ArrayAdapter<String>placesAdapter=new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,places);
                View dialogView=inflater.inflate(R.layout.gmap_diaolg_icon,null);
                ExpandableListView listView = (ExpandableListView) dialogView.findViewById(R.id.mapsPlaces);
                listView.setAdapter(new ExpandableAdpter(context,MyMap.this));
                dialog=builder.setView(dialogView).setTitle("Hi").setCancelable(true).create();
                dialog.show();

                //builder.setView(dialogView)
            }
        });*/

      if(context.getSharedPreferences(MainActivity.APP_PREFERENCES,Context.MODE_PRIVATE).getString("role","").equals("leader"))
        {
            googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                @Override
                public void onMapLongClick(LatLng latLng) {
                    Retrofit retrofit = new Retrofit.Builder().baseUrl(MainActivity.SERVER).addConverterFactory(GsonConverterFactory.create()).build();
                    ServerSetCoordinates status = retrofit.create(ServerSetCoordinates.class);
                    googleMap.addMarker(new MarkerOptions()
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_flag))
                            .position(latLng));
                    String key =context.getSharedPreferences(MainActivity.APP_PREFERENCES,Context.MODE_PRIVATE).getString("groupPassword","");
                    String latlng=""+latLng.latitude+","+latLng.longitude;

                    Call<ResponseBody> call =status.setCoordinates(key,latlng);
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });

                }
            });
        }


    }





   protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {

            lng = new LatLng(location.getLatitude(), location.getLongitude());
            if(firstEnable){googleMap.moveCamera(CameraUpdateFactory.newLatLng(lng));firstEnable=false;}



        }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(500);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest,  this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void returnLatlngs(ArrayList<LatLng> latLngs) {
        dialog.dismiss();
        doFlags();
    }
}

