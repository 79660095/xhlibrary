package com.wxh.sdk.httputils;


import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.wxh.sdk.android.XHApp;

import java.net.ConnectException;
import java.net.UnknownHostException;

import okhttp3.Call;

/**
 * Created by Administrator on 2016/8/13.
 */

public abstract class HttpResponse<T> extends GenericsCallback<T> implements HttpResponseCallBack<T>{

    public HttpResponse(IGenericsSerializator serializator) {
        super(serializator);
    }

    public HttpResponse(){
        super(new JsonGenericsSerializator());
    }

    @Override
    public void onResponse(T response, int id) {
        onHttpResult(response);
    }

    @Override
    public void onError(Call call, Exception e, int id) {
        String error = "";
        if (e instanceof ConnectException || e instanceof UnknownHostException) {
            error = "网络状况不佳!";
        } else {
            error = "未知错误！";
        }
        Toast.makeText(XHApp.context,error,Toast.LENGTH_SHORT).show();
        onHttpResult(null);
    }
}