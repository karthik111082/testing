package com.hik.trendycraftshow;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.hik.trendycraftshow.JSON.Api;
import com.hik.trendycraftshow.JSON.WebServiceRequest;
import com.hik.trendycraftshow.Utils.Consts;
import com.hik.trendycraftshow.Utils.Dbhelper;
import com.hik.trendycraftshow.Utils.InternetStatus;
import com.hik.trendycraftshow.Utils.IsTablet;
import com.hik.trendycraftshow.Utils.Validation;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity {
    boolean isTablet;
    IsTablet tablet;
    private WebServiceRequest.HttpURLCONNECTION loginRequest;
    Api api;
    ProgressDialog pDialog;
    String Username,Password;
    EditText email,psw;
    TextView Forgot;
    InternetStatus internetStatus;
    boolean internet;
    ImageButton signin,signup,guest;
    Dbhelper mHelper;
    SQLiteDatabase database;
    CheckBox remember;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isTablet=tablet.isTablet(getApplicationContext());
        if(isTablet) {
            setContentView(R.layout.activity_main);
        }
        else{
            setContentView(R.layout.activity_main_mob);
        }
        mHelper=new Dbhelper(getApplicationContext());
        database=mHelper.getWritableDatabase();
        internetStatus=new InternetStatus();
        remember=(CheckBox)findViewById(R.id.remember_radio);
        email = (EditText) findViewById(R.id.email);
        psw = (EditText) findViewById(R.id.psw);
        Forgot=(TextView)findViewById(R.id.forgot);

        signin = (ImageButton) findViewById(R.id.sign_in);
        signup = (ImageButton) findViewById(R.id.sign_up);
        guest= (ImageButton)findViewById(R.id.guest);

        Getdata();

        guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), NavigationDrawer.class);
                finish();
                startActivity(i);
            }
        });

        Forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), ForgotPassword.class);
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
                    Toast.makeText(getApplicationContext(),"Trendy Craft Show requires internet. Please check!!!",Toast.LENGTH_SHORT).show();
                }


            }
        });

       remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

           }
       });

                signin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       boolean username=false,pass=false;
                        Username = email.getText().toString();
                        Password = psw.getText().toString();
                        if(Validation.isValidEmail(Username))
                        {
                            username=true;
                        }else{
                            username=false;
                            email.setError("Please check your email address!!!");
                        }
                        if(Validation.isValidPassword(Password))
                        {
                            pass=true;
                        }else{
                            pass=false;
                            psw.setError("The password should be more than 5 digits. Please check!!!");
                        }


                        if (username && pass) {
                            Username = email.getText().toString();
                            Password = psw.getText().toString();

                            if(internetStatus.InternetStatus(getApplicationContext())) {
                                showDialog();
                                Login();
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Trendy Craft Show requires internet. Please check!!!",Toast.LENGTH_SHORT).show();
                            }

                        }

                    }
                });

    }
    public void showDialog() {
        pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setMessage("Please wait ...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }
    public void hideDialog()
    {
        if(pDialog.isShowing())
        {
            pDialog.dismiss();
        }
    }
    public void Getdata()
    {
        String sql = "SELECT * FROM "+Dbhelper.TABLE_NAME;
        Log.d("query", sql);
        Cursor cursor  = database.rawQuery(sql,null);

        if (cursor.moveToNext())
        {
            email.setText(cursor.getString(cursor.getColumnIndex(Dbhelper.KEY_UNAME)));
            psw.setText(cursor.getString(cursor.getColumnIndex(Dbhelper.KEY_PASSWORD)));
            remember.setChecked(true);
            remember.setClickable(true);

        }else{
            remember.setChecked(false);
            remember.setClickable(true);
        }

    }
        public void Login()
        {
            String params = "uname=" + Username + "&pass="+Password;
            Log.d("parameters", params);
            params=params.replaceAll(" ","%20");
            loginRequest = api.LOGIN(params, new WebServiceRequest.Callback() {
                @Override
                public void onResult(int responseCode, String responseMessage, Exception exception) {

                    if (responseCode == 200) {
                        try {
                            JSONObject obj = new JSONObject(responseMessage);
                            Log.d("response", responseMessage.toString());
                            String status = obj.getString("msg");

                            if (status.equalsIgnoreCase("success")) {
                                Consts.UserName=obj.getString("UserName");
                                Consts.Password=obj.getString("Password");
                                Consts.FirstName=obj.getString("Firstname");
                                Consts.Phone=obj.getString("Phone");
                                Consts.Street=obj.getString("Street");
                                Consts.City=obj.getString("City");
                                Consts.State=obj.getString("State");
                                Consts.Zip=obj.getString("Zipcode");
                                Consts.Photo=obj.getString("photo");
                                Consts.UserId=obj.getString("userId");
                                Consts.Company_Name=obj.getString("companyName");
                                Consts.Cellphone=obj.getString("cellPhone");
                                Consts.QuickBloxId=obj.getString("quickId");
                                Consts.SpinnerItem=Integer.parseInt(obj.getString("stateCode"));

                                hideDialog();
                                if(remember.isChecked())
                                {
                                    String sql="delete from "+Dbhelper.TABLE_NAME;
                                    database.execSQL(sql);
                                    ContentValues value=new ContentValues();
                                    value.put(Dbhelper.KEY_UNAME,Consts.UserName);
                                    value.put(Dbhelper.KEY_PASSWORD,Consts.Password);
                                    database.insert(Dbhelper.TABLE_NAME,null,value);
                                }else
                                {
                                    String sql="delete from "+Dbhelper.TABLE_NAME;
                                    database.execSQL(sql);
                                }
                                Intent i = new Intent(getApplicationContext(), NavigationDrawer.class);
                                finish();
                                startActivity(i);

                            } else {

                                hideDialog();
                                Toast.makeText(getApplicationContext(), "Your username or password doesn't match our records. Please check!!!", Toast.LENGTH_SHORT).show();


                            }


                        } catch (JSONException e) {
                            hideDialog();
                            Log.d("Login Error", e.toString());
                        }
                    }
                }
            });
            loginRequest.execute();
        }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK) {
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            finish();
            startActivity(i);

        }
        return false;

    }

}
