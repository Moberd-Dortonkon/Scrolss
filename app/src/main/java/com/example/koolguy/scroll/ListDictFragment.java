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
public class ListDictFragment extends ListFragment {


    public ListDictFragment() {
        // Required empty public constructor
    }
   int i;
    View view;
    public void setI(int position){this.i =position;}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list_dict, container, false);
        if (i==0){
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
            ArrayAdapter<String> dictAdapter = new ArrayAdapter<String>(inflater.getContext(), android.R.layout.simple_list_item_1, dict);//и тут
            setListAdapter(dictAdapter);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

}
