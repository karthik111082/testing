package com.hik.trendycraftshow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hik.trendycraftshow.Adapters.PaymentAdapter;
import com.hik.trendycraftshow.JSON.Api;
import com.hik.trendycraftshow.JSON.WebServiceRequest;
import com.hik.trendycraftshow.Utils.Consts;
import com.hik.trendycraftshow.Utils.DisplayAdapter;
import com.hik.trendycraftshow.Utils.IsTablet;
import com.hik.trendycraftshow.Utils.Utils;
import com.hik.trendycraftshow.Utils.Validation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PaymentMethod extends NavigationDrawer {

    IsTablet tablet;
    boolean isTablet;
    public static ListView payment_list;
    EditText paypal_emailid;
    Button add_emailid,addpaypal;
    LinearLayout add_layout,addemail_layout;
    private WebServiceRequest.HttpURLCONNECTION sendPaypalid;
    private WebServiceRequest.HttpURLCONNECTION getAccounts;
    private WebServiceRequest.HttpURLCONNECTION deleteAccount;
    Consts consts;
    Api api;
    String PaypalId;
    boolean status=false;
    ArrayList<String> paypalids=new ArrayList<>();
    ArrayList<String> defaultstatus=new ArrayList<>();
    PaymentAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        title=(TextView)findViewById(R.id.titletoolbar);

        isTablet=tablet.isTablet(getApplicationContext());
        isTablet = tablet.isTablet(getApplicationContext());
        if (isTablet) {
            getLayoutInflater().inflate(R.layout.activity_payment_method, container);
        } else {
            getLayoutInflater().inflate(R.layout.activity_payment_method_mob, container);
        }

        payment_list=(ListView)findViewById(R.id.paymentmethod_listview);
        addpaypal=(Button)findViewById(R.id.add);
        paypal_emailid=(EditText)findViewById(R.id.paypal_emailid);
        add_emailid=(Button)findViewById(R.id.addpaypal_emailid);
        add_layout=(LinearLayout)findViewById(R.id.add_layout);

        addemail_layout=(LinearLayout)findViewById(R.id.addemail_layout);
        title.setText("PAYMENT METHOD");
        consts=new Consts(getApplicationContext());
        GetPaypalAccounts();
        addpaypal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paypal_emailid.setText("");
                addemail_layout.setVisibility(View.VISIBLE);
                add_layout.setVisibility(View.GONE);
            }
        });

        add_emailid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaypalId=paypal_emailid.getText().toString();
                if(PaypalId.length()>0)
                {
                    if(Validation.isValidEmail(PaypalId))
                    {
                        if(paypalids.size()==0)
                        {
                            status=true;
                        }
                        sendPaypalId();
                        consts.showDialog(PaymentMethod.this);
                    }else
                    {
                        paypal_emailid.setError("Invalid email address. Please Check!!!");
                    }
                }else{
                    paypal_emailid.setError("Enter your Paypal email address. Please Check!!!");
                }

            }
        });




    }

    public void sendPaypalId()
    {
        String params = "paypalmail=" + PaypalId + "&status="+status+ "&userid="+Consts.UserId;
        Log.d("parameters", params);
        params=params.replaceAll(" ", "%20");
        sendPaypalid = api.SEND_PAYPAL_ID(params, new WebServiceRequest.Callback() {
            @Override
            public void onResult(int responseCode, String responseMessage, Exception exception) {

                if (responseCode == 200) {
                    try {
                        consts.hideDialog();
                        JSONArray obj = new JSONArray(responseMessage);
                        Log.d("response", responseMessage.toString());

                        addemail_layout.setVisibility(View.GONE);
                        add_layout.setVisibility(View.VISIBLE);
                        paypalids.clear();
                        defaultstatus.clear();
                        for (int i = 0; i < obj.length(); i++)

                        {
                            JSONObject data = obj.getJSONObject(i);
                            String paypalid = data.getString("paypalMail");
                            String stat = data.getString("status");
                            paypalids.add(paypalid);
                            defaultstatus.add(stat);


                        }
                        adapter = new PaymentAdapter(PaymentMethod.this, paypalids, defaultstatus);
                        payment_list.setAdapter(adapter);


                    } catch (JSONException e) {
                        addemail_layout.setVisibility(View.GONE);
                        add_layout.setVisibility(View.VISIBLE);
                        consts.hideDialog();
                        Toast.makeText(getApplicationContext(), "Paypal email address already exists!!!", Toast.LENGTH_SHORT).show();


                        consts.hideDialog();
                    }
                }
            }
        });
        sendPaypalid.execute();

    }

    public void GetPaypalAccounts()
    {
        consts.showDialog(PaymentMethod.this);
        String params = "userid="+Consts.UserId;
        Log.d("parameters", params);
        params=params.replaceAll(" ", "%20");
        sendPaypalid = api.GET_PAYPAL_ACCOUNTS(params, new WebServiceRequest.Callback() {
            @Override
            public void onResult(int responseCode, String responseMessage, Exception exception) {

                if (responseCode == 200) {
                    try {
                        consts.hideDialog();
                        paypalids.clear();
                        defaultstatus.clear();
                        JSONArray obj = new JSONArray(responseMessage);
                        for (int i = 0; i < obj.length(); i++)

                        {
                            JSONObject data = obj.getJSONObject(i);
                            String paypalid = data.getString("paypalMail");
                            String stat = data.getString("status");
                            paypalids.add(paypalid);
                            defaultstatus.add(stat);


                        }
                        adapter = new PaymentAdapter(PaymentMethod.this, paypalids, defaultstatus);
                        payment_list.setAdapter(adapter);
                        addemail_layout.setVisibility(View.GONE);
                        add_layout.setVisibility(View.VISIBLE);
                        consts.hideDialog();
                        Toast.makeText(getApplicationContext(), "Please enter valid email-address!!!", Toast.LENGTH_SHORT).show();


                    } catch (JSONException e) {
                        Log.d("Forgot Password Error", e.toString());
                        consts.hideDialog();
                    }
                }
            }
        });
        sendPaypalid.execute();

    }



}