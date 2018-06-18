package com.moberd.koolguy.scroll.groups2;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.moberd.koolguy.scroll.LeaderGroup;
import com.moberd.koolguy.scroll.R;
import com.moberd.koolguy.scroll.VolonteersInfo.Group;

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
        com.moberd.koolguy.scroll.groups2.LeaderGroup leaderGroup = new com.moberd.koolguy.scroll.groups2.LeaderGroup();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
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
        FrameLayout chat =(FrameLayout) view.findViewById(R.id.bubble_chat);
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(),"lol",Toast.LENGTH_SHORT).show();
                getFragmentManager().beginTransaction().replace(R.id.frames,new GroupChat()).addToBackStack(null).commit();
            }
        });
        TextView textView = (TextView)view.findViewById(R.id.message_count);
        textView.setVisibility(View.INVISIBLE);
        leaderGroup.setGroup(group);
        leaderGroup.setlName(groupid);
        leaderGroup.setActivity(getActivity());
        getChildFragmentManager().beginTransaction().replace(R.id.leader_group_fragment_show_framelayout,leaderGroup).disallowAddToBackStack().commit();
        return view;
    }

}
