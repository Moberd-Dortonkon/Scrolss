package com.example.koolguy.scroll;


import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ListActivity;
import android.app.ListFragment;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;


/**
 * A simple {@link Fragment} subclass.
 */
public class DictionaryFragment extends ListFragment { //Есть встроенные лист фрагмент.Я добавил метод нажатия
    View view;
    private DictionaryFragment.DictionaryListener list;

    public static interface DictionaryListener{
        void DictionaryClick(int position);
    }


    public void onAttach(Activity activity) {
        super.onAttach(activity);
        list= (DictionaryListener) activity;
    }

    public DictionaryFragment() {
        // Required empty public constructor
    }

    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_dictionary, container, false);
        Resources res=getResources();
        final String[] phrases = res.getStringArray(R.array.dictionary);
        ArrayAdapter<String> dictAdapter=new ArrayAdapter<String>(inflater.getContext(), android.R.layout.simple_list_item_1, phrases);
        setListAdapter(dictAdapter);
//        Arrays.sort(phrases);
//        List<String> list = Arrays.asList(phrases);
//        Set<String> set = new TreeSet<String>(list);
//        Toolbar toolbar= (Toolbar)view.findViewById(R.id.toolbar);
//        final EditText editText=(EditText)view.findViewById(R.id.searchEditText);
//        ImageButton search=(ImageButton)view.findViewById(R.id.search);
//        search.setOnClickListener((new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String s=editText.getText().toString();
//                Arrays.sort(phrases);
//                ArrayAdapter<String> dictAdapter=new ArrayAdapter<String>(inflater.getContext(), android.R.layout.simple_list_item_1, phrases);
//                setListAdapter(dictAdapter);
//            }
//        }));
        return view;
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        list.DictionaryClick(position);

    }



}
