package com.study.okhttpdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.study.okhttpdemo.bean.Banner;
import com.study.okhttpdemo.okhttp.OkHttpHelper;
import com.study.okhttpdemo.okhttp.SimpleCallBack;
import com.study.okhttpdemo.retrofit.HttpResponseListener;
import com.study.okhttpdemo.retrofit.Request;
import com.study.okhttpdemo.retrofit.RetrofitHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class MainActivity extends AppCompatActivity {

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
        Request request = RetrofitHelper.newPostRequest("course_api/banner/query");
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
}
