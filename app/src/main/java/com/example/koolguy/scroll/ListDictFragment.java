package com.example.koolguy.scroll;


import android.app.ListFragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListDictFragment extends ListFragment {


    public ListDictFragment() {
        // Required empty public constructor
    }
    String[]phrase;
    View view;
    public void setI(String[]phrase){this.phrase = phrase;}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list_dict, container, false);
       /* if (i==0){
            Resources res=getResources();
            String[] dict = res.getStringArray(R.array.Hello);
            ArrayAdapter<String> dictAdapter = new ArrayAdapter<String>(inflater.getContext(), android.R.layout.simple_list_item_1, dict);
            setListAdapter(dictAdapter);
        }
        if (i==1){
            Resources res=getResources();
            String[] dict = res.getStringArray(R.array.How);
            ArrayAdapter<String> dictAdapter = new ArrayAdapter<String>(inflater.getContext(), android.R.layout.simple_list_item_1, dict);
            setListAdapter(dictAdapter);
        }
        if (i==2){
            Resources res=getResources();
            String[] dict = res.getStringArray(R.array.Help);
            ArrayAdapter<String> dictAdapter = new ArrayAdapter<String>(inflater.getContext(), android.R.layout.simple_list_item_1, dict);
            setListAdapter(dictAdapter);
        }
        if (i==3){
            Resources res=getResources();
            String[] dict = res.getStringArray(R.array.do_you_eng);
            ArrayAdapter<String> dictAdapter = new ArrayAdapter<String>(inflater.getContext(), android.R.layout.simple_list_item_1, dict);//и тут
            setListAdapter(dictAdapter);
        }
        if (i==4){
            Resources res=getResources();
            String[] dict = res.getStringArray(R.array.i_not_eng);
            ArrayAdapter<String> dictAdapter = new ArrayAdapter<String>(inflater.getContext(), android.R.layout.simple_list_item_1, dict);//и тут
            setListAdapter(dictAdapter);
        }
        if (i==5){
            Resources res=getResources();
            String[] dict = res.getStringArray(R.array.i_litll_eng);
            ArrayAdapter<String> dictAdapter = new ArrayAdapter<String>(inflater.getContext(), android.R.layout.simple_list_item_1, dict);//и тут
            setListAdapter(dictAdapter);
        }
        if (i==6){
            Resources res=getResources();
            String[] dict = res.getStringArray(R.array.pls_slower);
            ArrayAdapter<String> dictAdapter = new ArrayAdapter<String>(inflater.getContext(), android.R.layout.simple_list_item_1, dict);//и тут
            setListAdapter(dictAdapter);
        }
        if (i==7){
            Resources res=getResources();
            String[] dict = res.getStringArray(R.array.repeat_pls);
            ArrayAdapter<String> dictAdapter = new ArrayAdapter<String>(inflater.getContext(), android.R.layout.simple_list_item_1, dict);//и тут
            setListAdapter(dictAdapter);
        }
        if (i==8){
            Resources res=getResources();
        String[] dict = res.getStringArray(R.array.how_eng);
        ArrayAdapter<String> dictAdapter = new ArrayAdapter<String>(inflater.getContext(), android.R.layout.simple_list_item_1, dict);//и тут
        setListAdapter(dictAdapter);
    }*/

        ArrayAdapter<String>adapter=new ArrayAdapter<>(inflater.getContext(),android.R.layout.simple_list_item_1,phrase);
        setListAdapter(adapter);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

}
