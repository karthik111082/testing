package com.hik.trendycraftshow.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;

import com.hik.trendycraftshow.JSON.Api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by DHARMA on 11/24/2015.
 */
public class Consts {

    ProgressDialog pDialog;
    Context context;
   public final static String PhotoDownloadPath= Api.PhotoDownloadPath;
    public static int SpinnerItem;
    public static String UserId="",UserName="",Password="",FirstName="",Phone="",Street="",City,State="",Zip,AccessCode="",Cellphone="",Company_Name="",QuickBloxId="",UserSince="";
    public static String Photo="";
    public static boolean isGuest=false;
    public static String ActivePaymentId="";
    public static String AdminPaypalID="trendycraftshows@hotmail.com";
    final public static String QuickPassword="TrendyChatUser";
    public static int imagecode=0;
    public static Bitmap thumbnail=null;
    public static String image1="",image2="",image3="",image4="";
    public static Bitmap UserPhoto=null;
    public static boolean isPhotoUpload;






    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

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
    public static boolean DateValidation(String start,String end)
    {
        try{

            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
            Date date1 = sdf.parse(start);
            Date date2 = sdf.parse(end);
            Calendar c=new GregorianCalendar();
            Calendar c1=new GregorianCalendar();
            c.setTime(date1);
            c1.setTime(date2);
            int days=getdays(c.getTime(),c1.getTime());
            if(days<=30)
            {
                return true;
            }else
            {
                return false;
            }


        }catch(ParseException ex){
            ex.printStackTrace();
        }
        return false;
    }
    public static int getdays(Date d1,Date d2)
    {
        return (int)((d2.getTime()-d1.getTime())/(1000*60*60*24));
    }

    public static boolean SearchEndDate(String start,String end)
    {
        try{

            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
            Date date1 = sdf.parse(start);
            Date date2 = sdf.parse(end);
            Calendar c=new GregorianCalendar();
            Calendar c1=new GregorianCalendar();
            c.setTime(date1);
            c1.setTime(date2);
            int days=getdays(c.getTime(),c1.getTime());
            if(days>=0)
            {
                return true;
            }else
            {
                return false;
            }


        }catch(ParseException ex){
            ex.printStackTrace();
        }
        return false;
    }


    public static Map<Integer,String> state=new HashMap<>();
    public static void addState()
    {
        state.put(0,"Choose State");
        state.put(1,"Alabama");
        state.put(2,"Alaska");
        state.put(3,"Arizona");
        state.put(4,"Arkansas");
        state.put(5,"California");
        state.put(6,"Colorado");
        state.put(7,"Connecticut");
        state.put(8,"Delaware");
        state.put(9,"District Of Columbia");
        state.put(10,"Florida");
        state.put(11,"Georgia");
        state.put(12,"Hawaii");
        state.put(13,"Idaho");
        state.put(14,"Illinois");
        state.put(15,"Indiana");
        state.put(16,"Iowa");
        state.put(17,"Kansas");
        state.put(18,"Kentucky");
        state.put(19,"Louisiana");
        state.put(20,"Maine");
        state.put(21,"Maryland");
        state.put(22,"Michigan");
        state.put(23,"Massachusetts");
        state.put(24,"Michigan");
        state.put(25,"Minnesota");
        state.put(26,"Mississippi");
        state.put(27,"Missouri");
        state.put(28,"Montana");
        state.put(29,"Nebraska");
        state.put(30,"Nevada");
        state.put(31,"New Hampshire");
        state.put(32,"New Jersey");
        state.put(33,"New Mexicoa");
        state.put(34,"New York");
        state.put(35,"North Carolina");
        state.put(36,"North Dakota");
        state.put(37,"Ohio");
        state.put(38,"Oklahoma");
        state.put(39,"Oregon");
        state.put(40,"Pennsylvania");
        state.put(41,"Rhode Island");
        state.put(42,"South Carolina");
        state.put(43,"South Dakota");
        state.put(44,"Tennessee");
        state.put(45,"Texas");
        state.put(46,"Utah");
        state.put(47,"Vermont");
        state.put(48,"Virginia");
        state.put(49,"Washington");
        state.put(50,"West Virginia");
        state.put(51,"Wisconsin");
        state.put(52,"Wyoming");


    }

}
