package com.hik.trendycraftshow.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by DHARMA on 11/25/2015.
 */
public class Validation {
    // validating email id
    public static boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    // validating Name
    public static boolean isValidName(String name) {
        String Name_Pattern = "^[a-zA-Z ]+$";

        Pattern pattern = Pattern.compile(Name_Pattern);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    // validating Phone Number
    public static boolean isValidPhone(String name) {
        if (name.length()==10) {
            return true;
        }
        return false;
    }
    // validating Zip
    public static boolean isZip(String zip) {
        if (zip.length()==5) {
            return true;
        }
        return false;
    }

    // validating password with retype password
    public static boolean isValidPassword(String pass) {
        if (pass != null && pass.length() >=6) {
            return true;
        }
        return false;
    }
    //
    public static boolean isEmpty(String val) {
        if (val == null ||val.length()==0) {
            return true;
        }
        return false;
    }
    // validating password with retype password
    public static boolean isValidConfirmPassword(String pass,String Cpass) {
        if (pass.equals(Cpass)) {
            return true;
        }
        return false;
    }
}
