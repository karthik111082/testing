package com.hik.trendycraftshow;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hik.trendycraftshow.ListAdapters.ProductListAdapter;
import com.hik.trendycraftshow.Utils.Consts;
import com.hik.trendycraftshow.Utils.GPSTracker;
import com.hik.trendycraftshow.Utils.IsTablet;

import java.util.Calendar;

/**
 * Created by HP on 26-11-2015.
 */
public class Search extends NavigationDrawer {
    boolean isTablet;
    IsTablet tablet;
    ImageButton back;
    EditText keyword,streetaddress,city,zipcode;
    SeekBar seek;
    Button search;
    boolean isListView;
    Button SearchBtn;
    int Distance=0,catid;
    final String minPrice="",maxPrice="";
    String Keywords="",Street="",City="",Zip="",Startdate="",Enddate="";
    boolean isStartdate=false;
    TextView distance,startdate,enddate;
    Calendar calendar;
    private int year, month, day;
    double latitude;
    double longitude;
    GPSTracker gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isTablet = tablet.isTablet(getApplicationContext());
        isTablet = tablet.isTablet(getApplicationContext());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        title=(TextView)findViewById(R.id.titletoolbar);
        title.setText("Search");
        if (isTablet) {
            getLayoutInflater().inflate(R.layout.activity_search, container);
        } else {
            getLayoutInflater().inflate(R.layout.activity_search_mob, container);
        }
        GpsLocation();
        catid=getIntent().getExtras().getInt("catid");
        isListView=getIntent().getExtras().getBoolean("isListView");
        keyword=(EditText)findViewById(R.id.keyword_c);
        streetaddress=(EditText)findViewById(R.id.sac);
        city=(EditText)findViewById(R.id.city_c);
        zipcode=(EditText)findViewById(R.id.zipcode_c);
        seek=(SeekBar)findViewById(R.id.seek_c);
        search=(Button)findViewById(R.id.search_c);
        startdate=(TextView)findViewById(R.id.pd1);
        enddate=(TextView)findViewById(R.id.pd2);
        distance=(TextView)findViewById(R.id.tx1_c);
        SearchBtn=(Button)findViewById(R.id.search_c);
        SearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Keywords=keyword.getText().toString();
                Street=streetaddress.getText().toString();
                City=city.getText().toString();
                Zip=zipcode.getText().toString();
                Intent i=new Intent(getApplicationContext(), ProductListAdapter.class);
                finish();
                i.putExtra("catid", catid);
                i.putExtra("keyword",Keywords);
                i.putExtra("street",Street);
                i.putExtra("city",City);
                i.putExtra("zip",Zip);
                i.putExtra("distance",String.valueOf(Distance));
                i.putExtra("startdate",Startdate);
                i.putExtra("enddate",Enddate);
                i.putExtra("minprice",minPrice);
                i.putExtra("maxprice",maxPrice);
                i.putExtra("isListView",isListView);
                i.putExtra("isSearch",true);
                i.putExtra("latitude",latitude);
                i.putExtra("longitude",longitude);
                startActivity(i);

            }
        });


        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Distance=progress;
                distance.setText(String.valueOf(progress)+" MILES");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        startdate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isStartdate=true;
                showDialog(999);
                return true;
            }
        });
        enddate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isStartdate=false;
                showDialog(999);
                return true;
            }
        });
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }
    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);

    }
    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            DatePickerDialog dialog;
            //return new DatePickerDialog(this, myDateListener, year, month, day);

     dialog = new DatePickerDialog(this, myDateListener, year, month, day);



            return dialog;
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
            // arg1 = year
            // arg2 = month
            // arg3 = day
            showDate(arg1, arg2+1, arg3);
        }
    };

    private void showDate(int year, int month, int day) {
        if(isStartdate) {
            startdate.setText(new StringBuilder().append(String.format("%02d", month)).append("-").append(String.format("%02d", day)).append("-").append(year));
            Startdate=startdate.getText().toString();
        }
        else
        {
            enddate.setText(new StringBuilder().append(String.format("%02d", month)).append("-").append(String.format("%02d", day)).append("-").append(year));
            Enddate=enddate.getText().toString();
            if(Consts.SearchEndDate(Startdate, Enddate))
            {

            }else{
                enddate.setText(Startdate);
                Toast.makeText(getApplicationContext(), "The ad will be available for maximum period of 30 days from the start date!!!", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK) {

        }
        return false;

    }
    public void GpsLocation()
    {
        gps = new GPSTracker(Search.this);

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
