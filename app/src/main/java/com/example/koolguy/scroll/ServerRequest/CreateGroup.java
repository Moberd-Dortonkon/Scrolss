package com.example.koolguy.scroll.ServerRequest;


import android.os.AsyncTask;

import com.example.koolguy.scroll.MainActivity;
import com.example.koolguy.scroll.serverInterfaces.ServerCreateGroup;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateGroup extends AsyncTask<String,String,String>
{
    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    String lName;
    @Override
    protected String doInBackground(String... strings) {


        Retrofit retrofit =new Retrofit.Builder().baseUrl(MainActivity.SERVER).addConverterFactory(GsonConverterFactory.create()).build();

        ServerCreateGroup group =retrofit.create(ServerCreateGroup.class);
        Call<String> call=group.createGroup(lName);
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