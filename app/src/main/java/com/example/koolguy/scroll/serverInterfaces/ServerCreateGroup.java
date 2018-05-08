package com.example.koolguy.scroll.serverInterfaces;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ServerCreateGroup {
    @POST("/createGroup")
    Call <String> createGroup(@Query("lName")String lName);

}
