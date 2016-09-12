package com.wxh.sdk.httputils;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

/**
 * Created by JimGong on 2016/6/23.
 */
public class JsonGenericsSerializator implements IGenericsSerializator {
    Gson mGson = new Gson();
    @Override
    public <T> T transform(String response, Class<T> classOfT) {
        Logger.json(response);
        return mGson.fromJson(response, classOfT);
    }
}