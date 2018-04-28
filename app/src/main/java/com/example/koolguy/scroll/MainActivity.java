package com.example.koolguy.scroll;

import android.Manifest;
import android.app.Dialog;
import android.app.Fragment;
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
import com.example.koolguy.scroll.VolonteersInfo.Volonteer;
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

import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements Check.Listener,DictionaryFragment.DictionaryListener,HandBookFragment.HandbookListener,LeaderCreateGroup.LeaderCreateGroupNext,ChooseStatus.ChooseStatusClick{
    HorizontalScrollMenuView menu;
    TextView textView;
    MyMap map;
    public static final String SERVER= "https://immense-wave-82247.herokuapp.com";
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
       LeaderCreateGroup mapFragment = new LeaderCreateGroup();
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
                        handBookFragment(); //создать метод который вызывает справочник
                        break;
                    case 1:
                         map.makeMap(new ArrayList<LatLng>());break;
                    case 2:
                        anotherFragment(); // для сервера
                        break;
                    case 3:
                        dictionaryFragment(); //Создать метод который вызывыет словарь
                        break;
                }

            }
        });

    }

    private void dictionaryFragment() //создание
    {
       DictionaryFragment mapFragment = new DictionaryFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frames, mapFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void handBookFragment() //создание
    {
        HandBookFragment mapFragment = new HandBookFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frames, mapFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    @Override

    public void click() {
        ArrayList<LatLng>latLngs = new ArrayList<LatLng>();
        latLngs.add(new LatLng(47.277424, 39.707281));
        latLngs.add(new LatLng(47.213866, 39.711912));
        latLngs.add(new LatLng(47.219229, 39.704359));
        latLngs.add(new LatLng(47.221792, 39.723543));
        latLngs.add(new LatLng(47.225965, 39.746229));
        latLngs.add(new LatLng(47.226343, 39.739019));
        map.makeMap(latLngs);

    }


    public void DictionaryClick(int position) {
        ListDictFragment dict = new ListDictFragment();
        dict.setI(position);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frames, dict); //если
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void extraNumbersClick(){
        ExtraNumbersFragment mapFragment = new ExtraNumbersFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frames, mapFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    @Override
    public void handBookClick(int position) {
        if(position==0){

        }
        if (position==1)
        {
            FirstHelpFragment mapFragment = new FirstHelpFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.frames, mapFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        if (position==2){
            TeamListFragment mapFragment=new TeamListFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.frames, mapFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }


    @Override
    public void leaderCreateClick() {
        LeaderGroup mapFragment = new LeaderGroup();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frames, mapFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void chooseStatusClick(int id) {
        switch (id)
        {
            case 1:   
                LeaderCreateGroup mapFragment = new LeaderCreateGroup();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frames, mapFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;


            case 2:
                VolonteerStatus volonteerStatus = new VolonteerStatus();
                FragmentTransaction trans = getFragmentManager().beginTransaction();
                trans.replace(R.id.frames, volonteerStatus);
                trans.addToBackStack(null);
                trans.commit();
                break;
            
        }
    }
}
