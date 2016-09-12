package com.wxh.sdk.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 广告ViewPager
 * @desc TODO
 * @author：wxh
 * @create：2015/10/19 20:41
 */
public class AdViewPager extends ViewPager {


    public AdViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // 当手指按下的时候，获取到点击那个球

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 当手指按下的时候，接管ScrollView滑动
                // this.getParent();//获取到LinearLayout
                this.getParent().getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                //updatePop(child);
                break;
            case MotionEvent.ACTION_UP:
                // 当手指按下的时候，放行，ScrollView滑动
                this.getParent().getParent().requestDisallowInterceptTouchEvent(false);
                break;
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }


}

