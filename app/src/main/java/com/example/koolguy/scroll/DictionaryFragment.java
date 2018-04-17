package com.example.koolguy.scroll;


import android.app.ListActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DictionaryFragment extends Fragment {

    public DictionaryFragment() {
        // Required empty public constructor
    }



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Resources res=getResources();
        String[] dict = res.getStringArray(R.array.dictionary);
        ArrayAdapter<String> dictAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,dict);
        ListView listView = (ListView) findViewById(R.id.ListDict);
        listView.setAdapter(dictAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent=new Intent(view.getContext(),ListActivity.class);
                intent.putExtra("bbb",position);
                startActivityForResult(intent, 0);



            }
        });







        return inflater.inflate(R.layout.fragment_dictionary, container, false);
    }

}
