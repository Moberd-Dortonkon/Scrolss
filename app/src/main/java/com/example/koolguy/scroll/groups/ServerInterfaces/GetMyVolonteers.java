package com.example.koolguy.scroll.groups.ServerInterfaces;

import com.example.koolguy.scroll.VolonteersInfo.Volonteer;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetMyVolonteers {
    @GET("display/volonteers")
    Call<List<Volonteer>> getMyVolonteer(@Query("groupid")String groupid);
}
