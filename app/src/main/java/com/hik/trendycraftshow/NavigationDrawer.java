package com.hik.trendycraftshow;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.hik.trendycraftshow.Utils.Consts;
import com.hik.trendycraftshow.Utils.IsTablet;

public class NavigationDrawer extends AppCompatActivity


        implements NavigationView.OnNavigationItemSelectedListener {

    TextView title;
   public FrameLayout container;
    IsTablet tablet;
    boolean isTablet;
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

     /*   FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
/*

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
*/

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
        } else if (id == R.id.nav_profile) {


//           Intent i=new Intent(getApplicationContext(),ProfileActivity.class);
//            finish();
//            startActivity(i);

        } else if (id == R.id.nav_inbox) {

        } else if (id == R.id.nav_payment) {

        } else if (id == R.id.nav_wishlist) {

        }
        else if (id == R.id.nav_businesscard) {

        }else if (id == R.id.nav_mysales) {

        }else if (id == R.id.nav_advertisment) {

        }else if (id == R.id.nav_logout) {
            Intent i=new Intent(getApplicationContext(),LoginActivity.class);
            finish();

            Consts.FirstName="";
            Consts.UserName="";
            Consts.Password="";
            Consts.Phone="";
            Consts.Cellphone="";
            Consts.City="";
            Consts.Zip="";
            Consts.Street="";
            Consts.SpinnerItem=0;

            startActivity(i);
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
