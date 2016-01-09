package com.hik.trendycraftshow;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.hik.trendycraftshow.Adapters.MySalesAdapter;
import com.hik.trendycraftshow.JSON.Api;
import com.hik.trendycraftshow.JSON.WebServiceRequest;
import com.hik.trendycraftshow.Utils.Consts;
import com.hik.trendycraftshow.Utils.InternetStatus;
import com.hik.trendycraftshow.Utils.IsTablet;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Mysales extends NavigationDrawer {

    IsTablet tablet;
    boolean isTablet;

    TextView toatl_sale,usersince,name;
    ListView listView;
    CircularImageView photo;
    boolean hasNext;
    private WebServiceRequest.HttpURLCONNECTION getSalesList;
    Api api;
    ProgressDialog pDialog;
    int start=0;
    int limit=5;
    private ArrayList<String> addImage=new ArrayList<String>();
    private ArrayList<String> addTitle=new ArrayList<String>();;
    private ArrayList<String> price=new ArrayList<String>();;
    private ArrayList<String> category=new ArrayList<String>();;
    String totalprice;
    MySalesAdapter adapter;
    boolean internet;
    InternetStatus internetStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        title=(TextView)findViewById(R.id.titletoolbar);
        title.setText("My Sales");
        isTablet=tablet.isTablet(getApplicationContext());
        isTablet = tablet.isTablet(getApplicationContext());
        if (isTablet) {
            getLayoutInflater().inflate(R.layout.activity_mysales, container);
        } else {
            getLayoutInflater().inflate(R.layout.activity_mysales_mob, container);
        }
        internetStatus=new InternetStatus();

        photo=(CircularImageView)findViewById(R.id.profile_image_mysales);
        toatl_sale=(TextView)findViewById(R.id.tatal_sale);
        usersince=(TextView)findViewById(R.id.member_date);
        listView=(ListView)findViewById(R.id.business_list);
        name=(TextView)findViewById(R.id.name);
        setData();
        if(internet=internetStatus.InternetStatus(Mysales.this))
        new GetMysales().execute();
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == 0) {
                    if (hasNext) {
                        if(internet=internetStatus.InternetStatus(Mysales.this))
                        ++start;
                        new GetMysales().execute();

                    }
                }


            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });


    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK) {

        }
        return false;

    }
public void setData()
{
    try{
    name.setText(Consts.FirstName);
    usersince.setText(Consts.UserSince);
    if(Consts.Photo==null)
    {
        photo.setBackgroundResource(R.drawable.avator);
    }
    else
    {
        Picasso.with(this)
                .load(Consts.PhotoDownloadPath+Consts.Photo)
                .placeholder(R.drawable.avator) // optional
                .error(R.drawable.avator)
                .skipMemoryCache()// optional
                .into(photo);
        photo.setBorderWidth(5);
    }
    }catch (Exception e){}

}

    class GetMysales extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Mysales.this);
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
                        getSalesList = api.GET_MYSALES_LIST(params, new WebServiceRequest.Callback() {
                            @Override
                            public void onResult(int responseCode, String responseMessage, Exception exception) {
                                Log.d("response", responseMessage);
                                if (responseCode == 200) {
                                    try {
                                        JSONObject obj = new JSONObject(responseMessage);
                                        hasNext = Boolean.valueOf(obj.getString("hasNext"));
                                        JSONArray arr = obj.getJSONArray("mySales");
                                        for (int i = 0; i < arr.length(); i++) {
                                            JSONObject data = arr.getJSONObject(i);
                                            addTitle.add(data.getString("title"));
                                            addImage.add(data.getString("photo"));
                                            price.add(data.getString("price"));
                                            category.add(data.getString("category"));


                                        }
                                        totalprice=obj.getString("totalPrice");
                                        Double amount = Double.parseDouble(totalprice);
                                        DecimalFormat decim = new DecimalFormat("0.00");
                                        Log.d("Price",decim.format(amount));
                                        toatl_sale.setText("$ "+decim.format(amount));
                                        adapter = new MySalesAdapter(Mysales.this, addImage,addTitle, price, category);
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
                        getSalesList.execute();

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
