package com.hik.trendycraftshow.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.os.Environment;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;


/**
 * Created by DHARMA on 12/16/2015.
 */
public class Utils {

    StringBuffer test=new StringBuffer();
    public static String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        Log.d("IMAGE", temp);
        return temp;


    }





    public static Bitmap StringToBitMap(String encodedString){

        try{
            byte[] encodeByte= Base64.decode(encodedString, Base64.NO_WRAP);
           Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;

        }catch(Exception e){
            e.getMessage();
            return null;
        }

    }




public static String GetPhotoString(String photo)
{

    String PhotoString= photo.replaceAll("\\\\n","\n").replaceAll("\\\\u0026", "&").replaceAll("\\\\u003e",">").replaceAll("\\\\u003c","<").replaceAll("\\\\u0027","'").replaceAll("\\\\u003d","=");
    Log.d("Response", PhotoString);
    return PhotoString;
}
}
