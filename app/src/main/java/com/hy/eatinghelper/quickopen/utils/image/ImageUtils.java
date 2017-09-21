package com.hy.eatinghelper.quickopen.utils.image;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestManager;
import com.hy.eatinghelper.quickopen.base.BaseApplication;

import java.lang.ref.WeakReference;

/**
 * Created by huyin on 2017/8/9.
 */

public class ImageUtils {

     private static ImageUtils imageUtils = null;

     public ImageUtils() {
     }

     public static ImageUtils getInstances() {
          if (imageUtils == null) {
               synchronized (ImageUtils.class) {
                    if (imageUtils == null) {
                         imageUtils = new ImageUtils();
                    }
               }
          }
          return imageUtils;
     }

     /**
      * 得到上下文对象
      *
      * @param context
      * @return
      */
     private RequestManager getContext(Context context) {
          return Glide.with(context);
     }

     /**
      * 利用弱引用调用
      *
      * @param imageView
      * @return
      */
     private ImageView getImage(ImageView imageView) {
          final WeakReference<ImageView> imageViewWeakReference = new WeakReference<>(imageView);
          ImageView target = imageViewWeakReference.get();
          return target;
     }

     /**
      * 清理内存
      *
      * @param context
      */
     public ImageUtils clearMemory(Context context) {
          Glide.get(context).clearMemory();
          return this;
     }

     /**
      * 停止加载图片，保证操作界面流畅
      *
      * @param context
      */
     public ImageUtils pauseRequests(Context context) {
          getContext(context).pauseRequests();
          return this;
     }

     /**
      * 静止状态时，继续加载图片
      *
      * @param context
      */
     public ImageUtils resumeRequests(Context context) {
          getContext(context).resumeRequests();
          return this;
     }

     /**
      * 不设置with对象的加载
      *
      * @param object
      * @param imageView
      */
     public void glideOBJ(Object object, ImageView imageView) {
          getContext(BaseApplication.getContext()).load(object)
                    .centerCrop().priority(Priority.HIGH).into(getImage(imageView));
     }

     /**
      * 不设置with对象但设定默认图的加载
      *
      * @param object
      * @param imageView
      */
     public void glideWithPlace(Object object, Drawable placeDrawable, ImageView imageView) {
          getContext(BaseApplication.getContext()).load(object).centerCrop()
                    .placeholder(placeDrawable).priority(Priority.HIGH).into(getImage(imageView));
     }

     /**
      * 设定with对象并加载为静态图
      *
      * @param context
      * @param object
      * @param imageView
      */
     public void glideAsBitmap(Context context, Object object, ImageView imageView) {
          getContext(context).load(object).asBitmap().centerCrop().priority(Priority.HIGH).into(getImage(imageView));
     }

     /**
      * 设定with对象并加载
      *
      * @param context
      * @param object
      * @param imageView
      */
     public void glideOBJContext(Context context, Object object, ImageView imageView) {
          getContext(context).load(object).centerCrop().priority(Priority.HIGH).into(getImage(imageView));
     }

     /**
      * 设定with对象和默认图并加载
      *
      * @param context
      * @param object
      * @param imageView
      */
     public void glideWithContext(Context context, Object object,
                                  Drawable placeDrawable, ImageView imageView) {
          getContext(context).load(object).centerCrop()
                    .placeholder(placeDrawable).priority(Priority.HIGH).into(getImage(imageView));
     }

     /**
      * 转化为Circle
      *
      * @param context
      * @param object
      * @param imageView
      */
     public void glide2Circle(Context context, Object object, ImageView imageView) {
          getContext(context).load(object).centerCrop().priority(Priority.HIGH)
                    .transform(new GlideCircleTFUtil(context)).into(getImage(imageView));
     }

     /**
      * 转化为Circle
      *
      * @param context
      * @param object
      * @param placeDrawable
      * @param imageView
      */
     public void glide2Circle(Context context, Object object,
                              Drawable placeDrawable, ImageView imageView) {
          getContext(context).load(object).centerCrop().placeholder(placeDrawable)
                    .priority(Priority.HIGH).transform(new GlideCircleTFUtil(context))
                    .into(getImage(imageView));
     }

     /**
      * 加载显示为自定义圆角图片
      *
      * @param context
      * @param object
      * @param imageView
      * @param placeDrawable
      * @param round
      * @param margin
      * @param cornerType
      */
     public void glide2Round(Context context, Object object, ImageView imageView, Drawable placeDrawable,
                             int round, int margin, GlideRoundTFUtil.CornerType cornerType) {
          getContext(context)
                    .load(object)
                    .centerCrop()
                    .placeholder(placeDrawable)
                    .priority(Priority.HIGH)
                    .bitmapTransform(new GlideRoundTFUtil(context, round, margin, cornerType))
                    .into(getImage(imageView));
     }

     /**
      * 加载显示为自定义圆角图片
      *
      * @param context
      * @param object
      * @param imageView
      * @param round
      * @param margin
      * @param cornerType
      */
     public void glide2Round(Context context, Object object, ImageView imageView,
                             int round, int margin, GlideRoundTFUtil.CornerType cornerType) {
          getContext(context)
                    .load(object)
                    .centerCrop()
                    .priority(Priority.HIGH)
                    .bitmapTransform(new GlideRoundTFUtil(context, round, margin, cornerType))
                    .into(getImage(imageView));
     }

     /**
      * 加载并设置大小
      *
      * @param context
      * @param object
      * @param placeDrawable
      * @param imageView
      * @param width
      * @param height
      */
     public void glideWithOverride(Context context, Object object, Drawable placeDrawable,
                                   ImageView imageView, int width, int height) {
          getContext(context)
                    .load(object)
                    .centerCrop()
                    .placeholder(placeDrawable)
                    .priority(Priority.HIGH)
                    .override(width, height)
                    .into(getImage(imageView));
     }

     /**
      * 加载并设置大小
      *
      * @param context
      * @param object
      * @param imageView
      * @param width
      * @param height
      */
     public void glideWithOverride(Context context, Object object,
                                   ImageView imageView, int width, int height) {
          getContext(context)
                    .load(object)
                    .centerCrop()
                    .priority(Priority.HIGH)
                    .override(width, height)
                    .into(getImage(imageView));
     }

     /**
      * 加载并模糊处理
      *
      * @param context
      * @param object
      * @param placeDrawable
      * @param imageView
      * @param radius
      */
     public void glide2Blur(Context context, Object object, Drawable placeDrawable,
                            ImageView imageView, float radius) {
          getContext(context)
                    .load(object)
                    .centerCrop()
                    .placeholder(placeDrawable)
                    .priority(Priority.HIGH)
                    .bitmapTransform(new GlideBlurTFUtil(context, radius))
                    .dontAnimate()
                    .into(getImage(imageView));
     }

     /**
      * 加载并模糊处理
      *
      * @param context
      * @param object
      * @param imageView
      * @param radius
      */
     public void glide2Blur(Context context, Object object,
                            ImageView imageView, float radius) {
          getContext(context)
                    .load(object)
                    .centerCrop()
                    .priority(Priority.HIGH)
                    .bitmapTransform(new GlideBlurTFUtil(context, radius))
                    .dontAnimate()
                    .into(getImage(imageView));
     }
}
