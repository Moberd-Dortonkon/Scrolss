package com.moberd.koolguy.scroll;


import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewMap extends Fragment implements OnMapReadyCallback {

    View view;
    MapView mapView;
    GoogleMap gMap;

    public NewMap() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_map, container, false);
        mapView=(MapView)view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        mapView.onResume();

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

      gMap=googleMap;
        if (ActivityCompat.checkSelfPermission(view.getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(view.getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }
        gMap.setMyLocationEnabled(true);
        Resources r=view.getResources();
        gMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(47.236361,39.715731)));
        gMap.moveCamera(CameraUpdateFactory.zoomTo(12));
        String[]names=r.getStringArray(R.array.place_names);
        String[]latlng=r.getStringArray(R.array.place_latlngs);
        String[]type=r.getStringArray(R.array.place_types);
        for(int i =0;i<names.length;i++)
        {
            LatLng lng=new LatLng(Double.parseDouble(latlng[i].split(", ")[0]),Double.parseDouble(latlng[i].split(", ")[1]));
            if(type[i].equals("eat"))gMap.addMarker(new MarkerOptions().title(names[i]).draggable(false).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_restaurant_black)).position(lng));
            if(type[i].equals("pass"))gMap.addMarker(new MarkerOptions().title(names[i]).draggable(false).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_tickets)).position(lng));
        }

    }
}
