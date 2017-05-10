package com.example.jason.fooder1;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jason.fooder1.pojo.SearchResponse;
import com.google.gson.Gson;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeInState;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutState;

import org.json.JSONArray;
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

    private SearchResponse mBusiness;
    private Context mContext;
    private SwipePlaceHolderView mSwipeView;

    public TinderCard(Context context, SearchResponse bus, SwipePlaceHolderView swipeView) {
        mContext = context;
        mBusiness = bus;
        mSwipeView = swipeView;
    }

    @Resolve
    private void onResolved() throws JSONException{
        Gson gson = new Gson();
        String business = gson.toJson(mBusiness);

        JSONObject jsonObject = new JSONObject(business);
       // Log.d("test", jsonObject.toString());
        JSONArray getBusinesses = jsonObject.getJSONArray("businesses");
        //Log.d("test", getBusinesses.toString());
        String businessString = jsonObject.getString("businesses");
        String nameString = getBusinesses.getJSONObject(0).getString("name");
        String imgString = getBusinesses.getJSONObject(0).getString("image_url");
        String priceString = getBusinesses.getJSONObject(0).getString("price");
        String getLocation = getBusinesses.getJSONObject(0).getString("location");
        JSONObject getAddress = new JSONObject(getLocation);
        String location = getAddress.getString("display_address");
        Log.d("test", location);
        //String address = getLocation.getJSONObject(0).getString("display_address");

        Glide.with(mContext).load(imgString).into(profileImageView);
        namePriceTxt.setText(nameString + ", " + priceString);
        locationNameTxt.setText(location);
    }

    @SwipeOut
    private void onSwipedOut(){
        Log.d("EVENT", "onSwipedOut");
        mSwipeView.addView(this);
    }

    @SwipeCancelState
    private void onSwipeCancelState(){
        Log.d("EVENT", "onSwipeCancelState");
    }

    @SwipeIn

    private void onSwipeIn(){
        Log.d("EVENT", "onSwipedIn");
    }

    @SwipeInState
    private void onSwipeInState(){
        Log.d("EVENT", "onSwipeInState");
    }

    @SwipeOutState
    private void onSwipeOutState(){
        Log.d("EVENT", "onSwipeOutState");
    }
}