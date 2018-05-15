package com.example.koolguy.scroll;


import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.koolguy.scroll.Tools.GroupAdapter;
import com.example.koolguy.scroll.VolonteersInfo.DisplayVolonteers;
import com.example.koolguy.scroll.VolonteersInfo.Volonteer;
import com.example.koolguy.scroll.serverInterfaces.ServerDisplayGroup;

import java.io.IOException;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class LeaderGroup extends Fragment {
    ListView groupView;
    Button button;
    EditText editText;
    View v;
    TextView textView;
    RefreshStatus ref;
    Button refresh;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ref=(RefreshStatus)activity;
    }
    public void setlName(String key) {
        this.key = key;
    }

    String key;
    HashMap<String,Volonteer> vGroup;

    public LeaderGroup() {
        // Required empty public constructor
    }


    public void setPasswordView(String s)
    {
        textView = (TextView)v.findViewById(R.id.refreshGroup);
        textView.setText(s);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.fragment_leader_group, container, false);
        groupView =(ListView)v.findViewById(R.id.groupView);
        refresh=(Button)v.findViewById(R.id.reset);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ref.refrsh();
            }
        });
        textView = (TextView)v.findViewById(R.id.refreshGroup);
        textView.setText(key);
        new CountDownTimer(1600000, 500) {
            @Override
            public void onTick(long l) {
                     new MyAsyncTask().execute("");
            }

            @Override
            public void onFinish() {

            }
        }.start();
        return v;
    }
    class MyAsyncTask extends AsyncTask<String,String,String>
    {

        @Override
        protected String doInBackground(String... strings) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(MainActivity.SERVER)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            ServerDisplayGroup group = retrofit.create(ServerDisplayGroup.class);

            Call<DisplayVolonteers> call = group.getVolonteers(key);
            try {
                Response<DisplayVolonteers> response = call.execute();
                DisplayVolonteers d =response.body();
                vGroup = d.getVolonteers();
            }catch (IOException e){}



            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            String info ="";
            if(vGroup!=null) {

                groupView.setAdapter(new GroupAdapter(v.getContext(),vGroup));
            }


        }
    }

}
