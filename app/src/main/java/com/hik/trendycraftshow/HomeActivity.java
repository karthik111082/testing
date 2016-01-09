package com.hik.trendycraftshow;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;

import com.hik.trendycraftshow.ListAdapters.ProductListAdapter;
import com.hik.trendycraftshow.Utils.Consts;
import com.hik.trendycraftshow.Utils.IsTablet;

public class HomeActivity extends NavigationDrawer {

    boolean isTablet;
    IsTablet tablet;
    ImageButton craftBtn,artBtn,expoBtn,fairsBtn,trendyBtn,businessBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        isTablet = tablet.isTablet(getApplicationContext());
        if (isTablet) {
            getLayoutInflater().inflate(R.layout.activity_postadd_chooser, container);
        } else {
            getLayoutInflater().inflate(R.layout.activity_postadd_chooser_mob, container);
        }
        Log.d("UserId",Consts.UserId);
        craftBtn=(ImageButton)findViewById(R.id.craft);
        artBtn=(ImageButton)findViewById(R.id.art);
        expoBtn=(ImageButton)findViewById(R.id.expo);
        fairsBtn=(ImageButton)findViewById(R.id.fairs);
        businessBtn=(ImageButton)findViewById(R.id.business_card);
        trendyBtn=(ImageButton)findViewById(R.id.trendy);
        craftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Consts.isGuest)
                {
                    openAlert();

                }else{

                    Intent i = new Intent(getApplicationContext(), ProductListAdapter.class);
                    finish();
                    i.putExtra("catid", 1);
                    i.putExtra("isSearch",false);
                    i.putExtra("isListView",true);
                    startActivity(i);
                }

            }
        });
        artBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Consts.isGuest)
                {
                    openAlert();
                }else {

                    Intent i = new Intent(getApplicationContext(), ProductListAdapter.class);
                    finish();
                    i.putExtra("catid", 2);
                    i.putExtra("isSearch", false);
                    i.putExtra("isListView", true);
                    startActivity(i);
                }
            }
        });
        expoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Consts.isGuest)
                {
                    openAlert();

                }else {

                    Intent i = new Intent(getApplicationContext(), ProductListAdapter.class);
                    finish();
                    i.putExtra("catid", 3);
                    i.putExtra("isSearch", false);
                    i.putExtra("isListView", true);
                    startActivity(i);
                }
            }
        });
        fairsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Consts.isGuest)
                {
                    openAlert();
                }else {

                    Intent i = new Intent(getApplicationContext(), ProductListAdapter.class);
                    finish();
                    i.putExtra("catid", 4);
                    i.putExtra("isSearch", false);
                    i.putExtra("isListView", true);
                    startActivity(i);
                }
            }
        });
        trendyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Consts.isGuest)
                {
                    openAlert();
                }else {

                    Intent i = new Intent(getApplicationContext(), ProductListAdapter.class);
                    finish();
                    i.putExtra("catid", 5);
                    i.putExtra("isSearch", false);
                    i.putExtra("isListView", true);
                    startActivity(i);
                }
            }
        });
        businessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Consts.isGuest)
                {
                    openAlert();
                }else {
                    Intent i = new Intent(getApplicationContext(), BusinessCardList.class);
                    i.putExtra("isSearch",false);
                    finish();
                    startActivity(i);
                }
            }
        });



    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK) {
            /*Intent i=new Intent(getApplicationContext(),MainActivity.class);
            finish();
            startActivity(i);*/
        }
        return false;

    }
    private void openAlert() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(HomeActivity.this);

        alertDialogBuilder.setTitle(this.getTitle() + "");
        alertDialogBuilder.setMessage("You are currently not logged in. Do you want log-in now!");

        // set positive button: OK message
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // go to a new activity of the app
                Intent loginActivity = new Intent(getApplicationContext(), LoginActivity.class);
                finish();
                Consts.isGuest=false;
                startActivity(loginActivity);
            }
        });

        // set negative button: CANCEL message
        alertDialogBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // cancel the alert box and put a Toast to the user
                dialog.cancel();
           /*Toast.makeText(getApplicationContext(), "You chose a negative answer",
                       Toast.LENGTH_LONG).show();*/
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show alert
        alertDialog.show();
    }
}
