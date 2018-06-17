package com.moberd.koolguy.scroll.groups2;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
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
    LayoutInflater inflater;
    LinearLayout linearLayout;
    FrameLayout frameLayout;
    DefineGroup listener;
    DatabaseReference ref;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener=(DefineGroup)activity;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_my_groups, container, false);
        this.inflater = inflater;
        linearLayout = (LinearLayout)view.findViewById(R.id.my_group_layout);
        View loading_view = inflater.inflate(R.layout.progress_view,null);
        loading_view.setTag("Test");
        linearLayout.addView(loading_view);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        //frameLayout.addView(inflater.inflate(R.layout.progress_view,null));

        ref= FirebaseDatabase.getInstance().getReference("Groups");
        ref.getParent().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.hasChild("Groups")){noGroups();}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String key = dataSnapshot.getKey();
                String name=dataSnapshot.child("LeaderName").getValue(String.class);
                String description=dataSnapshot.child("Description").getValue(String.class);
                String date=dataSnapshot.child("Time").getValue(String.class);
                Group group = new Group(date,name,key,name,description,null);
                addGroup(group);


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                  deletegroup(dataSnapshot.getKey());
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;
    }
    private  void deletegroup(String groupid)
    {
        linearLayout.removeView(view.findViewWithTag(groupid));

    }
    private  void addGroup(Group g)
    {

        final View group_view = inflater.inflate(R.layout.group_exist,null);
        TextView name=(TextView)group_view.findViewById(R.id.group_exist_name);
        TextView desc=(TextView)group_view.findViewById(R.id.group_exist_desc);
        TextView date=(TextView)group_view.findViewById(R.id.group_exist_date);
        name.setText(g.getLeaderName());
        desc.setText(g.getGroupdescription());
        date.setText(g.getGroupdate());
        group_view.setTag(g.getGroupid());
        ImageButton imageButton = (ImageButton)group_view.findViewById(R.id.deletegroup);
        imageButton.setVisibility(View.GONE);
        group_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.defineGroup((String)group_view.getTag());
            }
        });
        linearLayout.removeView(view.findViewWithTag("Test"));
        linearLayout.addView(group_view);
    }
    private void noGroups()
    {
        linearLayout.removeAllViews();
        View view = inflater.inflate(R.layout.group_nogroup,null);
        view.setTag("Test");
        TextView text = (TextView)view.findViewById(R.id.textView3);
        text.setTextSize(26);
        text.setText(R.string.groups_not_exist);
        linearLayout.addView(view);
    }

}
