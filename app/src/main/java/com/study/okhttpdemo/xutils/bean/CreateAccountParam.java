package com.study.okhttpdemo.xutils.bean;

/**
 * Created by win7 on 2017/4/20.
 * 创建账号参数
 */

public class CreateAccountParam {
    //8宝账号
    private String name;
    //账号类型 1：B端 2：C端（灵机端） 3：C端（手机端）
    private String source;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
