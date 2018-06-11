package com.moberd.koolguy.scroll.groups2;


import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moberd.koolguy.scroll.MainActivity;
import com.moberd.koolguy.scroll.R;
import com.moberd.koolguy.scroll.VolonteersInfo.Group;
import com.moberd.koolguy.scroll.groups.ServerInterfaces.CreateGroup;

import java.io.IOException;
import java.nio.file.attribute.GroupPrincipal;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.util.Calendar;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
    FirebaseDatabase database;
    private SoundPool mySoundPool;
    private AssetManager myAssetManager;
    private int myButtonSound;
    private int myStreamID;

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    FragmentManager fragmentManager;
    FrameLayout frameLayout;
    String leadernid; String s;
    DatabaseReference groupId;
    boolean test;
    String StringDate;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

         view =inflater.inflate(R.layout.fragment_group_creator, container, false);
         database=FirebaseDatabase.getInstance();test=true;
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
         name =(EditText)view.findViewById(R.id.group_creator_name);
         description=(EditText)view.findViewById(R.id.group_creator_description);
         button=(Button) view.findViewById(R.id.group_creator_create);
         button.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 groupId=database.getReference("Groups");
                 groupId.addValueEventListener(new ValueEventListener() {
                     @Override
                     public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                         while(test){
                          s = createPassword();
                          if(!dataSnapshot.child(s).exists())
                          {
                          test=false;
                          addtoDb();}

                         }
                     }

                     @Override
                     public void onCancelled(@NonNull DatabaseError databaseError) {

                     }
                 });
             }
         });


        return view;
    }
    private void addtoDb()
    {
        DatabaseReference realGroup = groupId.child(s);
        String userName=view.getContext().getSharedPreferences(MainActivity.GROUP_PREFERENCES,Context.MODE_PRIVATE).getString("name","test");
        String groupsNAme=groupId.getKey();

      //  Toast.makeText(getActivity(),description.getText().toString(),Toast.LENGTH_SHORT).show();
        realGroup.child("Description").setValue(description.getText().toString());
        realGroup.child("Time").setValue( StringDate = DateFormat.getDateInstance(DateFormat.SHORT).format(Calendar.getInstance().getTime()));
        //realGroup.child("Name").setValue(name.getText().toString());
        realGroup.child("LeaderName").setValue(userName);
        DatabaseReference myGroups=database.getReference("Leaders").child(userName).child("Groups").child(s);
        myGroups.child("Description").setValue(description.getText().toString());
        myGroups.child("Name").setValue(name.getText().toString());
        database.getReference("Leaders").child(userName).child("Test").setValue("JustExist");
        myGroups.child("Time").setValue( StringDate = DateFormat.getDateInstance(DateFormat.SHORT).format(Calendar.getInstance().getTime()));
        LeaderGroupFragmentShow leaderG=new LeaderGroupFragmentShow();
        leaderG.setGroupid(s);
        // Toast.makeText(getActivity(),group.getGroupcoordinates(),Toast.LENGTH_SHORT).show();
        Group group = new Group(null,null,s,null,null,null);
        leaderG.setGroup(group);
        fragmentManager.beginTransaction().replace(R.id.frames,leaderG).addToBackStack(null).commit();

    }
    private String createPassword()
    {
        SecureRandom secureRandom = new SecureRandom();
        String s="Aaa";
        String summ="";
        for(int i =0;i<s.length();i++)
        {
            char c=(char) (s.charAt(i)+secureRandom.nextInt(25));
            summ=summ+c;

        }
        summ+=secureRandom.nextInt(999);
        return summ;
    }
}
