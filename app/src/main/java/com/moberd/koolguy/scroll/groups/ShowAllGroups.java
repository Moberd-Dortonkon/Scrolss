package com.moberd.koolguy.scroll.groups;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.moberd.koolguy.scroll.MainActivity;
import com.moberd.koolguy.scroll.R;
import com.moberd.koolguy.scroll.Tools.ShowGroupForVolonteerAdapter;
import com.moberd.koolguy.scroll.VolonteersInfo.Group;
import com.moberd.koolguy.scroll.groups.ServerInterfaces.ShowAllGroupsInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowAllGroups extends Fragment {

    public interface DefineGroup {
        void defineGroup(String groupid);

    }
    public ShowAllGroups() {
        // Required empty public constructor
    }
    List<Group>groups;
    ListView listView;
    View view;
    FrameLayout frameLayout;
    DefineGroup listener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener=(DefineGroup)activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_show_all_groups, container, false);
        frameLayout=(FrameLayout)view.findViewWithTag("test");
        frameLayout.addView(inflater.inflate(R.layout.progress_view,null));

        listView=(ListView)view.findViewById(R.id.show_all_groups_listview);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MainActivity.TEST_SERVER)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ShowAllGroupsInterface showAllGroupsInterface=retrofit.create(ShowAllGroupsInterface.class);
        Call<List<Group>> call =showAllGroupsInterface.downloadgroups();
        call.enqueue(new Callback<List<Group>>() {
            @Override
            public void onResponse(Call<List<Group>> call, Response<List<Group>> response) {
                if(response.isSuccessful())
                {
                    frameLayout.removeAllViews();
                    frameLayout.setVisibility(View.GONE);
                    groups=response.body();

                    String[] names = new String[groups.size()];
                    for(int i=0;i<groups.size();i++)
                    {
                        names[i]=groups.get(i).getGroupName();
                    }
                    //ShowGroupForVolonteerAdapter show = new ShowGroupForVolonteerAdapter(view.getContext(),groups,getActivity(),names,getFragmentManager(),listener);
                    //Toast.makeText(getActivity(),groups.get(3).getGroupid(),Toast.LENGTH_SHORT).show();
                   // listView.setAdapter(show);
                }
            }

            @Override
            public void onFailure(Call<List<Group>> call, Throwable t) {

            }
        });

       // ShowGroupForVolonteerAdapter showGroupForVolonteerAdapter = new ShowGroupForVolonteerAdapter(view.getContext())
        return view;
    }


}
