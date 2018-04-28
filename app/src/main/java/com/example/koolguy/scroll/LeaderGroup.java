package com.example.koolguy.scroll;


import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
    TextView textView;
    Button button;
    EditText editText;
    View v;
    HashMap<String,Volonteer> vGroup;

    public LeaderGroup() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.fragment_leader_group, container, false);
        textView=(TextView)v.findViewById(R.id.groupInfo);
        button =(Button)v.findViewById(R.id.refreshGroup);
        editText = (EditText)v.findViewById(R.id.refreshGroupName);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MyAsyncTask().execute("");

            }
        });


        return v;
    }
    class MyAsyncTask extends AsyncTask<String,String,String>
    {

        @Override
        protected String doInBackground(String... strings) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://immense-wave-82247.herokuapp.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            ServerDisplayGroup group = retrofit.create(ServerDisplayGroup.class);
            String lName=editText.getText().toString();
            Call<DisplayVolonteers> call = group.getVolonteers(lName);
            try {
                Response<DisplayVolonteers> response = call.execute();
                DisplayVolonteers d =response.body();
                vGroup = d.getVolonteers();
            }catch (IOException e){}



            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            Volonteer v;
            String info ="";
            for (String name:vGroup.keySet())
            {
                v=vGroup.get(name);
                info=info+v.getName()+":     "+("пришел:"+(v.isCome()?"да":"нет"))+"      "+("поел:"+(v.isEat()?"да":"нет"))+"\n";

            }
            textView.setText(info);


        }
    }

}
