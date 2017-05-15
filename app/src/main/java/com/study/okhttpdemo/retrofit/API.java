package com.study.okhttpdemo.retrofit;

/**
 * Created by zhangzhenguo on 2017/4/10.
 * 网络请求接口
 */

public class API {
    public static final String IP = "http://192.168.1.147:9090";

    private static final String PROJECT = "/fnh";

    private static final String GATEWAY = "/gateway";

    public static final String BASE_URL = IP + PROJECT + GATEWAY;
    //创建账号
    public static final String METHOD_CREATE_ACCOUNT = "purchaserP2p.checkAccount";

}
