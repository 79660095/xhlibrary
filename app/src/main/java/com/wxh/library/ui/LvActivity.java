package com.wxh.library.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.wxh.library.R;
import com.wxh.sdk.qrcode.CaptureActivity;
import com.wxh.sdk.ui.base.BaseActivity;
import com.wxh.sdk.util.IntentUtil;

public class LvActivity extends BaseActivity  {
    Button qrcode;
    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_lv);
    }

    @Override
    protected boolean intentData() {
        return true;
    }

    @Override
    protected void initUI() {
        qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtil.startActivity(LvActivity.this, CaptureActivity.class);
            }
        });
    }

    @Override
    protected void loadData() {

    }


}
