package com.example.koolguy.scroll;


import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Context;
import android.content.res.Resources;
import android.media.Image;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ScrollView;


/**
 * A simple {@link Fragment} subclass.
 */
public class HandBookFragment extends Fragment {
    MyMap map;
    View view;
    Activity activity;

    public HandBookFragment() {
        // Required empty public constructor
    }

    public static interface HandbookListener {
        void handBookClick(int position);

    }

    HandbookListener listener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (HandbookListener) activity;
        this.activity = activity;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_hand_book, container, false);
        map = new MyMap(activity,view.getContext());
        ScrollView sv = (ScrollView) view.findViewById(R.id.handbook);
        ImageButton schBtn=(ImageButton)view.findViewById(R.id.schdBtn);
        schBtn.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TeamList2 mapFragment = new TeamList2();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frames, mapFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        }));

        ImageButton mapBtn=(ImageButton)view.findViewById(R.id.mapBtn);
        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                map.makeMap();

            }
        });

        ImageButton helpBtn=(ImageButton) view.findViewById(R.id.helpBtn);
        helpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirstHelpFragment mapFragment = new FirstHelpFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frames, mapFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return view;
    }

}

//    @Override
//
//    public void onListItemClick(ListView l, View v, int position, long id) {
//        super.onListItemClick(l, v, position, id);
//        listener.handBookClick(position);
//
//
//    }
//}
