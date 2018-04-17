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

        return inflater.inflate(R.layout.fragment_check, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        View view = getView();
        butt = (Button)view.findViewById(R.id.button2);
        textView = (TextView)view.findViewById(R.id.text);
        textView.setText("NotWork");
        butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                textView.setText("Works");
                list.click();
            }
        });
    }
}
