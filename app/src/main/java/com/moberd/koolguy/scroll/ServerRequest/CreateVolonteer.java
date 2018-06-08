package com.moberd.koolguy.scroll.ServerRequest;


import android.os.AsyncTask;

import com.moberd.koolguy.scroll.MainActivity;
import com.moberd.koolguy.scroll.serverInterfaces.ServerCreateVolonteer;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateVolonteer extends AsyncTask {

    String name2;
    String lNam;

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public void setlNam(String lNam) {
        this.lNam = lNam;
    }
    @Override
    protected Object doInBackground(Object[] objects) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(MainActivity.SERVER).addConverterFactory(GsonConverterFactory.create()).build();
        ServerCreateVolonteer group = retrofit.create(ServerCreateVolonteer.class);
        Call<ResponseBody> call = group.createVolonteer(name2,lNam);
        try {
          call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }



        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

    }
}