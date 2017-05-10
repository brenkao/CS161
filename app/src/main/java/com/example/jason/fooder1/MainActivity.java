package com.example.jason.fooder1;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.example.jason.fooder1.pojo.AccessToken;
import com.example.jason.fooder1.pojo.Business;
import com.example.jason.fooder1.pojo.SearchResponse;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;

public class MainActivity extends AppCompatActivity {

    private SwipePlaceHolderView mSwipeView;
    private Context mContext;
    private ImageView Prof_Pic2;
    private String myName;
    private String myEmail;
    private String list;
    private static final String CLIENT_ID = "uX4P1ceVg4_kfsi-miohDA"; // ENTER CLIENT_ID
    private static final String CLIENT_SECRET = "4fOdvNkltK7LWrf4poQkEhBgUVGDJKk86oziaMIjgiiFsIyVmQlVaART0jhFtMDO"; // ENTER CLIENT_SECRET
    private static final String SAMPLE_BUSINESS_ID = "anchor-oyster-bar-san-francisco";
    private static final String SAMPLE_LOCATION = "San Jose, CA";
    public static SearchResponse searchResponse;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Bundle info = getIntent().getExtras();
        if(info != null) {
            myName = info.getString("myName");
            myEmail = info.getString("myEmail");
        }

        getSupportActionBar().setTitle("Welcome!! " + myName);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_icon);

//        Toast.makeText(MainActivity.this, "Welcome " + myName,
//                Toast.LENGTH_SHORT).show();


        //Prof_Pic2 = (ImageView)findViewById(R.id.prof_pic2);
        //Prof_Pic2.setImageDrawable(LoginPage.Prof_Pic.getDrawable());


        mSwipeView = (SwipePlaceHolderView)findViewById(R.id.swipeView);
        mContext = getApplicationContext();

        int bottomMargin = Utils.dpToPx(160);
        Point windowSize = Utils.getDisplaySize(getWindowManager());
        mSwipeView.getBuilder()
                .setDisplayViewCount(3)
                .setSwipeDecor(new SwipeDecor()
                        .setViewWidth(windowSize.x)
                        .setViewHeight(windowSize.y - bottomMargin)
                        .setViewGravity(Gravity.TOP)
                        .setPaddingTop(20)
                        .setRelativeScale(0.01f)
                        .setSwipeInMsgLayoutId(R.layout.tinder_swipe_in_msg_view)
                        .setSwipeOutMsgLayoutId(R.layout.tinder_swipe_out_msg_view));


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        assert (CLIENT_ID != null && CLIENT_SECRET != null);

        final YelpV3APIProvider factory = new YelpV3APIProvider(CLIENT_ID, CLIENT_SECRET);
        final Business business;
        List<Business> businessList = new ArrayList<>();
        List<SearchResponse> searchList = new ArrayList<>();

        try {
            final AccessToken accessToken = factory.getAccessToken().execute().body();
            final YelpV3API yelp = factory.getAPI(accessToken.access_token);
//            final Call<Business> businessCall = yelp.business(SAMPLE_BUSINESS_ID, null);
//            business = businessCall.execute().body();
            //businessList.add(business);
            final HashMap<String, String> params = new HashMap<String, String>();
            params.put("location", SAMPLE_LOCATION);
            params.put("term", "restaurants");
            final Call<SearchResponse> searchCall = yelp.search(params);
            searchResponse = searchCall.execute().body();


            //searchList.add(searchResponse);
            Log.d("test", searchList.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
//        final Gson gson = new Gson();
//        System.out.println("--Business API--");
//        System.out.println(gson.toJson(searchResponse));

        for(String bus: Utils.loadProfiles()){
            try {
                mSwipeView.addView(new TinderCard(mContext, bus, mSwipeView));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        findViewById(R.id.rejectBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeView.doSwipe(false);
            }
        });

        findViewById(R.id.acceptBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeView.doSwipe(true);
            }
        });
    }
}