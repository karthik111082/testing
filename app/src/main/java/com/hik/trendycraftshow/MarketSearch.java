package com.hik.trendycraftshow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;

import com.hik.trendycraftshow.Utils.IsTablet;

/**
 * Created by HP on 26-11-2015.
 */
public class MarketSearch extends Activity {
    boolean isTablet;
    IsTablet tablet;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isTablet = tablet.isTablet(getApplicationContext());
        if (isTablet) {
            setContentView(R.layout.activity_marketsearch);
        } else {
            setContentView(R.layout.activity_marketsearch_mob);
        }
        back=(ImageButton)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(getApplicationContext(),MainActivity.class);
                finish();
                startActivity(i);
            }
        });

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
