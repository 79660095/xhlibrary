package com.wxh.sdk.ui.base;


import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wxh.sdk.R;
import com.wxh.sdk.util.StringUtil;
import com.wxh.sdk.util.ViewUtils;


/**
 * Fragment基类
 */
public abstract class BaseFragment extends Fragment {

    /**
     * 上下文对象
     */
    protected Context context;
    /**
     * 加载缓冲时菊花布局
     */
    protected View ps_bview;
    /**
     * 菊花控件
     */
    protected ImageView iv_ps;
    /**
     * 菊花下面的文字控件
     */
    protected TextView tv_pmsg;
    /**
     * 加载布局对象
     */
    protected LayoutInflater inflater;
    /**
     * 内容View
     */
    protected View viewRoot;

    @Override
    public void onCreate(Bundle savedInstanceState) {//创建时 做一些初始化操作
        context = getActivity();
        inflater = LayoutInflater.from(context);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewRoot = initView(inflater);
        ViewUtils.inject(this, viewRoot);
        return viewRoot;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initUI();
    }

    /**
     * 初始化view,自动调用
     */
    protected abstract View initView(LayoutInflater inflater);


    /**
     * 初始化UI
     */
    protected abstract void initUI();

    /**
     * 请求初始化数据 加载initUI 后手动调用
     */
    protected abstract void loadData();

    /**
     * 弹出消息
     *
     * @param msg
     */
    protected void showToast(String msg) {
        if (StringUtil.isEmpty(msg)) return;
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }


    /**
     * 设置缓冲布局View
     * 参数如果为 true 正常展示缓存界面  false则轻触重新加载 自动调用初始化数据
     *
     * @desc TODO
     * @author：wh
     * @create：2016/4/1
     */
    protected void setLoadProgress(boolean load) {
        setLoadProgress(load, "轻触重新加载");
    }

    /**
     * 设置缓冲布局View 自己定义缓冲文字
     * 参数如果为 true 正常展示缓存界面  false则轻触重新加载 自动调用初始化数据
     *
     * @desc TODO
     * @author：wh
     * @create：2016/4/1
     */
    protected void setLoadProgress(boolean load, String lable) {
        if (null == ps_bview) {
            ps_bview = viewRoot.findViewById(R.id.ps_bview);
            iv_ps = (ImageView) viewRoot.findViewById(R.id.iv_ps);
            AnimationDrawable animationDrawable = (AnimationDrawable) iv_ps.getDrawable();
            animationDrawable.start();
            tv_pmsg = (TextView) viewRoot.findViewById(R.id.tv_pmsg);
        }
        ps_bview.setVisibility(View.VISIBLE);
        iv_ps.setVisibility(View.VISIBLE);
        if (load) {//如果是正常的
            tv_pmsg.setText("加载中...");
            ps_bview.setOnClickListener(null);//设置没有点击事件
        } else {
            iv_ps.setVisibility(View.GONE);//菊花隐藏
            tv_pmsg.setText(lable);
            ps_bview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setLoadProgress(true);//设置缓冲布局展示
                    loadData();//重新加载数据
                }
            });
        }
    }

    /**
     * 取消缓冲布局
     *
     * @desc TODO
     * @author：wxh
     * @create：2015/4/1
     */
    protected void setProgressDisMiss() {
        if (null != ps_bview) {
            ps_bview.setVisibility(View.GONE);
        }
    }

}
