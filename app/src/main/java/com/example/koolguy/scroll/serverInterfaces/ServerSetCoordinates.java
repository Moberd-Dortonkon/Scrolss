package com.example.koolguy.scroll.serverInterfaces;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ServerSetCoordinates {
    @POST("/coordinates/getCoordinates")
    Call<ResponseBody> getCoordinates(@Query("lName")String lName,@Query("latlng")String latlng);
}