package com.example.jason.fooder1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by alexandriale on 5/9/17.
 */

public class Settings extends AppCompatActivity{


    private GoogleApiClient googleApiClient;

    private TextView ratings;
    private TextView distance;
    private TextView price;
    private TextView settings;
    private TextView miles;

    private RatingBar ratingBar;
    private SeekBar seekBar;
    private int progress;
    public float ratingNumber;

    public CheckBox check1;
    public CheckBox check2;
    public CheckBox check3;
    public CheckBox check4;

    public boolean price1 = false;
    public boolean price2 = false;
    public boolean price3 = false;
    public boolean price4 = false;

    public Intent intent;

    private Button signout;
    private Button fooder;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private GoogleApiClient mGoogleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser() == null) {
                    startActivity(new Intent(Settings.this, LoginPage.class));
                }
            }
        };

        ratings = (TextView) findViewById(R.id.rating);
        distance = (TextView) findViewById(R.id.distance);
        price = (TextView) findViewById(R.id.price);
        settings = (TextView) findViewById(R.id.settings);
        miles = (TextView) findViewById(R.id.miles);

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        seekBar = (SeekBar) findViewById(R.id.seekBar);

        ratingBar.setRating(3);
        progress = seekBar.getProgress();
        miles.setText(progress + "miles");
        ratingNumber = ratingBar.getRating();

        check1 = (CheckBox) findViewById(R.id.check1);
        check2 = (CheckBox) findViewById(R.id.check2);
        check3 = (CheckBox) findViewById(R.id.check3);
        check4 = (CheckBox) findViewById(R.id.check4);

        check1.setChecked(false);
        check2.setChecked(false);
        check3.setChecked(false);
        check4.setChecked(false);

        signout = (Button) findViewById(R.id.signout);
        fooder = (Button) findViewById(R.id.fooder);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                progress = i;
                miles.setText(progress + " miles");
<<<<<<< HEAD
                //distance.setText( progress + "miles");
=======
               //distance.setText( progress + "miles");
                intent.putExtra("seekBar", progress);
>>>>>>> master

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        intent = new Intent(Settings.this, MainActivity.class);
        intent.putExtra("price1", price1);
        intent.putExtra("price2", price2);
        intent.putExtra("price3", price3);
        intent.putExtra("price4", price4);
        intent.putExtra("seekBar", progress);
        intent.putExtra("rating", ratingNumber);

        check1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                price1 = isChecked;

                intent.putExtra("price1", price1);
            }
        });
        check2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                price2 = isChecked;

                intent.putExtra("price2", price2);
            }
        });
        check3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                price3 = isChecked;

                intent.putExtra("price3", price3);
            }
        });
        check4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                price4 = isChecked;

                intent.putExtra("price4", price4);
            }
        });

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent i = new Intent(Settings.this, LoginPage.class);
                startActivity(i);
            }
        });
        fooder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(intent);
            }

        });
<<<<<<< HEAD
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean b) {
=======

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingNumber = rating;
>>>>>>> master
            }
        });
        }
}








