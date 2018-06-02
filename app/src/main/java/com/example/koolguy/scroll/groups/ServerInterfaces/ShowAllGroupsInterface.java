package com.example.koolguy.scroll.groups.ServerInterfaces;

import com.example.koolguy.scroll.VolonteersInfo.Group;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ShowAllGroupsInterface {
    @GET("/display/groups")
    Call<List<Group>> downloadgroups();
}
