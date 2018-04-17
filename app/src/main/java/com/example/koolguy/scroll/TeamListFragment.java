package com.example.koolguy.scroll;


import android.os.Bundle;
import android.app.Fragment;
import android.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class TeamListFragment extends ListFragment {

    View view;

    public TeamListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_first_help, container, false);
        MyMatch[] matches=new MyMatch[1];
        matches[0]= new MyMatch("Бразилия","Швейцария", "17 Июня 21:00");
        MyMatchAdapter adapter=new MyMatchAdapter(inflater.getContext(), R.layout.adapter_item, matches);
        setListAdapter(adapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

}
