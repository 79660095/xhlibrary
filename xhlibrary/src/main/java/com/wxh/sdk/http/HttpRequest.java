package com.wxh.sdk.http;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.wxh.sdk.des3.AESUtils;
import com.wxh.sdk.httputils.HttpResponse;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.builder.PostStringBuilder;

import java.util.Map;

import okhttp3.MediaType;

/**
 * 网络请求 2016/8/14.
 */

public class HttpRequest {

    /**
     * Get请求
     * @param url
     * @param params
     * @param listener
     * @param <T>
     */
    public static <T> void doGet(String url, Map<String, Object> params, HttpResponse<T> listener) {
        GetBuilder builder = OkHttpUtils.get();
        builder.url(url);
        if (params!=null){
            for (String key : params.keySet()) {
                builder.addParams(key, String.valueOf(params.get(key)));
            }
        }
        builder.build().execute(listener);
    }

    /**
     * Get请求
     * @param url
     * @param listener
     * @param <T>
     */
    public static <T> void doGet(String url,HttpResponse<T> listener) {
        doGet(url,null,listener);
    }

    /**
     * Post请求
     * @param url
     * @param params
     * @param listener
     * @param <T>
     */
    public static <T> void doPost(String url, Map<String, Object> params, HttpResponse<T> listener) {
        PostFormBuilder builder = OkHttpUtils.post();
        builder.url(url);
        if (params!=null){
            for (String key : params.keySet()) {
                builder.addParams(key, String.valueOf(params.get(key)));
            }
        }
        builder.build().execute(listener);
    }

    /**
     * Post请求
     * @param url
     * @param listener
     * @param <T>
     */
    public static <T> void doPost(String url,HttpResponse<T> listener) {
       doPost(url,null,listener);
    }

    /**
     * POSTJOSN数据
     * @param url
     * @params
     * @param listener
     * @param <T>
     */
    public static <T> void doPostJson(String url,Map<String,Object> params, HttpResponse<T> listener) {
        PostStringBuilder builder = OkHttpUtils.postString();
        builder.url(url);
        String str=url;
        builder.addHeader("Content-Type", "application/json");
        builder.mediaType(MediaType.parse("application/json; charset=utf-8"));
        if(params!=null){
            String json=new Gson().toJson(params);
            builder.content(AESUtils.Encrypt(json));
            str=str+"?"+json;
        }
        Logger.i(str);
        builder.build().execute(listener);
    }

    /**
     * POSTJOSN数据
     * @param url
     * @param listener
     * @param <T>
     */
    public static <T> void doPostJson(String url,HttpResponse<T> listener) {
        doPostJson(url,null,listener);
    }

}
