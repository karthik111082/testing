package com.hik.trendycraftshow;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;

import com.hik.trendycraftshow.Adapters.PaymentAndPurchaseAdapter;
import com.hik.trendycraftshow.JSON.Api;
import com.hik.trendycraftshow.JSON.WebServiceRequest;
import com.hik.trendycraftshow.Utils.Consts;
import com.hik.trendycraftshow.Utils.IsTablet;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class PaymentAndPurchase extends NavigationDrawer {


    IsTablet tablet;
    boolean isTablet;
    private static final String ALL_TIME = "AllTime";
    private static final String MONTH = "Month";
    Button order,payment;
    PaymentAndPurchaseAdapter adapter;

    private WebServiceRequest.HttpURLCONNECTION getPayment;
    private WebServiceRequest.HttpURLCONNECTION getOrder;
    Api api;
    ProgressDialog pDialog;
    int start=0;
    int limit=10;
    ListView listView;
    boolean hasNext;
    boolean isOrder;
    private ArrayList<String> title=new ArrayList<String>();
    private ArrayList<String> price=new ArrayList<String>();
    private ArrayList<String> category=new ArrayList<String>();
    private ArrayList<String> date=new ArrayList<String>();
    private ArrayList<String> address=new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        isTablet = tablet.isTablet(getApplicationContext());
        if (isTablet) {
            getLayoutInflater().inflate(R.layout.activity_payment_and_purchase, container);
        } else {
            getLayoutInflater().inflate(R.layout.activity_payment_and_purchase_mob, container);
        }
        order=(Button)findViewById(R.id.order);
        payment=(Button)findViewById(R.id.payment);
        listView=(ListView)findViewById(R.id.list);
        clearList();
        new GetOrder().execute();
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isOrder=true;
                clearList();
                order.setBackgroundResource(R.drawable.lefttrue);
                payment.setBackgroundResource(R.drawable.rightfalse);
                new GetOrder().execute();

            }
        });
       payment.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               isOrder=false;
               clearList();
               order.setBackgroundResource(R.drawable.leftfalse);
               payment.setBackgroundResource(R.drawable.righttrue);
               new GetPayment().execute();

           }
       });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == 0) {
                    if (hasNext) {
                        if(isOrder)
                        {
                            ++start;
                            new GetOrder().execute();

                        }
                        else
                        {
                            ++start;
                            new GetPayment().execute();
                        }



                    }
                }


            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });


    }

public void clearList()
{
    title.clear();
    price.clear();
    category.clear();
    address.clear();
    date.clear();
}


    class GetPayment extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(PaymentAndPurchase.this);
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
                        String params = "userid="+ Consts.UserId+"&start="+start+"&end="+limit;
                        Log.d("parameters", params);
                        params=params.replaceAll(" ", "%20");
                        getPayment = api.GET_PAYMENT_HISTORY(params, new WebServiceRequest.Callback() {
                            @Override
                            public void onResult(int responseCode, String responseMessage, Exception exception) {
                                Log.d("response", responseMessage);
                                if (responseCode == 200) {
                                    try {
                                        JSONObject obj = new JSONObject(responseMessage);
                                        hasNext=Boolean.valueOf(obj.getString("hasNext"));
                                        JSONArray arr = obj.getJSONArray("paymentHistories");
                                        for (int i = 0; i < arr.length(); i++) {
                                            JSONObject data=arr.getJSONObject(i);
                                           title.add(data.getString("title"));
                                            price.add(data.getString("price"));
                                            category.add(data.getString("category"));
                                            date.add(data.getString("ordereddate"));
                                            address.add(data.getString("address"));


                                        }
                                        adapter = new PaymentAndPurchaseAdapter(PaymentAndPurchase.this,title,price,category,date,address );
                                        listView.setAdapter(adapter);
                                        int pos = start * limit;
                                        listView.setSelectionFromTop(pos, 0);







                                    } catch (Exception e) {
                                        Log.d(getApplicationContext().toString(), e.toString());

                                    }
                                } else {

                                }
                            }
                        });
                        getPayment.execute();

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

    class GetOrder extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(PaymentAndPurchase.this);
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
                        String params = "userid="+ Consts.UserId+"&start="+start+"&end="+limit;
                        Log.d("parameters", params);
                        params=params.replaceAll(" ", "%20");
                        getOrder = api.GET_ORDER_HISTORY(params, new WebServiceRequest.Callback() {
                            @Override
                            public void onResult(int responseCode, String responseMessage, Exception exception) {
                                Log.d("response", responseMessage);
                                if (responseCode == 200) {
                                    try {


                                        JSONObject obj = new JSONObject(responseMessage);
                                        hasNext=Boolean.valueOf(obj.getString("hasNext"));
                                        JSONArray arr = obj.getJSONArray("orderHistories");
                                        for (int i = 0; i < arr.length(); i++) {
                                            JSONObject data=arr.getJSONObject(i);
                                            title.add(data.getString("title"));
                                            price.add(data.getString("price"));
                                            category.add(data.getString("category"));
                                            date.add(data.getString("ordereddate"));
                                            address.add(data.getString("address"));


                                        }
                                        adapter = new PaymentAndPurchaseAdapter(PaymentAndPurchase.this,title,price,category,date,address );
                                        listView.setAdapter(adapter);
                                        int pos = start * limit;
                                        listView.setSelectionFromTop(pos, 0);






                                    } catch (Exception e) {
                                        Log.d(getApplicationContext().toString(), e.toString());

                                    }
                                } else {

                                }
                            }
                        });
                        getOrder.execute();

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

