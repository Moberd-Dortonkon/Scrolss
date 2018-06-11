package com.moberd.koolguy.scroll.Tools;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.moberd.koolguy.scroll.R;
import com.moberd.koolguy.scroll.VolonteersInfo.Volonteer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class GroupAdapter extends ArrayAdapter<String> {
    HashMap<String,Volonteer> vGroup;
    Context context;
    String[]group;
    long test; long serverTime;

    public GroupAdapter(@NonNull Context context, HashMap<String,Volonteer> vGroup) {
        super(context, R.layout.group_apadter,vGroup.keySet().toArray(new String[vGroup.size()])) ;
        this.context=context;
        this.vGroup=vGroup;
        group=vGroup.keySet().toArray(new String[vGroup.size()]);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView=LayoutInflater.from(getContext()).inflate(R.layout.group_apadter,null);
        TextView textView =(TextView)convertView.findViewById(R.id.vName);
        ImageView isCame=(ImageView) convertView.findViewById(R.id.isCame);
        ImageView isEated=(ImageView) convertView.findViewById(R.id.isEated);
        textView.setText(group[position]);
        SimpleDateFormat format2=new SimpleDateFormat("dd.MM.yyyy/HH:mm:ss");
        String fromServer=vGroup.get(group[position]).getEattime();
        Date date= Calendar.getInstance().getTime();
        String dateFormated=format2.format(date);
        try {
            test=format2.parse(dateFormated).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            serverTime=format2.parse(fromServer).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.d("time",""+(test-serverTime));
        if(vGroup.get(group[position]).isCome())isCame.setImageResource(R.drawable.ic_thumup);
        if(!vGroup.get(group[position]).isCome())isCame.setImageResource(R.drawable.ic_thumdown);
        if(test-serverTime<25200*1000)isEated.setImageResource(R.drawable.ic_thumup);
        if(test-serverTime>25200*1000)isEated.setImageResource(R.drawable.ic_thumdown);
        return convertView;

    }


}
