package com.hik.trendycraftshow.ListAdapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hik.trendycraftshow.Adapters.ProductAdapter;
import com.hik.trendycraftshow.AddsListActivity;
import com.hik.trendycraftshow.JSON.Api;
import com.hik.trendycraftshow.JSON.WebServiceRequest;
import com.hik.trendycraftshow.ProductDetail;
import com.hik.trendycraftshow.R;
import com.hik.trendycraftshow.Utils.Consts;
import com.hik.trendycraftshow.Utils.InternetStatus;
import com.hik.trendycraftshow.Utils.IsTablet;
import com.hik.trendycraftshow.Utils.ListConsts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class ProductListAdapter extends AddsListActivity implements OnMapReadyCallback,GoogleMap.OnMarkerClickListener,
        GoogleMap.OnInfoWindowClickListener {


    class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

        // These a both viewgroups containing an ImageView with id "badge" and two TextViews with id
        // "title" and "snippet".
        private final View mWindow;



        CustomInfoWindowAdapter() {
            mWindow = getLayoutInflater().inflate(R.layout.layout, null);

        }

        @Override
        public View getInfoWindow(Marker marker) {

            render(marker, mWindow);
            return mWindow;
        }

        @Override
        public View getInfoContents(Marker marker) {

            return null;
        }

        private void render(Marker marker, View view) {
            int badge;
            // Use the equals() method on a Marker to check for equals.  Do not use ==.

          //  ((ImageView) view.findViewById(R.id.badge)).setImageResource(badge);
int id=Integer.parseInt(marker.getTitle());
            String title = ListConsts.Title.get(id);
            TextView titleUi = ((TextView) view.findViewById(R.id.title));
            if (title != null) {
                // Spannable string allows us to edit the formatting of the text.
                SpannableString titleText = new SpannableString(title);
                titleText.setSpan(new ForegroundColorSpan(Color.RED), 0, titleText.length(), 0);
                titleUi.setText(titleText);
            } else {
                titleUi.setText("");
            }

            String Price = ListConsts.Price.get(id);
            TextView price = ((TextView) view.findViewById(R.id.price));
            if(Integer.parseInt(ListConsts.CatId.get(id))>4&&Integer.parseInt(ListConsts.CatId.get(id))<8) {
                if (Price != null && Price.length() > 0) {
                    SpannableString snippetText = new SpannableString(Price);
                    snippetText.setSpan(new ForegroundColorSpan(Color.BLUE), 0, Price.length(), 0);
                    price.setText("Price: $ "+snippetText);
                } else {
                    price.setText("");
                }
            }else{
                price.setText("");
                price.setVisibility(View.GONE);
            }
            String Address = ListConsts.Address.get(id);
            TextView address = ((TextView) view.findViewById(R.id.address));
            if (Address != null && Address.length() > 0) {
                SpannableString addresstext = new SpannableString(Address);
                addresstext.setSpan(new ForegroundColorSpan(Color.BLUE), 0, Address.length(), 0);
                address.setText(addresstext);
            } else {
                address.setText("");
            }
            TextView detail = ((TextView) view.findViewById(R.id.detail));
            detail.setText("View details");
        }
    }








    IsTablet tablet;
    public ListView listView;
   public boolean hasNext=false;
    private WebServiceRequest.HttpURLCONNECTION getProducts;
    private WebServiceRequest.HttpURLCONNECTION searchProducts;
    int i;
    Api api;
    ProductAdapter adapter;
    int start=0;
    final int limit=5;
   public LinearLayout mapview;
   public boolean isSearch=false,isListView=false;
    public int categoryId;
   public String keyword,street,city,zip,startdate,enddate,minPrice,maxPrice,distance;
    public GoogleMap mMap;

    double latitude;
    double longitude;
    Context mContext;
    ProgressDialog pDialog;


    InternetStatus internetStatus;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean isTablet=tablet.isTablet(getApplicationContext());
        if(isTablet)
        {
            getLayoutInflater().inflate(R.layout.activity_listview, listContainer);}
        else
        {
            getLayoutInflater().inflate(R.layout.activity_listview, listContainer);
        }


        listView=(ListView)findViewById(R.id.list);
        mapview=(LinearLayout)findViewById(R.id.mapView);
        categoryId=getIntent().getExtras().getInt("catid");
        isSearch=getIntent().getExtras().getBoolean("isSearch");
        isListView=getIntent().getExtras().getBoolean("isListView");

if(!isListView)
{
    Log.d("enter", "map");
    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
            .findFragmentById(R.id.map);
    mapFragment.getMapAsync(ProductListAdapter.this);


}

        if(isSearch)
        {
            keyword=getIntent().getExtras().getString("keyword");
            street=getIntent().getExtras().getString("street");
            city=getIntent().getExtras().getString("city");
            zip=getIntent().getExtras().getString("zip");
            distance=getIntent().getExtras().getString("distance");
            startdate=getIntent().getExtras().getString("startdate");
            enddate=getIntent().getExtras().getString("enddate");
            minPrice=getIntent().getExtras().getString("minprice");
            maxPrice=getIntent().getExtras().getString("maxprice");
            latitude=getIntent().getExtras().getDouble("latitude");
            longitude=getIntent().getExtras().getDouble("longitude");

        }

        if(isListView)
        {
            if(isSearch)
            {
               ListConsts.clearList();
                new SearchDta().execute();
                listView.setVisibility(View.VISIBLE);
                mapview.setVisibility(View.GONE);
                ChangeButton(8);

            }else{
                ListConsts.clearList();
                new LoadData().execute();
                listView.setVisibility(View.VISIBLE);
                mapview.setVisibility(View.GONE);
                ChangeButton(8);
            }

        }else
        {

            ChangeButton(9);
            listView.setVisibility(View.GONE);
            mapview.setVisibility(View.VISIBLE);

        }
        ChangeButton(categoryId);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == 0) {
                    if (hasNext) {
                        if (pDialog.isShowing()) {
                            hideDialog();
                        }
                        ++start;
                        if (isSearch) {
                            new SearchDta().execute();
                        } else {
                            if (!pDialog.isShowing())
                                new LoadData().execute();
                        }


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
                if(!Consts.isGuest)
                {
                    Intent i = new Intent(getApplicationContext(), ProductDetail.class);
                    i.putExtra("fileid", position);
                    i.putExtra("catid", categoryId);
                    i.putExtra("isWishlist",false);
                    i.putExtra("source","product");
                    finish();
                    startActivity(i);

                }

            }
        });

    }
    public void showDialog() {
        pDialog = new ProgressDialog(ProductListAdapter.this);
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


    private Map<String, String> SearchParams() {

        Map<String, String> params = new HashMap<String, String>();

        params.put("userid", Consts.UserId);
        params.put("catid",String.valueOf(categoryId));
        params.put("start",String.valueOf(start));
        params.put("end", String.valueOf(limit));
        params.put("keyword", keyword);
        params.put("street", street);
        params.put("city", city);
        params.put("zip", zip);
        params.put("distance", String.valueOf(distance));
        params.put("startdate", startdate);
        params.put("enddate", enddate);
        params.put("latitude", String.valueOf(latitude));
        params.put("langitude", String.valueOf(longitude));
        params.put("minprize",String.valueOf(minPrice));
        params.put("maxprize", String.valueOf(maxPrice));

        return params;
    }
    public void SearchData()
    {
        showDialog();


    }

    public void map()
    {
runOnUiThread(new Runnable() {
    @Override
    public void run() {

    }
});

    }
    private void addMarker(LatLng point) {
        Log.d("test", "map");
        MarkerOptions markerOptions = new MarkerOptions();

        // Setting latitude and longitude for the marker
        markerOptions.position(point);


        // Setting title for the InfoWindow
        markerOptions.title("Position");

        // Setting InfoWindow contents
        markerOptions.snippet("Latitude:" + point.latitude + ",Longitude" + point.longitude);

        // Adding marker on the Google Map
        mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(point));
    }



    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    public boolean onMarkerClick(final Marker marker) {

        // We return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        if(!Consts.isGuest)
        {
            Intent i = new Intent(getApplicationContext(), ProductDetail.class);
            i.putExtra("fileid", Integer.parseInt(marker.getTitle()));
            i.putExtra("catid", categoryId);
            i.putExtra("isWishlist",false);
            i.putExtra("source","product");
            finish();
            startActivity(i);
        }

    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());
        MarkerOptions marker=new MarkerOptions();

        // Set listeners for marker events.  See the bottom of this class for their behavior.
        mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowClickListener(this);
        Log.d("Call","Mapready call");
        Log.d("latsize",""+ListConsts.Latitude.size());
            for(i=0;i<ListConsts.Latitude.size();i++)
            {
                Log.d("lat", ListConsts.Latitude.get(i));
                Log.d("lat", ListConsts.Langtitude.get(i));
                double latitude,longitude;
                if(ListConsts.Latitude.get(i).trim().isEmpty())
                {
                    latitude=2222222;
                }else{
                    latitude=Double.parseDouble(ListConsts.Latitude.get(i));
                }
                if(ListConsts.Langtitude.get(i).trim().isEmpty())
                {
                    longitude=4444444;
                }else
                {
                    longitude=Double.parseDouble(ListConsts.Langtitude.get(i));
                }
                LatLng point = new LatLng(latitude, longitude);
                marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                mMap.addMarker(marker.position(point).title(String.valueOf(i)));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(point));

                // Showing InfoWindow on the GoogleMap




           }
        // Add a marker in Sydney and move the camera

    }

    class LoadData extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ProductListAdapter.this);
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
                        getProducts = api.GET_PRODUCTS(params, new WebServiceRequest.Callback() {
                            @Override
                            public void onResult(int responseCode, String responseMessage, Exception exception) {
                                Log.d("response", responseMessage);
                                if (responseCode == 200) {
                                    try {
                                        hideDialog();

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
                                            ListConsts.Address.add(street +","+" " + city +","+" " + state + ","+" " + zip);
                                            ListConsts.Duration.add(startdate + " - " + enddate);


                                        }

                                        adapter = new ProductAdapter(ProductListAdapter.this, ListConsts.image1, ListConsts.Title, ListConsts.PostDate, ListConsts.Address, ListConsts.Duration);
                                        listView.setAdapter(adapter);
                                        int pos = start * limit;
                                        listView.setSelectionFromTop(pos, 0);
                                        map();
                                        if (pDialog.isShowing())
                                            hideDialog();


                                    } catch (JSONException e) {
                                        Log.d(getApplicationContext().toString(), e.toString());
                                        hideDialog();
                                    }
                                } else {
                                    hideDialog();
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

    class SearchDta extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ProductListAdapter.this);
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
                        Map<String, String> params=SearchParams();
                        StringBuffer requestParams = new StringBuffer();
                        try {
                            if (params != null && params.size() > 0) {
                                // creates the params string, encode them using URLEncoder
                                Iterator<String> paramIterator = params.keySet().iterator();
                                while (paramIterator.hasNext()) {
                                    String key = paramIterator.next();
                                    String value = params.get(key);
                                    requestParams.append(URLEncoder.encode(key, "UTF-8"));
                                    requestParams.append("=").append(
                                            URLEncoder.encode(value, "UTF-8"));
                                    requestParams.append("&");
                                }


                            }
                        }catch (Exception e){}

                        Log.d("parameters", requestParams.toString());
                        searchProducts = api.SEARCH_PRODUCTS(requestParams.toString(), new WebServiceRequest.Callback() {
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
                                            double rating = Double.parseDouble(data.getString("rating"));

                                            String latitude = data.getString("latitude");
                                            String langtitude = data.getString("langitude");
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
                                            ListConsts.Langtitude.add(langtitude);
                                            ListConsts.Latitude.add(latitude);
                                            ListConsts.Title.add(title);
                                            int rnd=Integer.parseInt(String.valueOf(Math.round(rating)));
                                            Log.d("original", "" + rating);
                                            Log.d("Round", "" + rnd);
                                            ListConsts.Rating.add(rnd);
                                            ListConsts.Address.add(street + "," + city + "," + state + "," + zip);
                                            ListConsts.Duration.add(startdate + " - " + enddate);


                                        }

                                        adapter = new ProductAdapter(ProductListAdapter.this, ListConsts.image1, ListConsts.Title, ListConsts.PostDate, ListConsts.Address, ListConsts.Duration);
                                        listView.setAdapter(adapter);
                                        int pos = start * limit;
                                        listView.setSelectionFromTop(pos, 0);


                                    } catch (JSONException e) {
                                        Log.d(getApplicationContext().toString(), e.toString());
                                        hideDialog();
                                    }
                                } else {
                                    hideDialog();
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




        }

    }
}


