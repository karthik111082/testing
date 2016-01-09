package com.hik.trendycraftshow;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.AsyncTask;
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
import com.hik.trendycraftshow.QuickChat.ApplicationSingleton;
import com.hik.trendycraftshow.QuickChat.core.ChatService;
import com.hik.trendycraftshow.Utils.Consts;
import com.hik.trendycraftshow.Utils.Dbhelper;
import com.hik.trendycraftshow.Utils.InternetStatus;
import com.hik.trendycraftshow.Utils.IsTablet;
import com.hik.trendycraftshow.Utils.Validation;
import com.quickblox.core.QBEntityCallbackImpl;
import com.quickblox.core.QBSettings;
import com.quickblox.users.model.QBUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

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
    public static Bitmap bitmap;
    ImageButton signin,signup,guest;
    Dbhelper mHelper;
    SQLiteDatabase database;
    public static Consts consts;
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
        QBSettings.getInstance().fastConfigInit(ApplicationSingleton.APP_ID, ApplicationSingleton.AUTH_KEY, ApplicationSingleton.AUTH_SECRET);
        consts=new Consts(getApplicationContext());
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

                Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                Consts.isGuest=true;
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

                                new LoginProcess().execute();
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Trendy Craft Show requires internet. Please check!!!",Toast.LENGTH_SHORT).show();
                            }

                        }

                    }
                });

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






    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK) {
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            finish();
            startActivity(i);

        }
        return false;

    }



    class LoginProcess extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Loading ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }


        protected String doInBackground(String... args) {
            try{
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
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
                                            Consts.UserId=obj.getString("userId");
                                            Consts.Company_Name=obj.getString("companyName");
                                            Consts.Cellphone=obj.getString("cellPhone");
                                            Consts.QuickBloxId=obj.getString("quickId");
                                            Consts.SpinnerItem=Integer.parseInt(obj.getString("stateCode"));
                                            Consts.Photo=obj.getString("photo");
                                            Consts.UserSince=obj.getString("createdDate");
                                            Log.d("jsonimage", obj.getString("photo"));
                                            Log.d("jsonlength", String.valueOf(obj.getString("photo").length()));
                                            String sql1="delete from "+Dbhelper.USER_TABLE;
                                            database.execSQL(sql1);
                                            ContentValues values=new ContentValues();
                                            values.put(Dbhelper.UserName,Consts.UserName);
                                            values.put(Dbhelper.Password, Consts.Password);
                                            values.put(Dbhelper.FirstName, Consts.FirstName);
                                            values.put(Dbhelper.Phone, Consts.Phone);
                                            values.put(Dbhelper.Street, Consts.Street);
                                            values.put(Dbhelper.City, Consts.City);
                                            values.put(Dbhelper.State, Consts.State);
                                            values.put(Dbhelper.Zip, Consts.Zip);
                                            values.put(Dbhelper.UserId, Consts.UserId);
                                            values.put(Dbhelper.Company_Name, Consts.Company_Name);
                                            values.put(Dbhelper.CellPhone, Consts.Cellphone);
                                            values.put(Dbhelper.QuickBloxId, Consts.QuickBloxId);
                                            values.put(Dbhelper.Photo, Consts.Photo);
                                            values.put(Dbhelper.UserSince, Consts.UserSince);
                                            database.insert(Dbhelper.USER_TABLE, null, values);
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
                                            /*Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                                            Consts.isGuest = false;
                                            finish();
                                            startActivity(i);*/
                               final QBUser user = new QBUser();

                                user.setEmail(obj.getString("UserName"));
                                user.setPassword(ApplicationSingleton.USER_PASSWORD);

                                ChatService.initIfNeed(MainActivity.this);

                                ChatService.getInstance().login(user, new QBEntityCallbackImpl() {

                                    @Override
                                    public void onSuccess() {
                                        // Go to Dialogs screen
                                        //
                                       GoHome();

                                        Log.d("userid", ChatService.getInstance().getCurrentUser().getId().toString());

                                    }

                                    @Override
                                    public void onError(List errors) {
                                        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                                        dialog.setMessage("chat login errors: " + errors).create().show();
                                    }
                                });
                                            GoHome();
                                        } else {


                                            Toast.makeText(getApplicationContext(), "Your username or password doesn't match our records. Please check!!!", Toast.LENGTH_SHORT).show();


                                        }


                                    } catch (JSONException e) {

                                        Log.d("Login Error", e.toString());
                                    }
                                }
                            }
                        });
                        loginRequest.execute();

                    }
                });







            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();



        }

    }

public void GoHome()
{
    Intent i = new Intent(getApplicationContext(), HomeActivity.class);
    Consts.isGuest = false;
    finish();
    startActivity(i);
}
}
