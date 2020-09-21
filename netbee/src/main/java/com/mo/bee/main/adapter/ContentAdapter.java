package com.mo.bee.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mo.bee.R;

import java.util.ArrayList;
import java.util.List;

public class ContentAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<String> mDatas = new ArrayList<>();

    //MyAdapter需要一个Context，通过Context获得Layout.inflater，然后通过inflater加载item的布局
    public ContentAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public void setDatas(List<String> datas) {
        this.mDatas.clear();
        this.mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public List<String> getDatas() {
        return mDatas;
    }

    public void clear() {
        this.mDatas.clear();
        notifyDataSetChanged();
    }


    //返回数据集的长度
    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //这个方法才是重点，我们要为它编写一个ViewHolder
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.bee_view_content_item, parent, false); //加载布局
            holder = new ViewHolder();
            holder.message = (TextView) convertView.findViewById(R.id.message);
            convertView.setTag(holder);
        } else {   //else里面说明，convertView已经被复用了，说明convertView中已经设置过tag了，即holder
            holder = (ViewHolder) convertView.getTag();
        }
        String message = mDatas.get(position);
        holder.message.setText(message);
        return convertView;
    }

    private static class ViewHolder {
        TextView message;
    }

}