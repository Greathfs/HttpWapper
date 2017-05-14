package com.study.okhttpdemo.okhttp;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by HFS on 2017/5/13.
 */
public class OkHttpHelper {
    /**
     * 采用单例模式使用OkHttpClient
     */
    private static final Class STRING_TYPE = String.class;
    private static final String TAG = OkHttpHelper.class.getSimpleName();
    private static OkHttpHelper mOkHttpHelperInstance;
    private static OkHttpClient mClientInstance;
    private Handler mHandler;
    private Gson mGson;

    /**
     * 单例模式，私有构造函数，构造函数里面进行一些初始化
     */
    private OkHttpHelper() {
        mClientInstance = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS) //设置连接超时
                .writeTimeout(10, TimeUnit.SECONDS)   //设置写超时
                .readTimeout(30, TimeUnit.SECONDS)    //设置读超时
                .retryOnConnectionFailure(true)       //是否自动重连
                .build();
        mGson = new Gson();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 获取单例
     *
     * @return
     */
    public static OkHttpHelper getInstance() {

        if (mOkHttpHelperInstance == null) {
            synchronized (OkHttpHelper.class) {
                if (mOkHttpHelperInstance == null) {
                    mOkHttpHelperInstance = new OkHttpHelper();
                }
            }
        }
        return mOkHttpHelperInstance;
    }

    /**
     * 封装一个request方法，不管post或者get方法中都会用到
     */
    public void request(final Request request, final BaseCallback callback) {

        //在请求之前所做的事，比如弹出对话框等

        callback.onRequestBefore();

        mClientInstance.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //返回失败
                callbackFailure(request, callback, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //请求成功响应，可关闭对话框
                callback.onResponse();

                if (response.isSuccessful()) {
                    //返回成功回调
                    String resString = response.body().string();
                    if (resString.contains("false")) {
                        //返回结果 result为false(根据实际需求请求返回的string判断)
                        try {
                            JSONObject jsonObject = new JSONObject(resString);
                            String err_msg = jsonObject.getString("err_msg");
                            callbackFalse(response, err_msg, callback);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            callbackError(response, callback, e);
                        }
                    } else {
                        if (callback.mType == STRING_TYPE) {
                            //如果我们需要返回String类型
                            callbackSuccess(response, resString, callback);
                        } else {
                            //如果返回的是其他类型，则利用Gson去解析
                            try {
                                Object o = mGson.fromJson(resString, callback.mType);
                                callbackSuccess(response, o, callback);
                            } catch (JsonParseException e) {
                                e.printStackTrace();
                                callbackError(response, callback, e);
                            }
                        }
                    }
                } else {
                    //返回错误
                    callbackError(response, callback, null);
                }
            }
        });
    }

    /**
     * 在主线程中执行的回调
     *
     * @param response
     * @param o
     * @param callback
     */
    private void callbackSuccess(final Response response, final Object o, final BaseCallback callback) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(response, o);
            }
        });
    }

    /**
     * 在主线程中执行的回调
     *
     * @param response
     * @param callback
     * @param e
     */
    private void callbackError(final Response response, final BaseCallback callback, final Exception e) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onError(response, response.code(), e);
            }
        });
    }

    /**
     * 在主线程中执行的回调
     *
     * @param request
     * @param callback
     * @param e
     */
    private void callbackFailure(final Request request, final BaseCallback callback, final Exception e) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onFailure(request, e);
            }
        });
    }

    /**
     * 在主线程中执行的回调
     *
     * @param response
     * @param callback
     * @param err_msg
     */
    private void callbackFalse(final Response response, final String err_msg, final BaseCallback callback) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onFalse(response, err_msg);
            }
        });
    }

    /**
     * 对外公开的get方法
     *
     * @param url
     * @param callback
     */
    public void get(String url, BaseCallback callback) {
        Request request = buildRequest(url, null, HttpMethodType.GET);
        request(request, callback);
    }

    /**
     * 对外公开的get方法
     *
     * @param url
     * @param callback
     */
    public void get(String url,Map<String, Object> params, BaseCallback callback) {
        Request request = buildRequest(url, params, HttpMethodType.GET);
        request(request, callback);
    }

    /**
     * 对外公开的post方法
     *
     * @param url
     * @param params
     * @param callback
     */
    public void post(String url, Map<String, Object> params, BaseCallback callback) {
        Request request = buildRequest(url, params, HttpMethodType.POST);
        request(request, callback);
    }

    /**
     * 对外公开的手动释放内存线程池方法，在内存不足时可调用
     */
    public void closeThreadPools() {
        mClientInstance.dispatcher().executorService().shutdown();   //清除并关闭线程池
        mClientInstance.connectionPool().evictAll();                 //清除并关闭连接池
        try {
            if (mClientInstance.cache() != null)
                mClientInstance.cache().close();                             //清除cache
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 对外公开的取消当前请求
     */
    public void cancleRequest(Call call) {
        if (call.isCanceled()) {
            call.cancel();  //取消请求
        }
    }

    /**
     * 构建请求对象
     *
     * @param url
     * @param params
     * @param type
     * @return
     */
    private Request buildRequest(String url, Map<String, Object> params, HttpMethodType type) {
        Request.Builder builder = new Request.Builder();

        if (type == HttpMethodType.GET) {

            if(params!=null){
                builder.url(attachHttpGetParams(url,params));
                builder.get();

            }else {
                builder.url(url);
                builder.get();
            }
        } else if (type == HttpMethodType.POST) {
            builder.url(url);
            RequestBody body = buildRequestBody(params);
            builder.post(body);
            try {
                long length = body.contentLength();
                builder.addHeader("Content-Length", length + "");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return builder.build();
    }

    /**
     * 通过Map的键值对构建post请求对象的body
     *
     * @param params
     * @return
     */
    private RequestBody buildRequestBody(final Map<String, Object> params) {
        FormBody.Builder builder = new FormBody.Builder();
        Iterator<Map.Entry<String, Object>> iterator = params.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> next = iterator.next();
            String key = next.getKey();
            Object value = next.getValue();
            builder.add(key, value + "");
        }
        return builder.build();
    }

    /**
     * 这个枚举用于指明是哪一种提交方式
     */
    private enum HttpMethodType {
        GET,
        POST
    }

    /**
     * 为HttpGet 的 url 方便的添加多个name value 参数。
     * @param url
     * @param params
     * @return
     */
    public static String attachHttpGetParams(String url, Map<String,Object> params){

        Iterator<String> keys = params.keySet().iterator();
        Iterator<Object> values = params.values().iterator();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("?");

        for (int i=0;i<params.size();i++ ) {
            stringBuffer.append(keys.next()+"="+values.next());
            if (i!=params.size()-1) {
                stringBuffer.append("&");
            }
        }

        return url + stringBuffer.toString();
    }


    /**
     * 为HttpGet 的 url 方便的添加1个name value 参数。
     * @param url
     * @param name
     * @param value
     * @return
     */
    public static String attachHttpGetParam(String url, String name, String value){
        return url + "?" + name + "=" + value;
    }

}
