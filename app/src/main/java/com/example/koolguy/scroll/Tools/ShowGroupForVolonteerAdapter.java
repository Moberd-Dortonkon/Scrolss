package com.example.koolguy.scroll.Tools;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.koolguy.scroll.R;
import com.example.koolguy.scroll.VolonteersInfo.Group;

import java.util.List;

public class ShowGroupForVolonteerAdapter extends ArrayAdapter<String> {
    Context context;
    Activity activity;
    List<Group>groups;
    public ShowGroupForVolonteerAdapter(@NonNull Context context, List<Group>groups, Activity activity,String[]strings) {
        super(context, R.layout.adapter_group,strings);
        this.context=context;
        this.groups=groups;
        this.activity=activity;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView= LayoutInflater.from(getContext()).inflate(R.layout.adapter_group,null);
        TextView name=(TextView)convertView.findViewById(R.id.adapter_group_name);
        TextView description=(TextView)convertView.findViewById(R.id.adapter_group_description);
        TextView type =(TextView)convertView.findViewById(R.id.adapter_group_type);
        name.setText(groups.get(position).getGroupName());
        description.setText(groups.get(position).getGroupdescription());
        type.setText(groups.get(position).getGroupType());
        convertView.setTag(groups.get(position).getGroupid());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s= (String) v.getTag();
                Toast.makeText(activity,s,Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }

}
