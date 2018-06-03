package com.example.koolguy.scroll.Tools;

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
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.koolguy.scroll.MainActivity;
import com.example.koolguy.scroll.R;
import com.example.koolguy.scroll.VolonteersInfo.Group;
import com.example.koolguy.scroll.groups.ServerInterfaces.CreateVolonteer;
import com.example.koolguy.scroll.groups.ShowAllGroups;
import com.example.koolguy.scroll.groups.ShowOneGroup;

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
    ShowAllGroups.DefineGroup listener;
    public ShowGroupForVolonteerAdapter(@NonNull Context context, List<Group>groups, Activity activity, String[]strings, FragmentManager fm, ShowAllGroups.DefineGroup defineGroup) {
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
        name.setText(groups.get(position).getGroupName());
        description.setText(groups.get(position).getGroupdescription());
        type.setText(groups.get(position).getGroupType());
        convertView.setTag(groups.get(position).getGroupid());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s= (String) v.getTag();
                getContext().getSharedPreferences(MainActivity.GROUP_PREFERENCES,Context.MODE_PRIVATE).edit().putString("groupid",s).apply();
                Retrofit retrofit = new Retrofit.Builder().baseUrl(MainActivity.TEST_SERVER).addConverterFactory(GsonConverterFactory.create()).build();
                CreateVolonteer createVolonteer = retrofit.create(CreateVolonteer.class);
                Call<ResponseBody> call = createVolonteer.createVolonteer(getContext().getSharedPreferences(MainActivity.GROUP_PREFERENCES,Context.MODE_PRIVATE).getString("name",""),s);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()) {
                            try {
                                Toast.makeText(activity,response.body().string(),Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });



                listener.defineGroup(s);
                //fm.beginTransaction().replace(R.id.frames,new ShowOneGroup()).addToBackStack(null).commit();
                Toast.makeText(activity,s,Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }

}
