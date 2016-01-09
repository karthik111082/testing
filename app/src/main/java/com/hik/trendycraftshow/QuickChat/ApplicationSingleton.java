package com.hik.trendycraftshow.QuickChat;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.hik.trendycraftshow.Utils.Consts;
import com.hik.trendycraftshow.Utils.Utils;
import com.paypal.android.MEP.PayPal;
import com.quickblox.auth.QBAuth;
import com.quickblox.auth.model.QBSession;
import com.quickblox.core.QBEntityCallbackImpl;
import com.quickblox.core.QBSettings;

import java.util.List;

public class ApplicationSingleton extends Application {
    private static final String TAG = ApplicationSingleton.class.getSimpleName();

    public static final String APP_ID = "33260";
    public static final String AUTH_KEY = "xKNeASXccVQVS8A";
    public static final String AUTH_SECRET = "MSZj-zJ9kZZPHpd";
    public static final String STICKER_API_KEY = "847b82c49db21ecec88c510e377b452c";

    public static final String USER_LOGIN = Consts.UserName;
    public static final String USER_PASSWORD = Consts.QuickPassword;

    private static ApplicationSingleton instance;
    public static ApplicationSingleton getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "onCreate");

        instance = this;
            initLibrary();
        // Initialise QuickBlox SDK
        //
        QBSettings.getInstance().fastConfigInit(APP_ID, AUTH_KEY, AUTH_SECRET);
        //StickersManager.initialize(STICKER_API_KEY, this);

    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    public void initLibrary() {
        PayPal pp = PayPal.getInstance();
        if (pp == null) {

            pp = PayPal.initWithAppID(this, Utils.paypal_liv_id,PayPal.ENV_LIVE);

        }
    }
    public int getAppVersion() {
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }


}
