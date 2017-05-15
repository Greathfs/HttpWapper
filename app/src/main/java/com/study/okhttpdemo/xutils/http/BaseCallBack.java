package com.study.okhttpdemo.xutils.http;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.google.gson.internal.$Gson$Types;

/**
 * Created by zhangzhenguo on 2017/4/7.
 */

public abstract class BaseCallBack<T> {
    public Type mType;

    public BaseCallBack() {
        mType = getSuperclassTypeParameter(getClass());
    }

    /**
     * 等待
     */
    public void onWaiting() {

    }

    /**
     * 开始
     */
    public void onStarted() {
    }

    /**
     * 已取消
     */
    public void onCancelled() {
    }

    /**
     * 正在加载
     *
     * @param total         总进度
     * @param current       当前进度
     * @param isDownloading 是否正在下载
     */
    public void onLoading(long total, long current, boolean isDownloading) {
    }

    /**
     * 请求成功
     *
     * @param result 对象返回
     */
    public abstract void onSuccess(T result);

    /**
     * 失败
     *
     * @param error 异常
     * @param msg   异常信息
     */
    public void onError(Exception error, String msg) {
//        ToastUtil.showShort(R.string.net_connect_error);
    }

    /**
     * 结束（包括取消、成功、失败）
     */
    public void onFinished() {

    }

    public static Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }
}

