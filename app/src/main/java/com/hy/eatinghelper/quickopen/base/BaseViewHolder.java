package com.hy.eatinghelper.quickopen.base;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/**
 * Created by huyin on 2017/8/10.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {

     SparseArray<View> views;

     public BaseViewHolder(View itemView) {
          super(itemView);
          views = new SparseArray<>();
     }

     public <T extends View> T getView(int viewId) {
          View view = views.get(viewId);
          if (view == null) {
               view = itemView.findViewById(viewId);
               views.put(viewId, view);
          }
          return (T) view;
     }


     /**
      * 设置文本
      *
      * @param viewId
      * @param text
      * @return
      */
     public BaseViewHolder setText(int viewId, CharSequence text) {
          TextView textView = getView(viewId);
          textView.setText(text);
          return this;
     }


     /**
      * 设置本地图片
      *
      * @param viewId
      * @param resId
      * @return
      */
     public BaseViewHolder setImagResId(int viewId, int resId) {
          ImageView imageView = getView(viewId);
          imageView.setBackgroundResource(resId);
          return this;
     }


     /**
      * 设置网络图片
      *
      * @param viewId
      * @param holderImage
      * @return
      */
     public BaseViewHolder setImageUrl(int viewId, HolderImage holderImage) {
          ImageView imageView = getView(viewId);
          final WeakReference<ImageView> imageViewWeakReference = new WeakReference<>(imageView);
          ImageView target = imageViewWeakReference.get();
          holderImage.loadImg(target, holderImage.getmUrl());
          return this;
     }

     public abstract static class HolderImage {

          String mUrl;

          public String getmUrl() {
               return mUrl;
          }

          public HolderImage(String url) {
               this.mUrl = url;
          }

          public abstract void loadImg(ImageView imageView, String url);
     }
}
