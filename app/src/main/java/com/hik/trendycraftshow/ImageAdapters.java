package com.hik.trendycraftshow;

import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;

/**
 * Created by DHARMA on 11/29/2015.
 */
public class ImageAdapters extends PagerAdapter {
    Context context;
    private int[] GalImages = new int[] {
            R.drawable.one,
            R.drawable.two,
            R.drawable.three,
            R.drawable.four,
            R.drawable.five

    };
    ImageAdapters(Context context){
        this.context=context;
    }
    @Override
    public int getCount() {
        return GalImages.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ImageView) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);

        int padding = 10;
        imageView.setPadding(padding, padding, padding, padding);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageView.setImageResource(GalImages[position]);
        ((ViewPager) container).addView(imageView, 0);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ImageView) object);
    }

    public static class Payment extends TabActivity {
        private static final String ALL_TIME = "AllTime";
        private static final String MONTH = "Month";
        ProgressDialog pDialog;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_payment_method);

        }

        private View makeTabIndicator(Drawable drawable) {
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
                    Intent i = new Intent(Payment.this, Mysales.OrderFragment.class);
                    // Tab Content
                    alltime.setContent(i);

                    // Month Tab
                    TabHost.TabSpec month = tabHost.newTabSpec(MONTH);
                    month.setIndicator(makeTabIndicator(getResources().getDrawable(R.drawable.artshow2)));
                    Intent monthi = new Intent(Payment.this, LauncherActivity.PaymentFragment.class);
                    month.setContent(monthi);


                    // Adding all TabSpec to TabHost
                    tabHost.addTab(alltime); // Adding All Time tab
                    tabHost.addTab(month); // Adding Month tab
                }
            });
        }
        }
}