package com.example.koolguy.scroll.groups.ServerInterfaces;

import com.example.koolguy.scroll.VolonteersInfo.Group;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServerDisplayGroupForMe {

    @GET("/display/myGroup")
    Call<List<Group>>downloadgroups(@Query("leaderid")String leaderid);
}
