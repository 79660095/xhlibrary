package com.wxh.sdk.httputils;

/**
 * Created by Administrator on 2016/8/14.
 */

public interface HttpResponseCallBack<T> {

    public void onHttpResult(T response);

}
