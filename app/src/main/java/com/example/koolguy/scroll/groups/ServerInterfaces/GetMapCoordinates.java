package com.example.koolguy.scroll.groups.ServerInterfaces;

import com.example.koolguy.scroll.VolonteersInfo.MapCoordinates;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetMapCoordinates {
    @GET("/MapCoordinates")
    Call<List<MapCoordinates>>getMapCoordinates();
}
