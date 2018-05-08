package com.example.koolguy.scroll;


import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.koolguy.scroll.serverInterfaces.ServerCreateGroup;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class LeaderCreateGroup extends Fragment {

    View v;
    EditText leaderName;
    Button createGroup;
    TextView test;
    LeaderCreateGroupNext listener;
    public interface LeaderCreateGroupNext
    {
        void leaderCreateClick(String lName);
    }
    public LeaderCreateGroup() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener=(LeaderCreateGroupNext) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_leader_create_group, container, false);
        leaderName =(EditText)v.findViewById(R.id.leadername);
        test =(TextView)v.findViewById(R.id.test);
        createGroup=(Button)v.findViewById(R.id.createGroup);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        createGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                test.setText("hi");
                Toast.makeText(v.getContext(),"Succses",Toast.LENGTH_LONG);
                new MyAsyncTask().execute("");

            }
        });
        Toast.makeText(v.getContext(),"Succses",Toast.LENGTH_LONG);
    }

    class MyAsyncTask extends AsyncTask<String,String,String>
    {
        @Override
        protected String doInBackground(String... strings) {


            Retrofit retrofit =new Retrofit.Builder().baseUrl(MainActivity.SERVER).addConverterFactory(GsonConverterFactory.create()).build();
            ServerCreateGroup group =retrofit.create(ServerCreateGroup.class);
            String lName =leaderName.getText().toString();
            Call<String>call=group.createGroup(lName);
            try {
              Response<String> response = call.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //save name
            listener.leaderCreateClick(leaderName.getText().toString());
            
        }
    }

}
