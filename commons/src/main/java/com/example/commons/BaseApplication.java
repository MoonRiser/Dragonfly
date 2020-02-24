package com.example.commons;

import android.app.Application;

import androidx.annotation.CallSuper;

public class BaseApplication extends Application {
    public static Application INSTANCE;

    @Override @CallSuper
    public void onCreate() {
        super.onCreate();
        INSTANCE = (Application) getApplicationContext();
    }
}
