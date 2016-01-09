package com.hik.trendycraftshow;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.hik.trendycraftshow.JSON.Api;
import com.hik.trendycraftshow.JSON.WebServiceRequest;
import com.hik.trendycraftshow.QuickChat.UI.InboxActivity;
import com.hik.trendycraftshow.Utils.Consts;
import com.hik.trendycraftshow.Utils.Dbhelper;
import com.hik.trendycraftshow.Utils.InternetStatus;
import com.hik.trendycraftshow.Utils.IsTablet;
import com.quickblox.core.QBEntityCallbackImpl;
import com.quickblox.users.QBUsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class NavigationDrawer extends AppCompatActivity


        implements NavigationView.OnNavigationItemSelectedListener {

    public TextView title;
   public FrameLayout container;
    IsTablet tablet;
    boolean isTablet;
    ImageButton search;
    Dbhelper mHelper;
    SQLiteDatabase database;
    InternetStatus internetStatus;
    private WebServiceRequest.HttpURLCONNECTION getActiveAccount;

    private WebServiceRequest.HttpURLCONNECTION logoutRequest;
    Api api;
    Consts consts;
    DrawerLayout drawer;
    String fontpath= "fonts/fonts2.ttf";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigationmain);
        mHelper=new Dbhelper(getApplicationContext());
        database=mHelper.getWritableDatabase();
        Getdata();
     Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        title=(TextView)findViewById(R.id.titletoolbar);
        search=(ImageButton)findViewById(R.id.search);
        Typeface typeface=Typeface.createFromAsset(getAssets(),fontpath);
        title.setTypeface(typeface);
        title.setText("Trendy Craft Shows");
        isTablet = tablet.isTablet(getApplicationContext());
        container=(FrameLayout)findViewById(R.id.container);
       /* toolbar.setTitle("Trendy Craft Show");
        toolbar.setTitleTextColor(Color.WHITE);*/
       //setSupportActionBar(toolbar);
        if(isTablet)
        {
            title.setTextSize(20);
            getLayoutInflater().inflate(R.layout.activity_home, container);
        }else{
            title.setTextSize(18);
            getLayoutInflater().inflate(R.layout.activity_home_mob, container);
        }

consts=new Consts(getApplicationContext());
        internetStatus=new InternetStatus();
        Consts.addState();
        Consts.SpinnerItem=getKey(Consts.state,Consts.State);
        Log.d("StateCode", "" + Consts.SpinnerItem);
        Log.d("State", "" + Consts.State);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

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
public int getKey(Map<Integer,String> map,String value)
{
    for(int key:map.keySet())
    {
        if(map.get(key).equals(value))
        {
            return key;
        }
    }
    return 0;
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

    private void AlertPaymentMathod() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(NavigationDrawer.this);

        alertDialogBuilder.setTitle(this.getTitle() + "");
        alertDialogBuilder.setMessage("Please add payment method before posting an advertisement!!!");

        // set positive button: OK message
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // go to a new activity of the app
                Intent loginActivity = new Intent(getApplicationContext(), PaymentMethod.class);
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
            Consts.image1="";
            Consts.image2="";
            Consts.image3="";
            Consts.image4="";
            Intent i=new Intent(getApplicationContext(),HomeActivity.class);
            finish();
            startActivity(i);
            // Handle the camera action
        }
        else if (id == R.id.nav_profile)
        {

            Consts.image1="";
            Consts.image2="";
            Consts.image3="";
            Consts.image4="";
            if(Consts.isGuest)
            {
                openAlert();
            }
            else{
                if(internetStatus.InternetStatus(NavigationDrawer.this)) {
                    Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
                    finish();
                    startActivity(i);

                }
                else {
                    Toast.makeText(getApplicationContext(), "Trendy Craft Show requires internet. Please check!!!", Toast.LENGTH_SHORT).show();
                }
            }

        }
        else if (id == R.id.nav_inbox)
        {
            Consts.image1="";
            Consts.image2="";
            Consts.image3="";
            Consts.image4="";
            if(Consts.isGuest)
            {
                openAlert();
            }
            else {
                if(internetStatus.InternetStatus(NavigationDrawer.this)) {
                   Intent i = new Intent(getApplicationContext(), InboxActivity.class);
                    finish();
                    startActivity(i);
                    //Toast.makeText(getApplicationContext(),"Content under development",Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(getApplicationContext(), "Trendy Craft Show requires internet. Please check!!!", Toast.LENGTH_SHORT).show();
                }

            }
        }
        else if (id == R.id.nav_payment)
        {
            Consts.image1="";
            Consts.image2="";
            Consts.image3="";
            Consts.image4="";
            if(Consts.isGuest)
            {
                openAlert();
            }
            else {
                if(internetStatus.InternetStatus(NavigationDrawer.this)) {
                    Intent i = new Intent(getApplicationContext(), PaymentAndPurchase.class);
                    finish();
                    startActivity(i);

                }
                else {
                    Toast.makeText(getApplicationContext(), "Trendy Craft Show requires internet. Please check!!!", Toast.LENGTH_SHORT).show();
                }
            }
        }
        else if (id == R.id.nav_wishlist)
        {
            Consts.image1="";
            Consts.image2="";
            Consts.image3="";
            Consts.image4="";
            if(Consts.isGuest)
            {
                openAlert();
            }
            else {
                if(internetStatus.InternetStatus(NavigationDrawer.this)) {
                    Intent i = new Intent(getApplicationContext(), Wishlist.class);
                    finish();
                    startActivity(i);

                }
                else {
                    Toast.makeText(getApplicationContext(), "Trendy Craft Show requires internet. Please check!!!", Toast.LENGTH_SHORT).show();
                }
            }
        }
        else if (id == R.id.nav_businesscard)
        {
            Consts.image1="";
            Consts.image2="";
            Consts.image3="";
            Consts.image4="";
            if(Consts.isGuest)
            {
                openAlert();
            }
            else {

                if(internetStatus.InternetStatus(NavigationDrawer.this)) {
                    Intent i = new Intent(getApplicationContext(), MyBusinessCard.class);
                    finish();
                    startActivity(i);
                   // Toast.makeText(getApplicationContext(),"Content under development",Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(getApplicationContext(), "Trendy Craft Show requires internet. Please check!!!", Toast.LENGTH_SHORT).show();
                }
            }
        }
        else if (id == R.id.nav_followup)
        {
            Consts.image1="";
            Consts.image2="";
            Consts.image3="";
            Consts.image4="";
            if(Consts.isGuest)
            {
                openAlert();
            }
            else
            {

                if(internetStatus.InternetStatus(NavigationDrawer.this)) {
                    Intent i = new Intent(getApplicationContext(), MyfollowUp.class);
                    finish();
                    startActivity(i);

                }
                else {
                    Toast.makeText(getApplicationContext(), "Trendy Craft Show requires internet. Please check!!!", Toast.LENGTH_SHORT).show();
                }            }


        }
        else if (id == R.id.nav_mysales)
        {
            Consts.image1="";
            Consts.image2="";
            Consts.image3="";
            Consts.image4="";
            if(Consts.isGuest)
            {
                openAlert();
            }
            else {
                if(internetStatus.InternetStatus(NavigationDrawer.this)) {
                    Intent i = new Intent(getApplicationContext(), Mysales.class);
                    finish();
                    startActivity(i);

                }
                else {
                    Toast.makeText(getApplicationContext(), "Trendy Craft Show requires internet. Please check!!!", Toast.LENGTH_SHORT).show();
                }
            }
        }
        else if (id == R.id.nav_paymentmethod)
        {
            Consts.image1="";
            Consts.image2="";
            Consts.image3="";
            Consts.image4="";
            if(Consts.isGuest)
            {
                openAlert();
            }
            else {
                if(internetStatus.InternetStatus(NavigationDrawer.this)) {
                    Intent i = new Intent(getApplicationContext(), PaymentMethod.class);
                    finish();
                    startActivity(i);

                }
                else {
                    Toast.makeText(getApplicationContext(), "Trendy Craft Show requires internet. Please check!!!", Toast.LENGTH_SHORT).show();
                }
            }

        }
        else if (id == R.id.nav_advertisment)
        {
            Consts.image1="";
            Consts.image2="";
            Consts.image3="";
            Consts.image4="";
            if(Consts.isGuest)
            {
                openAlert();
            }
            else {
                if(internetStatus.InternetStatus(NavigationDrawer.this)) {
                    getActiveAccount();

                }
                else {
                    Toast.makeText(getApplicationContext(), "Trendy Craft Show requires internet. Please check!!!", Toast.LENGTH_SHORT).show();
                }


            }

        }
        else if (id == R.id.nav_logout)

        {
            Consts.image1="";
            Consts.image2="";
            Consts.image3="";
            Consts.image4="";
            if(Consts.isGuest)
            {
                Consts.isGuest=false;
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                finish();
                startActivity(i);
            }
            else
            {
                if(internetStatus.InternetStatus(NavigationDrawer.this)) {
                    /*Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                    finish();
                    startActivity(i);*/
                    QBUsers.signOut(new QBEntityCallbackImpl() {
                        @Override
                        public void onSuccess() {
                            Log.d("Chat Session Closed","");
                        }

                        @Override
                        public void onError(List errors) {

                        }
                    });
                   logout();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Trendy Craft Show requires internet. Please check!!!", Toast.LENGTH_SHORT).show();
                }
            }
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

    public void getActiveAccount()
    {
        consts.showDialog(NavigationDrawer.this);
        String params = "userid="+Consts.UserId;
        Log.d("parameters", params);
        params=params.replaceAll(" ", "%20");
        getActiveAccount = api.GET_ACTIVE_PAYMENT(params, new WebServiceRequest.Callback() {
            @Override
            public void onResult(int responseCode, String responseMessage, Exception exception) {

                if (responseCode == 200) {
                    try {
                        consts.hideDialog();
                        JSONArray obj = new JSONArray(responseMessage);
                        Log.d("response", responseMessage.toString());
                        if(obj.length()>0)
                        {
                            for (int i = 0; i < obj.length(); i++)

                            {
                                JSONObject data = obj.getJSONObject(i);
                                String paypalid = data.getString("paypalMail");
                                String stat = data.getString("status");
                                if (Boolean.valueOf(stat)) {
                                    Consts.ActivePaymentId=paypalid;
                                    Log.d("constid",Consts.ActivePaymentId);
                                    Intent ii = new Intent(getApplicationContext(), PostAddChooser.class);
                                    finish();
                                    startActivity(ii);
                                }else {
                                    Toast.makeText(getApplicationContext(), "Please add payment method before posting an advertisement!!!", Toast.LENGTH_SHORT).show();
                                }
                            }

                        }else {
                            Toast.makeText(getApplicationContext(), "Please add payment method before posting an advertisement!!!", Toast.LENGTH_SHORT).show();
                        }







                    } catch (JSONException e) {

                        consts.hideDialog();
                        Toast.makeText(getApplicationContext(), "Please add payment method before posting an advertisement!!!", Toast.LENGTH_SHORT).show();



                    }
                }
            }
        });
        getActiveAccount.execute();

    }
public void logout()
{
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
                            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                            finish();
                            startActivity(i);
                            Log.d("Logout Error", e.toString());
                        }
                    }
                }
            });
            logoutRequest.execute();

}

    public void Getdata()
    {
        String sql = "SELECT * FROM "+ Dbhelper.USER_TABLE;
        Log.d("query", sql);
        Cursor cursor  = database.rawQuery(sql,null);
        Log.d("database total value",""+cursor.getCount());

        if (cursor.moveToNext())
        {
            Consts.UserId=cursor.getString(cursor.getColumnIndex(Dbhelper.UserId));
            Consts.UserName=cursor.getString(cursor.getColumnIndex(Dbhelper.UserName));
            Consts.Password=cursor.getString(cursor.getColumnIndex(Dbhelper.Password));
            Consts.FirstName=cursor.getString(cursor.getColumnIndex(Dbhelper.FirstName));
            Consts.Street=cursor.getString(cursor.getColumnIndex(Dbhelper.Street));
            Consts.City=cursor.getString(cursor.getColumnIndex(Dbhelper.City));
            Consts.State=cursor.getString(cursor.getColumnIndex(Dbhelper.State));
            Consts.Zip=cursor.getString(cursor.getColumnIndex(Dbhelper.Zip));
            Consts.Phone=cursor.getString(cursor.getColumnIndex(Dbhelper.Phone));
            Consts.Cellphone=cursor.getString(cursor.getColumnIndex(Dbhelper.CellPhone));
            Consts.Company_Name=cursor.getString(cursor.getColumnIndex(Dbhelper.Company_Name));
            Consts.Photo=cursor.getString(cursor.getColumnIndex(Dbhelper.Photo));
            Consts.QuickBloxId=cursor.getString(cursor.getColumnIndex(Dbhelper.QuickBloxId));
            Consts.UserSince=cursor.getString(cursor.getColumnIndex(Dbhelper.UserSince));
            Log.d("Database quickid",cursor.getString(cursor.getColumnIndex(Dbhelper.QuickBloxId)));
        }

    }

}
