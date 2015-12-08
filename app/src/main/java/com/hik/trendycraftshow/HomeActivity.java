package com.hik.trendycraftshow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;

import com.hik.trendycraftshow.Utils.IsTablet;

public class HomeActivity extends NavigationDrawer {

    boolean isTablet;
    IsTablet tablet;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        isTablet = tablet.isTablet(getApplicationContext());
        if (isTablet) {
            getLayoutInflater().inflate(R.layout.activity_home, container);
        } else {
            getLayoutInflater().inflate(R.layout.activity_home_mob, container);
        }



    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK) {
            /*Intent i=new Intent(getApplicationContext(),MainActivity.class);
            finish();
            startActivity(i);*/
        }
        return false;

    }
}
