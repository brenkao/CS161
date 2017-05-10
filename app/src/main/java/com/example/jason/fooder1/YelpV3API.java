package com.example.jason.fooder1;

import com.example.jason.fooder1.pojo.Business;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface YelpV3API {
   String BUSINESS_PATH = "/v3/businesses/{id}";

   @GET(BUSINESS_PATH)
   Call<Business> business(@Path("id") String id, @Query("locale") String locale);
}