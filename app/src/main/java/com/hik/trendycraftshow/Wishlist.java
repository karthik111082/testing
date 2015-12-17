package com.hik.trendycraftshow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

public class Wishlist extends NavigationDrawer {
        ListView list;
    RadioButton ads,businessdirec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        title=(TextView)findViewById(R.id.titletoolbar);
        title.setText("WISHLIST");
        if (isTablet) {
            getLayoutInflater().inflate(R.layout.activity_wishlist, container);
        } else {
            getLayoutInflater().inflate(R.layout.activity_wishlist_mob, container);
        }

        list=(ListView)findViewById(R.id.list_wish);
        ads=(RadioButton)findViewById(R.id.ads);
        businessdirec=(RadioButton)findViewById(R.id.businessdirec);
        ads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ads.setChecked(true);
                businessdirec.setChecked(false);
            }
        });
        businessdirec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ads.setChecked(false);
                businessdirec.setChecked(true);

            }
        });

    }
}
