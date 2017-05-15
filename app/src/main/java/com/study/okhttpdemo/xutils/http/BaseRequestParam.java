package com.study.okhttpdemo.xutils.http;


import com.study.okhttpdemo.xutils.Constant;

/**
 * Created by zhangzhenguo on 2017/4/10.
 * 网络请求参数基本字段
 */

public class BaseRequestParam {
    /**
     * 方法名
     */
    private String apiMethod;
    /**
     * 合作伙伴ID
     */
    private String partnerId;
    /**
     * 签名
     */
    private String sign;
    /**
     * 签名类型
     */
    private String signType;
    /**
     * 时间戳
     */
    private String timestamp;
    /**
     * 请求体
     */
    private String bizContent;

    public BaseRequestParam(String apiMethod) {
        this.apiMethod = apiMethod;
        this.signType = "RSA";
        this.partnerId = Constant.PARTNER_ID;
        this.timestamp = String.valueOf(System.currentTimeMillis());
    }

    public String getApiMethod() {
        return apiMethod;
    }

    public void setApiMethod(String apiMethod) {
        this.apiMethod = apiMethod;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getBizContent() {
        return bizContent;
    }

    public void setBizContent(String bizContent) {
        this.bizContent = bizContent;
    }
}
