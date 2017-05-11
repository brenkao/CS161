package com.example.jason.fooder1;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Context;
import android.graphics.Point;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private SwipePlaceHolderView mSwipeView;
    private Context mContext;
    private ImageView Prof_Pic2;
    private String myName;
    private String myEmail;
    private Button logOutButton;
    private Button bucketList_btn;
    private TextView userName;

    private String test;
    private String temp;
    private int counter;

    private ArrayList myList;
    private TextView bucketList_text;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
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

        mSwipeView = (SwipePlaceHolderView) findViewById(R.id.swipeView);
        mContext = getApplicationContext();
        userName = (TextView) findViewById(R.id.u_name);
        test = temp = "";
        counter = 0;
        bucketList_text = (TextView) findViewById(R.id.bList_text);
        logOutButton = (Button) findViewById(R.id.bn_logout);
        logOutButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mAuth.signOut();
            }
        });

        // Get current user's display name
        //userName.setText(mAuth.getCurrentUser().getDisplayName());
        userName.setText(mAuth.getCurrentUser().getDisplayName());

        //Prof_Pic2.setImageDrawable(mAuth.getCurrentUser().getPhotoUrl());
        //Prof_Pic2.setImageDrawable();

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


        myList = new ArrayList();

        for (Profile profile : Utils.loadProfiles(this.getApplicationContext())) {
            // Creating the card like this so I can pull out individual attributes
            TinderCard asdf = new TinderCard(mContext, profile, mSwipeView);
            myList.add(asdf.getName());
            mSwipeView.addView(asdf);
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

                // If swipped yes, add the card to the list
                test += temp + "\n";
                counter++;
                test += myList.get(counter).toString() + "\n";
                update();
            }
        });
    }

    public void update() {
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