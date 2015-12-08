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
    final static String FORGOT_URL=BASEURL+"forgotpassword?";
    final static String PROFILE_URL=BASEURL+"forgotpassword?";




    public static WebServiceRequest.HttpURLCONNECTION REGISTRATION(String params, WebServiceRequest.Callback callback) {
        WebServiceRequest.HttpURLCONNECTION urlConn = new WebServiceRequest.HttpURLCONNECTION();
        String ur=SIGNUP_URL+params;
        Log.d("URL", ur);
        urlConn.setUrl(ur);
        urlConn.setRequestMethod("GET");
        urlConn.setCallback(callback);

        return urlConn;
    }
    public static WebServiceRequest.HttpURLCONNECTION LOGIN(String params, WebServiceRequest.Callback callback) {
        WebServiceRequest.HttpURLCONNECTION urlConn = new WebServiceRequest.HttpURLCONNECTION();
    String url=LOGIN_URL+params;
        Log.d("URL",url);
        urlConn.setUrl(url);
        urlConn.setRequestMethod("GET");
        urlConn.setCallback(callback);
        return urlConn;
    }

    public static WebServiceRequest.HttpURLCONNECTION ACTIVATE_ACCOUNT(String params, WebServiceRequest.Callback callback) {
        WebServiceRequest.HttpURLCONNECTION urlConn = new WebServiceRequest.HttpURLCONNECTION();
        String url=ACTIVATE_URL+params;
        Log.d("URL",url);
        urlConn.setUrl(url);
        urlConn.setRequestMethod("GET");
        urlConn.setCallback(callback);

        return urlConn;
    }

    public static WebServiceRequest.HttpURLCONNECTION FORGOT_PASSWORD(String params, WebServiceRequest.Callback callback) {
        WebServiceRequest.HttpURLCONNECTION urlConn = new WebServiceRequest.HttpURLCONNECTION();
        String url=FORGOT_URL+params;
        Log.d("URL",url);
        urlConn.setUrl(url);
        urlConn.setRequestMethod("GET");
        urlConn.setCallback(callback);

        return urlConn;
    }
    public static WebServiceRequest.HttpURLCONNECTION PROFILE(String params, WebServiceRequest.Callback callback) {
        WebServiceRequest.HttpURLCONNECTION urlConn = new WebServiceRequest.HttpURLCONNECTION();
        String url=PROFILE_URL+params;
        Log.d("URL",url);
        urlConn.setUrl(url);
        urlConn.setRequestMethod("GET");
        urlConn.setCallback(callback);

        return urlConn;
    }



}
