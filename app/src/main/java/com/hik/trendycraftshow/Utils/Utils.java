package com.hik.trendycraftshow.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.hik.trendycraftshow.R;

import java.io.ByteArrayOutputStream;

/**
 * Created by DHARMA on 12/16/2015.
 */
public class Utils {
    public static String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        Log.d("IMAGE", temp);
        return temp;

    }
    public static Bitmap StringToBitMap(String encodedString){

        try{
            byte[] encodeByte=Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }
}
