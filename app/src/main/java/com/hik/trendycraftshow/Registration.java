package com.hik.trendycraftshow;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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
import com.hik.trendycraftshow.Utils.Consts;
import com.hik.trendycraftshow.Utils.IsTablet;
import com.hik.trendycraftshow.Utils.Validation;

import org.json.JSONException;
import org.json.JSONObject;

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


    private WebServiceRequest.HttpURLCONNECTION signup;
    private WebServiceRequest.HttpURLCONNECTION activate;
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
        SignUpLayout=(LinearLayout)findViewById(R.id.signUpLayout);
        OtpLayout=(LinearLayout)findViewById(R.id.OtpLayout);
        back=(ImageButton)findViewById(R.id.back);
        fname=(EditText)findViewById(R.id.fname);
        email = (EditText) findViewById(R.id.email);
        terms = (TextView) findViewById(R.id.terms);

        OTPContinue=(ImageButton)findViewById(R.id.continue_otp);
        cellphone=(EditText)findViewById(R.id.phoneno_cell);
        psw = (EditText) findViewById(R.id.psw);
        Congrats=(LinearLayout)findViewById(R.id.congratulation);
        state=(Spinner)findViewById(R.id.state);
        otp=(EditText)findViewById(R.id.otp);
        cnfrm_psw=(EditText)findViewById(R.id.cnfrm);
        phone=(EditText)findViewById(R.id.phoneno);
        street=(EditText)findViewById(R.id.street_address);
        city=(EditText)findViewById(R.id.city);
        zip=(EditText)findViewById(R.id.zip);
        createaccount = (Button) findViewById(R.id.crtaccount);
        OTP=(ImageButton)findViewById(R.id.siginin_otp);
        OtpLayout.setVisibility(View.GONE);
        SignUpLayout.setVisibility(View.VISIBLE);
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
                startActivity(i);
            }
        });
        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(getApplicationContext(),TermsActivity.class);
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
                if(otp.getText().toString().length()>0)
                {
                    if(otp.getText().toString().equals(OTPValue))
                    {
                        String params = "username=" + Email;
                        Log.d("parameters", params);
                        params=params.replaceAll(" ", "%20");
                        activate = api.ACTIVATE_ACCOUNT(params, new WebServiceRequest.Callback() {
                            @Override
                            public void onResult(int responseCode, String responseMessage, Exception exception) {

                                if (responseCode == 200) {
                                    try {
                                        Log.d("response", responseMessage.toString());

                                        JSONObject obj = new JSONObject(responseMessage);
                                        String status=obj.getString("msg");
                                        hideDialog();
                                        if(status.equalsIgnoreCase("Activation Failed"))
                                        {
                                            Toast.makeText(getApplicationContext(),"We encountered an error while connecting to the server, please try after sometime!!!",Toast.LENGTH_SHORT).show();
                                        }else
                                        {

                                            Intent i=new Intent(getApplicationContext(),NavigationDrawer.class);
                                            finish();
                                            startActivity(i);

                                        }



                                    } catch (JSONException e) {
                                        hideDialog();
                                    }
                                }
                            }
                        });
                        activate.execute();



                    }else{
                        otp.setError("The activation code doesn't match our records. Please check your email!!!");
                    }
                }
                else {
                    otp.setError("Please check your email for activation code!!!");
                }
            }
        });
    }

    public void SignUp()
    {
        String params = "username=" + Email + "&password="+cPwd+ "&firstname="+Fname+ "&phone="+Phone+ "&street="+Street+ "&city="+City+ "&state="+State+ "&zipcode="+Zip+ "&photo="+"0"+ "&cellphone="+CellNo;
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
                            hideDialog();
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
                            Consts.Photo=obj.getString("photo").getBytes();
                            Consts.AccessCode = obj.getString("code");
                            Log.d("ConsValue",Consts.AccessCode);
                            OTPValue = obj.getString("code");
                            hideDialog();
                            OtpLayout.setVisibility(View.GONE);
                            SignUpLayout.setVisibility(View.GONE);
                            Congrats.setVisibility(View.VISIBLE);


                        }


                    } catch (JSONException e) {
                        hideDialog();
                        Log.d("Registration Error",e.toString());
                    }
                }
            }
        });
        signup.execute();
    }
    public void showDialog()
    {
        pDialog = new ProgressDialog(Registration.this);
        pDialog.setMessage("Please wait ...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }
    public void hideDialog() {
        if (pDialog.isShowing()) {
            pDialog.dismiss();
        }
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
        if(Validation.isValidName(Fname))
        {
            VName=true;
        }
        else {
            VName=false;
            fname.setError("Please enter valid name!!!");
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
        {
            showDialog();
            SignUp();
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

}
