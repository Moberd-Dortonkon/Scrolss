package com.example.koolguy.scroll.groups;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.koolguy.scroll.MainActivity;
import com.example.koolguy.scroll.R;
import com.example.koolguy.scroll.groups.ServerInterfaces.CreateLeader;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * A simple {@link Fragment} subclass.
 */
public class GreetingsGroupFragment extends Fragment {



    public GreetingsGroupFragment() {
        // Required empty public constructor
    }
    EditText name;
    SharedPreferences resources;
    EditText second_name;
    String leaderid;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_greetings_group, container, false);
        // Inflate the layout for this fragment
       // ViewGroup viewGroup = new ViewGroup();
        //viewGroup.e
        resources =view.getContext().getSharedPreferences(MainActivity.GROUP_PREFERENCES, Context.MODE_PRIVATE);
        name = view.findViewById(R.id.greetingf_name);
        second_name=view.findViewById(R.id.family);
        Button apply=view.findViewById(R.id.greetings_button);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!name.getText().toString().isEmpty()&&!second_name.getText().toString().isEmpty()){
                resources.edit().putString("first_name",name.getText().toString()).apply();
                resources.edit().putString("second_name",second_name.getText().toString()).apply();
                    Retrofit retrofit = new Retrofit.Builder().baseUrl(MainActivity.TEST_SERVER)
                            .addConverterFactory(GsonConverterFactory.create()).build();
                    CreateLeader createLeader = retrofit.create(CreateLeader.class);
                    Call<ResponseBody>call=createLeader.createLeader();
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if(response.isSuccessful())
                            {
                                try {
                                    leaderid=response.body().string();
                                    Toast.makeText(getActivity(),leaderid,Toast.LENGTH_SHORT).show();
                                    resources.edit().putString("leaderid",leaderid).apply();
                                    getFragmentManager().beginTransaction().replace(R.id.frames,new ChooseToDo()).disallowAddToBackStack().commit();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });
                   // Toast.makeText(getActivity(),leaderid,Toast.LENGTH_SHORT).show();
                //getFragmentManager().beginTransaction().replace(R.id.frames,new ChooseToDo()).disallowAddToBackStack().commit();
                    }
            }
        });


        return view;
    }

}
