package com.example.koolguy.scroll.Tools;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.koolguy.scroll.R;
import com.example.koolguy.scroll.VolonteersInfo.Volonteer;

import java.util.HashMap;

public class GroupAdapter extends ArrayAdapter<String> {
    HashMap<String,Volonteer> vGroup;
    Context context;
    String[]group;

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
        if(vGroup.get(group[position]).isCome())isCame.setImageResource(R.drawable.ic_thumup);
        if(!vGroup.get(group[position]).isCome())isCame.setImageResource(R.drawable.ic_thumdown);
        if(vGroup.get(group[position]).isEat())isEated.setImageResource(R.drawable.ic_thumup);
        if(!vGroup.get(group[position]).isEat())isEated.setImageResource(R.drawable.ic_thumdown);

        return convertView;

    }


}
