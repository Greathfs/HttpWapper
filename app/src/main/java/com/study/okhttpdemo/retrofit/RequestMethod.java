package com.study.okhttpdemo.retrofit;

public enum RequestMethod {
    GET("GET"),

    POST("POST");

    private final String value;

    RequestMethod(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
