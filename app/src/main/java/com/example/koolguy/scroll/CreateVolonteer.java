package com.example.koolguy.scroll;


import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
        import android.app.Fragment;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.koolguy.scroll.serverInterfaces.ServerCreateGroup;
import com.example.koolguy.scroll.serverInterfaces.ServerCreateVolonteer;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateVolonteer extends Fragment {
    EditText lName;
    EditText name1;
    Button join;
    View v;
    String string;

    interface createVolonteer {
        void createVolonteerCLick(String lName, String name);

    }

    public CreateVolonteer() {
        // Required empty public constructor
    }

    createVolonteer listener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (createVolonteer) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_create_volonteer, container, false);
        lName = (EditText) v.findViewById(R.id.leaderName);
        name1 = (EditText) v.findViewById(R.id.yourName);
        join = (Button) v.findViewById(R.id.join);
        string="";
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AsyncTask().execute("");
                if(string.equals("complete"))listener.createVolonteerCLick(lName.getText().toString(),name1.getText().toString());
            }
        });

        return v;

    }

    class AsyncTask extends android.os.AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {

            Retrofit retrofit = new Retrofit.Builder().baseUrl(MainActivity.SERVER).addConverterFactory(GsonConverterFactory.create()).build();
            ServerCreateVolonteer group = retrofit.create(ServerCreateVolonteer.class);
            String lNam = lName.getText().toString();
            String name2 = name1.getText().toString();
            Call<ResponseBody>  call = group.createVolonteer(name2,lNam);
                try {
                    Response<ResponseBody>response=call.execute();
                    string =response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }



           return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            String s=string;

            if(s!=null)
            {
                if(s.equals("complete"))listener.createVolonteerCLick(lName.getText().toString(),name1.getText().toString());
                if(s.equals("try another name")){
                    Toast.makeText(v.getContext(),"try another name",Toast.LENGTH_LONG);}
            }
        }
    }
}