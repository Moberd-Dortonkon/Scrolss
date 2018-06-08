package com.moberd.koolguy.scroll.groups.ServerInterfaces;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CreateLeader
{
    @GET("/create/leader")
    Call<ResponseBody> createLeader(@Query("name") String name);
}
