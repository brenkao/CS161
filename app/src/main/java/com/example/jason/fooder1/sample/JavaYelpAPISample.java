package com.example.jason.fooder1.sample;

import com.google.gson.Gson;
import com.example.jason.fooder1.YelpV3API;
import com.example.jason.fooder1.YelpV3APIProvider;
import com.example.jason.fooder1.pojo.AccessToken;
import com.example.jason.fooder1.pojo.Business;

import java.io.IOException;
import java.util.HashMap;

import retrofit2.Call;

public class JavaYelpAPISample {
   private static final String CLIENT_ID = "uX4P1ceVg4_kfsi-miohDA"; // ENTER CLIENT_ID
   private static final String CLIENT_SECRET = "4fOdvNkltK7LWrf4poQkEhBgUVGDJKk86oziaMIjgiiFsIyVmQlVaART0jhFtMDO"; // ENTER CLIENT_SECRET

   private static final String SAMPLE_LOCATION = "San Francisco, CA";
   private static final String SAMPLE_BUSINESS_ID = "anchor-oyster-bar-san-francisco";
   private static final String SAMPLE_PHONE_NUMBER = "+14159083801";
   private static final String SAMPLE_TRANSACTION_TYPE = "delivery";
   private static final String SAMPLE_AUTOCOMPLETE_TEXT = "del";

   public static void main(String... args) throws IOException {
      // Make sure to enter CLIENT_ID and CLIENT_SECRET
      assert (CLIENT_ID != null && CLIENT_SECRET != null);

      final YelpV3APIProvider factory = new YelpV3APIProvider(CLIENT_ID, CLIENT_SECRET);
      final AccessToken accessToken = factory.getAccessToken().execute().body();

      final YelpV3API yelp = factory.getAPI(accessToken.access_token);

      // Search API
      final HashMap<String, String> params = new HashMap<String, String>();
      params.put("location", SAMPLE_LOCATION);
      // Business API
      final Call<Business> businessCall = yelp.business(SAMPLE_BUSINESS_ID, null);
      final Business business = businessCall.execute().body();

      // Output results from API calls
      final Gson gson = new Gson();
      System.out.println("--Business API--");
      System.out.println(gson.toJson(business));
   }

}
