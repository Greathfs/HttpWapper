package com.study.okhttpdemo.xutils.http;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.google.gson.internal.$Gson$Types;
import com.study.okhttpdemo.xutils.utils.LogUtil;

/**
 * Created by zhangzhenguo on 2017/4/10.
 * 统一处理网络回调
 */

public abstract class RequestCallBack<T> {

    public Type mType;

    public RequestCallBack() {
        mType = getSuperclassTypeParameter(getClass());
    }
    /**
     * 请求成功
     * @param result
     */
    public abstract void onSuccess(T result);

    /**
     * 失败
     * @param error 异常
     * @param code 错误代码
     * @param msg 错误信息
     */
    public  void onError(Exception error,String code, String msg) {
        LogUtil.d("onError","code:"+code+", msg:"+msg);
    }

    /**
     * 业务处理失败
     * @param subCode 业务错误代码
     * @param msg 错误信息
     */
    public void onSubError(String subCode,String msg){
        LogUtil.d("onSubError","subCode:"+subCode+", subMsg:"+msg);
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
