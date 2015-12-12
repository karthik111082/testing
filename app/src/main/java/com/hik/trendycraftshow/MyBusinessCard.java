package com.hik.trendycraftshow;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.hik.trendycraftshow.Utils.IsTablet;

public class MyBusinessCard extends NavigationDrawer {
    boolean isTablet;
    IsTablet tablet;

    TextView toatl_sale,date;
    ListView business_list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_business_card);

        toatl_sale=(TextView)findViewById(R.id.tatal_sale);
        date=(TextView)findViewById(R.id.member_date);
        business_list=(ListView)findViewById(R.id.business_list);


    }
}
