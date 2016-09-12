package com.wxh.sdk.view;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.wxh.sdk.R;


public class LoadingDialog extends Dialog {

    private TextView tips_loading_msg;

    private CharSequence message = "加载中";

    public LoadingDialog(Context context) {
        super(context,R.style.FullDialog);
    }

    public LoadingDialog(Context context, String message) {
        super(context);
        this.message = message;
    }

    public LoadingDialog(Context context, int theme, String message) {
        super(context, theme);
        this.message = message;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.v_loadingdialog);
        tips_loading_msg = (TextView) findViewById(R.id.tips_loading_msg);
        tips_loading_msg.setText(this.message);
    }

    public void setMessage(CharSequence message) {
        this.message = message;
    }

    public void setMessage(int resId) {
        setMessage(getContext().getResources().getString(resId));
    }

}
