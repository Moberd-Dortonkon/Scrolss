package com.example.koolguy.scroll.groups;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import com.example.koolguy.scroll.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChooseToDoLeader extends Fragment {
    Button creategroup;
    Button showGroups;
    View view;
    public ChooseToDoLeader() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view=inflater.inflate(R.layout.fragment_choose_to_do_leader, container, false);
        final MyGroups myGroups=new MyGroups();
        final GroupCreator groupCreator = new GroupCreator();
        groupCreator.setFragmentManager(getFragmentManager());
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        myGroups.setFragmentManager(getFragmentManager());
        creategroup = (Button)view.findViewById(R.id.choose_to_do_leader_creategroup);
        showGroups = (Button)view.findViewById(R.id.choosetodoleadergroups);
        showGroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getChildFragmentManager().beginTransaction().replace(R.id.choose_to_do_leader_framelayout,myGroups).disallowAddToBackStack().commit();
            }
        });

        creategroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getChildFragmentManager().beginTransaction().replace(R.id.choose_to_do_leader_framelayout,groupCreator).disallowAddToBackStack().commit();
            }
        });



        return view;
    }

}
