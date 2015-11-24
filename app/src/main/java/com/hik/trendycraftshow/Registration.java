package com.hik.trendycraftshow;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.hik.trendycraftshow.JSON.Api;
import com.hik.trendycraftshow.JSON.WebServiceRequest;
import com.hik.trendycraftshow.Utils.Consts;
import com.hik.trendycraftshow.Utils.IsTablet;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registration extends Activity {
    boolean isTablet;
    IsTablet tablet;
    EditText fname,email,psw,cnfrm_psw,phone,street,city,zip;
    String Fname,Email,Pwd,cPwd,Phone,Street,City,Zip,State;
    Button createaccount;
    ImageButton OTP;
    LinearLayout SignUpLayout,OtpLayout;
    EditText otp;
    Spinner state;


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
        fname=(EditText)findViewById(R.id.fname);
        email = (EditText) findViewById(R.id.email);
        psw = (EditText) findViewById(R.id.psw);
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
                                       /* String res=obj.getString("msg");
                                        if(res.equalsIgnoreCase("Activation Failed"))
                                        {
                                            Toast.makeText(getApplicationContext(),"Activation Failed",Toast.LENGTH_SHORT).show();
                                        }else{*/
                                            Intent i=new Intent(getApplicationContext(),HomeActivity.class);
                                            startActivity(i);
                                      //  }


                                    } catch (JSONException e) {
                                    }
                                }
                            }
                        });
                        activate.execute();
                       // hideDialog();
                        Intent i=new Intent(getApplicationContext(),HomeActivity.class);
                        startActivity(i);

                    }else{
                        otp.setError("Invalid OTP");
                    }
                }
                else {
                    otp.setError("Enter OTP");
                }
            }
        });
    }

    public void SignUp()
    {
        String params = "username=" + Email + "&password="+cPwd+ "&firstname="+Fname+ "&phone="+Phone+ "&street="+Street+ "&city="+City+ "&state="+State+ "&zipcode="+Zip+ "&photo="+"0";
        params=params.replaceAll(" ","%20");
        Log.d("parameters", params);
        signup = api.REGISTRATION(params, new WebServiceRequest.Callback() {
            @Override
            public void onResult(int responseCode, String responseMessage, Exception exception) {

                if (responseCode == 200) {
                    try {
                        JSONObject obj = new JSONObject(responseMessage);
                        Log.d("response", responseMessage.toString());

                       // String res=obj.getString("msg");
                        String code=obj.getString("code");
                        //Log.d("Respons",res);
                       /* if (res.equalsIgnoreCase("User Already Exists")) {
                           // hideDialog();
                            Toast.makeText(getApplicationContext(), "User already exists", Toast.LENGTH_SHORT).show();
                        } else if (res.equalsIgnoreCase("signup failed")) {
                            //hideDialog();
                            Toast.makeText(getApplicationContext(), "Server Failed", Toast.LENGTH_SHORT).show();
                        } if(code.length()>0) {*/

                            Consts.AccessCode = obj.getString("code");
                            OTPValue = obj.getString("code");
                           // hideDialog();
                            OtpLayout.setVisibility(View.VISIBLE);
                            SignUpLayout.setVisibility(View.GONE);


                       // }


                    } catch (JSONException e) {
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
    /*public void hideDialog()
    {
        if(pDialog.isShowing())
        {
            pDialog.dismiss();
        }
    }*/
    public void Validation()
    {
        boolean fn = false;
        boolean e = false;
        boolean ps=false;
        boolean cpw=false;
        boolean ph = false;
        boolean st = false;
        boolean ci = false;
        boolean cp = false;
        boolean zp = false;
        Fname = fname.getText().toString();
        Email=email.getText().toString();
        Pwd=psw.getText().toString();
        cPwd=cnfrm_psw.getText().toString();
        Phone=phone.getText().toString();
        Street=street.getText().toString();
        City=city.getText().toString();
        State=state.getSelectedItem().toString();
        Zip=zip.getText().toString();

        final String fstname = fname.getText().toString();

        if (!isValidName(fstname)) {
            fn = false;
            fname.setError("Invalid Name");
        } else {
            fn = true;
        }


        final String emailid = email.getText().toString();
        if (!isValidEmail(emailid)) {
            e = false;
            email.setError("Invalid Email");
        } else {
            e = true;
        }

        final String pswd = psw.getText().toString();

        if (!isValidPassword(pswd)) {
            ps = false;
            psw.setError("Invalid  Password");
        } else {
            ps = true;
        }
        final String cnfrms = cnfrm_psw.getText().toString();
        if (!isValidConfrimPassword()) {
            cpw = false;
            cnfrm_psw.setError("Invalid Password ");
        } else {
            cpw = true;
        }
        final String phones = phone.getText().toString();
        if (!isValidPhone(phones)) {
            ph = false;
            phone.setError("Invalid Phone Number");
        } else {
            ph = true;
        }

        final String streets = street.getText().toString();
        if (!isValidStretAddress(streets)) {
            st = false;
            street.setError("Invalid Street Address");
        } else {
            st = true;
        }
        final String citys = city.getText().toString();
        if (!isValidCity(citys)) {
            ci = false;
            city.setError("Invalid City");
        } else {
            ci = true;
        }
        final String zips = zip.getText().toString();
        if (!isZip(zips)) {
            zp = false;
            zip.setError("Invalid Zip");
        } else {
            zp = true;
        }


        if (fn && e && ps && cpw && ph && st && ci && zp   )
        {
            //showDialog();
            SignUp();
        }
    }


    // validating email id
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    // validating Name
    private boolean isValidName(String name) {
        String Name_Pattern = "^[a-zA-Z ]+$";

        Pattern pattern = Pattern.compile(Name_Pattern);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    // validating StretAddress
    private boolean isValidStretAddress(String street) {
        String EMAIL_PATTERN = "^[a-zA-Z ]+$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(street);
        return matcher.matches();
    }
    // validating City
    private boolean isValidCity(String city) {
        String EMAIL_PATTERN = "^[a-zA-Z ]+$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(city);
        return matcher.matches();
    }
    // validating Phone Number
    private boolean isValidPhone(String name) {
        if (name.length()==10) {
            return true;
        }
        return false;
    }
    // validating Zip
    private boolean isZip(String zip) {
        if (zip.length()==5) {
            return true;
        }
        return false;
    }

    // validating password with retype password
    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() >=5) {
            return true;
        }
        return false;
    }
    // validating password with retype password
    private boolean isValidConfrimPassword() {
        if (Pwd.equals(cPwd)) {
            return true;
        }
        return false;
    }
}
