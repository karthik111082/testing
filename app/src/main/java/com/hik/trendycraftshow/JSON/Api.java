package com.hik.trendycraftshow.JSON;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DHARMA on 10/27/2015.
 */
public class Api {

    final static String BASEURL="http://52.88.123.74/TrendyCraftShow/";
    //final static String BASEURL="http://192.168.0.109:8080/TrendyCraftShow/";
    public final static String QUICKPHOTOURL=BASEURL+"downloadimage/quickimage/";
    final static String PAYMENT_URL=BASEURL+"payment/";
    public final static String PhotoDownloadPath=BASEURL+"downloadimage/download/";
    final static String SEND_CHATID_URL=BASEURL+"insertchatid?";
    final static String WISHLIST_URL=BASEURL+"wishlist/";
    final static String PRODUCT_URL=BASEURL+"adds/";
    final static String MY_SALES_URL=BASEURL+"mysales/";
    final static String ADD_URL=BASEURL+"adds/";
    final static String PAYMENT_PURCHASE_URL=BASEURL+"orderpayment/";
    final static String SIGNUP_URL=BASEURL+"signup?";
    final static String LOGIN_URL=BASEURL+"login?";
    final static String LOGOUT_URL=BASEURL+"logout?";
    final static String ACTIVATE_URL=BASEURL+"activateuser?";
    final static String FORGOT_URL=BASEURL+"forgotpassword?";
    final static String SEND_PROFILE_URL=BASEURL+"updateprofile";
    final static String CHANGE_PASSWORD_URL=BASEURL+"changepassword";
    final static String BUSINESS_DIRCTORY=BASEURL+"businessdirectory/";
    final static String POST_ADD_URL=ADD_URL+"postadd";
    final static String PAYMENT_STATUS_URL=ADD_URL+"paymentstatus?";
    final static String SEND_PAYPAL_ID=PAYMENT_URL+"insertpaymentmethod?";
    final static String GET_PAYPAL_ACCOUNTS_URL=PAYMENT_URL+"getpaymentmethods?";
    final static String DELETE_PAYPAL_URL=PAYMENT_URL+"deletepaymentmethod?";
    final static String GET_ACTIVE_PAYPAL_URL=PAYMENT_URL+"getactivepaymentmethod?";
    final static String UPDATE_PAYPAL_URL=PAYMENT_URL+"defaultpaymentmethod?";
    final static String GET_PRODUCTS_URL=PRODUCT_URL+"getadds?";
    final static String PRODUCT_SEARCH_URL=PRODUCT_URL+"getaddsearch?";
    final static String ADD_WISHLIST_URL=WISHLIST_URL+"addtowishlist?";
    final static String GET_ADDWISHLISTURL=WISHLIST_URL+"getwishlists?";
    final static String REMOVE_WISHLIST_URL=WISHLIST_URL+"removewishlist?";
    final static String PURCHASECOMPLETE_URL=PAYMENT_PURCHASE_URL+"insertpaymenthistory?";
    final static String GET_PAYMENTHISTORY_URL=PAYMENT_PURCHASE_URL+"getpaymenthistory?";
    final static String GET_ORDER_URL=PAYMENT_PURCHASE_URL+"getorderhistory?";
    final static String POST_BUSSINESSCARD=BUSINESS_DIRCTORY+"adddirectory";
    final static String SEND_CARD_PAYMENT_URL=BUSINESS_DIRCTORY+"updatedirectorypayment?";
    final static String GET_BUSINESSCARD_URL=BUSINESS_DIRCTORY+"getbusinessdirectorylist?";
    final static String GET_CARDS_WISHLIST_URL=BUSINESS_DIRCTORY+"getdirectorywishlist?";
    final static String GET_ADS_URL=BUSINESS_DIRCTORY+"getaddslist?";
    final static String GET_MYSALES_URL=MY_SALES_URL+"getmysaleslist?";
    final static String ADD_WISHLIST_CARD_URL=BUSINESS_DIRCTORY+"addtodirectorywishlist?";
    final static String REMOVE_WISHLIST_CARD_URL=BUSINESS_DIRCTORY+"removedirectorywishlist?";
    final static String RATE_DIRECTORY_URL=BUSINESS_DIRCTORY+"updatedirectoryrating?";
    final static String REMOVE_RATE_DIRECTORY_URL=BUSINESS_DIRCTORY+"removedirectoryrating?";
    final static String GET_BUSINESSCARD_SEARCH_URL=BUSINESS_DIRCTORY+"directorysearch?";
    final static String GET_FOLLOWUP_URL=BUSINESS_DIRCTORY+"getfollowupaddslist?";





    public static WebServiceRequest.HttpURLCONNECTION REGISTRATION(String params, WebServiceRequest.Callback callback) {
        WebServiceRequest.HttpURLCONNECTION urlConn = new WebServiceRequest.HttpURLCONNECTION();
        String ur=SIGNUP_URL+params;
        Log.d("URL", ur);
        urlConn.setUrl(ur);
        urlConn.setRequestMethod("GET");
        urlConn.setCallback(callback);

        return urlConn;
    }
    public static WebServiceRequest.HttpURLCONNECTION SEND_CHAT_ID(String params, WebServiceRequest.Callback callback) {
        WebServiceRequest.HttpURLCONNECTION urlConn = new WebServiceRequest.HttpURLCONNECTION();
        String ur=SEND_CHATID_URL+params;
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
    public static WebServiceRequest.HttpURLCONNECTION SEND_PAYMENT_STATUS(String params, WebServiceRequest.Callback callback) {
        WebServiceRequest.HttpURLCONNECTION urlConn = new WebServiceRequest.HttpURLCONNECTION();
        String url=PAYMENT_STATUS_URL+params;
        Log.d("URL",url);
        urlConn.setUrl(url);
        urlConn.setRequestMethod("GET");
        urlConn.setCallback(callback);
        return urlConn;
    }
    public static WebServiceRequest.HttpURLCONNECTION SEND_CARD_PAYMENT(String params, WebServiceRequest.Callback callback) {
        WebServiceRequest.HttpURLCONNECTION urlConn = new WebServiceRequest.HttpURLCONNECTION();
        String url=SEND_CARD_PAYMENT_URL+params;
        Log.d("URL",url);
        urlConn.setUrl(url);
        urlConn.setRequestMethod("GET");
        urlConn.setCallback(callback);
        return urlConn;
    }

    public static WebServiceRequest.HttpURLCONNECTION ACTIVATE_ACCOUNT(String params, WebServiceRequest.Callback callback) {
        WebServiceRequest.HttpURLCONNECTION urlConn = new WebServiceRequest.HttpURLCONNECTION();
        String url=ACTIVATE_URL+params;
        Log.d("URL", url);
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
        Log.d("URL", url);
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
    public static WebServiceRequest.HttpURLCONNECTION GET_MYSALES_LIST(String params, WebServiceRequest.Callback callback) {
        WebServiceRequest.HttpURLCONNECTION urlConn = new WebServiceRequest.HttpURLCONNECTION();
        String url=GET_MYSALES_URL+params;
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

    public static WebServiceRequest.HttpURLCONNECTION GET_PRODUCTS(String params, WebServiceRequest.Callback callback) {
        WebServiceRequest.HttpURLCONNECTION urlConn = new WebServiceRequest.HttpURLCONNECTION();
        String url=GET_PRODUCTS_URL+params;
        Log.d("URL",url);
        urlConn.setUrl(url);
        urlConn.setRequestMethod("GET");
        urlConn.setCallback(callback);

        return urlConn;
    }
    public static WebServiceRequest.HttpURLCONNECTION ADD_TO_WISHLIST(String params, WebServiceRequest.Callback callback) {
        WebServiceRequest.HttpURLCONNECTION urlConn = new WebServiceRequest.HttpURLCONNECTION();
        String url=ADD_WISHLIST_URL+params;
        Log.d("URL",url);
        urlConn.setUrl(url);
        urlConn.setRequestMethod("GET");
        urlConn.setCallback(callback);

        return urlConn;
    }
    public static WebServiceRequest.HttpURLCONNECTION GET_ADS_WISHLIST(String params, WebServiceRequest.Callback callback) {
        WebServiceRequest.HttpURLCONNECTION urlConn = new WebServiceRequest.HttpURLCONNECTION();
        String url=GET_ADDWISHLISTURL+params;
        Log.d("URL",url);
        urlConn.setUrl(url);
        urlConn.setRequestMethod("GET");
        urlConn.setCallback(callback);

        return urlConn;
    }
    public static WebServiceRequest.HttpURLCONNECTION PURCHASE_COMPLETE(String params, WebServiceRequest.Callback callback) {
        WebServiceRequest.HttpURLCONNECTION urlConn = new WebServiceRequest.HttpURLCONNECTION();
        String url=PURCHASECOMPLETE_URL+params;
        Log.d("URL",url);
        urlConn.setUrl(url);
        urlConn.setRequestMethod("GET");
        urlConn.setCallback(callback);

        return urlConn;
    }
    public static WebServiceRequest.HttpURLCONNECTION GET_PAYMENT_HISTORY(String params, WebServiceRequest.Callback callback) {
        WebServiceRequest.HttpURLCONNECTION urlConn = new WebServiceRequest.HttpURLCONNECTION();
        String url=GET_PAYMENTHISTORY_URL+params;
        Log.d("URL",url);
        urlConn.setUrl(url);
        urlConn.setRequestMethod("GET");
        urlConn.setCallback(callback);

        return urlConn;
    }
    public static WebServiceRequest.HttpURLCONNECTION GET_ORDER_HISTORY(String params, WebServiceRequest.Callback callback) {
        WebServiceRequest.HttpURLCONNECTION urlConn = new WebServiceRequest.HttpURLCONNECTION();
        String url=GET_ORDER_URL+params;
        Log.d("URL",url);
        urlConn.setUrl(url);
        urlConn.setRequestMethod("GET");
        urlConn.setCallback(callback);

        return urlConn;
    }
    public static WebServiceRequest.HttpURLCONNECTION REMOVE_WISHLIST(String params, WebServiceRequest.Callback callback) {
        WebServiceRequest.HttpURLCONNECTION urlConn = new WebServiceRequest.HttpURLCONNECTION();
        String url=REMOVE_WISHLIST_URL+params;
        Log.d("URL",url);
        urlConn.setUrl(url);
        urlConn.setRequestMethod("GET");
        urlConn.setCallback(callback);

        return urlConn;
    }
    public static WebServiceRequest.HttpURLCONNECTION ADD_WISHLIST_CARD(String params, WebServiceRequest.Callback callback) {
        WebServiceRequest.HttpURLCONNECTION urlConn = new WebServiceRequest.HttpURLCONNECTION();
        String url=ADD_WISHLIST_CARD_URL+params;
        Log.d("URL",url);
        urlConn.setUrl(url);
        urlConn.setRequestMethod("GET");
        urlConn.setCallback(callback);

        return urlConn;
    }
    public static WebServiceRequest.HttpURLCONNECTION REMOVE_WISHLIST_CARD(String params, WebServiceRequest.Callback callback) {
        WebServiceRequest.HttpURLCONNECTION urlConn = new WebServiceRequest.HttpURLCONNECTION();
        String url=REMOVE_WISHLIST_CARD_URL+params;
        Log.d("URL",url);
        urlConn.setUrl(url);
        urlConn.setRequestMethod("GET");
        urlConn.setCallback(callback);

        return urlConn;
    }
    public static WebServiceRequest.HttpURLCONNECTION RATE_DIRECTORY(String params, WebServiceRequest.Callback callback) {
        WebServiceRequest.HttpURLCONNECTION urlConn = new WebServiceRequest.HttpURLCONNECTION();
        String url=RATE_DIRECTORY_URL+params;
        Log.d("URL",url);
        urlConn.setUrl(url);
        urlConn.setRequestMethod("GET");
        urlConn.setCallback(callback);

        return urlConn;
    }
    public static WebServiceRequest.HttpURLCONNECTION REMOVE_RATE_DIRECTORY(String params, WebServiceRequest.Callback callback) {
        WebServiceRequest.HttpURLCONNECTION urlConn = new WebServiceRequest.HttpURLCONNECTION();
        String url=REMOVE_RATE_DIRECTORY_URL+params;
        Log.d("URL",url);
        urlConn.setUrl(url);
        urlConn.setRequestMethod("GET");
        urlConn.setCallback(callback);

        return urlConn;
    }
    public static WebServiceRequest.HttpURLCONNECTION SEARCH_PRODUCTS(String params, WebServiceRequest.Callback callback) {
        WebServiceRequest.HttpURLCONNECTION urlConn = new WebServiceRequest.HttpURLCONNECTION();
        Log.d("URL",PRODUCT_SEARCH_URL);
        urlConn.setUrl(PRODUCT_SEARCH_URL);
        urlConn.setRequestMethod("POST");
        List<ValuePair> headers = new ArrayList<>();
        headers.add(new ValuePair("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8"));
        urlConn.setHeaders(headers);
        urlConn.setCallback(callback);
        urlConn.setParameters(params);
        return urlConn;
    }
    public static WebServiceRequest.HttpURLCONNECTION POST_BUSSINESSCARD(String params, WebServiceRequest.Callback callback) {
        WebServiceRequest.HttpURLCONNECTION urlConn = new WebServiceRequest.HttpURLCONNECTION();
        Log.d("URL",POST_BUSSINESSCARD);
        urlConn.setUrl(POST_BUSSINESSCARD);
        urlConn.setRequestMethod("POST");
        List<ValuePair> headers = new ArrayList<>();
        headers.add(new ValuePair("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8"));
        urlConn.setHeaders(headers);
        urlConn.setCallback(callback);
        urlConn.setParameters(params);
        return urlConn;
    }

    public static WebServiceRequest.HttpURLCONNECTION GET_BUSINESS_CARDS(String params, WebServiceRequest.Callback callback) {
        WebServiceRequest.HttpURLCONNECTION urlConn = new WebServiceRequest.HttpURLCONNECTION();
        String url=GET_BUSINESSCARD_URL+params;
        Log.d("URL", url);
        urlConn.setUrl(url);
        urlConn.setRequestMethod("GET");
        urlConn.setCallback(callback);
        return urlConn;
    }
    public static WebServiceRequest.HttpURLCONNECTION GET_CARDS_WISHLIST(String params, WebServiceRequest.Callback callback) {
        WebServiceRequest.HttpURLCONNECTION urlConn = new WebServiceRequest.HttpURLCONNECTION();
        String url=GET_CARDS_WISHLIST_URL+params;
        Log.d("URL", url);
        urlConn.setUrl(url);
        urlConn.setRequestMethod("GET");
        urlConn.setCallback(callback);
        return urlConn;
    }

    public static WebServiceRequest.HttpURLCONNECTION GET_SEARCH_BUSINESS_CARDS(String params, WebServiceRequest.Callback callback) {
        WebServiceRequest.HttpURLCONNECTION urlConn = new WebServiceRequest.HttpURLCONNECTION();
        String url=GET_BUSINESSCARD_SEARCH_URL+params;
        Log.d("URL", url);
        urlConn.setUrl(url);
        urlConn.setRequestMethod("GET");
        urlConn.setCallback(callback);
        return urlConn;
    }
    public static WebServiceRequest.HttpURLCONNECTION GET_PRODUCTS_BY_USER(String params, WebServiceRequest.Callback callback) {
        WebServiceRequest.HttpURLCONNECTION urlConn = new WebServiceRequest.HttpURLCONNECTION();
        String url=GET_ADS_URL+params;
        Log.d("URL", url);
        urlConn.setUrl(url);
        urlConn.setRequestMethod("GET");
        urlConn.setCallback(callback);
        return urlConn;
    }

    public static WebServiceRequest.HttpURLCONNECTION MY_FOLLOW_UP(String params, WebServiceRequest.Callback callback) {
        WebServiceRequest.HttpURLCONNECTION urlConn = new WebServiceRequest.HttpURLCONNECTION();
        String url=GET_FOLLOWUP_URL+params;
        Log.d("URL", url);
        urlConn.setUrl(url);
        urlConn.setRequestMethod("GET");
        urlConn.setCallback(callback);
        return urlConn;
    }


}
