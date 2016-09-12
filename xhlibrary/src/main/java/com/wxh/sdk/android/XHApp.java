package com.wxh.sdk.android;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2016/8/14.
 */

public class XHApp extends Application {
    public static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
    }
}