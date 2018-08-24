package com.gkshanmugavel.newapp;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;


public class Utils {

    public static class TimeWait {
        /**
         * seconds delay time
         */
        public static void delay(int seconds) {
            try {
                Thread.sleep(seconds * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public static class StringUtils {
        /*
         * id is getting String from Resource. String Resource Id.
         * */
        public static String getResourceString(int id) {
            Context targetContext = InstrumentationRegistry.getTargetContext();
            return targetContext.getResources().getString(id);
        }
    }

    public static class App {

        /*
        * packageName # Package name.
        * */
        public static void Open(String packageName) {
            Context context = InstrumentationRegistry.getInstrumentation().getContext();
            Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);
        }
    }
}
