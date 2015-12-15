package com.hik.trendycraftshow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.widget.ListView;
import android.widget.TextView;

import com.hik.trendycraftshow.Utils.IsTablet;

public class MyBusinessCard extends NavigationDrawer {
    boolean isTablet;
    IsTablet tablet;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        title=(TextView)findViewById(R.id.titletoolbar);
        title.setText("MY BUSSINESS CARD");
        isTablet = tablet.isTablet(getApplicationContext());
        if (isTablet) {
            getLayoutInflater().inflate(R.layout.activity_my_business_card, container);
        } else {
            getLayoutInflater().inflate(R.layout.activity_my_business_card_mob, container);
        }

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK) {
            Intent i=new Intent(getApplicationContext(),MainActivity.class);
            finish();
            startActivity(i);
        }
        return false;

    }
}

