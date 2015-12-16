package com.hik.trendycraftshow.Utils;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by DHARMA on 11/24/2015.
 */
public class Consts {

    ProgressDialog pDialog;
    Context context;
    public static int SpinnerItem;
    public static String UserId="",UserName="",Password="",FirstName="",Phone="",Street="",City,State="",Zip,AccessCode="",Cellphone="",Photo="",Company_Name="",QuickBloxId="";

    public Consts(Context context) {
        this.context = context;
    }


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



}
