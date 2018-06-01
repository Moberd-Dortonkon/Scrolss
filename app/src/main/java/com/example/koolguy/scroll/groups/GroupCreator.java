package com.example.koolguy.scroll.groups;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.koolguy.scroll.MainActivity;
import com.example.koolguy.scroll.R;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupCreator extends Fragment {

       public interface createLeader
       {
       @GET("/create/leader")
           Call<ResponseBody>createLeader();
       }

       private interface  createGroup
       {
          @GET("/create/group")
          Call<ResponseBody> createGroup(@Query("leaderid")String leaderid,@Query("grouptype")String groupType,@Query("leadername")String leadername,@Query("groupname")String groupname);
       }



    public GroupCreator() {
        // Required empty public constructor
    }
    View view;
    EditText name,description;
    Button button;
    String leadernid;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view =inflater.inflate(R.layout.fragment_group_creator, container, false);
         name = view.findViewById(R.id.group_creator_name);
         description=view.findViewById(R.id.group_creator_description);
         button=view.findViewById(R.id.group_creator_create);
         button.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Retrofit retrofit = new Retrofit.Builder()
                         .baseUrl("https://ancient-forest-80024.herokuapp.com")
                         .addConverterFactory(GsonConverterFactory.create())
                         .build();
                 createLeader createLeader=retrofit.create(GroupCreator.createLeader.class);
                 Call<ResponseBody> leadercall = createLeader.createLeader();
                 leadercall.enqueue(new Callback<ResponseBody>() {
                     @Override
                     public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if(response.isSuccessful())
                            {

                                try {
                                    leadernid=response.body().string();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                Toast.makeText(getActivity(),leadernid,Toast.LENGTH_LONG).show();
                                view.getContext().getSharedPreferences(MainActivity.GROUP_PREFERENCES, Context.MODE_PRIVATE).edit().putString("leaderid",leadernid).apply();

                            }
                     }
                     @Override
                     public void onFailure(Call<ResponseBody> call, Throwable t) {

                     }
                 });




             }
         });


        return view;
    }

}
