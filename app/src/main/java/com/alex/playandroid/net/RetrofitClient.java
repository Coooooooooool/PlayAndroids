package com.alex.playandroid.net;


import com.alex.playandroid.net.log.LogInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Alex on 2018/6/13.
 */

public class RetrofitClient {

    private Call<Object> call;


    private static volatile RetrofitClient mInstance;
    private Retrofit mRetrofit;

    public static RetrofitClient getInstance() {
        if (mInstance == null) {
            synchronized (RetrofitClient.class) {
                if (mInstance == null) {
                    mInstance = new RetrofitClient();
                }
            }
        }
        return mInstance;
    }

    private RetrofitClient() {
        initRetrofit();
    }



    private void initRetrofit() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        // 设置超时
        builder.connectTimeout(Configure.TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(Configure.TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(Configure.TIMEOUT, TimeUnit.SECONDS);
        builder.addNetworkInterceptor(new LogInterceptor());

        OkHttpClient client = builder.build();
        mRetrofit = new Retrofit.Builder()
                // 设置请求的域名
                .baseUrl(Configure.BASE_URL)
                // 设置解析转换工厂
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    /**
     * 创建API
     */
    public <T> T create(Class<T> clazz) {
        return mRetrofit.create(clazz);
    }


}
