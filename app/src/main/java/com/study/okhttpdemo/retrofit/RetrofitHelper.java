package com.study.okhttpdemo.retrofit;

import android.content.Context;

import retrofit2.Call;

/**
 * Created by HFS on 2017/5/14.
 * Retrofit工具类
 */

public class RetrofitHelper {


    private static volatile RetrofitHelper mInstance;

    private RetrofitHelper() {
    }

    /**
     * 单例创建对象
     * @return
     */
    public static RetrofitHelper getInstance() {
        if (mInstance == null) {
            synchronized (RetrofitHelper.class) {
                if (mInstance == null) {
                    mInstance = new RetrofitHelper();
                }
            }
        }
        return mInstance;
    }

    private static Context mContext;

    public static Context getContext() {
        return mContext;
    }

    public static void setHttpCache(boolean cache){
        RetrofitWrapper.setHttpCache(cache);
    }

    public static void init(Context context, String httpBaseUrl) {
        mContext = context.getApplicationContext();
        RetrofitWrapper.setBaseUrl(httpBaseUrl);
    }

    public static <T> Call getAsync(String apiUrl, final HttpResponseListener<T> httpResponseListener) {
        return RetrofitWrapper.getAsync(apiUrl, null, null, httpResponseListener);
    }

    public static <T> Call postAsync(String apiUrl, HttpResponseListener<T> httpResponseListener) {
        return RetrofitWrapper.postAsync(apiUrl, null, null, httpResponseListener);
    }



    /**
     * 发送http网络请求
     *
     * @param request
     * @param httpResponseListener
     * @param <T>
     * @return
     */
    public static <T> Call send(Request request, HttpResponseListener<T> httpResponseListener) {
        if (RequestMethod.GET.equals(request.getRequestMethod())) {
            return RetrofitWrapper.getAsync(request.getApiUlr()
                    , request.getHeaderMap()
                    , request.getParamsMap()
                    , httpResponseListener);
        } else {
            return RetrofitWrapper.postAsync(request.getApiUlr()
                    , request.getHeaderMap()
                    , request.getParamsMap()
                    , httpResponseListener);
        }
    }

    public static <T> okhttp3.Call upload(Request request, UploadListener uploadListener) {
        return RetrofitWrapper.upload(request, uploadListener);
    }

    /**
     * @param apiUlr 格式：xxxx/xxxxx
     * @return
     */
    public static Request newRequest(String apiUlr, RequestMethod method) {
        return new Request(apiUlr, method);
    }

    /**
     * @param apiUlr 格式：xxxx/xxxxx
     * @return
     */
    public static Request newPostRequest(String apiUlr) {
        return new Request(apiUlr, RequestMethod.POST);
    }

    /**
     * @param uploadFileUrl 格式：http://xxxx/xxxxx
     * @return
     */
    public static Request newUploadRequest(String uploadFileUrl, RequestMethod method) {
        return new Request(uploadFileUrl, method);
    }

    /**
     * 默认是GET方式
     *
     * @param apiUlr 格式：xxxx/xxxxx
     * @return
     */
    public static Request newGetRequest(String apiUlr) {
        return new Request(apiUlr, RequestMethod.GET);
    }

    /**
     * 是否显示日志，默认不现实 true:显示
     *
     * @param isDebug
     */
    public static void setDebug(boolean isDebug) {
        LogWrapper.isDebug = isDebug;
    }

}
