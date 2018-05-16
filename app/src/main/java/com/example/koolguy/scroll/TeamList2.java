package com.example.koolguy.scroll;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;


/**
 * A simple {@link Fragment} subclass.
 */
public class TeamList2 extends Fragment {
View view;

    public TeamList2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_team_list2, container, false);
        ScrollView s =(ScrollView)view.findViewById(R.id.teamlist);
        return view;
    }

}
