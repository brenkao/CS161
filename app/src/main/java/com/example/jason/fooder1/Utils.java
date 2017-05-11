package com.example.jason.fooder1;

import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jason on 3/14/2017.
 */

public class Utils {

    private static final String TAG = "Utils";

    private static final String CLIENT_ID = "uX4P1ceVg4_kfsi-miohDA"; // ENTER CLIENT_ID
    private static final String CLIENT_SECRET = "4fOdvNkltK7LWrf4poQkEhBgUVGDJKk86oziaMIjgiiFsIyVmQlVaART0jhFtMDO"; // ENTER CLIENT_SECRET
    private static final String SAMPLE_BUSINESS_ID = "anchor-oyster-bar-san-francisco";
    private static final String SAMPLE_LOCATION = "San Jose, CA";






    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static List<String> loadProfiles(){
        try{

            Gson gson = new Gson();
            String business = gson.toJson(MainActivity.searchResponse);


            JSONObject jsonObject = new JSONObject(business);
            JSONArray getBusinesses = jsonObject.getJSONArray("businesses");

            //System.out.println("--Business API--");
            //System.out.println(gson.fromJson(getBusinesses.getString(0), SearchResponse.class));


            List<String> businessList = new ArrayList<>();
            for(int i=0;i<getBusinesses.length();i++){
                String business1 = getBusinesses.getString(i).toString();
                businessList.add(business1);

                //Log.d("test", getBusinesses.toString());
//            String businessString = jsonObject.getString("businesses");
//            String nameString = getBusinesses.getJSONObject(0).getString("name");
//            String imgString = getBusinesses.getJSONObject(0).getString("image_url");
//            String priceString = getBusinesses.getJSONObject(0).getString("price");
//            String getLocation = getBusinesses.getJSONObject(0).getString("location");
//            JSONObject getAddress = new JSONObject(getLocation);
//            String location = getAddress.getString("display_address");
//            Log.d("test", location);
////
//            //JSONArray array = new JSONArray(loadJSONFromAsset(contex)));
//            List<Profile> profileList = new ArrayList<>();
//            //for(int i=0;i<array.length();i++){
//              //  Profile profile = gson.fromJson(array.getString(i), Profile.class);
//                //profileList.add(profile);
            }

            return businessList;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

//    private static String loadJSONFromAsset(Context context, String jsonFileName) {
//        String json = null;
//        InputStream is=null;
//        try {
//            AssetManager manager = context.getAssets();
//            Log.d(TAG,"path "+jsonFileName);
//            is = manager.open(jsonFileName);
//            int size = is.available();
//            byte[] buffer = new byte[size];
//            is.read(buffer);
//            is.close();
//            json = new String(buffer, "UTF-8");
//        } catch (IOException ex) {
//            ex.printStackTrace();
//            return null;
//        }
//        return json;
//    }

    public static Point getDisplaySize(WindowManager windowManager){
        try {
            if(Build.VERSION.SDK_INT > 16) {
                Display display = windowManager.getDefaultDisplay();
                DisplayMetrics displayMetrics = new DisplayMetrics();
                display.getMetrics(displayMetrics);
                return new Point(displayMetrics.widthPixels, displayMetrics.heightPixels);
            }else{
                return new Point(0, 0);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new Point(0, 0);
        }
    }
    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
}