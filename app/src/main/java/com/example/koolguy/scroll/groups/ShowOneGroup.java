package com.example.koolguy.scroll.groups;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.koolguy.scroll.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowOneGroup extends Fragment {


    public ShowOneGroup() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_one_group, container, false);
    }

}