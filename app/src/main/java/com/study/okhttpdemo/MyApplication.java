package com.study.okhttpdemo;

import android.app.Application;

import com.study.okhttpdemo.retrofit.RetrofitHelper;

/**
 * Created by HFS on 2017/5/14.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RetrofitHelper.init(this,"http://112.124.22.238:8081");
    }
}
