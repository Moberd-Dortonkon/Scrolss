package com.moberd.koolguy.scroll.groups2;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moberd.koolguy.scroll.MainActivity;
import com.moberd.koolguy.scroll.R;
import com.moberd.koolguy.scroll.Tools.ShowGroupForVolonteerAdapter;
import com.moberd.koolguy.scroll.VolonteersInfo.Group;
import com.moberd.koolguy.scroll.groups.ServerInterfaces.ShowAllGroupsInterface;

import java.util.ArrayList;
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
    DatabaseReference ref;

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
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        frameLayout.addView(inflater.inflate(R.layout.progress_view,null));
        groups = new ArrayList<>();

        listView=(ListView)view.findViewById(R.id.show_all_groups_listview);
        ref= FirebaseDatabase.getInstance().getReference("Groups");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long count = dataSnapshot.getChildrenCount();
                for(DataSnapshot group:dataSnapshot.getChildren())
                {
                    String desck = group.child("Description").getValue(String.class);
                    String name = group.child("LeaderName").getValue(String.class);
                    String time = group.child("Time").getValue(String.class);
                    String groupid = group.getRef().getKey();
                   Group group1 = new Group(time,name,groupid,"test",desck,"test");
                   groups.add(group1);
                    count--;
                    if(count==0)
                    {
                        frameLayout.removeAllViews();
                        frameLayout.setVisibility(View.GONE);
                        for(int i=0;i<groups.size();i++)
                        {
                            String[] names = new String[groups.size()];
                            names[i]=groups.get(i).getGroupName();
                            ShowGroupForVolonteerAdapter show = new ShowGroupForVolonteerAdapter(view.getContext(),groups,getActivity(),names,getFragmentManager(), listener);
                            listView.setAdapter(show);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
       // ShowGroupForVolonteerAdapter showGroupForVolonteerAdapter = new ShowGroupForVolonteerAdapter(view.getContext())
        return view;
    }


}
