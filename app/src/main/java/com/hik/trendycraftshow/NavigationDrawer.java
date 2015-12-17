package com.hik.trendycraftshow;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hik.trendycraftshow.JSON.Api;
import com.hik.trendycraftshow.JSON.WebServiceRequest;
import com.hik.trendycraftshow.Utils.Consts;
import com.hik.trendycraftshow.Utils.Dbhelper;
import com.hik.trendycraftshow.Utils.IsTablet;
import com.hik.trendycraftshow.Utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class NavigationDrawer extends AppCompatActivity


        implements NavigationView.OnNavigationItemSelectedListener {

    TextView title;
   public FrameLayout container;
    IsTablet tablet;
    boolean isTablet;
    private WebServiceRequest.HttpURLCONNECTION logoutRequest;
    Api api;
    Consts consts;
    DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigationmain);
     Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        title=(TextView)findViewById(R.id.titletoolbar);
        title.setText("TRENDY CRAFT SHOWS");
        isTablet = tablet.isTablet(getApplicationContext());
        container=(FrameLayout)findViewById(R.id.container);
       /* toolbar.setTitle("Trendy Craft Show");
        toolbar.setTitleTextColor(Color.WHITE);*/

       //setSupportActionBar(toolbar);
        if(isTablet)
        {
            getLayoutInflater().inflate(R.layout.activity_home, container);
        }else{
            getLayoutInflater().inflate(R.layout.activity_home_mob, container);
        }
consts=new Consts(getApplicationContext());
     /*   FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

         drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
//   GUEST login Show dialog

    private void openAlert() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(NavigationDrawer.this);

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
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

            Intent i=new Intent(getApplicationContext(),HomeActivity.class);
            finish();
            startActivity(i);
            // Handle the camera action
        }
        else if (id == R.id.nav_profile)
        {
            if(Consts.isGuest)
            {
                openAlert();
            }
            else{

                Intent i=new Intent(getApplicationContext(),ProfileActivity.class);
                finish();
                startActivity(i);
                title=(TextView)findViewById(R.id.titletoolbar);
                title.setText("MY PROFILE");
            }

        }
        else if (id == R.id.nav_inbox)
        {
            if(Consts.isGuest)
            {
                openAlert();
            }
            else {
                Intent i = new Intent(getApplicationContext(), InboxActivity.class);
                finish();
                startActivity(i);
                title = (TextView) findViewById(R.id.titletoolbar);
                title.setText("INBOX");
            }
        }
        else if (id == R.id.nav_payment)
        {
            if(Consts.isGuest)
            {
                openAlert();
            }
            else {
                Intent i = new Intent(getApplicationContext(), PaymentMethod.class);
                finish();
                startActivity(i);
                title.setText("PAYMENT AND PURCHASE");
            }
        }
        else if (id == R.id.nav_wishlist)
        {
            if(Consts.isGuest)
            {
                openAlert();
            }
            else {
                Intent i = new Intent(getApplicationContext(), Wishlist.class);
                finish();
                startActivity(i);
            }
        }
        else if (id == R.id.nav_businesscard)
        {
            if(Consts.isGuest)
            {
                openAlert();
            }
            else {
                Toast.makeText(getApplicationContext(),"Content under development",Toast.LENGTH_SHORT).show();
                /*Intent i = new Intent(getApplicationContext(), MyBusinessCard.class);
                finish();
                startActivity(i);*/
            }
        }
        else if (id == R.id.nav_followup)
        {
            if(Consts.isGuest)
            {
                openAlert();
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Content under development",Toast.LENGTH_SHORT).show();
            }


        }
        else if (id == R.id.nav_mysales)
        {
            if(Consts.isGuest)
            {
                openAlert();
            }
            else {
                Intent i = new Intent(getApplicationContext(), Mysales.class);
                finish();
                startActivity(i);
            }
        }
        else if (id == R.id.nav_paymentmethod)
        {
            if(Consts.isGuest)
            {
                openAlert();
            }
            else {
                Intent i = new Intent(getApplicationContext(), PaymentMethod.class);
                finish();
                startActivity(i);
            }

        }
        else if (id == R.id.nav_advertisment)
        {
            if(Consts.isGuest)
            {
                openAlert();
            }
            else {
                Intent i = new Intent(getApplicationContext(), PostAddChooser.class);
                finish();
                startActivity(i);
            }

        }
        else if (id == R.id.nav_logout) {

Consts.isGuest=false;

            String params="userid="+Consts.UserId;
            logoutRequest = api.LOGOUT(params, new WebServiceRequest.Callback() {
                @Override
                public void onResult(int responseCode, String responseMessage, Exception exception) {

                    if (responseCode == 200) {
                        try {
                            JSONObject obj = new JSONObject(responseMessage);
                            Log.d("response", responseMessage.toString());
                            String status = obj.getString("msg");

                            if (status.equalsIgnoreCase("success")) {
                                Consts.FirstName = "";
                                Consts.UserName = "";
                                Consts.Password = "";
                                Consts.Phone = "";
                                Consts.Cellphone = "";
                                Consts.City = "";
                                Consts.Zip = "";
                                Consts.Street = "";
                                Consts.SpinnerItem = 0;
                                Consts.Photo = null;
                                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                                finish();
                                startActivity(i);

                            } else {


                                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                                finish();
                                startActivity(i);


                            }


                        } catch (JSONException e) {
                            Intent i=new Intent(getApplicationContext(),LoginActivity.class);
                            finish();
                            startActivity(i);
                            Log.d("Logout Error", e.toString());
                        }
                    }
                }
            });
            logoutRequest.execute();


        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK) {
         /*   Intent i=new Intent(getApplicationContext(),MainActivity.class);
            finish();
            startActivity(i);*/
        }
        return false;

    }
}
