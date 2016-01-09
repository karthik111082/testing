package com.hik.trendycraftshow.Utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by ELANGO on 10/1/2015.
 */
public class Dbhelper extends SQLiteOpenHelper {
    static String DATABASE_NAME="trendy";
    public static final String TABLE_NAME="user";
    public static final String KEY_UNAME="uname";
    public static final String KEY_PASSWORD="password";

    public static final String USER_TABLE="userdata";
    public static final String UserName="username";
    public static final String Password="Password";
    public static final String FirstName="FirstName";
    public static final String Phone="Phone";
    public static final String Street="Street";
    public static final String City="City";
    public static final String Zip="Zip";
    public static final String UserId="UserId";
    public static final String Company_Name="Company_Name";
    public static final String QuickBloxId="QuickBloxId";
    public static final String Photo="Photo";
    public static final String UserSince="UserSince";
    public static final String State="State";
    public static final String CellPhone="cell";



    public Dbhelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

        @Override
    public void onCreate(SQLiteDatabase db) {

            String  CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+"("+ KEY_UNAME +" TEXT  ," +KEY_PASSWORD+" TEXT)";
            db.execSQL(CREATE_TABLE);
            String  CREATE_USER_TABLE = "CREATE TABLE "+USER_TABLE+"("+ UserName +" TEXT  PRIMARY KEY," +Password+" TEXT ," +FirstName+" TEXT ," +Phone+" TEXT ," +Street+" TEXT ," +City+" TEXT ," +Zip+" TEXT, " +UserId+" TEXT ," +Company_Name+" TEXT ," +QuickBloxId+" TEXT ," +Photo+" TEXT ," +UserSince+" TEXT ," +State+" TEXT ," +CellPhone+" TEXT)";
            db.execSQL(CREATE_USER_TABLE);
            Log.d("database",CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS"+TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS"+USER_TABLE );
            onCreate(db);

    }


}
