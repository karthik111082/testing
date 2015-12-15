package com.hik.trendycraftshow;

import android.app.Fragment;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.hik.trendycraftshow.Utils.IsTablet;

public class Mysales extends NavigationDrawer {

    IsTablet tablet;
    boolean isTablet;

    TextView toatl_sale,date;
    ListView business_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        title=(TextView)findViewById(R.id.titletoolbar);
        title.setText("MY SALES");
        isTablet=tablet.isTablet(getApplicationContext());
        isTablet = tablet.isTablet(getApplicationContext());
        if (isTablet) {
            getLayoutInflater().inflate(R.layout.activity_mysales, container);
        } else {
            getLayoutInflater().inflate(R.layout.activity_mysales_mob, container);
        }



        toatl_sale=(TextView)findViewById(R.id.tatal_sale);
        date=(TextView)findViewById(R.id.member_date);
        business_list=(ListView)findViewById(R.id.business_list);


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

    /**
     * Created by DHARMA on 12-Dec-15.
     */
    public static class OrderFragment extends Fragment {

        @Nullable

        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.order_history_tabsactivity,null);
        }
    }
}
