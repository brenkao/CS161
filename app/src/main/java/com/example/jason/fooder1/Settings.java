package com.example.jason.fooder1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Button;

import com.google.android.gms.common.api.GoogleApiClient;
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
    private double progress;

    private RadioButton radio1;
    private RadioButton radio2;
    private RadioButton radio3;
    private RadioButton radio4;
    private RadioButton radio5;

    private Button signout;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser() == null) {
                    startActivity(new Intent(Settings.this, LoginPage.class));
                }
            }
        };

        ratings = (TextView)findViewById(R.id.rating);
        distance = (TextView) findViewById(R.id.distance);
        price = (TextView) findViewById(R.id.price);
        settings = (TextView) findViewById(R.id.settings);
        miles = (TextView) findViewById(R.id.miles);

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        progress = seekBar.getProgress();
        miles.setText(progress + "miles");

        radio1 = (RadioButton) findViewById(R.id.radio1);
        radio2 = (RadioButton) findViewById(R.id.radio2);
        radio3 = (RadioButton) findViewById(R.id.radio3);
        radio4 = (RadioButton) findViewById(R.id.radio4);
        radio5 = (RadioButton) findViewById(R.id.radio5);

        signout = (Button) findViewById(com.example.jason.fooder1.R.id.signout);


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                progress = i;
                miles.setText(progress + " miles");
               //distance.setText( progress + "miles");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                }

            });
        }
}








