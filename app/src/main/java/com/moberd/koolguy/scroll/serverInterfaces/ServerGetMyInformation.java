package com.moberd.koolguy.scroll.serverInterfaces;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ServerGetMyInformation {
    @POST("/getMyInformation")
    Call<ResponseBody> getMyInfromation(@Query("lName")String lName, @Query("name")String name);

}
