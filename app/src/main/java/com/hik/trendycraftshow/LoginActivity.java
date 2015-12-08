package com.hik.trendycraftshow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.hik.trendycraftshow.Utils.InternetStatus;
import com.hik.trendycraftshow.Utils.IsTablet;

/**
 * Created by HP on 29-11-2015.
 */
public class LoginActivity extends Activity {
    boolean isTablet;
    IsTablet tablet;
    Button login,signup;
    TextView intro;
    ImageButton guest;
    InternetStatus internetStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        isTablet = tablet.isTablet(getApplicationContext());
        if (isTablet) {
            setContentView(R.layout.activity_main_login);
        } else {
            setContentView(R.layout.activity_main_login_mob);
        }
        login=(Button)findViewById(R.id.sign_in);
        intro=(TextView)findViewById(R.id.intro);
        signup=(Button)findViewById(R.id.sign_up);
        guest=(ImageButton)findViewById(R.id.loginguest);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                finish();
                startActivity(i);
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(internetStatus.InternetStatus(getApplicationContext())) {
                    Intent i = new Intent(getApplicationContext(), Registration.class);
                    finish();
                    startActivity(i);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Trendy Craft Show requires internet. Please check!!!", Toast.LENGTH_SHORT).show();
                }


            }
        });
        guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), NavigationDrawer.class);
                finish();
                startActivity(i);
            }
        });
        intro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Introduction.class);
                finish();
                startActivity(i);

            }
        });


    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK) {

            finish();


        }
        return false;

    }
}

