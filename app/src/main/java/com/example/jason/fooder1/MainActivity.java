package com.example.jason.fooder1;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jason.fooder1.pojo.AccessToken;
import com.example.jason.fooder1.pojo.SearchResponse;
import com.google.firebase.auth.FirebaseAuth;
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
    private String test = "";
    private static final String CLIENT_ID = "uX4P1ceVg4_kfsi-miohDA"; // ENTER CLIENT_ID
    private static final String CLIENT_SECRET = "4fOdvNkltK7LWrf4poQkEhBgUVGDJKk86oziaMIjgiiFsIyVmQlVaART0jhFtMDO"; // ENTER CLIENT_SECRET
    public static SearchResponse searchResponse;
    private Button bucketList_btn;
    private TextView bucketList_text;
    private ArrayList myList = new ArrayList();
    private ArrayList<String> addressList = new ArrayList();
    private int counter = 0;
    private double longitude = 1;
    private double latitude = 1;
    private Location location;
    private static final int MY_PERMISSIONS_ACCESS_FINE_LOCATION = 2;
    private LocationManager lm;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private List<String> bus;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startFooder();
    }
    @Override
    protected void onRestart(){
        super.onRestart();
        finish();
        startActivity(getIntent());

        Toast.makeText(MainActivity.this, "RESTARTING", Toast.LENGTH_SHORT).show();
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
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_ACCESS_FINE_LOCATION: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    private final LocationListener ll = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void startFooder()
    {
        myName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();

        getSupportActionBar().setTitle("Welcome " + myName);
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
        bucketList_text = (TextView) findViewById(R.id.bList_text);



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

        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_ACCESS_FINE_LOCATION);
            }
        }
        else {
            lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            location = lm.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            latitude = location.getLatitude();
            longitude = location.getLongitude();


//            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0 , ll);
//            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0 , ll);

        }

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        assert (CLIENT_ID != null && CLIENT_SECRET != null);

        final YelpV3APIProvider factory = new YelpV3APIProvider(CLIENT_ID, CLIENT_SECRET);

        final HashMap<String, String> params = new HashMap<String, String>();
        try {
            final AccessToken accessToken = factory.getAccessToken().execute().body();
            final YelpV3API yelp = factory.getAPI(accessToken.access_token);
            //params.put("location", SAMPLE_LOCATION);
            params.put("latitude", String.valueOf(latitude));
            params.put("longitude", String.valueOf(longitude));
            params.put("term", "restaurants");
            int radius = getIntent().getExtras().getInt("seekBar");
            radius = (int) (radius * 1609.34);
            params.put("radius", String.valueOf(radius));
            String price = "";
            boolean price1 = getIntent().getExtras().getBoolean("price1");
            boolean price2 = getIntent().getExtras().getBoolean("price2");
            boolean price3 = getIntent().getExtras().getBoolean("price3");
            boolean price4 = getIntent().getExtras().getBoolean("price4");

            if(price1) {
                price += "1,";
            }
            if(price2) {
                price +="2,";
            }
            if(price3) {
                price +="3,";
            }
            if(price4) {
                price +="4,";
            }
            if(price.endsWith(",")) {
                price = price.substring(0, price.length()-1);
            }
            if(price1 || price2 || price3 || price4){
                params.put("price", price);
            }
            Call<SearchResponse> searchCall = yelp.search(params);
            searchResponse = searchCall.execute().body();
            bus = Utils.loadProfiles();
            for (int i = 0; i < bus.size();i++) {
                try {
                    TinderCard tc = new TinderCard(mContext, bus.get(i), mSwipeView);
                    addressList.add(tc.getAddress());
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

                test+= "\n";
                test += myList.get(counter).toString() + "\n";
                update();
                counter++;
            }
        });
        findViewById(R.id.mapBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + addressList.get(counter).replace("[","").replace("]","").replace("\"","")));
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                startActivity(intent);
            }
        });
        findViewById(R.id.prof_pic2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Settings.class);
                //intent.setClass(android.content.Intent.ACTION_VIEW, Settings.class);
                startActivity(intent);
            }
        });
    }
}