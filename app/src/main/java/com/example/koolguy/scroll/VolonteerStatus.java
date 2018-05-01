package com.example.koolguy.scroll;


import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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
    Switch come;
    Switch eat;
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
        v=inflater.inflate(R.layout.fragment_volonteer_status, container, false);
        come = (Switch)v.findViewById(R.id.come);
        eat = (Switch)v.findViewById(R.id.eat);
        come.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                 new ComeMyAsyncTask().execute("") ;

                return true;
            }
        });
        eat.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
               // new EatMyAsyncTask().execute("") ;
                return true;
            }
        });
        return v;
    }

    class ComeMyAsyncTask extends AsyncTask<String,String,String>
    {
        @Override
        protected String doInBackground(String... strings) {


            Retrofit retrofit =new Retrofit.Builder().baseUrl("https://immense-wave-82247.herokuapp.com").addConverterFactory(GsonConverterFactory.create()).build();
            ServerVolonteerStatus group =retrofit.create(ServerVolonteerStatus.class);

            Call<String> call=group.volonteerStatus(lName,name);
            try {
               Response<String>response = call.execute();
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
   /* class EatMyAsyncTask extends AsyncTask<String,String,String>
    {
        @Override
        protected String doInBackground(String... strings) {


            Retrofit retrofit =new Retrofit.Builder().baseUrl("https://immense-wave-82247.herokuapp.com").addConverterFactory(GsonConverterFactory.create()).build();
            ServerVolonteerStatus group =retrofit.create(ServerVolonteerStatus.class);

            Call<String> call=group.volonteerStatus("eat",lName,name);
            try {
                call.execute();
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
    */

}
