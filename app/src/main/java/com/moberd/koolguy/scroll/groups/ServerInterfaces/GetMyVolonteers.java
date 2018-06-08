package com.moberd.koolguy.scroll.groups.ServerInterfaces;

import com.moberd.koolguy.scroll.VolonteersInfo.Volonteer;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetMyVolonteers {
    @GET("display/volonteers")
    Call<List<Volonteer>> getMyVolonteer(@Query("groupid")String groupid);
}
