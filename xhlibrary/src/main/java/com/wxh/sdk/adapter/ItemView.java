package com.wxh.sdk.adapter;

import android.view.View;

/**
 * Created by lenovo on 2016/8/15.
 */

public interface ItemView {
    public abstract View getView(int position, View convertView);
}
