package com.example.koolguy.scroll;


import android.app.FragmentManager;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.Toast;


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

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbarId);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        return view;
    }

}
