package com.wxh.library;

import android.os.Bundle;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.lzy.widget.AlphaIndicator;
import com.wxh.sdk.adapter.ViewPagerAdapter;
import com.wxh.sdk.ui.base.BaseActivity;
import com.wxh.sdk.ui.base.BaseFragment;
import com.wxh.sdk.widget.SViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    SViewPager viewPager;
    AlphaIndicator alphaIndicator;
    List<BaseFragment> fragments;
    ViewPagerAdapter mAdapter;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected boolean intentData() {
        return true;
    }

    @Override
    protected void initUI() {
        initViewPager();
    }

    private void initViewPager() {
        fragments = new ArrayList<BaseFragment>();
        fragments.clear();
        fragments.add(new WXFragment());
        fragments.add(new TxlFragment());
        fragments.add(new FxFragment());
        fragments.add(new MeFragment());

        mAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setSlideable(true);
        viewPager.setOffscreenPageLimit(fragments.size());
        viewPager.setAdapter(mAdapter);
        alphaIndicator.setViewPager(viewPager);
    }

    @Override
    protected void loadData() {

    }

}
