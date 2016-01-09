package com.hik.trendycraftshow;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.hik.trendycraftshow.ListAdapters.ProductListAdapter;
import com.hik.trendycraftshow.Utils.Consts;
import com.hik.trendycraftshow.Utils.GPSTracker;
import com.hik.trendycraftshow.Utils.InternetStatus;
import com.hik.trendycraftshow.Utils.IsTablet;
import com.hik.trendycraftshow.Utils.Validation;

import java.util.Calendar;

/**
 * Created by HP on 26-11-2015.
 */
public class BusinessSearch extends NavigationDrawer {
    boolean isTablet;
    IsTablet tablet;

    EditText keyword, streetaddress, city, zipcode;
    TextView distance,startdate,enddate;
    SeekBar seek;
    Button business_search;
    Spinner category;
    boolean isListView;


    int Distance=0, catid;
    String Keywords="",Street="",City="",Zip="",Category="";
    double latitude=0.0;
    double longitude=0.0;
    GPSTracker gps;

    InternetStatus internetStatus;


    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isTablet = tablet.isTablet(getApplicationContext());

        title=(TextView)findViewById(R.id.titletoolbar);
        title.setText("SEARCH");

        if (isTablet) {
            getLayoutInflater().inflate(R.layout.activity_business_search, container);
        } else {
            getLayoutInflater().inflate(R.layout.activity_business_search_mob, container);
        }

        GpsLocation();
        catid=getIntent().getExtras().getInt("catid");

        keyword=(EditText)findViewById(R.id.keyword_c);
        streetaddress=(EditText)findViewById(R.id.street_address);
        city=(EditText)findViewById(R.id.city_c);
        zipcode=(EditText)findViewById(R.id.zipcode_c);
        seek=(SeekBar)findViewById(R.id.seek_c);
        business_search=(Button)findViewById(R.id.business_search);
        distance=(TextView)findViewById(R.id.tx1_c);
        startdate=(TextView)findViewById(R.id.startdate);
        enddate=(TextView)findViewById(R.id.enddate);
        category=(Spinner)findViewById(R.id.category);





        business_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Keywords=keyword.getText().toString();
                Street= streetaddress.getText().toString();
                City = city.getText().toString();
                Zip = zipcode.getText().toString();
                Category = category.getSelectedItem().toString();

                if (category.getSelectedItemPosition() > 0)
                {
                    catid=category.getSelectedItemPosition()+4;
                    Intent i = new Intent(getApplicationContext(), BusinessCardList.class);
                    finish();
                    i.putExtra("catid", catid);
                    i.putExtra("keyword", Keywords);
                    i.putExtra("street", Street);
                    i.putExtra("city", City);
                    i.putExtra("zip", Zip);
                    i.putExtra("distance", String.valueOf(Distance));
                    i.putExtra("isSearch", true);
                    i.putExtra("latitude", String.valueOf(latitude));
                    i.putExtra("longitude", String.valueOf(longitude));
                    i.putExtra("category", Category);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Please choose category!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Distance = progress;
                distance.setText(String.valueOf(progress) + " MILES");
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK) {
        }
        return false;    }
    public void GpsLocation()
    {
        gps = new GPSTracker(BusinessSearch.this);
        // check if GPS enabled
        if(gps.canGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            // \n is for new line
        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
    }
}
