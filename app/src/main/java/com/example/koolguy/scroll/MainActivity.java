package com.example.koolguy.scroll;

import android.Manifest;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.darwindeveloper.horizontalscrollmenulibrary.custom_views.HorizontalScrollMenuView;
import com.darwindeveloper.horizontalscrollmenulibrary.extras.MenuItem;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements Check.Listener{
    HorizontalScrollMenuView menu;
    TextView textView;
    MyMap map;
    CameraPosition saveCamera;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        menu = (HorizontalScrollMenuView) findViewById(R.id.menu);
        textView = (TextView) findViewById(R.id.text);
        int i = 0;
         map = new MyMap(this,this);
        initMenu();



    }



    private void mateToast(int position) {
        Toast.makeText(this, "" + position, Toast.LENGTH_SHORT).show();
    }
    private void anotherFragment()
    {
        Check mapFragment = new Check();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frames, mapFragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }
    private void initMenu() {
        menu.addItem("Transcation", R.drawable.ic_book);
        menu.addItem("Map", R.drawable.ic_map);
        menu.addItem("Account", R.drawable.ic_account);
        menu.addItem("Support", R.drawable.ic_dictionary);
        menu.setOnHSMenuClickListener(new HorizontalScrollMenuView.OnHSMenuClickListener() {
            @Override
            public void onHSMClick(MenuItem menuItem, int position) {
                switch (position) {
                    case 0:
                        anotherFragment(); //создать метод который вызывает справочник
                        break;
                    case 1:
                         map.makeMap(new ArrayList<LatLng>());break;
                    case 2:

                        anotherFragment(); // для сервера
                        break;
                    case 3:

                        anotherFragment(); //Создать метод который вызывыет словарь
                        break;
                }

            }
        });

    }


    @Override
    public void click() {
        Toast.makeText(this,"Hello",Toast.LENGTH_SHORT).show();
        ArrayList<LatLng>latLngs = new ArrayList<LatLng>();
        latLngs.add(new LatLng(47.277424, 39.707281));
        latLngs.add(new LatLng(47.213866, 39.711912));
        latLngs.add(new LatLng(47.219229, 39.704359));
        latLngs.add(new LatLng(47.221792, 39.723543));
        latLngs.add(new LatLng(47.225965, 39.746229));
        latLngs.add(new LatLng(47.226343, 39.739019));
        map.makeMap(latLngs);

    }
}
