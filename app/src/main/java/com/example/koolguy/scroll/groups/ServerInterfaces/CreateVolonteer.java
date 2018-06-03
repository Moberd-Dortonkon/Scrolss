package com.example.koolguy.scroll.groups.ServerInterfaces;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CreateVolonteer {
    @GET("/insertIntoVolonteers")
    Call<ResponseBody>createVolonteer(@Query("volonteerid")String volonteerid,@Query("groupid")String groupid);
}
