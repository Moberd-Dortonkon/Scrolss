package com.example.koolguy.scroll.serverInterfaces;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServerVolonteerStatus {
    @GET("/come")
    Call<String>volonteerStatus(@Query("lName")String lName,@Query("name")String name);
}
