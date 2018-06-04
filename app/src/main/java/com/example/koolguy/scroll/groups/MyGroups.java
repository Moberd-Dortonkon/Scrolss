package com.example.koolguy.scroll.groups;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.koolguy.scroll.MainActivity;
import com.example.koolguy.scroll.R;
import com.example.koolguy.scroll.VolonteersInfo.Group;
import com.example.koolguy.scroll.groups.ServerInterfaces.ServerDisplayGroupForMe;

import org.w3c.dom.Text;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyGroups extends Fragment {
    LinearLayout linearLayout;
    View view;
    View mini_view;
    List<Group>groups;
    public MyGroups() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_my_groups, container, false);
        linearLayout=view.findViewById(R.id.my_group_layout);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MainActivity.TEST_SERVER)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ServerDisplayGroupForMe displayGroupForMe=retrofit.create(ServerDisplayGroupForMe.class);
        Call<List<Group>> call = displayGroupForMe.downloadgroups(view.getContext().getSharedPreferences(MainActivity.GROUP_PREFERENCES, Context.MODE_PRIVATE).getString("leaderid",""));
        call.enqueue(new Callback<List<Group>>() {
            @Override
            public void onResponse(Call<List<Group>> call, Response<List<Group>> response) {
                groups= response.body();
                makeGroups(groups);
                //Toast.makeText(getActivity(),groups.get(2).getGroupid(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Group>> call, Throwable t) {

            }
        });


        return view;
    }
    private void makeGroups(final List<Group>groups)
    {
        LayoutInflater inflate=LayoutInflater.from(view.getContext());
        LinearLayout linearLayout =(LinearLayout)view.findViewById(R.id.my_group_layout);
        if(groups.isEmpty())
        {
            View view = inflate.inflate(R.layout.group_creategroup,null);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getFragmentManager().beginTransaction().disallowAddToBackStack().replace(R.id.frames,new GroupCreator()).commit();
                }
            });
            //linearLayout.addView(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }

        //Для материал дезигна,надо делати крутой дезигн group_notexist ии в group_creategroup,я всего лишь даю значения объектам внтри них

        else
            {
               // LayoutInflater inflate=LayoutInflater.from(view.getContext());
              //  LinearLayout linearLayout =(LinearLayout)view.findViewById(R.id.my_group_layout);
                int i=1;
                for(Group g:groups)
                {

                   /* FrameLayout frameLayout=view.findViewById(view.getContext().getResources().getIdentifier("my_groups_layout"+i,"id",view.getContext().getPackageName()));
                    frameLayout.removeAllViews();
                    mini_view=inflate.inflate(R.layout.group_exist,null);
                    TextView name =(TextView)mini_view.findViewById(R.id.groupExist_name);
                    TextView description=(TextView)mini_view.findViewById(R.id.groupExist_descriptyp);
                    TextView type =(TextView)mini_view.findViewById(R.id.groupexist_type);
                    name.setText(g.getGroupName());
                    description.setText(g.getGroupdescription());
                    type.setText(g.getGroupType());
                    frameLayout.setTag(i);
                    frameLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int tag =(int)v.getTag();
                            Group group=groups.get(tag-1);
                            LeaderGroupFragmentShow leaderG=new LeaderGroupFragmentShow();
                            leaderG.setGroupid(group.getGroupid());
                            getFragmentManager().beginTransaction().replace(R.id.frames,leaderG).addToBackStack(null).commit();
                            Toast.makeText(getActivity(),""+group.getGroupid(),Toast.LENGTH_SHORT).show();
                        }
                    });
                    frameLayout.addView(mini_view);*/
                    View view = inflate.inflate(R.layout.group_notexist,null);
                    TextView name =(TextView)view.findViewById(R.id.group_not_exist_name);
                    TextView description=(TextView)view.findViewById(R.id.group_not_exist_desc);
                    TextView type =(TextView)view.findViewById(R.id.group_not_exist_type);
                    name.setText(g.getGroupName());
                    description.setText(g.getGroupdescription());
                    type.setText(g.getGroupType());
                    view.setTag(i);
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int tag =(int)v.getTag();
                            Group group=groups.get(tag-1);
                            LeaderGroupFragmentShow leaderG=new LeaderGroupFragmentShow();
                            leaderG.setGroupid(group.getGroupid());
                            Toast.makeText(getActivity(),group.getGroupcoordinates(),Toast.LENGTH_SHORT).show();
                            leaderG.setGroup(group);
                            getFragmentManager().beginTransaction().replace(R.id.frames,leaderG).addToBackStack(null).commit();
                            Toast.makeText(getActivity(),""+group.getGroupid(),Toast.LENGTH_SHORT).show();
                        }
                    });
                    linearLayout.addView(view);
                    i++;
                }
                View view = inflate.inflate(R.layout.group_creategroup,null);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getFragmentManager().beginTransaction().disallowAddToBackStack().replace(R.id.frames,new GroupCreator()).commit();
                    }
                });
               // linearLayout.addView(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            }



    }

}
