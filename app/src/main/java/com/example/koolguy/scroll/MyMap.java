package com.example.koolguy.scroll;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.internal.IGoogleMapDelegate;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MyMap implements OnMapReadyCallback,LocationListener,GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener {

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
    boolean firstEnable;

    public MyMap(Activity activity, Context context) {
        this.activity = activity;
        this.context = context;

        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 9999);

    }

    public void makeMap(ArrayList<LatLng> list) { //
        if (googleServicesAvaliable()) {
             gmap = new MapFragment();
            firstEnable=true;
            ft = activity.getFragmentManager().beginTransaction();
            ft.replace(R.id.frames, gmap); //Единственное что не могу автоматизировать так это framelayout,его надо вручную ставить(((
            ft.addToBackStack(null);
            ft.commit();
            gmap.getMapAsync(this);
            if(!list.isEmpty())doFlags(list);
        }
    }


    public void doFlags(ArrayList<LatLng> list)
    {
     for(LatLng lng:list)
     {
         MarkerOptions mp = new MarkerOptions().draggable(false).position(lng).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_flag));
         googleMap.addMarker(mp);

     }

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
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        googleMap. moveCamera(CameraUpdateFactory.zoomTo(12));
        buildGoogleApiClient();

        googleMap.setMyLocationEnabled(true);



    }



    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
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
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}

