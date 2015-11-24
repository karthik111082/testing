package com.hik.trendycraftshow;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.hik.trendycraftshow.JSON.Api;
import com.hik.trendycraftshow.JSON.WebServiceRequest;
import com.hik.trendycraftshow.Utils.Consts;
import com.hik.trendycraftshow.Utils.IsTablet;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends Activity {
    boolean isTablet;
    IsTablet tablet;
    private WebServiceRequest.HttpURLCONNECTION loginRequest;
    Api api;
    ProgressDialog pDialog;
    String Username,Password;
    EditText email,psw;
    ImageButton signin,signup,guest;
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
        email = (EditText) findViewById(R.id.email);
        psw = (EditText) findViewById(R.id.psw);

        signin = (ImageButton) findViewById(R.id.sign_in);
        signup = (ImageButton) findViewById(R.id.sign_up);
        guest= (ImageButton)findViewById(R.id.guest);

        guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(i);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), Registration.class);
                startActivity(i);
            }
        });

                signin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //showDialog();
                        boolean e = false;
                        boolean p = false;


                        final String name = email.getText().toString();
                        if (!isValidEmail(name)) {
                            e = false;
                            email.setError("Invalid Email Id");
                        } else {
                            e = true;
                        }
                        final String pswd = psw.getText().toString();

                        if (!isValidPassword(pswd)) {
                            p = false;
                            psw.setError("Invalid Password");
                        } else {
                            p = true;
                        }


                        if (e && p) {
                            Username=email.getText().toString();
                            Password=psw.getText().toString();
                            Login();

                        }

                    }
                });

    }
    public void showDialog()
    {
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

                       /* Log.d("response", responseMessage.toString());
                        String res = obj.getString("msg");
                        String uname = obj.getString("UserName");
                        Log.d("user",uname);
                        Log.d("String", res);
                        if (res.equalsIgnoreCase("failed")) {
                            Log.d("response inner", responseMessage);
                            //hideDialog();
                            Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();

                        } if(uname.length()>0) {*/

                            Consts.UserName = obj.getString("UserName");
                            Consts.Password = obj.getString("Password");
                            //hideDialog();
                            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(i);

                       // }


                    } catch (JSONException e) {
                    }
                }
            }
        });
        loginRequest.execute();
    }

    // validating email id
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // validating password with retype password
    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() > 5) {
            return true;
        }
        return false;
    }
}
