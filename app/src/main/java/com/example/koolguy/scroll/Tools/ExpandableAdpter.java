package com.example.koolguy.scroll.Tools;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.koolguy.scroll.R;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;



public class ExpandableAdpter extends BaseExpandableListAdapter {


    Context context;
    Activity activity;
    ExpandableListListener listListener;
     List<String>listData;
    HashMap<String,List<String>>map;
    public ExpandableAdpter(Context context,ExpandableListListener listListener) {
        this.context = context;
        this.listListener = listListener;
        Resources res = context.getResources();
        map = new HashMap<>();
        listData=Arrays.asList(res.getStringArray(R.array.Places));
        /*ArrayList<String>list2=new ArrayList<>();
        list2.add("poka");
        list2.add("sorry no poka");
        listData=new ArrayList<String>();
        listData.add("Test");
        listData.add("Test2");
        map.put("Test",list);
        map.put("Test2",list2);*/
        for (String s:listData)
        {
            int resId=res.getIdentifier(s,"array",context.getPackageName());
            List<String>strings= Arrays.asList(res.getStringArray(resId));
            map.put(s,strings);

        }

    }

    @Override
    public int getGroupCount() {
        return map.keySet().size();
    }

    @Override
    public int getChildrenCount(int i) {
        return map.get(listData.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return listData.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return map.get(listData.get(i)).get(i1);
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
        textView.setText(listData.get(i));
        textView.setTextSize(25);
        return textView;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        final TextView textView = new TextView(context);
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
        });
        return textView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
