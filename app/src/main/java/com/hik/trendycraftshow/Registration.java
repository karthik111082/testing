package com.hik.trendycraftshow;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hik.trendycraftshow.JSON.Api;
import com.hik.trendycraftshow.JSON.WebServiceRequest;
import com.hik.trendycraftshow.QuickChat.ApplicationSingleton;
import com.hik.trendycraftshow.Utils.Consts;
import com.hik.trendycraftshow.Utils.Dbhelper;
import com.hik.trendycraftshow.Utils.InternetStatus;
import com.hik.trendycraftshow.Utils.IsTablet;
import com.hik.trendycraftshow.Utils.Validation;
import com.quickblox.core.QBEntityCallbackImpl;
import com.quickblox.core.QBSettings;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Registration extends Activity {
    boolean isTablet;
    IsTablet tablet;
    EditText fname,email,psw,cnfrm_psw,phone,street,city,zip,cellphone;
    String Fname,Email,Pwd,cPwd,Phone,Street,City,Zip,State,CellNo;
    Button createaccount;
    ImageButton OTP;
    LinearLayout SignUpLayout,OtpLayout,Congrats;
    EditText otp;
    Spinner state;
    ImageButton back,OTPContinue;
    TextView terms;
    Intent intent;
    Dbhelper mHelper;
    SQLiteDatabase database;
    InternetStatus internetStatus;


    private WebServiceRequest.HttpURLCONNECTION signup;
    private WebServiceRequest.HttpURLCONNECTION activate;
    private WebServiceRequest.HttpURLCONNECTION sendQuickId;
    Api api;
    ProgressDialog pDialog;
    String OTPValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isTablet=tablet.isTablet(getApplicationContext());
        if(isTablet) {
            setContentView(R.layout.activity_registration);
        }
        else{
            setContentView(R.layout.activity_registration_mob);
        }
        mHelper=new Dbhelper(getApplicationContext());
        database=mHelper.getWritableDatabase();
        QBSettings.getInstance().fastConfigInit(ApplicationSingleton.APP_ID, ApplicationSingleton.AUTH_KEY, ApplicationSingleton.AUTH_SECRET);
        SignUpLayout=(LinearLayout)findViewById(R.id.signUpLayout);
        OtpLayout=(LinearLayout)findViewById(R.id.OtpLayout);
        back=(ImageButton)findViewById(R.id.back);
        fname=(EditText)findViewById(R.id.fname);
        email = (EditText) findViewById(R.id.email);
        terms = (TextView) findViewById(R.id.terms);

        OTPContinue=(ImageButton)findViewById(R.id.continue_otp);
        psw = (EditText) findViewById(R.id.psw);
        Congrats=(LinearLayout)findViewById(R.id.congratulation);
        state=(Spinner)findViewById(R.id.state);
        otp=(EditText)findViewById(R.id.otp);
        cnfrm_psw=(EditText)findViewById(R.id.cnfrm);
        cellphone=(EditText)findViewById(R.id.phoneno_cell);
        phone=(EditText)findViewById(R.id.phoneno);
        street=(EditText)findViewById(R.id.street_address);
        city=(EditText)findViewById(R.id.city);
        zip=(EditText)findViewById(R.id.zip);
        createaccount = (Button) findViewById(R.id.crtaccount);
        OTP=(ImageButton)findViewById(R.id.siginin_otp);
        OtpLayout.setVisibility(View.GONE);
        SignUpLayout.setVisibility(View.VISIBLE);
        getdata();
        createaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Validation();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
        });
        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(getApplicationContext(),TermsActivity.class);
                Consts.FirstName=fname.getText().toString();
                Consts.UserName=email.getText().toString();
                Consts.Password=psw.getText().toString();
                Consts.Phone=phone.getText().toString();
                Consts.Cellphone=cellphone.getText().toString();
                Consts.Street=street.getText().toString();
                Consts.City=city.getText().toString();
                Consts.Zip=zip.getText().toString();
                Consts.SpinnerItem=state.getSelectedItemPosition();

                finish();
             
                startActivity(i);

            }
        });
        OTPContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Congrats.setVisibility(View.GONE);
                OtpLayout.setVisibility(View.VISIBLE);
                SignUpLayout.setVisibility(View.GONE);
            }
        });

        OTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showDialog();
                if (otp.getText().toString().length() > 0) {
                    if (otp.getText().toString().equals(OTPValue)) {
                        if (internetStatus.InternetStatus(getApplicationContext())) {
                                new SendOtp().execute();

                        } else {
                            Toast.makeText(getApplicationContext(), "Trendy Craft Show requires internet. Please check!!!", Toast.LENGTH_SHORT).show();
                        }


                    } else {
                        otp.setError("The activation code doesn't match our records. Please check your email!!!");
                    }
                } else {
                    otp.setError("Please check your email for activation code!!!");
                }
            }
        });


    }



    public void SendQuickBloxId(String quickid)
    {
        ContentValues cv = new ContentValues();
        cv.put(Dbhelper.QuickBloxId, quickid);
        String sql="update "+Dbhelper.USER_TABLE+" SET "+Dbhelper.QuickBloxId+"="+"'"+quickid+"'"+" WHERE "+Dbhelper.UserName+"="+"'"+Consts.UserName+"'";
        Log.d("query",sql);
        database.execSQL(sql);
        Consts.QuickBloxId=quickid;
        Log.d("quickid",Consts.QuickBloxId);
        String params = "username="+Consts.UserName+"&chatid=" + quickid;
        params=params.replaceAll(" ","%20");
        Log.d("parameters", params);
        signup = api.SEND_CHAT_ID(params, new WebServiceRequest.Callback() {
            @Override
            public void onResult(int responseCode, String responseMessage, Exception exception) {

                if (responseCode == 200) {
                    try {
                        JSONObject obj = new JSONObject(responseMessage);
                        Log.d("response", responseMessage.toString());
                        String status = obj.getString("msg");

                        if (status.equalsIgnoreCase("User Already Exists")) {
                            Toast.makeText(getApplicationContext(), "User already exists", Toast.LENGTH_SHORT).show();
                        }
                        if (status.equalsIgnoreCase("signup failed")) {
                            Toast.makeText(getApplicationContext(), "Server Failed", Toast.LENGTH_SHORT).show();
                        }
                        if (status.equalsIgnoreCase("success")) {

                            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                            finish();
                            startActivity(i);

                        } else {
                            Toast.makeText(getApplicationContext(), "Server Failed", Toast.LENGTH_SHORT).show();
                        }


                    } catch (JSONException e) {

                        Log.d("Registration Error", e.toString());
                    }
                }
            }
        });
        signup.execute();
    }

    public void getdata(){

        fname.setText(Consts.FirstName);
        email.setText(Consts.UserName);
        psw.setText(Consts.Password);
        cnfrm_psw.setText(Consts.Password);
        phone.setText(Consts.Phone);
        cellphone.setText(Consts.Cellphone);
        street.setText(Consts.Street);
        city.setText(Consts.City);
        zip.setText(Consts.Zip);
        state.setSelection(Consts.SpinnerItem);

    }

    public void Validation()
    {
       boolean VName=false,VEmail=false,VPass=false,VcPass=false,VPhone=false,VCity=false,VStreet=false,VZip=false,VState=false;
        Fname = fname.getText().toString();
        Email=email.getText().toString();
        Pwd=psw.getText().toString();
        cPwd=cnfrm_psw.getText().toString();
        Phone=phone.getText().toString();
        Street=street.getText().toString();
        City=city.getText().toString();
        State=state.getSelectedItem().toString();
        Zip=zip.getText().toString();
        CellNo=cellphone.getText().toString();
        if(Validation.isEmpty(Fname))
        {
            VName=false;
            fname.setError("Please enter valid name!!!");
        }
        else {

            VName=true;

        }
        if(Validation.isValidEmail(Email))
        {
            VEmail=true;
        }
        else {
            VEmail=false;
            email.setError("Please enter valid email-address!!!");
        }
        if(Validation.isValidPassword(Pwd))
        {
            VPass=true;
        }
        else {
            VPass=false;
            psw.setError("Password should be more then 5 digits!!!");
        }

        if(Validation.isValidConfirmPassword(Pwd, cPwd))
        {
            VcPass=true;
        }
        else {
            VcPass=false;
            cnfrm_psw.setError("Password & Confirm password doesn't match. Please check!!!");
        }
        if(Validation.isValidPhone(Phone))
        {
            VPhone=true;
        }
        else {
            VPhone=true;
            //zip.setError("Invalid phone number");
        }
        if(Validation.isZip(Zip))
        {
            VZip=true;
        }
        else {
            VZip=false;
            zip.setError("Please enter valid zip code!!!");
        }
        if(Validation.isEmpty(Street))
        {
            VStreet=false;
            street.setError("Please enter valid street address!!!");
        }
        else {
           VStreet=true;
        }
        if(Validation.isEmpty(City))
        {
            VCity=false;
            city.setError("Please enter valid city!!!");
        }
        else {
            VCity=true;
        }
       if(state.getSelectedItemPosition()>0)
        {
            VState=true;
        }
        else
        {
            VState=false;
           Toast.makeText(getApplicationContext(),"Please choose a valid state!!!",Toast.LENGTH_SHORT).show();
  }



        Log.d("Status",""+VName+VEmail+VPass+VcPass+VPhone+VZip+VStreet+VCity);

        if (VName && VEmail && VPass && VcPass && VPhone && VZip && VStreet && VCity &&VState)
        {  if(internetStatus.InternetStatus(getApplicationContext())) {

            new SignupUser().execute();
        }
        else{
            Toast.makeText(getApplicationContext(),"Trendy Craft Show requires internet. Please check!!!",Toast.LENGTH_SHORT).show();
        }

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK) {
            Intent i=new Intent(getApplicationContext(),LoginActivity.class);
            finish();
            startActivity(i);
        }
        return false;

    }


    class SignupUser extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Registration.this);
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
                        String params = "username=" + Email + "&password="+cPwd+ "&firstname="+Fname+ "&phone="+Phone+ "&street="+Street+ "&city="+City+ "&state="+State+ "&zipcode="+Zip+ "&photo="+"0"+ "&cellphone="+CellNo+ "&statecode="+state.getSelectedItemPosition();
                        params=params.replaceAll(" ","%20");
                        Log.d("parameters", params);
                        signup = api.REGISTRATION(params, new WebServiceRequest.Callback() {
                            @Override
                            public void onResult(int responseCode, String responseMessage, Exception exception) {

                                if (responseCode == 200) {
                                    try {
                                        JSONObject obj = new JSONObject(responseMessage);
                                        Log.d("response", responseMessage.toString());
                                        String status=obj.getString("msg");

                                        if (status.equalsIgnoreCase("User Already Exists")) {
                                            Toast.makeText(getApplicationContext(), "User already exists", Toast.LENGTH_SHORT).show();
                                        } if (status.equalsIgnoreCase("signup failed")) {
                                            Toast.makeText(getApplicationContext(), "Server Failed", Toast.LENGTH_SHORT).show();
                                        } if(status.equalsIgnoreCase("success")) {
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
                                            Consts.AccessCode = obj.getString("code");
                                            Log.d("ConsValue",Consts.AccessCode);
                                            OTPValue = obj.getString("code");
                                            String sql1="delete from "+ Dbhelper.USER_TABLE;
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
                                            OtpLayout.setVisibility(View.GONE);
                                            SignUpLayout.setVisibility(View.GONE);
                                            Congrats.setVisibility(View.VISIBLE);


                                        }


                                    } catch (JSONException e) {

                                        Log.d("Registration Error",e.toString());
                                    }
                                }
                            }
                        });
                        signup.execute();

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


    class SendOtp extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Registration.this);
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
                        String params = "username=" + Email;
                        Log.d("parameters", params);
                        params = params.replaceAll(" ", "%20");
                        activate = api.ACTIVATE_ACCOUNT(params, new WebServiceRequest.Callback() {
                            @Override
                            public void onResult(int responseCode, String responseMessage, Exception exception) {

                                if (responseCode == 200) {
                                    try {
                                        Log.d("response", responseMessage.toString());

                                        JSONObject obj = new JSONObject(responseMessage);
                                        String status = obj.getString("msg");

                                        if (status.equalsIgnoreCase("Activation Failed")) {
                                            Toast.makeText(getApplicationContext(), "We encountered an error while connecting to the server, please try after sometime!!!", Toast.LENGTH_SHORT).show();
                                        } else {

                                                final QBUser user = new QBUser();
                                                user.setEmail(Consts.UserName);
                                                user.setFullName(Consts.FirstName);
                                                user.setPassword(Consts.QuickPassword);


                                                QBUsers.signUp(user, new QBEntityCallbackImpl<QBUser>() {
                                                    @Override
                                                    public void onSuccess(QBUser user, Bundle args) {
                                                        String quickid = String.valueOf(user.getId());
                                                        Consts.QuickBloxId = quickid;
                                                        Log.d("Chat id", Consts.QuickBloxId);
                                                        SendQuickBloxId(quickid);

                                                    }

                                                    @Override
                                                    public void onError(List<String> errors) {
                                                        Log.d("error", errors.toString());
                                                    }
                                                });
                                           /* Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                                            finish();
                                            startActivity(i);*/


                                        }


                                    } catch (JSONException e) {

                                        Log.d("error", e.toString());
                                    }
                                }
                            }
                        });
                        activate.execute();

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


}
