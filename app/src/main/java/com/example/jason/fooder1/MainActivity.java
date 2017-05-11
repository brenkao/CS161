package com.example.jason.fooder1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jason.fooder1.pojo.AccessToken;
import com.example.jason.fooder1.pojo.Business;
import com.example.jason.fooder1.pojo.SearchResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
    private String test;
    private String temp;
    private static final String CLIENT_ID = "uX4P1ceVg4_kfsi-miohDA"; // ENTER CLIENT_ID
    private static final String CLIENT_SECRET = "4fOdvNkltK7LWrf4poQkEhBgUVGDJKk86oziaMIjgiiFsIyVmQlVaART0jhFtMDO"; // ENTER CLIENT_SECRET
    private static final String SAMPLE_BUSINESS_ID = "anchor-oyster-bar-san-francisco";
    private static final String SAMPLE_LOCATION = "San Jose, CA";
    public static SearchResponse searchResponse;
    private Button bucketList_btn;
    private TextView bucketList_text;
    private ArrayList myList = new ArrayList();
    private int counter = 0;
    private Button logOutButton;
    private TextView userName;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_icon);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser() == null) {
                    startActivity(new Intent(MainActivity.this, LoginPage.class));
                }
            }
        };

        mSwipeView = (SwipePlaceHolderView)findViewById(R.id.swipeView);
        mContext = getApplicationContext();
        userName = (TextView) findViewById(R.id.u_name);
        test = temp = "";
        bucketList_text = (TextView) findViewById(R.id.bList_text);

        logOutButton = (Button) findViewById(R.id.bn_logout);
        logOutButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mAuth.signOut();
            }
        });

        userName.setText(mAuth.getCurrentUser().getDisplayName());

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

        final HashMap<String, String> params = new HashMap<String, String>();
        try {
            final AccessToken accessToken = factory.getAccessToken().execute().body();
            final YelpV3API yelp = factory.getAPI(accessToken.access_token);
            params.put("location", SAMPLE_LOCATION);
            params.put("term", "restaurants");
            params.put("limit", "5");
            Call<SearchResponse> searchCall = yelp.search(params);
            searchResponse = searchCall.execute().body();

            List<String> bus = Utils.loadProfiles();
            Collections.shuffle(bus);
            //searchList.add(searchResponse);
            Log.d("test", searchList.toString());
//        final Gson gson = new Gson();
//        System.out.println("--Business API--");
//        System.out.println(gson.toJson(searchResponse));
            for (int i = 0; i < bus.size();i++) {
                try {
                    TinderCard tc = new TinderCard(mContext, bus.get(i), mSwipeView);
                    myList.add(tc.getName());
                    mSwipeView.addView(tc);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        findViewById(R.id.rejectBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeView.doSwipe(false);
                counter++;
            }
        });

        findViewById(R.id.acceptBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeView.doSwipe(true);

                test+= temp + "\n";
                counter++;
                test += myList.get(counter).toString() + "\n";
                update();
            }
        });

    }
    public void update()
    {
        bucketList_text.setText(test);
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth.addAuthStateListener(mAuthListener);
    }
    // Doesn't work yet
    public void change() {
        Toast.makeText(MainActivity.this, "Button Pressed",
                Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, BucketList.class);
        startActivity(intent);
    }
}