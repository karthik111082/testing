package com.hik.trendycraftshow.Utils;

import android.content.Context;

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
    public String CurrentDate()
    {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format1 = new SimpleDateFormat("MM-dd-yyyy");
        String formatted = format1.format(cal.getTime());
        System.out.println(formatted);
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
    public String OneWeek()
    {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -7);
        SimpleDateFormat format1 = new SimpleDateFormat("MM-dd-yyyy");
        String formatted = format1.format(cal.getTime());
        System.out.println(formatted);
        return formatted;
    }
    public String OneMonth()
    {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat month = new SimpleDateFormat("MM");
        SimpleDateFormat year = new SimpleDateFormat("yyyy");
        String getMonth = month.format(cal.getTime());
        String getYear = year.format(cal.getTime());
        String formatted=getMonth+"-01-"+getYear;
        System.out.println(formatted);
        return formatted;
    }
}
