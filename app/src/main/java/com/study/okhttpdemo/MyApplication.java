package com.study.okhttpdemo;

import android.app.Application;

import com.study.okhttpdemo.retrofit.RetrofitHelper;

import org.xutils.x;

/**
 * Created by HFS on 2017/5/14.
 */

public class MyApplication extends Application {

    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        RetrofitHelper.init(this,"http://112.124.22.238:8081/");

        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.
    }

    public static MyApplication getContext() {
        return instance;
    }
}
