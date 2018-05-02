package com.example.koolguy.scroll;


import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.koolguy.scroll.serverInterfaces.ServerCreateGroup;
import com.example.koolguy.scroll.serverInterfaces.ServerVolonteerStatus;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class VolonteerStatus extends Fragment {
    View v;
    String lName;
    String name;
    Button come;
    Button eat;

    public VolonteerStatus() {
        // Required empty public constructor
    }


    public void setlName(String lName) {
        this.lName = lName;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_volonteer_status, container, false);
        come = (Button) v.findViewById(R.id.come);
        eat = (Button) v.findViewById(R.id.eat);
        come.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ComeMyAsyncTask().execute("");
            }
        });
        eat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new EatMyAsyncTask().execute("");
            }
        });

        return v;

    }

    class ComeMyAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {


            Retrofit retrofit = new Retrofit.Builder().baseUrl("https://immense-wave-82247.herokuapp.com").addConverterFactory(GsonConverterFactory.create()).build();
            ServerVolonteerStatus status = retrofit.create(ServerVolonteerStatus.class);
            String servName = name;
            String servlName = lName;

            Call<String> call = status.volonteerStatus("come", servlName, servName);
            try {
                Response<String> response = call.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

    }
    class EatMyAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {


            Retrofit retrofit = new Retrofit.Builder().baseUrl("https://immense-wave-82247.herokuapp.com").addConverterFactory(GsonConverterFactory.create()).build();
            ServerVolonteerStatus status = retrofit.create(ServerVolonteerStatus.class);
            String servName = name;
            String servlName = lName;

            Call<String> call = status.volonteerStatus("eat", servlName, servName);
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

        }
    }
}