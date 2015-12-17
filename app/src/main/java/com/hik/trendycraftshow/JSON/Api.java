package com.hik.trendycraftshow.JSON;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DHARMA on 10/27/2015.
 */
public class Api {

    //final static String BASEURL="http://52.10.61.81/TrendyCraftShow/";
    final static String BASEURL="http://192.168.0.109:8080/TrendyCraftShow/";
    final static String PAYMENT_URL="http://192.168.0.109:8080/TrendyCraftShow/payment/";
    final static String ADD_URL="http://192.168.0.109:8080/TrendyCraftShow/adds/";
    final static String SIGNUP_URL=BASEURL+"signup?";
    final static String LOGIN_URL=BASEURL+"login?";
    final static String LOGOUT_URL=BASEURL+"logout?";
    final static String ACTIVATE_URL=BASEURL+"activateuser?";
    final static String FORGOT_URL=BASEURL+"forgotpassword?";
    final static String SEND_PROFILE_URL=BASEURL+"updateprofile";
    final static String CHANGE_PASSWORD_URL=BASEURL+"changepassword";
    final static String POST_ADD_URL=ADD_URL+"postadd";
    final static String SEND_PAYPAL_ID=PAYMENT_URL+"insertpaymentmethod?";
    final static String GET_PAYPAL_ACCOUNTS_URL=PAYMENT_URL+"getpaymentmethods?";
    final static String DELETE_PAYPAL_URL=PAYMENT_URL+"deletepaymentmethod?";
    final static String GET_ACTIVE_PAYPAL_URL=PAYMENT_URL+"getactivepaymentmethod?";
    final static String UPDATE_PAYPAL_URL=PAYMENT_URL+"defaultpaymentmethod?";




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
    public static WebServiceRequest.HttpURLCONNECTION SEND_PROFILE(String params, WebServiceRequest.Callback callback) {
        WebServiceRequest.HttpURLCONNECTION urlConn = new WebServiceRequest.HttpURLCONNECTION();

        Log.d("URL",SEND_PROFILE_URL);
        urlConn.setUrl(SEND_PROFILE_URL);
        urlConn.setRequestMethod("POST");
        List<ValuePair> headers = new ArrayList<>();
        headers.add(new ValuePair("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8"));
        urlConn.setHeaders(headers);
        urlConn.setCallback(callback);
        urlConn.setParameters(params);

        return urlConn;
    }

    public static WebServiceRequest.HttpURLCONNECTION CHANGE_PASSWORD(String params, WebServiceRequest.Callback callback) {
        WebServiceRequest.HttpURLCONNECTION urlConn = new WebServiceRequest.HttpURLCONNECTION();
        Log.d("URL",CHANGE_PASSWORD_URL);
        urlConn.setUrl(CHANGE_PASSWORD_URL);
        urlConn.setRequestMethod("POST");
        List<ValuePair> headers = new ArrayList<>();
        headers.add(new ValuePair("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8"));
        urlConn.setHeaders(headers);
        urlConn.setCallback(callback);
        urlConn.setParameters(params);
        return urlConn;
    }

    public static WebServiceRequest.HttpURLCONNECTION POST_ADD(String params, WebServiceRequest.Callback callback) {
        WebServiceRequest.HttpURLCONNECTION urlConn = new WebServiceRequest.HttpURLCONNECTION();
        Log.d("URL",POST_ADD_URL);
        urlConn.setUrl(POST_ADD_URL);
        urlConn.setRequestMethod("POST");
        List<ValuePair> headers = new ArrayList<>();
        headers.add(new ValuePair("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8"));
        urlConn.setHeaders(headers);
        urlConn.setCallback(callback);
        urlConn.setParameters(params);
        return urlConn;
    }
    public static WebServiceRequest.HttpURLCONNECTION LOGOUT(String params, WebServiceRequest.Callback callback) {
        WebServiceRequest.HttpURLCONNECTION urlConn = new WebServiceRequest.HttpURLCONNECTION();
        String url=LOGOUT_URL+params;
        Log.d("URL", url);
        urlConn.setUrl(url);
        urlConn.setRequestMethod("GET");
        urlConn.setCallback(callback);

        return urlConn;
    }

    public static WebServiceRequest.HttpURLCONNECTION SEND_PAYPAL_ID(String params, WebServiceRequest.Callback callback) {
        WebServiceRequest.HttpURLCONNECTION urlConn = new WebServiceRequest.HttpURLCONNECTION();
        String url=SEND_PAYPAL_ID+params;
        Log.d("URL",url);
        urlConn.setUrl(url);
        urlConn.setRequestMethod("GET");
        urlConn.setCallback(callback);

        return urlConn;
    }
    public static WebServiceRequest.HttpURLCONNECTION GET_PAYPAL_ACCOUNTS(String params, WebServiceRequest.Callback callback) {
        WebServiceRequest.HttpURLCONNECTION urlConn = new WebServiceRequest.HttpURLCONNECTION();
        String url=GET_PAYPAL_ACCOUNTS_URL+params;
        Log.d("URL",url);
        urlConn.setUrl(url);
        urlConn.setRequestMethod("GET");
        urlConn.setCallback(callback);

        return urlConn;
    }
    public static WebServiceRequest.HttpURLCONNECTION DELETE_PAYPAL_ACCOUNT(String params, WebServiceRequest.Callback callback) {
        WebServiceRequest.HttpURLCONNECTION urlConn = new WebServiceRequest.HttpURLCONNECTION();
        String url=DELETE_PAYPAL_URL+params;
        Log.d("URL",url);
        urlConn.setUrl(url);
        urlConn.setRequestMethod("GET");
        urlConn.setCallback(callback);

        return urlConn;
    }
    public static WebServiceRequest.HttpURLCONNECTION GET_ACTIVE_PAYMENT(String params, WebServiceRequest.Callback callback) {
        WebServiceRequest.HttpURLCONNECTION urlConn = new WebServiceRequest.HttpURLCONNECTION();
        String url=GET_ACTIVE_PAYPAL_URL+params;
        Log.d("URL",url);
        urlConn.setUrl(url);
        urlConn.setRequestMethod("GET");
        urlConn.setCallback(callback);

        return urlConn;
    }
    public static WebServiceRequest.HttpURLCONNECTION UPDATE_PAYPAL(String params, WebServiceRequest.Callback callback) {
        WebServiceRequest.HttpURLCONNECTION urlConn = new WebServiceRequest.HttpURLCONNECTION();
        String url=UPDATE_PAYPAL_URL+params;
        Log.d("URL",url);
        urlConn.setUrl(url);
        urlConn.setRequestMethod("GET");
        urlConn.setCallback(callback);

        return urlConn;
    }


}
