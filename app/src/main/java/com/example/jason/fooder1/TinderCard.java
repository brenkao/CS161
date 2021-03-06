package com.example.jason.fooder1;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeInState;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutState;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jason on 3/14/2017.
 */

@Layout(R.layout.tinder_card_view)
public class TinderCard {

    @View(R.id.profileImageView)
    private ImageView profileImageView;

    @View(R.id.namePriceTxt)
    private TextView namePriceTxt;

    @View(R.id.locationNameTxt)
    private TextView locationNameTxt;

    private String mBusiness;
    private Context mContext;
    private SwipePlaceHolderView mSwipeView;
    private String business;
    private JSONObject mBusiness2;
    private JSONObject getAddress;
    private JSONObject getCoord;
    private double distance;
    private final double milesConvert = 0.000621371;

    public TinderCard(Context context, String bus, SwipePlaceHolderView swipeView) throws JSONException {
        mContext = context;
        mBusiness = bus;
        mSwipeView = swipeView;

        mBusiness2 = new JSONObject(mBusiness);
        String getLocation = mBusiness2.getString("location");
        getAddress = new JSONObject(getLocation);
        String getCoordinates = mBusiness2.getString("coordinates");
        getCoord = new JSONObject((getCoordinates));

        distance = Double.parseDouble(mBusiness2.getString("distance"));
        distance = distance * milesConvert;



    }

    @Resolve
    public void onResolved() throws JSONException{

        Glide.with(mContext).load(mBusiness2.getString("image_url")).into(profileImageView);
        namePriceTxt.setText(mBusiness2.getString("name") + ", " + mBusiness2.getString("price") + ", " + String.format("%.2f", distance) + " miles");
        locationNameTxt.setText(getAddress.getString("display_address").replace("[","").replace("]","").replace("\"",""));


    }
    public String getName() throws JSONException
    {
        return mBusiness2.getString("name");
    }

    @SwipeIn
    public void onSwipeIn() {
        MainActivity.counter++;
        Log.d("EVENT", "onSwipedIn");
    }

    @SwipeCancelState
    private void onSwipeCancelState(){
        Log.d("EVENT", "onSwipeCancelState");
    }

    @SwipeOut
    public void onSwipedOut(){
        MainActivity.counter++;
        Log.d("EVENT", "onSwipedOut");
    }
    @SwipeInState
    private void onSwipeInState(){
        Log.d("EVENT", "onSwipeInState");
    }

    @SwipeOutState
    private void onSwipeOutState(){
        Log.d("EVENT", "onSwipeOutState");
    }

    public String getAddress() throws JSONException{
        return getAddress.getString("display_address");
    }

}