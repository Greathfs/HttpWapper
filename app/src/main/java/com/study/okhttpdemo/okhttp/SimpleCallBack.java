package com.study.okhttpdemo.okhttp;

import android.content.Context;

import dmax.dialog.SpotsDialog;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by HFS on 2017/5/13.
 */

public abstract class SimpleCallBack<T> extends BaseCallback<T> {

    private SpotsDialog mSpotsDialog;

    public SimpleCallBack(Context context){
        mSpotsDialog = new SpotsDialog(context);
    }

    @Override
    public void onRequestBefore() {
        showDialog();
    }

    @Override
    public void onFailure(Request request, Exception e) {
        dimissDialog();
    }


    @Override
    public void onFalse(Response response, String err_msg) {
        dimissDialog();
    }

    @Override
    public void onResponse() {
        mSpotsDialog.dismiss();
    }

    /**
     * 显示对话框
     */
    public void showDialog(){
        mSpotsDialog.show();
    }

    /**
     * 隐藏对话框
     */
    public void dimissDialog(){
        if (mSpotsDialog != null) {
            mSpotsDialog.dismiss();
        }
    }

    /**
     * 设置对话框显示内容
     * @param message
     */
    public void setShowText(String message){
        mSpotsDialog.setMessage(message);
    }
}
