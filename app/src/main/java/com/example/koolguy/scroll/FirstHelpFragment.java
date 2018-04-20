package com.example.koolguy.scroll;


import android.app.FragmentTransaction;
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
public class FirstHelpFragment extends ListFragment {
    View view;
    String[] FirstHelp;

    public FirstHelpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_first_help, container, false);
        Resources res=getResources();
        String[] FirstHelp = res.getStringArray(R.array.FirstHelp);
        ArrayAdapter<String> BookAdapter=new ArrayAdapter<String>(inflater.getContext(), android.R.layout.simple_list_item_1,FirstHelp);
        setListAdapter(BookAdapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    @Override

    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        if (position==0){
            extraNumbersClick();

        }
        else {
            Resources res = getResources();
            String[] FirstHelp = res.getStringArray(R.array.FirstHelp);
            String s = FirstHelp[position];
            Uri address = Uri.parse("https://www.google.ru/search?q=первая+помощь+при+" + s);
            Intent openlinkIntent = new Intent(Intent.ACTION_VIEW, address);
            startActivity(openlinkIntent);
        }
    }
    public void extraNumbersClick(){
        ExtraNumbersFragment mapFragment = new ExtraNumbersFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frames, mapFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
