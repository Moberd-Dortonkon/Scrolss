package com.example.koolguy.scroll;


import android.app.ListFragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class HandBookFragment extends ListFragment {

    View view;

    public HandBookFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_hand_book, container, false);
        Resources res=getResources();
        String[] HandBook = res.getStringArray(R.array.HandBook);
        ArrayAdapter<String> BookAdapter=new ArrayAdapter<String>(inflater.getContext(), android.R.layout.simple_list_item_1,HandBook);
        setListAdapter(BookAdapter);
        return inflater.inflate(R.layout.fragment_hand_book, container, false);
    }

}
