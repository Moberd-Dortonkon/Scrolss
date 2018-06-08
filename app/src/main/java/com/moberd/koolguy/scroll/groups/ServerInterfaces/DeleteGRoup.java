package com.moberd.koolguy.scroll.groups.ServerInterfaces;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DeleteGRoup {
    @GET("/deleteGroup")
    Call<ResponseBody>deleteGroup(@Query("groupid")String groupid);
}
