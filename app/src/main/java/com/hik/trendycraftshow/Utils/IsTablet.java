package com.hik.trendycraftshow.Utils;

import android.content.Context;
import android.content.res.Configuration;

/**
 * Created by DHARMA on 10/19/2015.
 */
public class IsTablet {
    private static Context _context;
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}
