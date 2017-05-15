package com.study.okhttpdemo.xutils.http;

/**
 * Created by zhangzhenguo on 2017/3/31.
 */

public class BaseResponseResult {
    public static final String SUCCESS = "0000";

    /**
     * 请求响应状态码
     */
    private String code;
    /**
     * 请求响应状态信息
     */
    private String message;
    /**
     * 合作者ID
     */
    private String partnerId;
    /**
     * 响应体
     */
    private String result;
    /**
     * 签名好的数据
     */
    private String sign;
    /**
     * 签名类型
     */
    private String signType;
    /**
     * 业务状态码
     */
    private String subCode;
    /**
     * 业务处理状态信息
     */
    private String subMessage;
    /**
     * 时间戳
     */
    private String timestamp;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
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

    public String getSubCode() {
        return subCode;
    }

    public void setSubCode(String subCode) {
        this.subCode = subCode;
    }

    public String getSubMessage() {
        return subMessage;
    }

    public void setSubMessage(String subMessage) {
        this.subMessage = subMessage;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * 请求是否成功
     */
    public boolean isSuccess(){
        return SUCCESS.equals(code);
    }

    /**
     * 业务处理是否成功
     */
    public boolean isSubSuccess(){
        return SUCCESS.equals(subCode);
    }
}
