package com.wxh.sdk.viewtool;


import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;


import com.wxh.sdk.R;
import com.wxh.sdk.util.ImageUtil;
import com.wxh.sdk.util.ViewTool;
import com.wxh.sdk.widget.AdViewPager;

import java.util.ArrayList;
import java.util.List;



/**
 * 幻灯片广告类 viewpager实现的无限左右循环滑动 圆点表示幻灯片页数，可自由选择显示或隐藏
 *
 * @author lxh 2014-12-18
 */
public class AdTool {

    private AdViewPager ad_viewpager;
    private LinearLayout pointGroup;

    private Context ct;
    private List<String> imageUrls = new ArrayList<String>();
    // private List<ActionDomain> actions = new ArrayList<ActionDomain>();
    private AdOnClickCallBack callBack;

    private ArrayList<View> imageList;
    private LayoutInflater inflater;
    private boolean isHidePoint;
    private int page;

    private static final long DELAY_MILLIS = 5000;

    private static final int HANDLER_AD_RUNNING_START = 11;

    /* 自动滚动 */
    private boolean isRunning = true;
    private LayoutParams imageParams;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case HANDLER_AD_RUNNING_START:
                    // 让viewPager 滑动到下一页
                    if (isRunning) {
                        ad_viewpager.setCurrentItem(ad_viewpager.getCurrentItem() + 1);
                        handler.sendEmptyMessageDelayed(HANDLER_AD_RUNNING_START, DELAY_MILLIS);
                    }
                    break;
            }
        }

        ;
    };

    public AdTool(Context ct, List<String> photos, AdOnClickCallBack callBack) {
        this.ct = ct;
        this.callBack = callBack;
        if (photos != null && photos.size() > 0) {
            for (String url : photos) {
                imageUrls.add(url);
                // actions.add(domain.action);
            }
        }
    }

    // public AdTool(Context ct, List<String> photos,
    // AdOnClickCallBack callBack) {
    // this.ct = ct;
    // this.callBack = callBack;
    // if (photos != null && photos.size() > 0) {
    // for (String url : photos) {
    // imageUrls.add(domain.src);
    // // actions.add(domain.action);
    // }
    // }
    // }

    /**
     * @param group 广告加载的父框体
     */
    public void initAdView(ViewGroup group) {
        View v = initView();
        group.addView(v);
    }

    /**
     * @param
     * @Desc: 是否自动切换
     */
    public void setAutoRun(boolean isAutoRun) {
        isRunning = isAutoRun;
    }

    private View initView() {

        inflater = LayoutInflater.from(ct);
        View rootView = inflater.inflate(R.layout.ad_view, null, false);

        // com.gogotown.app.sdk.business.widgets.AdViewPager
        ad_viewpager = (AdViewPager) rootView.findViewById(R.id.ad_viewpager);
        pointGroup = (LinearLayout) rootView.findViewById(R.id.point_group);

        page = imageUrls.size();
        setImageList();
        setPoints(page);

        ad_viewpager.setAdapter(new MyPagerAdapter());
        ad_viewpager.setCurrentItem(Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2 % imageList.size()));

        ad_viewpager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            /**
             * 页面切换后调用
             * position 新的页面位置
             */
            public void onPageSelected(int position) {
                // 改变指示点的状态
                // 把当前点enbale 为true
                if (isHidePoint)
                    return;
                position = position % page;
                for (int i = 0; i < page; i++) {
                    pointGroup.getChildAt(i).setEnabled(i == position);
                }

            }

            @Override
            /**
             * 页面正在滑动的时候，回调
             */
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            /**
             * 当页面状态发生变化的时候，回调
             */
            public void onPageScrollStateChanged(int state) {

            }
        });
        // 超过一页时，才自动切换
        if (page > 1) {
            ad_viewpager.setOnTouchListener(new OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            handler.removeMessages(HANDLER_AD_RUNNING_START);
                            break;
                        case MotionEvent.ACTION_UP:
                            handler.sendEmptyMessageDelayed(HANDLER_AD_RUNNING_START, DELAY_MILLIS);
                            break;
                        case MotionEvent.ACTION_MOVE:
                            handler.removeMessages(HANDLER_AD_RUNNING_START);
                            break;
                    }
                    return false;
                }
            });
            /*
			 * 自动循环： 1、定时器：Timer 2、开子线程 while true 循环 3、ColckManager 4、 用handler
			 * 发送延时信息，实现循环
			 */
            handler.sendEmptyMessageDelayed(HANDLER_AD_RUNNING_START, DELAY_MILLIS);
        }

        return rootView;
    }

    /**
     * 根据幻灯片页数，设置小圆点
     *
     * @param page
     */
    private void setPoints(int page) {
        isHidePoint = page <= 1;
        if (isHidePoint) {
            pointGroup.setVisibility(View.GONE);
            return;
        }
        pointGroup.setVisibility(View.VISIBLE);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.rightMargin = ViewTool.dip2px(ct, 6);
        // 添加指示点
        for (int i = 0; i < page; i++) {
            ImageView point = new ImageView(ct);
            point.setLayoutParams(params);
            point.setBackgroundResource(R.drawable.slide_point_select);
            point.setEnabled(i == 0);
            pointGroup.addView(point);
        }
    }

    private void setImageList() {
        // TODO Auto-generated method stub
        imageList = new ArrayList<View>();
        imageParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        if (page == 1) {
            // 仅一页时，准备三张幻灯片，否则会出现空白页
            for (int i = 0; i < 3; i++) {
                addImageView(0);
            }
        } else if (page == 2) {
            // 仅两页时，准备四页
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    addImageView(j);
                }
            }
        } else {
            // 三页以上
            for (int i = 0; i < page; i++) {
                addImageView(i);
            }
        }
    }

    private void addImageView(final int index) {
        // 初始化图片资源
        String imageUrl = imageUrls.get(index);
        ImageView image = new ImageView(ct);
        image.setLayoutParams(imageParams);
        image.setScaleType(ScaleType.CENTER_CROP);
        //bitmapUtils.display(image, imageUrl);
        ImageUtil.displayImage(imageUrl,image);
        // final ActionDomain action = actions.get(index);
        image.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.setOnAdClickListener(index);
            }
        });
        imageList.add(image);
    }

    private class MyPagerAdapter extends PagerAdapter {

        @Override
        /**
         * 获得页面的总数
         */
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        /**
         * 获得相应位置上的view
         * container  view的容器，其实就是viewpager自身
         * position 	相应的位置
         */
        public Object instantiateItem(ViewGroup container, int position) {

            // 给 container 添加一个view
            View view = imageList.get(position % imageList.size());
            if (view.getParent() != null) {
                ((ViewGroup) view.getParent()).removeView(view);
            }
            container.addView(view, 0);
            // 返回一个和该view相对的object
            return view;
        }

        @Override
        /**
         * 判断 view和object的对应关系
         */
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        /**
         * 销毁对应位置上的object
         */
        public void destroyItem(ViewGroup container, int position, Object object) {
            // container.removeView((View) object);
            // object = null;
        }
    }

    public abstract interface AdOnClickCallBack {
        public abstract void setOnAdClickListener(int index);
    }

}
