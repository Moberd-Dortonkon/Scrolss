package com.moberd.koolguy.scroll.groups.ServerInterfaces;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface  CreateGroup
{
    @GET("/create/group")
    Call<ResponseBody> createGroup(@Query("leaderid")String leaderid, @Query("groupdate")String groupdate, @Query("leadername")String leadername, @Query("groupname")String groupname, @Query("description")String desc);
}
