package com.hik.trendycraftshow;

import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;

public class Payment extends TabActivity {
    private static final String ALL_TIME = "AllTime";
    private static final String MONTH = "Month";
    ProgressDialog pDialog;



    private View makeTabIndicator(Drawable drawable){
        ImageView Tabimage = new ImageView(this);
        LinearLayout.LayoutParams LP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT, 1);
        LP.setMargins(0, 0, 0, 0);
        Tabimage.setLayoutParams(LP);
        Tabimage.setImageDrawable(drawable);
        //Tabimage.setBackgroundResource(R.drawable.tabview);
        return Tabimage;


    }
    protected void onPostExecute(String file_url) {
        // dismiss the dialog after getting all products
        pDialog.dismiss();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TabHost tabHost = getTabHost();

                // All Time Tab
                TabHost.TabSpec alltime = tabHost.newTabSpec(ALL_TIME);
                // Tab Icon
                alltime.setIndicator(makeTabIndicator(getResources().getDrawable(R.drawable.artshow)));
                Intent i = new Intent(Payment.this, OrderFragment.class);
                // Tab Content
                alltime.setContent(i);

                // Month Tab
                TabHost.TabSpec month = tabHost.newTabSpec(MONTH);
                month.setIndicator(makeTabIndicator(getResources().getDrawable(R.drawable.artshow2)));
                Intent monthi = new Intent(Payment.this, PaymentFragment.class);
                month.setContent(monthi);



                // Adding all TabSpec to TabHost
                tabHost.addTab(alltime); // Adding All Time tab
                tabHost.addTab(month); // Adding Month tab
            }
        });

    }

}
