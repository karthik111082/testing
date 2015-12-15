package com.hik.trendycraftshow;

import android.app.Activity;
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

import com.hik.trendycraftshow.Utils.IsTablet;

/**
 * Created by HP on 26-11-2015.
 */
public class MarketSearch extends NavigationDrawer {
    boolean isTablet;
    IsTablet tablet;
    ImageButton back;
    EditText keyword,streetaddress,city,zipcode;
    TextView tx1,tx2;
    Spinner sp;
    Button search;
    SeekBar seek1,seek2;


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

        keyword=(EditText)findViewById(R.id.keyword);
        streetaddress=(EditText)findViewById(R.id.sat);
        city=(EditText)findViewById(R.id.city_t);
        zipcode=(EditText)findViewById(R.id.zipcode_t);
        back=(ImageButton)findViewById(R.id.back);
        sp=(Spinner)findViewById(R.id.spt);
        search=(Button)findViewById(R.id.search_t);
        tx1=(TextView)findViewById(R.id.et1_t);
        tx2=(TextView)findViewById(R.id.et2_t);
        seek1=(SeekBar)findViewById(R.id.seek1_t);
        seek2=(SeekBar)findViewById(R.id.seek2_t);
        seek1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {


                tx1.setText(String.valueOf(progress) + " $");





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
        tx2.setText(String.valueOf(progress) + " $");


    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
});


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
