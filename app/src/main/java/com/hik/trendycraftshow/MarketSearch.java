package com.hik.trendycraftshow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hik.trendycraftshow.ListAdapters.ProductListAdapter;
import com.hik.trendycraftshow.Utils.GPSTracker;
import com.hik.trendycraftshow.Utils.IsTablet;

/**
 * Created by HP on 26-11-2015.
 */
public class MarketSearch extends NavigationDrawer {
    boolean isTablet;
    IsTablet tablet;
    ImageButton back;
    EditText keyword,streetaddress,city,zipcode;
    TextView minprice,maxprice;
    Spinner sp;
    Button search;
    SeekBar seek1,seek2;
    boolean isListView;
    int Distance=0,catid;
    String minPrice="",maxPrice="";
    String Keywords="",Street="",City="",Zip="",Startdate="",Enddate="";

    double latitude;
    double longitude;
    GPSTracker gps;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        title=(TextView)findViewById(R.id.titletoolbar);
        title.setText("Trendy Market Search");
        isTablet = tablet.isTablet(getApplicationContext());
        if (isTablet) {
            getLayoutInflater().inflate(R.layout.activity_marketsearch, container);
        } else {
            getLayoutInflater().inflate(R.layout.activity_marketsearch_mob, container);
        }
        GpsLocation();
        catid=getIntent().getExtras().getInt("catid");
        isListView=getIntent().getExtras().getBoolean("isListView");
        keyword=(EditText)findViewById(R.id.keyword);
        streetaddress=(EditText)findViewById(R.id.sat);
        city=(EditText)findViewById(R.id.city_t);
        zipcode=(EditText)findViewById(R.id.zipcode_t);
        sp=(Spinner)findViewById(R.id.spt);
        search=(Button)findViewById(R.id.search_t);
        minprice=(TextView)findViewById(R.id.et1_t);
        maxprice=(TextView)findViewById(R.id.et2_t);
        seek1=(SeekBar)findViewById(R.id.seek1_t);
        seek2=(SeekBar)findViewById(R.id.seek2_t);
        seek1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                minPrice=String.valueOf(progress);
                minprice.setText(String.valueOf(progress) + " $");





            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {


            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
seek2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        maxPrice=String.valueOf(progress);
        maxprice.setText(String.valueOf(progress) + " $");


    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
});
search.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        int id=sp.getSelectedItemPosition()+4;
        if(id>4)
        {
            Keywords=keyword.getText().toString();
            Street=streetaddress.getText().toString();
            City=city.getText().toString();
            Zip=zipcode.getText().toString();
            Intent i=new Intent(getApplicationContext(), ProductListAdapter.class);
            finish();
            i.putExtra("catid", id);
            i.putExtra("keyword",Keywords);
            i.putExtra("street",Street);
            i.putExtra("city",City);
            i.putExtra("zip",Zip);
            i.putExtra("distance",String.valueOf(Distance));
            i.putExtra("startdate",Startdate);
            i.putExtra("enddate",Enddate);
            i.putExtra("minprice",minPrice);
            i.putExtra("maxprice",maxPrice);
            i.putExtra("isListView",true);
            i.putExtra("isSearch",true);
            i.putExtra("latitude",latitude);
            i.putExtra("longitude",longitude);
            startActivity(i);
        }
        else{
            Toast.makeText(getApplicationContext(),"Please select category",Toast.LENGTH_SHORT).show();
        }

    }
});

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK) {

        }
        return false;

    }

    public void GpsLocation()
    {
        gps = new GPSTracker(MarketSearch.this);

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
