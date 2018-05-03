package com.example.koolguy.scroll;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChooseStatus extends Fragment {
    interface ChooseStatusClick
    {
        void chooseStatusClick(int id);

    }
    View v;
    Button volonteer,leader;
    public ChooseStatus() {
        // Required empty public constructor
    }
    ChooseStatusClick click;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        click=(ChooseStatusClick) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_choose_status, container, false);
        volonteer=(Button)v.findViewById(R.id.Volonteer);
        leader=(Button)v.findViewById(R.id.Leader);
        volonteer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click.chooseStatusClick(2);
            }
        });
        leader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click.chooseStatusClick(1);
            }
        });
        return v;
    }

}
