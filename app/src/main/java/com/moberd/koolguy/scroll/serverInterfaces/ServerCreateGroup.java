package com.moberd.koolguy.scroll.serverInterfaces;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ServerCreateGroup {
    @POST("/createGroup")
    Call <ResponseBody> createGroup(@Query("lName")String lName);

}
