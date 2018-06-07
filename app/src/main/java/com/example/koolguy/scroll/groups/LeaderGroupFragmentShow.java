package com.example.koolguy.scroll.groups;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.koolguy.scroll.LeaderGroup;
import com.example.koolguy.scroll.R;
import com.example.koolguy.scroll.VolonteersInfo.Group;

/**
 * A simple {@link Fragment} subclass.
 */
public class LeaderGroupFragmentShow extends Fragment {
   View view;
   Group group;
    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }
    public void setGroup(Group group){this.group=group;}
    String groupid;
    public LeaderGroupFragmentShow() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_leader_group_fragment_show, container, false);
        LeaderGroup leaderGroup = new LeaderGroup();
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) view.findViewById(R.id.toolbarId);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.frames,new ChooseToDoLeader()).addToBackStack(null).commit();
            }
        });
       // TextView textView =(TextView)view.findViewById(R.id.leader_group_fragemnt_show_sho_other);
     // textView.setOnClickListener(new View.OnClickListener() {
     //       @Override
      //      public void onClick(View v) {
     //           getFragmentManager().beginTransaction().replace(R.id.frames,new MyGroups()).addToBackStack(null).commit();
     //       }
    //    });
        leaderGroup.setGroup(group);
        leaderGroup.setlName(groupid);
        leaderGroup.setActivity(getActivity());
        getChildFragmentManager().beginTransaction().replace(R.id.leader_group_fragment_show_framelayout,leaderGroup).disallowAddToBackStack().commit();
        return view;
    }

}
