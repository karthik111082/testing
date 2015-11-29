package com.hik.trendycraftshow;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.hik.trendycraftshow.Utils.IsTablet;

public class Introduction extends AppCompatActivity {

    Button back;
    boolean isTablet;
    IsTablet tablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isTablet = tablet.isTablet(getApplicationContext());
        if (isTablet) {
            setContentView(R.layout.activity_introduction);
        } else {
            setContentView(R.layout.activity_introduction_mob);
        }
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        ImageAdapters adapter = new ImageAdapters(this);
        viewPager.setAdapter(adapter);
        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                finish();
                startActivity(i);

            }
        });

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK) {
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            finish();
            startActivity(i);

        }
        return false;

    }
}
