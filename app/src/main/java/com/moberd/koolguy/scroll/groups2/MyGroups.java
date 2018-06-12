package com.moberd.koolguy.scroll.groups2;


import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moberd.koolguy.scroll.MainActivity;
import com.moberd.koolguy.scroll.R;
import com.moberd.koolguy.scroll.VolonteersInfo.Group;
import com.moberd.koolguy.scroll.groups.ServerInterfaces.DeleteGRoup;
import com.moberd.koolguy.scroll.groups.ServerInterfaces.ServerDisplayGroupForMe;

import java.util.ArrayList;
import java.util.HashMap;
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
    View viewTrue;
    boolean check;
    FirebaseDatabase db;
    View mini_view;
    DatabaseReference reference;
    List<Group>groups;
    HashMap<String,Group>groupHashMap;
    Retrofit retrofit;
    LayoutInflater inflater;
    View loadingView;
    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    FragmentManager fragmentManager;
    public MyGroups() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewTrue = inflater.inflate(R.layout.fragment_my_groups, container, false);
        linearLayout = viewTrue.findViewById(R.id.my_group_layout);
        this.inflater=inflater;
        groupHashMap = new HashMap<>();
        //inflater.inflate(R.layout.progress_view,null);
        check=true;
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        loadingView=inflater.inflate(R.layout.progress_view,null);
        loadingView.setTag("Loading");
        linearLayout.addView(loadingView);
        groups = new ArrayList<>();
        String userName=viewTrue.getContext().getSharedPreferences(MainActivity.GROUP_PREFERENCES,Context.MODE_PRIVATE).getString("name","test");
        db = FirebaseDatabase.getInstance();

        reference = db.getReference("Leaders").child(userName).child("Groups");
        //Toast.makeText(getActivity(),"here3",Toast.LENGTH_SHORT).show()
        reference.getParent().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.hasChild("Groups")){


                    linearLayout.removeAllViews();
                    View view = inflater.inflate(R.layout.group_nogroup,null);
                    linearLayout.addView(view);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
            {
                try{
                linearLayout.removeView(viewTrue.findViewWithTag("Loading"));}catch (Exception e){}
               // Toast.makeText(getActivity(),dataSnapshot.getKey(),Toast.LENGTH_SHORT).show();

                String key = dataSnapshot.getKey();
                String name = dataSnapshot.child("Name").getValue(String.class);
                String desc = dataSnapshot.child("Description").getValue(String.class);
                String time = dataSnapshot.child("Time").getValue(String.class);
                Group group = new Group(time,name,key,name,desc,null);
                groupHashMap.put(group.getGroupid(),group);
                addGroup(group);

            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                //Toast.makeText(getActivity(),dataSnapshot.getKey(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return viewTrue;
    }
    private void addGroup(Group g)
    {
        final View view = inflater.inflate(R.layout.group_exist,null);
        TextView name=(TextView)view.findViewById(R.id.group_exist_name);
        TextView desc=(TextView)view.findViewById(R.id.group_exist_desc);
        TextView date=(TextView)view.findViewById(R.id.group_exist_date);
        name.setText(g.getGroupName());
        desc.setText(g.getGroupdescription());
        date.setText(g.getGroupdate());
        view.setTag(g.getGroupid());
        ImageButton imageButton = (ImageButton)view.findViewById(R.id.deletegroup);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteGroup((String)view.getTag());
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LeaderGroupFragmentShow leaderGroupFragmentShow= new LeaderGroupFragmentShow();
                leaderGroupFragmentShow.setGroupid((String)view.getTag());
                leaderGroupFragmentShow.setGroup(groupHashMap.get((String)view.getTag()));
                fragmentManager.beginTransaction().replace(R.id.frames,leaderGroupFragmentShow).addToBackStack(null).commit();



            }
        });
        linearLayout.addView(view);

    }
    private void deleteGroup(String groupid)
    {
        reference.child(groupid).removeValue();
        linearLayout.removeView(viewTrue.findViewWithTag(groupid));
        db.getReference("Groups").child(groupid).removeValue();
        if(linearLayout.getChildCount()==0)linearLayout.addView(inflater.inflate(R.layout.group_nogroup,null));
    }
    private void showNoGroups()
    {

    }

}
