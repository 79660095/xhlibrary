package com.wxh.sdk.ui.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wxh.sdk.R;
import com.wxh.sdk.android.XHActivityManager;
import com.wxh.sdk.http.HttpModel;
import com.wxh.sdk.http.HttpStatus;
import com.wxh.sdk.util.StringUtil;

import com.wxh.sdk.util.ViewUtils;
import com.wxh.sdk.view.LoadingDialog;



/**
 * Acticity基类
 */
public abstract class BaseActivity extends AppCompatActivity {
    /**
     * 上下文对象
     */
    protected Context context;
    /**
     * 顶部布局左边的图标
     */
    ImageView iv_back;
    /**
     * 顶部布局标题内容
     */
    TextView tv_tlable;
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

    public LoadingDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        initView(savedInstanceState);
        ViewUtils.inject(this, getWindow().getDecorView());
        XHActivityManager.getActivityManager().addActivity(this);
        if (intentData()) {
            initUI();
        }
    }


    /**
     * 设置界面布局
     */
    public abstract void initView(Bundle savedInstanceState);

    /**
     * 检查调用本类必需传递的参数条件是否满足
     * 默认返回true，在需要的类中重写此方法即可
     * - returns: true为满足
     */
    protected abstract boolean intentData();

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
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startActivity(Intent intent) {//跳转Activity动画
        super.startActivity(intent);
        overridePendingTransition(R.anim.base_slide_right_in, R.anim.base_slide_remain);
    }

    // Press the back button in mobile phone
    @Override
    public void onBackPressed() {//关闭Activity动画
        super.onBackPressed();
        overridePendingTransition(0, R.anim.base_slide_right_out);
    }

    /**
     * 初始化头部布局
     *
     * @param lable
     */
    public void initTitle(String lable) {
        ((TextView) findViewById(R.id.tv_tlable)).setText(lable);
        ((ImageView) findViewById(R.id.iv_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0, R.anim.base_slide_right_out);
            }
        });
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
            ps_bview = findViewById(R.id.ps_bview);
            iv_ps = (ImageView) findViewById(R.id.iv_ps);
            AnimationDrawable animationDrawable = (AnimationDrawable) iv_ps.getDrawable();
            animationDrawable.start();
            tv_pmsg = (TextView) findViewById(R.id.tv_pmsg);
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

    /**
     * 显示进度框-自定义内容
     */
    public void showLoading(Context context) {
        showLoading(context, "");
    }

    public void showLoading(Context con, String content) {
        if (null == content || "".equals(content))
            content = "请稍候";
        if (mProgressDialog == null) {
            mProgressDialog = new LoadingDialog(con);
        }
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.setMessage(content);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }
    }

    public void cancelLoading() {
        if (mProgressDialog != null) {
            mProgressDialog.cancel();
            mProgressDialog = null;
        }
    }


    public boolean chkStatus(HttpModel model, boolean ishow) {
        if (model == null) {
            if(ishow){
                setLoadProgress(false);
            }
            return false;
        } else if (model.code != HttpStatus.OK) {
            if(ishow){
                setLoadProgress(false);
            }
            showToast(model.info);
            return false;
        }
        setProgressDisMiss();
        return true;
    }

    public boolean chkStatus(HttpModel model){
        return chkStatus(model,true);
    }


}
