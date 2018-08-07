package com.gkshanmugavel.newapp.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.support.design.widget.Snackbar;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utility {

    /**
     * @param context Application Context
     * @param message Displaying the message
     */
    public static void showSnackBar(Context context, String message) {
        Snackbar snackbar = Snackbar.make(((Activity) context).findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    /**
     * To check internet connection
     *
     * @param context context for activity
     * @return boolean true if internet is connected else false
     */
    public static boolean isInternetConnected(Context context) {
        try {
            ConnectivityManager connec = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            android.net.NetworkInfo wifi = connec
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = connec
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if (wifi.isConnected()) {
                return true;
            } else {
                if (mobile != null && mobile.isAvailable()) {
                    if (!mobile.isConnected()) {
                        return false;
                    } else if (mobile.isConnected()) {
                        return true;
                    }
                } else {
                    return false;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Log error to logcat
     *
     * @param error Error string to be logged
     */
    public static void logError(String error) {
        Log.e("ErrorLog", error);
    }


}
