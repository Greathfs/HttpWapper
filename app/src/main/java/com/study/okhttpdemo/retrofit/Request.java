package com.study.okhttpdemo.retrofit;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;

/**
 * Created by HFS on 2017/5/14.
 */

public class Request {
    private Map<String, Object> mHeaderMap;
    private Map<String, Object> mParamsMap;
    private Map<String, File> mUploadFiles;
    private String mApiUlr;
    //默认GET请求
    private RequestMethod mRequestMethod = RequestMethod.GET;

    private MediaType mMediaType = MediaType.parse("application/octet-stream");

    public Request(String apiUlr, RequestMethod method) {
        this.mApiUlr = apiUlr;
        this.mRequestMethod = method;
    }

    /**
     * 添加头部信息
     * @param key
     * @param value
     * @return
     */
    public Request putHeader(String key, Object value) {
        if (mHeaderMap == null) {
            mHeaderMap = new HashMap<>();
        }
        mHeaderMap.put(key, value);
        return this;
    }

    /**
     * 添加头部信息集合
     * @param headerMap
     */
    public void putHeaderMap(Map<String, Object> headerMap) {
        if (mHeaderMap != null) {
            mHeaderMap.putAll(headerMap);
        } else {
            mHeaderMap = headerMap;
        }
    }

    /**
     * 添加参数
     * @param key
     * @param value
     * @return
     */
    public Request putParams(String key, Object value) {
        if (mParamsMap == null) {
            mParamsMap = new HashMap<>();
        }
        mParamsMap.put(key, value);
        return this;
    }

    /**
     * 设置参数是一个map集合
     * @param paramMap
     */
    public void putParamsMap(Map<String, Object> paramMap) {
        if (mParamsMap != null) {
            mParamsMap.putAll(paramMap);
        } else {
            mParamsMap = paramMap;
        }
    }

    public Request putMediaType(MediaType mediaType) {
        mMediaType = mediaType;
        return this;
    }

    public MediaType getMediaType() {
        return mMediaType;
    }

    public Request putUploadFile(String key, File uploadFile) {
        if (mUploadFiles == null) {
            mUploadFiles = new HashMap<>();
        }
        mUploadFiles.put(key, uploadFile);
        return this;
    }

    public Map<String, File> getUploadFiles() {
        return mUploadFiles;
    }

    public Map<String, Object> getHeaderMap() {
        return mHeaderMap;
    }

    public Map<String, Object> getParamsMap() {
        return mParamsMap;
    }

    public String getApiUlr() {
        return mApiUlr;
    }

    public RequestMethod getRequestMethod() {
        return mRequestMethod;
    }
}
