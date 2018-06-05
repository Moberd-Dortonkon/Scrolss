package com.example.koolguy.scroll.groups;


import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.koolguy.scroll.Check;
import com.example.koolguy.scroll.LeaderCreateGroup;
import com.example.koolguy.scroll.MainActivity;
import com.example.koolguy.scroll.R;
import com.example.koolguy.scroll.VolonteersInfo.Group;
import com.example.koolguy.scroll.groups.ServerInterfaces.CreateGroup;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupCreator extends Fragment {





    public GroupCreator() {
        // Required empty public constructor
    }

    View view;
       String responsem;
    EditText name,description;
    Button button;

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    FragmentManager fragmentManager;
    String leadernid;
    String StringDate;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view =inflater.inflate(R.layout.fragment_group_creator, container, false);
         name = view.findViewById(R.id.group_creator_name);
         description=view.findViewById(R.id.group_creator_description);
         button=view.findViewById(R.id.group_creator_create);
         button.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Retrofit retrofit = new Retrofit.Builder().baseUrl(MainActivity.TEST_SERVER)
                         .addConverterFactory(GsonConverterFactory.create()).build();
                 String leaderid=view.getContext().getSharedPreferences(MainActivity.GROUP_PREFERENCES,Context.MODE_PRIVATE).getString("leaderid","");
                 String name_leader=view.getContext().getSharedPreferences(MainActivity.GROUP_PREFERENCES,Context.MODE_PRIVATE).getString("name","");
                 Date date = Calendar.getInstance().getTime();
                 SimpleDateFormat dateFormat=new SimpleDateFormat("dd.mm.yyyy");
                 StringDate = dateFormat.format(date);

                 Toast.makeText(getActivity(),StringDate,Toast.LENGTH_SHORT).show();
                 String grouptype="testgroup";
                 CreateGroup createGroup=retrofit.create(CreateGroup.class);
                 Call<ResponseBody>call=createGroup.createGroup
                         (leaderid,StringDate,name_leader,name.getText().toString(),description.getText().toString());
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()) {
                            try {
                                responsem=response.body().string();
                                String myName=view.getContext().getSharedPreferences(MainActivity.GROUP_PREFERENCES,Context.MODE_PRIVATE).getString("name","");
                                Group group = new Group(StringDate,myName,responsem,name.getText().toString(),description.getText().toString(),"");
                                LeaderGroupFragmentShow lg=new LeaderGroupFragmentShow();
                                lg.setGroupid(responsem);
                                lg.setGroup(group);
                               // Toast.makeText(getActivity(),"hi",Toast.LENGTH_SHORT).show();
                                fragmentManager.beginTransaction().replace(R.id.frames,lg).disallowAddToBackStack().commit();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });

                 Toast.makeText(getActivity(),responsem,Toast.LENGTH_SHORT).show();
             }
         });


        return view;
    }

}
