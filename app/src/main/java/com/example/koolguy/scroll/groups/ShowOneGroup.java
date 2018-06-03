package com.example.koolguy.scroll.groups;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.koolguy.scroll.R;
import com.google.android.gms.maps.MapView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowOneGroup extends Fragment {

    View view;
    ImageView imageView;
    Button button;
    MapView mapView;
    TextView textView;
    public ShowOneGroup() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.fragment_show_one_group, container, false);
        imageView=(ImageView)view.findViewById(R.id.show_onegroup_imageview);
        button=(Button)view.findViewById(R.id.show_onegroup_button);
        mapView=(MapView)view.findViewById(R.id.show_onegroup_mapview);
        textView=(TextView)view.findViewById(R.id.show_onegroup_textview);
        textView.setText("Connecting");
        imageView.setImageResource(R.drawable.ic_loading);

        return view;
    }

}
