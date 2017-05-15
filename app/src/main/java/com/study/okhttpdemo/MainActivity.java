package com.study.okhttpdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.study.okhttpdemo.bean.Banner;
import com.study.okhttpdemo.okhttp.OkHttpHelper;
import com.study.okhttpdemo.okhttp.SimpleCallBack;
import com.study.okhttpdemo.retrofit.HttpResponseListener;
import com.study.okhttpdemo.retrofit.Request;
import com.study.okhttpdemo.retrofit.RetrofitHelper;
import com.study.okhttpdemo.xutils.API;
import com.study.okhttpdemo.xutils.bean.CreateAccountParam;
import com.study.okhttpdemo.xutils.bean.CreateAccountResult;
import com.study.okhttpdemo.xutils.http.BaseRequestParam;
import com.study.okhttpdemo.xutils.http.HttpManager;
import com.study.okhttpdemo.xutils.http.RequestCallBack;
import com.study.okhttpdemo.xutils.utils.LogUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private OkHttpHelper mOkHttpHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mOkHttpHelper=OkHttpHelper.getInstance();
    }

    public void testOkHttp(View view) {
        Map<String,Object> map=new HashMap<>();
        map.put("type",1);
        mOkHttpHelper.get("http://112.124.22.238:8081/course_api/banner/query",map ,

                new SimpleCallBack<List<Banner>>(MainActivity.this) {
                    @Override
                    public void onSuccess(Response response, List<Banner> banners) {
                        Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Response response, int errorCode, Exception e) {
                        Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();

                    }
                });
    }

    public void testRetrofit(View view) {
        Request request = RetrofitHelper.newGetRequest("course_api/banner/query");
        request.putParams("type",1);
        Call call = RetrofitHelper.send(request, new HttpResponseListener<List<Banner>>() {

            @Override
            public void onResponse(List<Banner> banners, Headers headers) {
                Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable e) {
                Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void testXutils(View view) {
        HttpManager httpManager = new HttpManager.Builder().build();
        BaseRequestParam baseRequestParam = new BaseRequestParam(API.METHOD_CREATE_ACCOUNT);
        CreateAccountParam createAccountParam = new CreateAccountParam();
        createAccountParam.setName("10043615");
        createAccountParam.setSource("2");
        baseRequestParam.setBizContent(new Gson().toJson(createAccountParam));
        httpManager.request(baseRequestParam, new RequestCallBack<CreateAccountResult>() {
            @Override
            public void onSuccess(CreateAccountResult result) {
                Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Exception error, String code, String msg) {
                super.onError(error, code, msg);
                LogUtil.d(TAG,msg);
                Toast.makeText(MainActivity.this, "onError", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSubError(String subCode, String msg) {
                super.onSubError(subCode, msg);
                Toast.makeText(MainActivity.this, "onSubError", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
