package com.wxh.sdk.util;

/**
 * Created by lenovo on 2016/8/15.
 */

public class StringUtil {

    /**
     * 判断字符串是否为null 或者""
     * @param content
     * @return
     */
    public static Boolean isEmpty(String content) {
        return "".equals(content) || null == content ? true : false;
    }
}