package com.gkshanmugavel.newapp;

import android.app.Application;
import android.content.Context;

public class BaseApp extends Application {
    private static BaseApp instance = null;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static Context getAppContext() {
        return instance;
    }
}
