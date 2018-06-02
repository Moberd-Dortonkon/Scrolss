package com.example.koolguy.scroll.groups.ServerInterfaces;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CreateLeader
{
    @GET("/create/leader")
    Call<ResponseBody> createLeader();
}
