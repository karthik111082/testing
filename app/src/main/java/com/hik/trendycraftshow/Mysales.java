package com.hik.trendycraftshow;

import android.support.v4.app.Fragment;
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

import com.hik.trendycraftshow.Utils.Consts;
import com.hik.trendycraftshow.Utils.IsTablet;
import com.mikhaellopez.circularimageview.CircularImageView;

public class Mysales extends NavigationDrawer {

    IsTablet tablet;
    boolean isTablet;

    TextView toatl_sale,usersince,name;
    ListView business_list;
    CircularImageView photo;
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
        photo=(CircularImageView)findViewById(R.id.profile_image_mysales);
        toatl_sale=(TextView)findViewById(R.id.tatal_sale);
        usersince=(TextView)findViewById(R.id.member_date);
        business_list=(ListView)findViewById(R.id.business_list);
        name=(TextView)findViewById(R.id.name);
        setData();


    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK) {

        }
        return false;

    }
public void setData()
{
    name.setText(Consts.FirstName);
    usersince.setText(Consts.UserSince);
    if(Consts.Photo==null)
    {
        photo.setBackgroundResource(R.drawable.avator);
    }
    else
    {
        photo.setImageBitmap(Consts.Photo);
    }

}

}
