package com.example.mapapp;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NewsApi {
    @GET("/3e7531b912d4e09a8560")
    Call<ArrayList<Hazard>> callNews();
}
