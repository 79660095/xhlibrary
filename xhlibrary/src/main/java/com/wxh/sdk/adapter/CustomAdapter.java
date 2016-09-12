package com.wxh.sdk.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * 自定义Adapter
 * @param <T>
 */
public class CustomAdapter<T> extends BaseAdapter {
    List<T> list;
    Context context;
    private int selectedPosition;
    ItemView baseView;

    public CustomAdapter(Context context, List<T> list,ItemView baseView) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.list = list;
        this.baseView=baseView;
    }

    public CustomAdapter(Context context, List<T> list) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.list = list;
        this.baseView=(ItemView) context;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public void notifyDataSetChanged(List<T> list){
        this.list=list;
        this.notifyDataSetChanged();
    }

    public void setSelectedPosition(int position){
        this.selectedPosition=position;
        this.notifyDataSetChanged();
    }

    public int getSelectedPosition(){
        return selectedPosition;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        return baseView.getView(position, convertView);
    }



}