package com.example.koolguy.scroll;


import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ListActivity;
import android.app.ListFragment;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DictionaryFragment extends ListFragment { //Есть встроенные лист фрагмент.Я добавил метод нажатия

    public static interface DictionaryListener{
        void DictionaryClick(int position);
    }

    private DictionaryFragment.DictionaryListener list;

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        list = (DictionaryListener) activity;
    }

    public DictionaryFragment() {
        // Required empty public constructor
    }

    View view;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Resources res=getResources();
        String[] phrases = res.getStringArray(R.array.dictionary);
        ArrayAdapter<String> dictAdapter=new ArrayAdapter<String>(inflater.getContext(), android.R.layout.simple_list_item_1,phrases);

        setListAdapter(dictAdapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        list.DictionaryClick(position);

    }



}
