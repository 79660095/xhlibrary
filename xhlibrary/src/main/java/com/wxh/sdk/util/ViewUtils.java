package com.wxh.sdk.util;

import android.view.View;

import java.lang.reflect.Field;

/**
 * 自定义工具
 *
 * @desc TODO
 * @author wxh
 * @create 2016-5-19
 */
public class ViewUtils {
    public static final void inject(Object object, View footView) {
        if (object == null) {
            return;
        }
        // 获取对象中所有属性-不包含父类私有成员
        Field[] fields = getFields(object);
        for (Field fi : fields) {
            if (View.class.isAssignableFrom(fi.getType())) {
                int id = footView.getResources().getIdentifier(fi.getName(),
                        "id", footView.getContext().getPackageName());
                if (id > 0) {
                    try {
                        fi.set(object, footView.findViewById(id));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }



    private static Field[] getFields(Object o) {
        Field[] f1 = o.getClass().getDeclaredFields();
        // 设置不检查访问
        for (int i = 0; i < f1.length; i++) {
            f1[i].setAccessible(true);
        }
        return f1;
    }

}
