package com.moberd.koolguy.scroll.groups2;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.moberd.koolguy.scroll.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChooseToDo extends Fragment {

    public ChooseToDo() {
        // Required empty public constructor
    }

    Button createGroup;
    Button showGroups;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_choose_to_do, container, false);
        createGroup = view.findViewById(R.id.chooseTo_createGroup);
        createGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.frames,new MyGroups()).addToBackStack(null).commit();

            }
        });
        showGroups=view.findViewById(R.id.choose_to_jointogroup);
        showGroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.frames,new ShowAllGroups()).addToBackStack(null).commit();
            }
        });


        return view;
    }

}
