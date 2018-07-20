package com.alex.playandroid.net.callback;


import com.alex.playandroid.net.response.NetResponse;
import com.alex.playandroid.utils.LogUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Alex on 2018/6/14.
 */

public abstract class NetCallback<T extends NetResponse> implements Callback<T> {

    private static final String TAG = "NetCallback";

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        int code = response.code();
        if (code == 200) {

            //对后台返回的数据进行处理
            if(response.body().getErrorCode()<0){
                onFail(response.body().getErrorCode(),response.body().getErrorMsg());
            }else{
                onSuccess(response.body(),response.message());
            }

        }  else {
            onFail(response.code(),response.message());
        }

    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        onFail(-1,t.getMessage());
    }

    /**
     * 执行成功
     *
     * @param data
     * @param msg
     */
    public abstract void onSuccess(T data, String msg);

    /**
     * 执行失败
     *
     * @param errorCode
     * @param msg
     */
    public  void onFail(int errorCode, String msg){
        LogUtil.e(TAG,"errorCode："+errorCode+"msg："+msg);
    };



}
