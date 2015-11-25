package com.hik.trendycraftshow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.hik.trendycraftshow.JSON.Api;
import com.hik.trendycraftshow.JSON.WebServiceRequest;
import com.hik.trendycraftshow.Utils.Consts;
import com.hik.trendycraftshow.Utils.Validation;

import org.json.JSONException;
import org.json.JSONObject;

public class ForgotPassword extends Activity {

    Button send;
    EditText email;
    String Email_Address;
    private WebServiceRequest.HttpURLCONNECTION forgotPasswordRequest;
    Consts consts;
    Api api;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        back=(ImageButton)findViewById(R.id.back);
        send=(Button)findViewById(R.id.send);
        email=(EditText)findViewById(R.id.email);
        back=(ImageButton)findViewById(R.id.back);
        consts=new Consts(getApplicationContext());
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                finish();
                startActivity(i);
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Email_Address=email.getText().toString();
                if(Validation.isEmpty(Email_Address))
                {
                    email.setError("Enter Email Address");
                }
                else{
                    if(Validation.isValidEmail(Email_Address))
                    {
                        consts.showDialog(ForgotPassword.this);
                        ForgotPwd();
                    }
                    else{
                        email.setError("Invalid Email Address");
                    }

                }

            }
        });

    }

    public void ForgotPwd()
    {
        String params = "username=" + Email_Address;
        Log.d("parameters", params);
        params=params.replaceAll(" ","%20");
        forgotPasswordRequest = api.FORGOT_PASSWORD(params, new WebServiceRequest.Callback() {
            @Override
            public void onResult(int responseCode, String responseMessage, Exception exception) {

                if (responseCode == 200) {
                    try {
                        consts.hideDialog();
                        JSONObject obj = new JSONObject(responseMessage);
                        Log.d("response", responseMessage.toString());
                        String status = obj.getString("msg");

                        if (status.equalsIgnoreCase("success")) {
                            Consts.UserName = obj.getString("UserName");
                            Consts.Password = obj.getString("Password");
                            Consts.FirstName = obj.getString("Firstname");
                            Consts.Phone = obj.getString("Phone");
                            Consts.Street = obj.getString("Street");
                            Consts.City = obj.getString("City");
                            Consts.State = obj.getString("State");
                            Consts.Zip = obj.getString("Zipcode");
                            Consts.Photo = obj.getString("photo").getBytes();
                            consts.hideDialog();
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            finish();
                            startActivity(i);

                        } else {

                            consts.hideDialog();
                            Toast.makeText(getApplicationContext(), "Invalid Email Address", Toast.LENGTH_SHORT).show();


                        }


                    } catch (JSONException e) {
                        Log.d("Forgot Password Error",e.toString());
                        consts.hideDialog();
                    }
                }
            }
        });
        forgotPasswordRequest.execute();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK) {
            Intent i=new Intent(getApplicationContext(),MainActivity.class);
            finish();
            startActivity(i);
        }
        return false;

    }
}
