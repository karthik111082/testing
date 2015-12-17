package com.hik.trendycraftshow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.hik.trendycraftshow.Utils.IsTablet;

public class MyfollowUp extends NavigationDrawer {
    IsTablet tablet;
    boolean isTablet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   setContentView(R.layout.activity_myfollow_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        title=(TextView)findViewById(R.id.titletoolbar);
        title.setText("MY FOLLOW-UP");
        if (isTablet) {
            getLayoutInflater().inflate(R.layout.activity_myfollow_up, container);
        } else {
            getLayoutInflater().inflate(R.layout.activity_myfollow_up_mob, container);
        }
    }
}
