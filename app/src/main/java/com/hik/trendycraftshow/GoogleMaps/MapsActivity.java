package com.hik.trendycraftshow.GoogleMaps;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.hik.trendycraftshow.NavigationDrawer;
import com.hik.trendycraftshow.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends NavigationDrawer implements View.OnClickListener {

    private GoogleMap mMap;
    final String TAG = "PathGoogleMapActivity";
     LatLng yourPosition,productPosition;
    double yourlatitude,yourlongitude,addLatitude,addLongitude;
    String productName;
    Polyline line;
    Context context;

    Location location;
    boolean check_provider_enabled = false;

    // Static LatLng
    LatLng startLatLng ;
    LatLng endLatLng ;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_maps, container);
        context=MapsActivity.this;
        yourlatitude=getIntent().getExtras().getDouble("yourlat");
        yourlongitude=getIntent().getExtras().getDouble("yourlong");
        addLatitude=getIntent().getExtras().getDouble("addlat");
        addLongitude=getIntent().getExtras().getDouble("addlong");
        productName=getIntent().getExtras().getString("addname");
        yourPosition = new LatLng(yourlatitude,yourlongitude);
        productPosition= new LatLng(addLatitude,addLongitude);
        startLatLng=yourPosition;
        endLatLng=productPosition;

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mMap = ((SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map)).getMap();

        /*
        // Construct a CameraPosition focusing on Mountain View and animate the camera to that position.
        CameraPosition cameraPosition = new CameraPosition.Builder()
            //.target(endLatLng)      // Sets the center of the map to Mountain View
            .zoom(17)                   // Sets the zoom
            .bearing(90)                // Sets the orientation of the camera to east
            .tilt(30)                   // Sets the tilt of the camera to 30 degrees
            .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));*/

        mMap.setMyLocationEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(startLatLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(12));

        mMap.getUiSettings().setZoomControlsEnabled(false);







        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);
        location = service.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        // check if enabled and if not send user to the GSP settings
        // Better solution would be to display a dialog and suggesting to
        // go to the settings
        if (!enabled) {
          /*Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
          startActivity(intent);*/
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
            Toast.makeText(getApplicationContext(), "Enable GPS servcies to use this app.", Toast.LENGTH_LONG).show();
        } else{



            try{

                final LatLng PERTH = new LatLng(-31.90, 115.86);
                Marker perth = mMap.addMarker(new MarkerOptions()
                        .position(PERTH)
                        .anchor((float)0.5,(float)0.5)
                        .rotation((float)90.0));

                String urlTopass = makeURL(startLatLng.latitude,
                        startLatLng.longitude, endLatLng.latitude,
                        endLatLng.longitude);
                 new connectAsyncTask(urlTopass).execute();

            }catch(Exception e){
                e.printStackTrace();
            }

        }

       /* if (mMap!=null){
            Marker hamburg = mMap.addMarker(new MarkerOptions().position(startLatLng)
                .title("Hamburg"));
            Marker kiel = mMap.addMarker(new MarkerOptions()
                .position(endLatLng)
                .title("Vivek")
                .snippet("VIVEK's ANDROID HACKER")
                .icon(BitmapDescriptorFactory
                    .fromResource(R.drawable.one)));
          }*/



        // Now auto clicking the button
        // btntemp.performClick();
    }
/*
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
        case R.id.btn_pass_home_call_temp:
            String urlTopass = makeURL(startLatLng.latitude,
                    startLatLng.longitude, endLatLng.latitude,
                    endLatLng.longitude);
            new connectAsyncTask(urlTopass).execute();
            break;

        default:
            break;
        }

    }*/

    private class connectAsyncTask extends AsyncTask<Void, Void, String> {
        private ProgressDialog progressDialog;
        String url;

        connectAsyncTask(String urlPass) {
            url = urlPass;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Fetching route, Please wait...");
            progressDialog.setIndeterminate(true);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            JSONParser jParser = new JSONParser();
            String json = jParser.getJSONFromUrl(url);
            return json;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.hide();
            if (result != null) {
                drawPath(result);
            }
        }
    }

    public String makeURL(double sourcelat, double sourcelog, double destlat,
                          double destlog) {
        StringBuilder urlString = new StringBuilder();
        urlString.append("http://maps.googleapis.com/maps/api/directions/json");
        urlString.append("?origin=");// from
        urlString.append(Double.toString(sourcelat));
        urlString.append(",");
        urlString.append(Double.toString(sourcelog));
        urlString.append("&destination=");// to
        urlString.append(Double.toString(destlat));
        urlString.append(",");
        urlString.append(Double.toString(destlog));
        //urlString.append("&sensor=false&mode=driving&alternatives=true");
        return urlString.toString();
    }

    public class JSONParser {

        InputStream is = null;
        JSONObject jObj = null;
        String json = "";

        // constructor
        public JSONParser() {
        }

        public String getJSONFromUrl(String url) {

            // Making HTTP request
            try {
                // defaultHttpClient
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);

                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(is, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }

                json = sb.toString();
                is.close();
            } catch (Exception e) {
                Log.e("Buffer Error", "Error converting result " + e.toString());
            }
            return json;

        }
    }

    public void drawPath(String result) {
        if (line != null) {
            mMap.clear();
        }
        mMap.addMarker(new MarkerOptions().position(endLatLng)).setTitle(productName);//.icon(BitmapDescriptorFactory.fromResource(R.drawable.avator)));
        mMap.addMarker(new MarkerOptions().position(startLatLng)).setTitle("You");//.icon( BitmapDescriptorFactory.fromResource(R.drawable.avator)));
        try {
            // Tranform the string into a json object
            final JSONObject json = new JSONObject(result);
            JSONArray routeArray = json.getJSONArray("routes");
            JSONObject routes = routeArray.getJSONObject(0);
            JSONObject overviewPolylines = routes
                    .getJSONObject("overview_polyline");
            String encodedString = overviewPolylines.getString("points");
            List<LatLng> list = decodePoly(encodedString);

            PolylineOptions options = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);
            for (int z = 0; z < list.size(); z++) {
                LatLng point = list.get(z);
                options.add(point);
            }
            line = mMap.addPolyline(options);

            /*for (int z = 0; z < list.size() - 1; z++) {
                LatLng src = list.get(z);
                LatLng dest = list.get(z + 1);
                line = mMap.addPolyline(new PolylineOptions()
                        .add(new LatLng(src.latitude, src.longitude),
                                new LatLng(dest.latitude, dest.longitude))
                        .width(5).color(Color.BLUE).geodesic(true));
            }*/

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub

    }

    /*@Override
    public void onMarkerDrag(Marker marker) {

       //add the marker's latlng in a arraylist of LatLng and pass it to the loop
        for (int i = 0; i < arraylistoflatlng.size(); i++) {
             mMap.addPolyline(new PolylineOptions()
            .addAll(arraylistoflatlng)
            .width(5)
            .color(Color.RED));
        }
        }*/
}