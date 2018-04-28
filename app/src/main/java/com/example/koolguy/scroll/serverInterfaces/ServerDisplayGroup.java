package com.example.koolguy.scroll.serverInterfaces;

import com.example.koolguy.scroll.VolonteersInfo.DisplayVolonteers;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServerDisplayGroup {
    @GET("/display")
    Call<DisplayVolonteers> getVolonteers(@Query(value ="lName",encoded=true)String lName);
}
