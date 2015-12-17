package com.hik.trendycraftshow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.hik.trendycraftshow.Utils.IsTablet;

/**
 * Created by DHARMA on 12/16/2015.
 */
public class PostAddChooser  extends NavigationDrawer {
    IsTablet tablet;
    boolean isTablet;
    ImageButton art,craft,expo,fairs,trendy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isTablet = tablet.isTablet(getApplicationContext());
        if (isTablet) {
            getLayoutInflater().inflate(R.layout.activity_home, container);
        } else {
            getLayoutInflater().inflate(R.layout.activity_home_mob, container);
        }
        art=(ImageButton)findViewById(R.id.art);
        craft=(ImageButton)findViewById(R.id.craft);
        expo=(ImageButton)findViewById(R.id.expo);
        fairs=(ImageButton)findViewById(R.id.fairs);
        trendy=(ImageButton)findViewById(R.id.trendy);
        art.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),PostAdvertisment.class);
                i.putExtra("category",1);
                startActivity(i);
                finish();
            }
        });
        craft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),PostAdvertisment.class);
                i.putExtra("category",2);
                startActivity(i);
                finish();
            }
        });
        expo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), PostAdvertisment.class);
                i.putExtra("category",3);
                startActivity(i);
                finish();
            }
        });
        fairs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),PostAdvertisment.class);
                i.putExtra("category",4);
                startActivity(i);
                finish();
            }
        });
        trendy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),PostAdvertismentTrendyMarket.class);
                startActivity(i);
                finish();
            }
        });




    }

}
