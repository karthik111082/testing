package com.hik.trendycraftshow;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.hik.trendycraftshow.Utils.IsTablet;

public class PaymentAndPurchase extends NavigationDrawer {


    IsTablet tablet;
    boolean isTablet;
    private static final String ALL_TIME = "AllTime";
    private static final String MONTH = "Month";
    RadioButton order,payment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        title=(TextView)findViewById(R.id.titletoolbar);
        title.setText("PAYMENT AND PURCHASE");
        isTablet=tablet.isTablet(getApplicationContext());
        isTablet = tablet.isTablet(getApplicationContext());
        if (isTablet) {
            getLayoutInflater().inflate(R.layout.activity_payment_and_purchase, container);
        } else {
            getLayoutInflater().inflate(R.layout.activity_payment_and_purchase_mob, container);
        }
        order=(RadioButton)findViewById(R.id.order);
        payment=(RadioButton)findViewById(R.id.payment);
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order.setChecked(true);
                payment.setChecked(false);

            }
        });
       payment.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        order.setChecked(false);
        payment.setChecked(true);

    }
});

    }
}

