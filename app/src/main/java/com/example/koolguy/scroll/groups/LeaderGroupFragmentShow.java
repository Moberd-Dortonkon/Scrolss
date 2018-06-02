package com.example.koolguy.scroll.groups;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.koolguy.scroll.LeaderGroup;
import com.example.koolguy.scroll.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LeaderGroupFragmentShow extends Fragment {
   View view;

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    String groupid;
    public LeaderGroupFragmentShow() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       view=inflater.inflate(R.layout.fragment_leader_group_fragment_show, container, false);
        LeaderGroup leaderGroup = new LeaderGroup();
        leaderGroup.setlName(groupid);
        getChildFragmentManager().beginTransaction().replace(R.id.leader_group_fragment_show_framelayout,leaderGroup).disallowAddToBackStack().commit();
        return view;
    }

}
