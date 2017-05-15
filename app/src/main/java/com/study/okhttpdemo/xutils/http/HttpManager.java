package com.study.okhttpdemo.xutils.http;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.study.okhttpdemo.MyApplication;
import com.study.okhttpdemo.xutils.API;
import com.study.okhttpdemo.xutils.KeyConstant;
import com.study.okhttpdemo.xutils.security.RSA;
import com.study.okhttpdemo.xutils.security.SignUtil;
import com.study.okhttpdemo.xutils.utils.LogUtil;
import com.study.okhttpdemo.xutils.utils.NetWorkUtil;

import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

/**
 * Created by zhangzhenguo on 2017/4/7.
 * 网络请求工具类
 */

public class HttpManager {
    private static final String TAG = "HttpManager";
//    public static String CHART_GB2312 = "GB2312";
    private static String CHART_UTF8 = "UTF-8";

    /**
     * 链接超时时间
     */
    private int connectTimeOut;
    /**
     * 编码
     */
    private String textCharset;
    /**
     * 请求方式
     */
    private HttpMethod httpMethod;

    /**
     * 是否验签
     */
    private boolean isVerifySign;

    private Callback.Cancelable cancelable;

    private HttpManager(Builder builder) {
        connectTimeOut = builder.connectTimeOut;
        textCharset = builder.textCharset;
        httpMethod = builder.httpMethod;
        isVerifySign = builder.isVerifySign;
    }

    /**
     * 构建器
     */
    public static class Builder {

        /**
         * 链接超时时间
         */
        private int connectTimeOut = 1000 * 60;
        /**
         * 编码
         */
        private String textCharset = CHART_UTF8;
        /**
         * 请求方式
         */
        private HttpMethod httpMethod = HttpMethod.POST;

        /**
         * 是否验签
         */
        private boolean isVerifySign = false;

        /**
         * 设置 链接超时时间
         * <p><strong>Note：</strong>默认值20秒
         *
         * @param connectTimeOut 链接超时时间
         */
        public Builder setConnectTimeOut(int connectTimeOut) {
            this.connectTimeOut = connectTimeOut;
            return this;
        }

        /**
         * 设置请求编码
         * <p><strong>Note：</strong>包括请求参数编码和返回数据的编码，默认为
         *
         * @param textCharset 编码
         */
        public Builder setTextCharset(String textCharset) {
            this.textCharset = textCharset;
            return this;
        }

        /**
         * 设置请求方式
         * <p><strong>Note：</strong>默认为HttpRequest.HttpMethod.POST
         *
         * @param httpMethod 请求方式
         */
        public Builder setHttpMethod(HttpMethod httpMethod) {
            this.httpMethod = httpMethod;
            return this;
        }

        /**
         * 是否验证签名
         * @param enable true:是，false:否
         * @return 默认返回false
         */
        public Builder isVerifySign(boolean enable){
            this.isVerifySign = enable;
            return this;
        }

        /**
         * @return 请求对象
         */
        public HttpManager build() {
            return new HttpManager(this);
        }
    }

    /**
     * 网路请求
     *
     * @param params          参数
     * @param requestCallBack 回调接口
     * @return HttpHandler
     */
    private <T> Callback.Cancelable request(final RequestParams params, final RequestCallBack<T> requestCallBack) {
       final BaseCallBack<BaseResponseResult> callBack = new BaseCallBack<BaseResponseResult>() {
            @Override
            public void onSuccess(BaseResponseResult result) {
                if (result.isSuccess()){//请求响应成功
                    if (result.isSubSuccess()){
                        try {
                            if (isVerifySign){
                                //网络请求的签名
                                String signData = result.getSign();
                                String origin = new SignUtil().getASCIISignOrigin(result,"sign");
                                boolean verifySign = RSA.verify(KeyConstant.PUBLIC_KEY,origin,signData);
                                if (!verifySign){
                                    requestCallBack.onSubError("0004","登录失败，本地验签不合法");
                                    return;
                                }
                            }
                            if (String.class != requestCallBack.mType) {
                                Object o = new Gson().fromJson(result.getResult(),requestCallBack.mType);
                                if (o == null) {
                                    requestCallBack.onError(null, null,"response null");
                                    return;
                                }
                                requestCallBack.onSuccess((T)o);
                            }else {
                                requestCallBack.onSuccess((T) result.getResult());
                            }

                        }catch (Exception e){
                            requestCallBack.onError(e,null,e.getMessage());
                        }
                    }else {
                        requestCallBack.onSubError(result.getSubCode(),result.getSubMessage());
                    }
                }else {
                    requestCallBack.onError(new Exception("请求响应失败"),result.getCode(),result.getMessage());
                }
            }

           @Override
           public void onError(Exception error, String msg) {
               super.onError(error, msg);
               requestCallBack.onError(error,null,msg);
           }
       };
        cancelable = http().request(httpMethod, params, new Callback.ProgressCallback<String>() {
            @Override
            public void onWaiting() {

            }

            @Override
            public void onStarted() {
                boolean networkConnected = NetWorkUtil.isNetConnected(MyApplication.getContext());
                if (!networkConnected) {
                    cancelable.cancel();
                    callBack.onError(null, "");
                } else {
                    callBack.onStarted();
                }
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                callBack.onLoading(total, current, isDownloading);
            }

            @Override
            public void onSuccess(String result) {
                try {
                    Log.i("SCANSYS", params.toString() + ":\n" + result);
                    BaseResponseResult baseResponseResult = new Gson().fromJson(result, BaseResponseResult.class);
                    if (baseResponseResult == null) {
                        callBack.onError(null, "response null");
                        return;
                    }
                    callBack.onSuccess(baseResponseResult);
                } catch (Exception e) {
                    e.printStackTrace();
                    callBack.onError(e, e.getMessage());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("SCANSYS", params.toString(), ex);
                callBack.onError(new Exception(ex), "");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                callBack.onCancelled();
            }

            @Override
            public void onFinished() {
                callBack.onFinished();
            }
        });

        return cancelable;

    }

    public <T> Callback.Cancelable post(final RequestParams params, final BaseCallBack<T> requestCallBack){
        cancelable = http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    Log.i("SCANSYS", params.toString() + ":\n" + result);
                    if (String.class != requestCallBack.mType) {
                        Object o = new Gson().fromJson(result,requestCallBack.mType);
                        if (o == null) {
                            requestCallBack.onError(null, "response null");
                            return;
                        }
                        requestCallBack.onSuccess((T)o);
                    }else {
                        requestCallBack.onSuccess((T) result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    requestCallBack.onError(e,e.getMessage());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("SCANSYS", params.toString(), ex);
                requestCallBack.onError(new Exception(ex), "");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

        return cancelable;
    }

    /**
     * 网路请求
     *
     * @param requestCallBack    回调接口
     * @return HttpHandler
     */
    public <T> Callback.Cancelable request(BaseRequestParam baseRequestParam, final RequestCallBack<T> requestCallBack){
        if (baseRequestParam != null){
            RequestParams params = params(API.BASE_URL);
            String bizContent = baseRequestParam.getBizContent();
            if (TextUtils.isEmpty(bizContent)){
                baseRequestParam.setBizContent("{}");
            }
            String signData = new SignUtil().getASCIISignOrigin(baseRequestParam,"sign");
            //私钥加密后的签名
            String signedData = RSA.signBase64(KeyConstant.PRIVATE_KEY,signData);
            LogUtil.d(TAG,"signData:"+signData+" singedData:"+signedData);
            params.addBodyParameter("bizContent", baseRequestParam.getBizContent());
            params.addBodyParameter("apiMethod", baseRequestParam.getApiMethod());
            params.addBodyParameter("partnerId", baseRequestParam.getPartnerId());
            params.addBodyParameter("sign", signedData);
            params.addBodyParameter("signType", baseRequestParam.getSignType());
            params.addBodyParameter("timestamp", String.valueOf(baseRequestParam.getTimestamp()));
            return request(params,requestCallBack);
        }else {
            requestCallBack.onError(new Exception("请求参数为空！"),null,"");
        }
        return null;
    }

    /**
     * 下载文件
     *
     * @param params          参数
     * @param requestCallBack 回调接口
     * @param savePath        保存路径
     * @param autoResume      是否断点续传
     * @return HttpHandler
     */
    public <T> Callback.Cancelable downLoad(RequestParams params, String savePath, boolean autoResume, final BaseCallBack<T> requestCallBack) {

        params.setSaveFilePath(savePath);
        params.setAutoResume(autoResume);//断点续传
        cancelable = http().get(params, new Callback.ProgressCallback<File>() {
            @Override
            public void onWaiting() {

            }

            @Override
            public void onStarted() {
                boolean networkConnected = NetWorkUtil.isNetConnected(MyApplication.getContext());
                if (!networkConnected) {
                    cancelable.cancel();
                    requestCallBack.onError(null, "");
                } else {
                    requestCallBack.onStarted();
                }
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                requestCallBack.onLoading(total, current, isDownloading);
            }

            @Override
            public void onSuccess(File result) {
                try {
                    if (File.class == requestCallBack.mType) {
                        requestCallBack.onSuccess((T) result);
                    } else {
                        requestCallBack.onError(null, null);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    requestCallBack.onError(e, e.getMessage());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                requestCallBack.onError(new Exception(ex), "");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                requestCallBack.onCancelled();
            }

            @Override
            public void onFinished() {
                requestCallBack.onFinished();
            }
        });

        return cancelable;
    }

    /**
     * 上传文件
     * @param file 要上传的文件
     * @param requestCallBack 回调
     */
//    public <T> Callback.Cancelable upload(File file, final BaseCallBack<UploadFileResult> requestCallBack){
//        final RequestParams params = new RequestParams(API.METHOD_MATERIAL_UPLOAD);
//        params.setMultipart(true);
//        params.addBodyParameter("file",file);
//
//        cancelable = http().post(params, new Callback.CommonCallback<String>() {
//
//            @Override
//            public void onSuccess(String result) {
//                try {
//                    LogUtil.i("SCANSYS", params.toString() + ":\n" + result);
//                    UploadFileResult uploadFileResult = new Gson().fromJson(result, UploadFileResult.class);
//                    if (uploadFileResult == null){
//                        requestCallBack.onError(null, "response null");
//                        return;
//                    }
//                    requestCallBack.onSuccess(uploadFileResult);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    requestCallBack.onError(e, e.getMessage());
//                }
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//                requestCallBack.onError(new Exception(ex), "");
//            }
//
//            @Override
//            public void onCancelled(CancelledException cex) {
//                requestCallBack.onCancelled();
//            }
//
//            @Override
//            public void onFinished() {
//                requestCallBack.onFinished();
//            }
//        });
//
//        return cancelable;
//    }


    /**
     * 获取http请求对象
     *
     * @return HttpUtils
     */
    public org.xutils.HttpManager http() {
        return x.http();
    }

    /**
     * 获取参数
     * @param url 请求地址
     * @return 请求参数
     */
    public RequestParams params(String url) {
        RequestParams params = new RequestParams(url);
        params.setConnectTimeout(connectTimeOut);
        params.setCharset(textCharset);
        return params;
    }


    /**
     * 获取String链接
     *
     * @param path 网络请求地址
     * @param map  参数
     * @return url地址
     */
//    public static String getUrl(String path, Map<String, String> map) {
//        StringBuilder urlBuilder = new StringBuilder();
//        urlBuilder.append(path);
//
//        if (path.endsWith("?")) {
//            urlBuilder.append("isScan=1");
//        } else {
//            urlBuilder.append("?isScan=1");
//        }
//        int versionCode = BuildConfig.VERSION_CODE;
//        urlBuilder.append("&version=" + versionCode);
//        if (map != null) {
//            for (Map.Entry<String, String> param : map.entrySet()) {
//                String key = param.getKey();
//                String value = param.getValue();
//                urlBuilder.append("&" + key + "=" + value);
//            }
//        }
//        return urlBuilder.toString();
//    }




}

