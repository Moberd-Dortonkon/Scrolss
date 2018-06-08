package com.moberd.koolguy.scroll.groups.ServerInterfaces;

import com.moberd.koolguy.scroll.VolonteersInfo.Group;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ShowAllGroupsInterface {
    @GET("/display/groups")
    Call<List<Group>> downloadgroups();
}
