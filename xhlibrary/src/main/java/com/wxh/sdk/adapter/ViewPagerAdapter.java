package com.wxh.sdk.adapter;


import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.wxh.sdk.ui.base.BaseFragment;


public class ViewPagerAdapter extends FragmentPagerAdapter {
    private List<BaseFragment> mfragments;
    private FragmentManager fm;

    public ViewPagerAdapter(FragmentManager fm , List<BaseFragment> fragments) {
        super(fm);
        this.fm = fm;
        this.mfragments = fragments;
    }

    @Override
    public Fragment getItem(int pos) {
        // TODO Auto-generated method stub
        return mfragments != null ? mfragments.get(pos) : null;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mfragments != null ? mfragments.size() : 0;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public void setFragments(List<BaseFragment> fragments) {
        if (this.mfragments != null) {
            FragmentTransaction ft = fm.beginTransaction();
            for (Fragment f : this.mfragments) {
                ft.remove(f);
            }
            ft.commit();
            ft = null;
            fm.executePendingTransactions();
        }
        this.mfragments = fragments;
        notifyDataSetChanged();
    }

}


