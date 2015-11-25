package com.hik.trendycraftshow.Utils;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by DHARMA on 11/24/2015.
 */
public class Consts {

    ProgressDialog pDialog;
    Context context;
    public static String UserName,Password,FirstName,LastName,Phone,Street,City,State,Zip,AccessCode,ForgotPwd;

    public Consts(Context context) {
        this.context = context;
    }

    public static byte[] Photo;
    public void showDialog(Context context) {
        pDialog = new ProgressDialog(context);
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


    public static String getUserName() {
        return UserName;
    }

    public static void setUserName(String userName) {
        UserName = userName;
    }

    public static String getPassword() {
        return Password;
    }

    public static void setPassword(String password) {
        Password = password;
    }

    public static String getFirstName() {
        return FirstName;
    }

    public static void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public static String getLastName() {
        return LastName;
    }

    public static void setLastName(String lastName) {
        LastName = lastName;
    }

    public static String getPhone() {
        return Phone;
    }

    public static void setPhone(String phone) {
        Phone = phone;
    }

    public static String getStreet() {
        return Street;
    }

    public static void setStreet(String street) {
        Street = street;
    }

    public static String getCity() {
        return City;
    }

    public static void setCity(String city) {
        City = city;
    }

    public static String getState() {
        return State;
    }

    public static void setState(String state) {
        State = state;
    }

    public static String getZip() {
        return Zip;
    }

    public static void setZip(String zip) {
        Zip = zip;
    }

    public static String getAccessCode() {
        return AccessCode;
    }

    public static void setAccessCode(String accessCode) {
        AccessCode = accessCode;
    }
}
