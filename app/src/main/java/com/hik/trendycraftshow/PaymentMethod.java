package com.hik.trendycraftshow;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hik.trendycraftshow.Utils.IsTablet;

public class PaymentMethod extends NavigationDrawer {

    IsTablet tablet;
    boolean isTablet;
    ListView payment_list;
    EditText paypal_emailid;
    Button add_emailid,addpaypal;
    LinearLayout add_layout,addemail_layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        title=(TextView)findViewById(R.id.titletoolbar);

        payment_list=(ListView)findViewById(R.id.paymentmethod_listview);
        addpaypal=(Button)findViewById(R.id.add);
        paypal_emailid=(EditText)findViewById(R.id.paypal_emailid);
        add_emailid=(Button)findViewById(R.id.addpaypal_emailid);
        add_layout=(LinearLayout)findViewById(R.id.add_layout);
        addemail_layout=(LinearLayout)findViewById(R.id.addemail_layout);




        title.setText("PAYMENT METHOD");
        isTablet=tablet.isTablet(getApplicationContext());
        isTablet = tablet.isTablet(getApplicationContext());
        if (isTablet) {
            getLayoutInflater().inflate(R.layout.activity_payment_method, container);
        } else {
            getLayoutInflater().inflate(R.layout.activity_payment_method_mob, container);
        }

        addpaypal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addemail_layout.setVisibility(View.VISIBLE);
                add_layout.setVisibility(View.GONE);
            }
        });

        add_emailid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addemail_layout.setVisibility(View.GONE);
                add_layout.setVisibility(View.VISIBLE);
            }
        });




    }

}