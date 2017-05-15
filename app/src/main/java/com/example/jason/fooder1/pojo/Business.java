package com.example.jason.fooder1.pojo;

import java.io.Serializable;
import java.util.List;

public class Business implements Serializable {

   public final Coordinates coordinates;
   public final Location location;
   public final String name;
   public final String price;
   public final String image_url;
   public final double rating;
   public final String distance;

   //---------------------------------------------------------------------------
   public Business(Coordinates coordinates,
                   Location location, String name,
                   String price, String image_url, double rating, String distance) {
      this.coordinates = coordinates;
      this.location = location;
      this.name = name;
      this.price = price;
      this.rating = rating;
      this.image_url = image_url;
      this.distance = distance;
   }

   //---------------------------------------------------------------------------
   public class Coordinates implements Serializable {
      public final double latitude;
      public final double longitude;

      public Coordinates(double latitude, double longitude) {
         this.latitude = latitude;
         this.longitude = longitude;
      }
   }

   //---------------------------------------------------------------------------
   public class Location implements Serializable {
      public final List<String> display_address;

      public Location(List<String> display_address) {
         this.display_address = display_address;
      }
   }
}
