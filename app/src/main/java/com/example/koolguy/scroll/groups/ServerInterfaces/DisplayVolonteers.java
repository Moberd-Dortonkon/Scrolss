package com.example.koolguy.scroll.groups.ServerInterfaces;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DisplayVolonteers {
    @GET("/display/volonteers")
    Call<com.example.koolguy.scroll.VolonteersInfo.DisplayVolonteers>display(@Query("groupid")String groupid);
}
