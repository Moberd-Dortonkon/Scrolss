package com.example.koolguy.scroll;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class Check extends Fragment {
   Button butt;
   TextView textView;

    static interface Listener
    {
        void click();
    }
    private Listener list;
    public Check() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.list = (Listener) activity;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_check, container, false);
        butt = view.findViewById(R.id.button2);
        textView = view.findViewById(R.id.text);
        butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"I wor",Toast.LENGTH_SHORT).show();
                list.click();
            }
        });
        return inflater.inflate(R.layout.fragment_check, container, false);
    }


}
