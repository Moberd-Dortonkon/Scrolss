package com.moberd.koolguy.scroll.serverInterfaces;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ServerGetCoordinates {
    @POST("/coordinates/get")
    Call<ResponseBody>getCoordinates(@Query("groupid")String lName);

}
