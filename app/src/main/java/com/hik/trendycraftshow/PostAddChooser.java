package com.hik.trendycraftshow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (TextView) findViewById(R.id.titletoolbar);
        title.setText("MY PROFILE");
        isTablet = tablet.isTablet(getApplicationContext());
        if (isTablet) {
            getLayoutInflater().inflate(R.layout.activity_postadd_chooser, container);
        } else {
            getLayoutInflater().inflate(R.layout.activity_postadd_chooser_mob, container);
        }
        art=(ImageButton)findViewById(R.id.art);
        craft=(ImageButton)findViewById(R.id.craft);
        expo=(ImageButton)findViewById(R.id.expo);
        fairs=(ImageButton)findViewById(R.id.fairs);
        trendy=(ImageButton)findViewById(R.id.trendy);
        art.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Clicked","clicked");
                Intent i=new Intent(getApplicationContext(),PostAdvertisment.class);
                i.putExtra("category", 2);
                finish();
                startActivity(i);

            }
        });
        craft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),PostAdvertisment.class);
                i.putExtra("category", 1);
                finish();
                startActivity(i);

            }
        });
        expo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), PostAdvertisment.class);
                i.putExtra("category", 3);
                finish();
                startActivity(i);

            }
        });
        fairs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),PostAdvertisment.class);
                i.putExtra("category", 4);
                finish();
                startActivity(i);

            }
        });
        trendy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),PostAdvertismentTrendyMarket.class);
                finish();
                startActivity(i);

            }
        });




    }

}
