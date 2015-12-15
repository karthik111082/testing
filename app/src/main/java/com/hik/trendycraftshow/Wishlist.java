package com.hik.trendycraftshow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.TextView;

public class Wishlist extends NavigationDrawer {
        ListView wish_list_view;

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

        wish_list_view=(ListView)findViewById(R.id.wish_list_view);

    }
}
