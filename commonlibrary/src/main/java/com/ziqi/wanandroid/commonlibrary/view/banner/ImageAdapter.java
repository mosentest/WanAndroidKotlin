package com.ziqi.wanandroid.commonlibrary.view.banner;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.youth.banner.adapter.BannerAdapter;
import com.ziqi.wanandroid.commonlibrary.bean.Banner;
import com.ziqi.wanandroid.commonlibrary.view.ImageViewX;

import java.util.List;

/**
 * 自定义布局，下面是常见的图片样式，更多实现可以看demo，可以自己随意发挥
 */
public abstract class ImageAdapter extends BannerAdapter<Banner, ImageAdapter.BannerViewHolder> {

    public ImageAdapter(List<Banner> mDatas) {
        //设置数据，也可以调用banner提供的方法,或者自己在adapter中实现
        super(mDatas);
    }

    //创建ViewHolder，可以用viewType这个字段来区分不同的ViewHolder
    @Override
    public BannerViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        ImageViewX imageViewX = new ImageViewX(parent.getContext());
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        imageViewX.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return new BannerViewHolder(imageViewX);
    }

    @Override
    public void onBindView(BannerViewHolder holder, Banner data, int position, int size) {
        convert(holder, data, position);
    }

    public abstract void convert(BannerViewHolder holder, Banner data, int position);

    public static class BannerViewHolder extends RecyclerView.ViewHolder {
        public ImageViewX imageViewX;

        public BannerViewHolder(@NonNull ImageViewX view) {
            super(view);
            this.imageViewX = view;
        }
    }
}