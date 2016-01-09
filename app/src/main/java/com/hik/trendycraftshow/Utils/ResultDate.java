package com.hik.trendycraftshow.Utils;

import android.content.Context;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by DHARMA on 8/27/2015.
 */
public class ResultDate {
    private Context _context;

    public ResultDate(Context context){
        this._context = context;
    }
    public static String CurrentDate()
    {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format1 = new SimpleDateFormat("MM-dd-yyyy");
        String formatted = format1.format(cal.getTime());
        Log.d("Current Date", formatted);

        return formatted;
    }
    public String CurrentDateFormated()
    {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");
        String formatted = format1.format(cal.getTime());
        System.out.println(formatted);
        return formatted;
    }
    public static String OneWeek()
    {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -7);
        SimpleDateFormat format1 = new SimpleDateFormat("MM-dd-yyyy");
        String formatted = format1.format(cal.getTime());

        return formatted;
    }
    public static String SixMonth()
    {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 6);
        SimpleDateFormat format1 = new SimpleDateFormat("MM-dd-yyyy");
        String formatted = format1.format(cal.getTime());
        System.out.println(formatted);
        Log.d("Six Month", formatted);
        return formatted;
    }
    public static String OneYear()
    {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, 1);
        SimpleDateFormat format1 = new SimpleDateFormat("MM-dd-yyyy");
        String formatted = format1.format(cal.getTime());
        System.out.println(formatted);
        Log.d("One Year",formatted);
        return formatted;
    }
}
