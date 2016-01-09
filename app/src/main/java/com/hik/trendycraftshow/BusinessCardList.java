package com.hik.trendycraftshow;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.hik.trendycraftshow.Adapters.BusinessDirectoryAdaptor;
import com.hik.trendycraftshow.JSON.Api;
import com.hik.trendycraftshow.JSON.WebServiceRequest;
import com.hik.trendycraftshow.Utils.Consts;
import com.hik.trendycraftshow.Utils.ListConsts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BusinessCardList extends NavigationDrawer {
    // Remove the below line after defining your own ad unit ID.
    private WebServiceRequest.HttpURLCONNECTION getProducts;
    private WebServiceRequest.HttpURLCONNECTION searchProducts;
    ProgressDialog pDialog;
    BusinessDirectoryAdaptor adapter;
    Api api;
    GridView gridView;
    int start=0;
    int limit=10;
    boolean hasNext;
    int categoryId=8;
    boolean isSearch;
    Button button1,button2,button3,button4,showmore;
    String keyword,street,city,zip,distance,latitude,langitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean isTablet=tablet.isTablet(getApplicationContext());
        if(isTablet)
        {
            getLayoutInflater().inflate(R.layout.activity_business_card_list_mob, container);}
        else
        {
            getLayoutInflater().inflate(R.layout.activity_business_card_list_mob, container);
        }
        gridView=(GridView)findViewById(R.id.business_grid);
        button1=(Button)findViewById(R.id.button1);
        button2=(Button)findViewById(R.id.button2);
        button3=(Button)findViewById(R.id.button3);
        button4=(Button)findViewById(R.id.button4);
        showmore=(Button)findViewById(R.id.showmore);

        // Load an ad into the AdMob banner view.
        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().setRequestAgent("android_studio:ad_template").build();
        adView.loadAd(adRequest);
        isSearch=getIntent().getExtras().getBoolean("isSearch");
        if(isSearch)
        {
            keyword=getIntent().getExtras().getString("keyword");
            street=getIntent().getExtras().getString("street");
            city=getIntent().getExtras().getString("city");
            zip=getIntent().getExtras().getString("zip");
            distance=getIntent().getExtras().getString("distance");
            latitude=getIntent().getExtras().getString("latitude");
            langitude=getIntent().getExtras().getString("longitude");
            categoryId=getIntent().getExtras().getInt("catid");
            ListConsts.clearList();
            ListConsts.clearbusiness();
            new SearchData().execute();
        }else {
            ListConsts.clearList();
            ListConsts.clearbusiness();
            new LoadData().execute();
        }
        // Toasts the test ad message on the screen. Remove this after defining your own ad unit ID.
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListConsts.clearList();
                ListConsts.clearbusiness();

                categoryId = 8;
                changeButton(categoryId);
                isSearch = false;
                new LoadData().execute();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListConsts.clearList();
                ListConsts.clearbusiness();
                isSearch=false;
                categoryId=5;
                changeButton(categoryId);
                new LoadData().execute();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListConsts.clearList();
                ListConsts.clearbusiness();
                isSearch=false;
                categoryId=6;
                changeButton(categoryId);
                new LoadData().execute();
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListConsts.clearList();
                ListConsts.clearbusiness();
                isSearch=false;
                categoryId=7;
                changeButton(categoryId);
                new LoadData().execute();
            }
        });
        showmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasNext) {

                    ++start;
                    if (isSearch) {
                        new SearchData().execute();
                    } else {
                        if (!pDialog.isShowing())
                            new LoadData().execute();
                    }


                }
            }
        });
        search.setVisibility(View.VISIBLE);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),BusinessSearch.class);
                i.putExtra("catid",categoryId);
                finish();
                startActivity(i);

            }
        });

changeButton(categoryId);
    }




    class LoadData extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(BusinessCardList.this);
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
                        String params = "userid=" + Consts.UserId + "&start=" + start + "&end=" + limit + "&catid=" + categoryId;
                        Log.d("parameters", params);
                        params = params.replaceAll(" ", "%20");
                        getProducts = api.GET_BUSINESS_CARDS(params, new WebServiceRequest.Callback() {
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

                                        adapter = new BusinessDirectoryAdaptor(BusinessCardList.this, ListConsts.BusinessName, ListConsts.BusinessImage1, ListConsts.BusinessOnlineStatus);
                                        gridView.setAdapter(adapter);
                                        int pos = start * limit;
                                        //gridView.setSelectionFromTop(pos, 0);


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
            if(hasNext)
            {
                showmore.setVisibility(View.VISIBLE);
            }else{
                showmore.setVisibility(View.GONE);
            }




        }

    }

    class SearchData extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(BusinessCardList.this);
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
                        String params = "userid=" + Consts.UserId + "&start=" + start + "&end=" + limit + "&catid=" + categoryId+ "&lat=" + latitude+ "&long=" + langitude+ "&distance=" + distance+ "&street=" + street+ "&city=" + city+ "&zip=" + zip+ "&keyword=" + keyword;
                        Log.d("parameters", params);
                        params = params.replaceAll(" ", "%20");
                        searchProducts = api.GET_SEARCH_BUSINESS_CARDS(params, new WebServiceRequest.Callback() {
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

                                        adapter = new BusinessDirectoryAdaptor(BusinessCardList.this, ListConsts.BusinessName, ListConsts.BusinessImage1, ListConsts.BusinessOnlineStatus);
                                        gridView.setAdapter(adapter);
                                        int pos = start * limit;
                                        //gridView.setSelectionFromTop(pos, 0);


                                    } catch (JSONException e) {
                                        Log.d(getApplicationContext().toString(), e.toString());

                                    }
                                } else {

                                }
                            }
                        });
                        searchProducts.execute();

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

    public void changeButton(int id)
    {
        switch (id)
        {
            case 8:
                button1.setBackgroundResource(R.drawable.lefttrue);
                button2.setBackgroundResource(R.drawable.centerfalse);
                button3.setBackgroundResource(R.drawable.centerfalse);
                button4.setBackgroundResource(R.drawable.rightfalse);
                break;
            case 5:
                button1.setBackgroundResource(R.drawable.leftfalse);
                button2.setBackgroundResource(R.drawable.centertrue);
                button3.setBackgroundResource(R.drawable.centerfalse);
                button4.setBackgroundResource(R.drawable.rightfalse);
                break;
            case 6:
                button1.setBackgroundResource(R.drawable.leftfalse);
                button2.setBackgroundResource(R.drawable.centerfalse);
                button3.setBackgroundResource(R.drawable.centertrue);
                button4.setBackgroundResource(R.drawable.rightfalse);
                break;
            case 7:
                button1.setBackgroundResource(R.drawable.leftfalse);
                button2.setBackgroundResource(R.drawable.centerfalse);
                button3.setBackgroundResource(R.drawable.centerfalse);
                button4.setBackgroundResource(R.drawable.righttrue);
                break;



        }
    }

}
