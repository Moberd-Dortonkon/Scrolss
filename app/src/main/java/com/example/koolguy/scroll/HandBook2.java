package com.example.koolguy.scroll;


import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ScrollView;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class HandBook2 extends Fragment {
    View view;
    Activity activity;
    MyMap map;
    HandBookFragment.HandbookListener listener;
    String[] FirstHelp;
    ListView listView;

    public HandBook2() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_hand_book2, container, false);
        Resources resources = view.getResources();
        map = new MyMap(activity,view.getContext(),resources.getIdentifier("frames","id",view.getContext().getPackageName()));
        ScrollView sv = (ScrollView) view.findViewById(R.id.handbook);

        Resources res=getResources();
        String[] FirstHelp = res.getStringArray(R.array.FirstHelp);
        ListView listView = (ListView)view.findViewById(R.id.lv);
        ArrayAdapter<String> BookAdapter=new ArrayAdapter<String>(inflater.getContext(), android.R.layout.simple_list_item_1,FirstHelp);
        listView.setAdapter(BookAdapter);

        return view;
    }

}
