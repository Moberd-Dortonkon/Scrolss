package com.example.koolguy.scroll.Tools;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.koolguy.scroll.R;
import com.google.android.gms.maps.model.LatLng;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;



public class ExpandableAdpter extends BaseExpandableListAdapter {


    Context context;
    Activity activity;
    ExpandableListListener listListener;
    String[] main_dict;
     List<String>listData;
    String[] dictionary;
    HashMap<String,HashMap<String,List<String>>>map;
    public ExpandableAdpter(Context context,ExpandableListListener listListener) {
        this.context = context;



        this.listListener = listListener;
        Resources res = context.getResources();
        map = new HashMap<>();
         main_dict =res.getStringArray(R.array.dictionary_main);
        dictionary = res.getStringArray(R.array.dictionary);
        HashMap<String,List<String>> dictionarMap=new HashMap<>();

        for(int i = 0;i<dictionary.length;i++)
        {/*
             if(i<10)
             {
                 int phrase_id = res.getIdentifier("dictionary_"+Integer.toString(i),"array",context.getPackageName());
                 dictionarMap.put(dictionary[i], Arrays.asList(res.getStringArray(phrase_id)));
                 if(i==9){
                 map.put(main_dict[0],dictionarMap);
                 dictionarMap.clear();
                 }
             }
             if(i>9&&i<21)
             {
                 int phrase_id = res.getIdentifier("dictionary_"+Integer.toString(i),"array",context.getPackageName());
                 dictionarMap.put(dictionary[i], Arrays.asList(res.getStringArray(phrase_id)));
                 if(i==20){
                 map.put(main_dict[1],dictionarMap);
                 dictionarMap.clear();
                 }
             }
             if(i>20&&i<40)
             {  int phrase_id = res.getIdentifier("dictionary_"+Integer.toString(i),"array",context.getPackageName());
                 dictionarMap.put(dictionary[i], Arrays.asList(res.getStringArray(phrase_id)));
                 if(i==39){
                 map.put(main_dict[2],dictionarMap);
                 dictionarMap.clear();
                 }}
             if(i>39&&i<56)
             {  int phrase_id = res.getIdentifier("dictionary_"+Integer.toString(i),"array",context.getPackageName());
                 dictionarMap.put(dictionary[i], Arrays.asList(res.getStringArray(phrase_id)));
                 if (i == 5) {


                 map.put(main_dict[3],dictionarMap);
                 dictionarMap.clear();
                 }}
             if(i>55&&i<88)
             {  int phrase_id = res.getIdentifier("dictionary_"+Integer.toString(i),"array",context.getPackageName());
                 dictionarMap.put(dictionary[i], Arrays.asList(res.getStringArray(phrase_id)));
                 if(i==87){
                 map.put(main_dict[4],dictionarMap);
                 dictionarMap.clear();
                 }}*/
            int phrase_id = res.getIdentifier("dictionary_"+Integer.toString(i),"array",context.getPackageName());
            dictionarMap.put(dictionary[i], Arrays.asList(res.getStringArray(phrase_id)));

        }



    }

    @Override
    public int getGroupCount() {
        return main_dict.length;
    }

    @Override
    public int getChildrenCount(int i) {
        if(i==0){return 10;}
        if(i==1){return 21;}
        if(i==2)return 19;
        if(i==3)return  16;
        else return 32;

    }

    @Override
    public Object getGroup(int i) {
        return main_dict[i];
    }

    @Override
    public Object getChild(int i, int i1) {
        String[]keys= (String[])map.get(main_dict[i]).keySet().toArray();

        return keys[i1];
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        TextView textView = new TextView(context);
        textView.setText(main_dict[i]);
        textView.setTextSize(25);
        return textView;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
       /* final TextView textView = new TextView(context);
        textView.setText(map.get(listData.get(i)).get(i1));
        textView.setTextSize(20);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String s = textView.getText().toString();

                    int resId = context.getResources().getIdentifier(s, "array", context.getPackageName());

                String[] strlatlng = context.getResources().getStringArray(resId);
                ArrayList<LatLng> latLngs= new ArrayList<>();
                for(String coord:strlatlng)
                {
                    String[]base =coord.split("!4d");
                    latLngs.add(new LatLng(Double.parseDouble(base[0]),Double.parseDouble(base[1])));

                }
                listListener.returnLatlngs(latLngs);}catch (Exception e){}
            }
        });*/
       final int test =i;
       final TextView textViev=new TextView(context);
       String[]keys= (String[])map.get(main_dict[i]).keySet().toArray();
       textViev.setText(keys[i1]);
       textViev.setTextSize(20);
       textViev.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               List<String> testList =map.get(main_dict[test]).get(textViev.getText().toString());
               String[] toListener=testList.toArray(new String [testList.size()]);
               listListener.returnPhrase(toListener);
           }
       });
       return textViev;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
