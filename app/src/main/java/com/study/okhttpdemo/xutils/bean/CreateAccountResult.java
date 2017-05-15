package com.study.okhttpdemo.xutils.bean;

/**
 * Created by win7 on 2017/4/20.
 * 创建账号返回
 */

public class CreateAccountResult {
    private long id;//Id
    private String name;//8宝账号
    private String nickName;//昵称
    private String locationAreaCode;//区域编码
    private String locationAreaName;//区域名称
    private String phone;//手机号
    private String financeAccount;//金融账号
    private String idNum;//身份证号
    private String email;//邮箱

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getLocationAreaCode() {
        return locationAreaCode;
    }

    public void setLocationAreaCode(String locationAreaCode) {
        this.locationAreaCode = locationAreaCode;
    }

    public String getLocationAreaName() {
        return locationAreaName;
    }

    public void setLocationAreaName(String locationAreaName) {
        this.locationAreaName = locationAreaName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFinanceAccount() {
        return financeAccount;
    }

    public void setFinanceAccount(String financeAccount) {
        this.financeAccount = financeAccount;
    }

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
