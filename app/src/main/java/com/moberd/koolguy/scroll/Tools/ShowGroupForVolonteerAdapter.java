package com.moberd.koolguy.scroll.Tools;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.moberd.koolguy.scroll.MainActivity;
import com.moberd.koolguy.scroll.R;
import com.moberd.koolguy.scroll.VolonteersInfo.Group;
import com.moberd.koolguy.scroll.groups.ServerInterfaces.CreateVolonteer;
import com.moberd.koolguy.scroll.groups.ShowAllGroups;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShowGroupForVolonteerAdapter extends ArrayAdapter<String> {
    Context context;
    Activity activity;
    FragmentManager fm;
    List<Group>groups;
    com.moberd.koolguy.scroll.groups2.ShowAllGroups.DefineGroup listener;
    public ShowGroupForVolonteerAdapter(@NonNull Context context, List<Group>groups, Activity activity, String[]strings, FragmentManager fm, com.moberd.koolguy.scroll.groups2.ShowAllGroups.DefineGroup defineGroup) {
        super(context, R.layout.adapter_group,strings);
        this.context=context;
        this.groups=groups;
        this.activity=activity;
        this.fm = fm;
        listener = defineGroup;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView= LayoutInflater.from(getContext()).inflate(R.layout.adapter_group,null);
        TextView name=(TextView)convertView.findViewById(R.id.adapter_group_name);
        TextView description=(TextView)convertView.findViewById(R.id.adapter_group_description);
        TextView type =(TextView)convertView.findViewById(R.id.adapter_group_type);
        name.setText(groups.get(groups.size()-1-position).getLeaderName());
        description.setText(groups.get(groups.size()-1-position).getGroupdescription());
        type.setText(groups.get(groups.size()-1-position).getGroupdate());
        convertView.setTag(groups.get(groups.size()-1-position).getGroupid());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s= (String) v.getTag();
                getContext().getSharedPreferences(MainActivity.GROUP_PREFERENCES,Context.MODE_PRIVATE).edit().putString("groupid",s).apply();




                listener.defineGroup(s);
                //fm.beginTransaction().replace(R.id.frames,new ShowOneGroup()).addToBackStack(null).commit();
               // Toast.makeText(activity,s,Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }

}
