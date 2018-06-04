package com.example.koolguy.scroll.groups;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.koolguy.scroll.R;

public class ChooseToDoVolonteer extends Fragment {


    View view;
    Button show_groups;
    Button conntect_directly;
    public ChooseToDoVolonteer() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_chooset_to_do__volonteer, container, false);

        show_groups=(Button)view.findViewById(R.id.choose_to_do_showgroups);
        conntect_directly=(Button)view.findViewById(R.id.chooset_to_do_coonnect_directly);
        show_groups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getChildFragmentManager().beginTransaction().replace(R.id.choose_to_do_volonteer_framelayout,new ShowAllGroups()).disallowAddToBackStack().commit();
            }
        });
        conntect_directly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getChildFragmentManager().beginTransaction().replace(R.id.choose_to_do_volonteer_framelayout,new ConnectDirectly()).disallowAddToBackStack().commit();
            }
        });
        return view;
    }

}
