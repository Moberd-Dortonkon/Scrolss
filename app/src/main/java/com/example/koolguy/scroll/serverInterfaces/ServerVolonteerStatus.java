package com.example.koolguy.scroll.serverInterfaces;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServerVolonteerStatus {
    @GET("/{path}")
    Call<String>volonteerStatus(@Path("path")String path,@Query("lName")String lName,@Query("name")String name);
}
