package com.moberd.koolguy.scroll.ServerRequest;


import android.os.AsyncTask;

import com.moberd.koolguy.scroll.MainActivity;
import com.moberd.koolguy.scroll.serverInterfaces.ServerCreateGroup;

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
       // Call<String> call=group.createGroup(lName);
     //   try {
         // call.execute();
      //  } catch (IOException e) {
           // e.printStackTrace();
    //    }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        //save name

    }
}