package com.hik.trendycraftshow;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.hik.trendycraftshow.Adapters.ProductAdapter;
import com.hik.trendycraftshow.JSON.Api;
import com.hik.trendycraftshow.JSON.WebServiceRequest;
import com.hik.trendycraftshow.Utils.Consts;
import com.hik.trendycraftshow.Utils.IsTablet;
import com.hik.trendycraftshow.Utils.ListConsts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyfollowUp extends NavigationDrawer {
    IsTablet tablet;
    boolean isTablet;
    public ListView listView;
    public boolean hasNext=false;
    private WebServiceRequest.HttpURLCONNECTION getProducts;
    private WebServiceRequest.HttpURLCONNECTION searchProducts;
    int i;
    Api api;
    ProductAdapter adapter;
    int start=0;
    final int limit=5;

    public int categoryId;


    ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   setContentView(R.layout.activity_myfollow_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        title=(TextView)findViewById(R.id.titletoolbar);
        title.setText("My Follow-up");
        if (isTablet) {
            getLayoutInflater().inflate(R.layout.activity_myfollow_up, container);
        } else {
            getLayoutInflater().inflate(R.layout.activity_myfollow_up_mob, container);
        }
        listView=(ListView)findViewById(R.id.follow_list);
        ListConsts.clearList();
        new LoadData().execute();

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == 0) {
                    if (hasNext) {

                        ++start;

                            if (!pDialog.isShowing())
                                new LoadData().execute();

                    }
                }


            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!Consts.isGuest) {
                    Intent i = new Intent(getApplicationContext(), ProductDetail.class);
                    i.putExtra("fileid", position);
                    i.putExtra("catid", categoryId);
                    i.putExtra("isWishlist", false);
                    i.putExtra("source", "followup");
                    finish();
                    startActivity(i);

                }

            }
        });
    }

    class LoadData extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MyfollowUp.this);
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
                        String params = "userid=" + Consts.UserId + "&start=" + start + "&end=" + limit;
                        Log.d("parameters", params);
                        params = params.replaceAll(" ", "%20");
                        getProducts = api.MY_FOLLOW_UP(params, new WebServiceRequest.Callback() {
                            @Override
                            public void onResult(int responseCode, String responseMessage, Exception exception) {
                                Log.d("response", responseMessage);
                                if (responseCode == 200) {
                                    try {


                                        ArrayList<String> tempwish = new ArrayList<String>();
                                        JSONObject obj = new JSONObject(responseMessage);
                                        JSONArray arr = obj.getJSONArray("adds");
                                        hasNext = Boolean.parseBoolean(obj.getString("hasNext"));
                                        if (!Consts.UserId.trim().isEmpty()) {
                                            JSONArray wish = obj.getJSONArray("wishLists");
                                            Log.d("length1", "" + arr.length());
                                            Log.d("length2", "" + wish.length());
                                            hasNext = Boolean.parseBoolean(obj.getString("hasNext"));
                                            for (int w = 0; w < wish.length(); w++) {
                                                JSONObject data = wish.getJSONObject(w);
                                                tempwish.add(data.getString("addId"));
                                            }
                                        }

                                        for (int i = 0; i < arr.length(); i++)

                                        {
                                            JSONObject data = arr.getJSONObject(i);
                                            String addid = data.getString("addid");
                                            if (tempwish.contains(addid)) {
                                                ListConsts.WishlistStatus.add(true);
                                            } else {
                                                ListConsts.WishlistStatus.add(false);
                                            }
                                            String catid = data.getString("catid");
                                            String userid = data.getString("userid");
                                            String title = data.getString("addTitle");
                                            String price = data.getString("price");
                                            String startdate = data.getString("startdate");
                                            String enddate = data.getString("enddate");
                                            String postdate = data.getString("posteddate");
                                            String desc = data.getString("description");
                                            String street = data.getString("street");
                                            String city = data.getString("city");
                                            String state = data.getString("state");
                                            String zip = data.getString("zip");
                                            String latitude = data.getString("latitude");
                                            String langtitude = data.getString("langitude");
                                            String online = data.getString("onlineStatus");
                                            String quickid = data.getString("quickId");
                                            double rating = Double.parseDouble(data.getString("rating"));
                                            JSONArray photoArray = data.getJSONArray("photos");
                                            for (int j = 0; j < photoArray.length(); j++) {
                                                JSONObject photo = photoArray.getJSONObject(j);
                                                if (j == 0) {
                                                    ListConsts.image1.add(photo.getString("photo"));
                                                } else if (j == 1) {
                                                    ListConsts.image2.add(photo.getString("photo"));
                                                } else if (j == 2) {
                                                    ListConsts.image3.add(photo.getString("photo"));
                                                } else if (j == 3) {
                                                    ListConsts.image4.add(photo.getString("photo"));
                                                }
                                            }
                                            JSONObject paypal = data.getJSONObject("method");
                                            String Paypal = paypal.getString("paypalMail");
                                            ListConsts.PaypalId.add(Paypal);
                                            ListConsts.AddId.add(addid);
                                            ListConsts.CatId.add(catid);
                                            ListConsts.UserId.add(userid);
                                            ListConsts.Price.add(price);
                                            ListConsts.StartDate.add(startdate);
                                            ListConsts.EndDate.add(enddate);
                                            ListConsts.PostDate.add(postdate);
                                            ListConsts.Desc.add(desc);
                                            ListConsts.Street.add(street);
                                            ListConsts.City.add(city);
                                            ListConsts.State.add(state);
                                            ListConsts.Zip.add(zip);
                                            ListConsts.QuickBloxIds.add(quickid);
                                            ListConsts.OnlineStatus.add(Boolean.valueOf(online));
                                            ListConsts.Langtitude.add(langtitude);
                                            ListConsts.Latitude.add(latitude);
                                            int rnd=Integer.parseInt(String.valueOf(Math.round(rating)));
                                            Log.d("original", "" + rating);
                                            Log.d("Round", "" + rnd);
                                            ListConsts.Rating.add(rnd);
                                            ListConsts.Title.add(title);
                                            ListConsts.Address.add(street + ", " + city + ", " + state + ", " + zip);
                                            ListConsts.Duration.add(startdate + " - " + enddate);


                                        }

                                        adapter = new ProductAdapter(MyfollowUp.this, ListConsts.image1, ListConsts.Title, ListConsts.PostDate, ListConsts.Address, ListConsts.Duration);
                                        listView.setAdapter(adapter);
                                        int pos = start * limit;
                                        listView.setSelectionFromTop(pos, 0);


                                    } catch (JSONException e) {
                                        Log.d(getApplicationContext().toString(), e.toString());

                                    }
                                } else {

                                }
                            }
                        });
                        getProducts.execute();

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
