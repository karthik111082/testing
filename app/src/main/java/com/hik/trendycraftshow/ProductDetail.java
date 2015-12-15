package com.hik.trendycraftshow;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hik.trendycraftshow.Utils.IsTablet;

public class ProductDetail extends NavigationDrawer {
    IsTablet tablet;
    boolean isTablet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title.setText("CRAFT SHOW");
        ViewPager viewPager = (ViewPager) findViewById(R.id.product_viewpager);

        isTablet=tablet.isTablet(getApplicationContext());
        if (isTablet) {
            getLayoutInflater().inflate(R.layout.activity_product_detail, container);
        } else {
            getLayoutInflater().inflate(R.layout.activity_product_detail_mob, container);
        }
    }
}
