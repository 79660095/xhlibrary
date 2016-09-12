package com.wxh.library;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.wxh.library.ui.LvActivity;
import com.wxh.sdk.ui.base.BaseFragment;
import com.wxh.sdk.util.IntentUtil;

/**
 * Created by lenovo on 2016/8/16.
 */

public class WXFragment extends BaseFragment{
    TextView tv_content;
    @Override
    protected View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_bank,null);
    }

    @Override
    protected void initUI() {
        tv_content.setText("我不知道");
        tv_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               IntentUtil.startActivity(getActivity(), LvActivity.class);
            }
        });
    }

    @Override
    protected void loadData() {

    }
}
