package com.hik.trendycraftshow;

import android.app.Activity;
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
    ImageButton img1,img2;
    EditText et1,et2;
    TextView tx;
    Calendar calendar;
    private int year, month, day;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isTablet = tablet.isTablet(getApplicationContext());
        isTablet = tablet.isTablet(getApplicationContext());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        title=(TextView)findViewById(R.id.titletoolbar);
        title.setText("SEARCH");
        if (isTablet) {
            getLayoutInflater().inflate(R.layout.activity_search, container);
        } else {
            getLayoutInflater().inflate(R.layout.activity_search_mob, container);
        }
        keyword=(EditText)findViewById(R.id.keyword_c);
        streetaddress=(EditText)findViewById(R.id.sac);
        city=(EditText)findViewById(R.id.city_c);
        zipcode=(EditText)findViewById(R.id.zipcode_c);
        seek=(SeekBar)findViewById(R.id.seek_c);
       search=(Button)findViewById(R.id.search_c);
        img1=(ImageButton)findViewById(R.id.date1);
        img2=(ImageButton)findViewById(R.id.date2);
        et1=(EditText)findViewById(R.id.etd1);
        et2=(EditText)findViewById(R.id.etd2);
        tx=(TextView)findViewById(R.id.tx1_c);


        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                tx.setText(String.valueOf(progress)+" $");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        img1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

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
            //return new DatePickerDialog(this, myDateListener, year, month, day);

            DatePickerDialog dialog = new DatePickerDialog(this, myDateListener, year, month, day);
            dialog.getDatePicker().setMaxDate(System.currentTimeMillis());

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
        et1.setText(new StringBuilder().append(month).append("/")
                .append(day).append("/").append(year));
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

}
