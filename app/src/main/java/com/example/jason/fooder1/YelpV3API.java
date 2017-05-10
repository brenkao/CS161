package com.example.jason.fooder1;

import com.example.jason.fooder1.pojo.Business;
import com.example.jason.fooder1.pojo.SearchResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface YelpV3API {
   String BUSINESS_PATH = "/v3/businesses/{id}";
   String SEARCH_PATH = "/v3/businesses/search";

   @GET(BUSINESS_PATH)
   Call<Business> business(@Path("id") String id, @Query("locale") String locale);
   @GET(SEARCH_PATH)
   Call<SearchResponse> search(@QueryMap Map<String, String> parameters);

}