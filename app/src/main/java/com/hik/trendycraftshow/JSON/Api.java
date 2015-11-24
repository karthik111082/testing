package com.hik.trendycraftshow.JSON;

import android.util.Log;

/**
 * Created by DHARMA on 10/27/2015.
 */
public class Api {

    final static String BASEURL="http://52.10.61.81/TrendyCraftShow/";
    final static String SIGNUP_URL=BASEURL+"signup?";
    final static String LOGIN_URL=BASEURL+"login?";
    final static String ACTIVATE_URL=BASEURL+"activateuser?";




    public static WebServiceRequest.HttpURLCONNECTION REGISTRATION(String params, WebServiceRequest.Callback callback) {
        WebServiceRequest.HttpURLCONNECTION urlConn = new WebServiceRequest.HttpURLCONNECTION();
        String ur=SIGNUP_URL+params;
        Log.d("URL", ur);
        String encodedURL=null;

        urlConn.setUrl(ur);
      //  List<ValuePair> headers = new ArrayList<>();
       // headers.add(new ValuePair("Content-Type", "application/json"));
        //urlConn.setHeaders(headers);
        urlConn.setRequestMethod("GET");
        urlConn.setCallback(callback);
        // urlConn.setParameters(para);

        //Log.d("Json URL", urlConn.getParameters());
        // Log.d("Json URL", "" + urlConn.getHeaders());
        return urlConn;
    }
    public static WebServiceRequest.HttpURLCONNECTION LOGIN(String params, WebServiceRequest.Callback callback) {
        WebServiceRequest.HttpURLCONNECTION urlConn = new WebServiceRequest.HttpURLCONNECTION();
    String url=LOGIN_URL+params;
        Log.d("URL",url);
        urlConn.setUrl(url);
        //urlConn.setHeaders(headers);
        urlConn.setRequestMethod("GET");
        urlConn.setCallback(callback);
        // urlConn.setParameters(para);
        //Log.d("Json URL", urlConn.getParameters());
        // Log.d("Json URL", "" + urlConn.getHeaders());
        return urlConn;
    }

    public static WebServiceRequest.HttpURLCONNECTION ACTIVATE_ACCOUNT(String params, WebServiceRequest.Callback callback) {
        WebServiceRequest.HttpURLCONNECTION urlConn = new WebServiceRequest.HttpURLCONNECTION();
        String url=ACTIVATE_URL+params;
        Log.d("URL",url);
        urlConn.setUrl(url);
        //urlConn.setHeaders(headers);
        urlConn.setRequestMethod("GET");
        urlConn.setCallback(callback);
        // urlConn.setParameters(para);
        //Log.d("Json URL", urlConn.getParameters());
        // Log.d("Json URL", "" + urlConn.getHeaders());
        return urlConn;
    }
}
