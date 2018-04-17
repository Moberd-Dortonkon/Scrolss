package com.example.koolguy.scroll;


import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
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
    public static interface HandbookListener
    {
        void handBookClick(int position);

    }
    HandbookListener listener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener =(HandbookListener)activity;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_hand_book, container, false);
        Resources res=getResources();
        String[] HandBook = res.getStringArray(R.array.HandBook);
        ArrayAdapter<String> BookAdapter=new ArrayAdapter<String>(inflater.getContext(), android.R.layout.simple_list_item_1,HandBook);
        setListAdapter(BookAdapter);
        return super.onCreateView(inflater, container, savedInstanceState); //ListFragment должен это возвращать
         }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        listener.handBookClick(position);


    }
}
