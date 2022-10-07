package com.nvc.scanbar.common.util;
import java.util.regex.Pattern;

public class ScanbarUtil {
    public static boolean isValidMobileString(String mobileNumber){
        String regex = "^(\\+\\d{1,3}[- ]?)?\\d{10}$";
        return Pattern.matches(regex, mobileNumber);
    }

    public static boolean isValidEmailString(String email) {
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
        return Pattern.matches(regex, email);
    }

    public static boolean isValidProductSeries(String productSeries) {
        if(productSeries!=null && productSeries.length() == 8) {
            return true;
        }
        return false;
    }
}
