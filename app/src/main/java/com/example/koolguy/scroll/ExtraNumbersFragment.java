package com.example.koolguy.scroll;


import android.app.ListFragment;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
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
public class ExtraNumbersFragment extends ListFragment {

View view;
    public ExtraNumbersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_extra_numbers, container, false);
        Resources res=getResources();
        String[] ExtraNumbers = res.getStringArray(R.array.ExtraNumbers);
        ArrayAdapter<String> BookAdapter=new ArrayAdapter<String>(inflater.getContext(), android.R.layout.simple_list_item_1,ExtraNumbers);
        setListAdapter(BookAdapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
            Resources res = getResources();
            String[] ExtraNumbers = res.getStringArray(R.array.ExtraNumbers);
            String s = ExtraNumbers[position];
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + s));
            startActivity(intent);
        }





}

