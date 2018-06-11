package com.moberd.koolguy.scroll.groups2;


import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    Retrofit retrofit;
    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    FragmentManager fragmentManager;
    public MyGroups() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewTrue = inflater.inflate(R.layout.fragment_my_groups, container, false);
        linearLayout = viewTrue.findViewById(R.id.my_group_layout);
        //inflater.inflate(R.layout.progress_view,null);
        check=true;
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        linearLayout.addView(inflater.inflate(R.layout.progress_view,null));
        groups = new ArrayList<>();
        String userName=viewTrue.getContext().getSharedPreferences(MainActivity.GROUP_PREFERENCES,Context.MODE_PRIVATE).getString("name","test");
        db = FirebaseDatabase.getInstance();

        reference = db.getReference("Leaders").child(userName).child("Groups");
        //Toast.makeText(getActivity(),"here3",Toast.LENGTH_SHORT).show();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                for(DataSnapshot test:dataSnapshot.getChildren())
                {

                            String desck = test.child("Description").getValue(String.class);
                            String name = test.child("Name").getValue(String.class);
                            String time = test.child("Time").getValue(String.class);
                            String groupid = test.getRef().getKey();
                            Group group = new Group(time, "test", groupid, name, desck, null);
                            groups.add(group);
                            if(check)makeGroups(groups);
                }
                else{makeGroups(groups);}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
               // Toast.makeText(getActivity(),"here4",Toast.LENGTH_SHORT).show();
            }
        });


        return viewTrue;
    }
    private void makeGroups(final List<Group>test)
    {
        linearLayout.removeAllViews();
//        Toast.makeText(getActivity(),"",Toast.LENGTH_SHORT).show();
        final LayoutInflater inflate=LayoutInflater.from(viewTrue.getContext());
        final LinearLayout linearLayout =(LinearLayout)viewTrue.findViewById(R.id.my_group_layout);
        if(groups.isEmpty()||groups==null)
        {
            View view = inflate.inflate(R.layout.group_nogroup,null);
            linearLayout.addView(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
        //Для материал дезигна,надо д
        //елати крутой дезигн group_notexist ии в group_creategroup,я всего лишь даю значения объектам внтри них
        else
            {
               // LayoutInflater inflate=LayoutInflater.from(view.getContext());
              //  LinearLayout linearLayout =(LinearLayout)view.findViewById(R.id.my_group_layout);

                for(int i = test.size()-1;i>=0;i--)
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
                    Group g=groups.get(i);
                    View view = inflate.inflate(R.layout.group_exist,null);
                    TextView name =(TextView)view.findViewById(R.id.group_exist_name);
                    TextView description=(TextView)view.findViewById(R.id.group_exist_desc);
                    TextView date =(TextView)view.findViewById(R.id.group_exist_date);
                    name.setText(g.getGroupName());
                    description.setText(g.getGroupdescription());
                    date.setText(g.getGroupdate());
                    view.setTag(i);
                    final ImageButton imageButton = (ImageButton)view.findViewById(R.id.deletegroup);
                    imageButton.setTag(i);
                    imageButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder builder=new AlertDialog.Builder(viewTrue.getContext());
                            builder.setCancelable(true).setTitle("Do you want to delete this group?").setPositiveButton("delete", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    int tag =(int)imageButton.getTag();
                                   // int tag2=(int)tag+1;
                                    linearLayout.removeView(linearLayout.findViewWithTag(tag));
                                    //linearLayout.removeView(linearLayout.findViewWithTag(tag2));
                                           if(groups.size()==1){linearLayout.removeAllViews();linearLayout.addView(inflate.inflate(R.layout.group_nogroup,null));}
                                           String groupid=groups.get(tag).getGroupid();
                                           groups.remove(tag);
                                    //Toast.makeText(getActivity(),groupid,Toast.LENGTH_SHORT).show();
                                           db.getReference("Groups").child(groupid).removeValue();
                                           reference.child(groupid).removeValue();
                                      //     makeGroups(groups);
                                           check=false;
                                    dialog.dismiss();
                                }
                            }).show();

                        }
                    });

                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int tag =(int)v.getTag();
                            Group group=groups.get(tag);
                            LeaderGroupFragmentShow leaderG=new LeaderGroupFragmentShow();
                            leaderG.setGroupid(group.getGroupid());
                           // Toast.makeText(getActivity(),group.getGroupcoordinates(),Toast.LENGTH_SHORT).show();
                            leaderG.setGroup(group);
                            fragmentManager.beginTransaction().replace(R.id.frames,leaderG).addToBackStack(null).commit();
                           // Toast.makeText(getActivity(),""+group.getGroupid(),Toast.LENGTH_SHORT).show();
                        }
                    });
                    linearLayout.addView(view);
                  //  view=inflate.inflate(R.layout.add_backsp,null);
                   // view.setTag(i+1);
                    //linearLayout.addView(view);
                }
                View view = inflate.inflate(R.layout.group_creategroup,null);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fragmentManager.beginTransaction().disallowAddToBackStack().replace(R.id.frames,new GroupCreator()).commit();
                    }
                });
               // linearLayout.addView(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            }

    }

}
