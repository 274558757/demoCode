package com.example.commen.baseapp;

import android.app.Application;
import android.content.Context;

/**
 * 作者： WangZL on 2016/9/29 0029.
 */

public class BaseApplication extends Application {
    private static BaseApplication baseApplocation;
    @Override
    public void onCreate() {
        super.onCreate();
        baseApplocation=this;
    }
    public static Context getAppContext(){
        return baseApplocation;
    }
}
