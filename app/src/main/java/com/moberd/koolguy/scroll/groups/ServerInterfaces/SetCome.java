package com.moberd.koolguy.scroll.groups.ServerInterfaces;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SetCome {
    @GET("/set/come")
    Call<ResponseBody>setCome(@Query("volonteerid")String vid,@Query("groupid")String gid,@Query("come")String come);
}
