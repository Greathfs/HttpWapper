# HttpWapper

OkHttp
```java
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
```

Retrofit

```java
    public void testRetrofit(View view) {
        Request request = RetrofitHelper.newPostRequest("/course_api/banner/query");
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
```
