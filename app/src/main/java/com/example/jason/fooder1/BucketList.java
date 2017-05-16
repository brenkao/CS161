package com.example.jason.fooder1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BucketList extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;

    private List<String> tempList = new ArrayList<String>(); // Temporary list to hold list array for parsing
    private String TAG = "myDebug";
    private String myUID, myFavorites = "", myFavorite_listview = "";
    private int favCounter = 0; // Counter to ensure starting favorites only inputted once
    private List<String> bus;
    private ArrayList myList;
    private Button back_button;
    private TextView bucketList_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bucketlist);

        bucketList_text = (TextView) findViewById(R.id.bucketlist_text);
        back_button = (Button) findViewById(R.id.back_button);
        myList = new ArrayList();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser() == null) {
                    startActivity(new Intent(BucketList.this, LoginPage.class));
                }
            }
        };
        myUID = mAuth.getCurrentUser().getUid();

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_icon);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cya = new Intent(BucketList.this, Settings.class);
                startActivity(cya);
            }
        });

        // Iterates through firebase database
        mDatabase.child(myUID).addValueEventListener(new ValueEventListener() {

            // Invokes anytime data on database changes, as well as oncreate for initial snapshot
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // get all children defined in above child
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                String favs; // not necessary, but used as a saftey string

                for (DataSnapshot child : children) {
                    // Only add favorites at start once, since this method is repeated many times
                    if (favCounter <= 0) {
                        favs = child.getValue().toString();
                        getFavorites(favs);
                    }

                    favCounter++;
                }

                // This increment is for when user has no favorites. Skips a double input of the first favorite
                favCounter++;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth.addAuthStateListener(mAuthListener);
    }

    // Get favorites when logging in
    private void getFavorites(String favs) {
        tempList = parseFavorites(favs);

        // Break favs into individual inputs
        for (int i = 0; i < tempList.size(); i++)
            myFavorite_listview += tempList.get(i) + "\n";

        bucketList_text.setText(myFavorite_listview);
    }

    // Function to parse user's favorites (string)
    private List<String> parseFavorites(String incomingFavorites){
        List<String> parsedList = new ArrayList<String>(Arrays.asList(incomingFavorites.split(",")));
        return parsedList;
    }
}