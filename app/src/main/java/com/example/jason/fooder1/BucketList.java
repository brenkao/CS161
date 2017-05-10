package com.example.jason.fooder1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.ArrayList;

public class BucketList extends AppCompatActivity {

    private ArrayList myList;
    private TextView bucketList_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bucketlist);

        bucketList_text = (TextView) findViewById(R.id.bucketlist_text);
        myList = new ArrayList();

        /**
         myList.add("hello");
         myList.add("how");
         myList.add("are");
         myList.add("you");
         myList.add("today");
         Toast.makeText(BucketList.this, "tostring:" + myList.get(0).toString(), Toast.LENGTH_SHORT).show();
         for(int i = 0; i < myList.size(); i++) {
         bucketList_text.setText(myList.get(i).toString());
         }
         **/
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_icon);

    }
}