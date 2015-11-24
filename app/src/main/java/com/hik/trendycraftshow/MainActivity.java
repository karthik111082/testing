package com.hik.trendycraftshow;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.hik.trendycraftshow.JSON.Api;
import com.hik.trendycraftshow.JSON.WebServiceRequest;
import com.hik.trendycraftshow.Utils.Consts;
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
                       boolean username=false,pass=false;
                        Username = email.getText().toString();
                        Password = psw.getText().toString();
                        if(Validation.isValidEmail(Username))
                        {
                            username=true;
                        }else{
                            username=false;
                            email.setError("Invalid Email");
                        }
                        if(Validation.isValidPassword(Password))
                        {
                            pass=true;
                        }else{
                            pass=false;
                            psw.setError("Invalid Password");
                        }


                        if (username && pass) {
                            Username = email.getText().toString();
                            Password = psw.getText().toString();
                            showDialog();
                            Login();

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
                            Consts.UserName=obj.getString("username");
                            Consts.Password=obj.getString("Password");
                            Consts.FirstName=obj.getString("Firstname");
                            Consts.Phone=obj.getString("Phone");
                            Consts.Street=obj.getString("Street");
                            Consts.City=obj.getString("City");
                            Consts.State=obj.getString("State");
                            Consts.Zip=obj.getString("Zipcode");
                            Consts.Photo=obj.getString("photo").getBytes();
                            hideDialog();
                            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(i);

                        } else {

                            hideDialog();
                            Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();


                        }


                    } catch (JSONException e) {
                    }
                }
            }
        });
        loginRequest.execute();
    }


}
