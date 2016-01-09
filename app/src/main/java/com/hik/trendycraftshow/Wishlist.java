package com.hik.trendycraftshow;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hik.trendycraftshow.Adapters.BusinessDirectoryAdaptor;
import com.hik.trendycraftshow.Adapters.ProductAdapter;
import com.hik.trendycraftshow.JSON.WebServiceRequest;
import com.hik.trendycraftshow.Utils.Consts;
import com.hik.trendycraftshow.Utils.ListConsts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Wishlist extends NavigationDrawer {
        ListView list;
    Button ads,businessdirec;
    private WebServiceRequest.HttpURLCONNECTION getAds;
    private WebServiceRequest.HttpURLCONNECTION getDirectory;
    ProductAdapter adapter;
    ProgressDialog pDialog;
    boolean hasNext;
    int start=0;
    int limit=5;
    BusinessDirectoryAdaptor wishlistAdapter;
    RelativeLayout gridLayout;
    GridView gridView;
    int categoryId=5;
    Button showmore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        title=(TextView)findViewById(R.id.titletoolbar);
        title.setText("Wishlist");
        if (isTablet) {
            getLayoutInflater().inflate(R.layout.activity_wishlist, container);
        } else {
            getLayoutInflater().inflate(R.layout.activity_wishlist_mob, container);
        }

        list=(ListView)findViewById(R.id.list_wish);
        ads=(Button)findViewById(R.id.ads);
        gridView=(GridView)findViewById(R.id.business_grid);
        businessdirec=(Button)findViewById(R.id.businessdirec);
        gridLayout=(RelativeLayout)findViewById(R.id.gridLayout);
        showmore=(Button)findViewById(R.id.showmore);
        ListConsts.clearList();
        new AdsWishList().execute();
        ads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.setVisibility(View.VISIBLE);
                gridLayout.setVisibility(View.GONE);
                businessdirec.setBackgroundResource(R.drawable.rightfalse);
                ads.setBackgroundResource(R.drawable.lefttrue);
                ListConsts.clearList();
                new AdsWishList().execute();


            }
        });
        businessdirec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ListConsts.clearbusiness();
                ListConsts.clearList();
                list.setVisibility(View.GONE);
                gridLayout.setVisibility(View.VISIBLE);
                businessdirec.setBackgroundResource(R.drawable.righttrue);
                ads.setBackgroundResource(R.drawable.leftfalse);
                new LoadData().execute();

            }
        });
        showmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (hasNext) {
                     ++start;

                    if (!pDialog.isShowing())
                        new LoadData().execute();
                }

            }
        });
        list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == 0) {
                    if (hasNext) {

                        ++start;
                        new AdsWishList().execute();

                    }


                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), ProductDetail.class);
                i.putExtra("fileid", position);
                i.putExtra("catid", Integer.parseInt(ListConsts.CatId.get(position)));
                i.putExtra("isWishlist", true);
                i.putExtra("source", "wishlist");
                finish();
                startActivity(i);
            }
        });


    }

    class AdsWishList extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Wishlist.this);
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
                        getAds = api.GET_ADS_WISHLIST(params, new WebServiceRequest.Callback() {
                            @Override
                            public void onResult(int responseCode, String responseMessage, Exception exception) {
                                Log.d("response", responseMessage);
                                if (responseCode == 200) {
                                    try {


                                        ArrayList<String> tempwish = new ArrayList<String>();
                                        JSONObject obj = new JSONObject(responseMessage);
                                        JSONArray arr = obj.getJSONArray("adds");
                                        Log.d("length1", "" + arr.length());
                                        hasNext = Boolean.parseBoolean(obj.getString("hasNext"));
                                        for (int i = 0; i < arr.length(); i++)

                                        {
                                            JSONObject data = arr.getJSONObject(i);
                                            String addid = data.getString("addid");
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
                                            double rating = Double.parseDouble(data.getString("rating"));
                                            String latitude = data.getString("latitude");
                                            String langtitude = data.getString("langitude");
                                            String online = data.getString("onlineStatus");
                                            String quickid = data.getString("quickId");
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
                                            ListConsts.Title.add(title);
                                            int rnd=Integer.parseInt(String.valueOf(Math.round(rating)));
                                            Log.d("original",""+rating);
                                            Log.d("Round",""+rnd);
                                            ListConsts.Rating.add(rnd);
                                            ListConsts.Address.add(street +", " + city + ", " + state + ", " + zip);
                                            ListConsts.Duration.add(startdate + " - " + enddate);


                                        }

                                        adapter = new ProductAdapter(Wishlist.this, ListConsts.image1, ListConsts.Title, ListConsts.PostDate, ListConsts.Address, ListConsts.Duration);
                                        list.setAdapter(adapter);
                                        int pos = start * limit;
                                        list.setSelectionFromTop(pos, 0);
                                        Log.d("addresslength", "" + ListConsts.Address.size());
                                        Log.d("addresslength",""+ListConsts.Address.toString());
                                        Log.d("street", "" + ListConsts.Street.toString());


                                    } catch (JSONException e) {
                                        Log.d(getApplicationContext().toString(), e.toString());

                                    }
                                } else {

                                }
                            }
                        });
                        getAds.execute();

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

    class LoadData extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Wishlist.this);
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
                        getDirectory = api.GET_CARDS_WISHLIST(params, new WebServiceRequest.Callback() {
                            @Override
                            public void onResult(int responseCode, String responseMessage, Exception exception) {
                                Log.d("response", responseMessage);
                                if (responseCode == 200) {
                                    try {


                                        ArrayList<String> tempwish = new ArrayList<String>();
                                        JSONObject obj = new JSONObject(responseMessage);
                                        JSONArray arr = obj.getJSONArray("directory");
                                        hasNext = Boolean.parseBoolean(obj.getString("hasNext"));
                                        if (!Consts.UserId.trim().isEmpty()) {
                                            JSONArray wish = obj.getJSONArray("directoryWishLists");
                                            Log.d("length1", "" + arr.length());
                                            Log.d("length2", "" + wish.length());
                                            for (int w = 0; w < wish.length(); w++) {
                                                JSONObject data = wish.getJSONObject(w);
                                                tempwish.add(data.getString("directoryId"));
                                            }
                                        }

                                        for (int i = 0; i < arr.length(); i++)

                                        {
                                            JSONObject data = arr.getJSONObject(i);
                                            String addid = data.getString("directoryId");
                                            if (tempwish.contains(addid)) {
                                                ListConsts.BusinessWishList.add(true);
                                            } else {
                                                ListConsts.BusinessWishList.add(false);
                                            }
                                            String catid = data.getString("catId");
                                            String userid = data.getString("userId");
                                            String description = data.getString("description");
                                            boolean rate = data.getBoolean("rating");
                                            JSONObject account = data.getJSONObject("account");
                                            String Email = account.getString("UserName");
                                            String name = account.getString("Firstname");
                                            String phone = account.getString("Phone");
                                            String cellPhone = account.getString("cellPhone");
                                            String street = account.getString("Street");
                                            String city = account.getString("City");
                                            String state = account.getString("State");
                                            String zip = account.getString("Zipcode");
                                            String quickId = account.getString("quickId");
                                            String companyName = account.getString("companyName");
                                            String online = account.getString("loginSatus");

                                            JSONArray photoArray = data.getJSONArray("photos");
                                            for (int j = 0; j < photoArray.length(); j++) {
                                                JSONObject photo = photoArray.getJSONObject(j);
                                                if (j == 0) {
                                                    ListConsts.BusinessImage1.add(photo.getString("photo"));
                                                } else if (j == 1) {
                                                    ListConsts.BusinessImage2.add(photo.getString("photo"));
                                                } else if (j == 2) {
                                                    ListConsts.BusinessImage3.add(photo.getString("photo"));
                                                } else if (j == 3) {
                                                    ListConsts.BusinessImage4.add(photo.getString("photo"));
                                                }
                                            }

                                            ListConsts.UserId.add(userid);
                                            ListConsts.CatId.add(catid);
                                            ListConsts.BusinessId.add(addid);
                                            ListConsts.BusinessCompany.add(companyName);
                                            ListConsts.BusinessName.add(name);
                                            ListConsts.BusinessCell.add(cellPhone);
                                            ListConsts.BusinessHomePhone.add(phone);
                                            ListConsts.Street.add(street);
                                            ListConsts.City.add(city);
                                            ListConsts.State.add(state);
                                            ListConsts.Zip.add(zip);
                                            ListConsts.BusinessAddress.add(street + ", " + city + ", " + state + ", " + zip);
                                            ListConsts.BusinessEmail.add(Email);
                                            ListConsts.BusinessQuickId.add(quickId);
                                            ListConsts.BusinessOnlineStatus.add(Boolean.valueOf(online));
                                            ListConsts.BusinessRate.add(rate);
                                            ListConsts.BusinessDesc.add(description);


                                        }

                                        wishlistAdapter = new BusinessDirectoryAdaptor(Wishlist.this, ListConsts.BusinessName, ListConsts.BusinessImage1, ListConsts.BusinessOnlineStatus);
                                        gridView.setAdapter(wishlistAdapter);
                                        int pos = start * limit;
                                        //gridView.setSelectionFromTop(pos, 0);


                                    } catch (JSONException e) {
                                        Log.d(getApplicationContext().toString(), e.toString());

                                    }
                                } else {

                                }
                            }
                        });
                        getDirectory.execute();

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
            if(hasNext)
            {
                showmore.setVisibility(View.VISIBLE);
            }else{
                showmore.setVisibility(View.GONE);
            }




        }

    }
}
