package com.hy.eatinghelper.quickopen.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by huyin on 2017/8/7.
 */

public abstract class BaseFragment extends Fragment {

     protected abstract int layoutId();

     private View contentView = null;

     Unbinder unbinder = null;

     @Nullable
     @Override
     public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                              @Nullable Bundle savedInstanceState) {
          if (contentView == null) {
               contentView = inflater.inflate(layoutId(), container, false);
          } else {
               return contentView;
          }
          unbinder = ButterKnife.bind(this, contentView);
          return contentView;
     }

     @Override
     public void onActivityCreated(@Nullable Bundle savedInstanceState) {
          super.onActivityCreated(savedInstanceState);
          initView();
          initData();
     }

     protected View getContentView() {
          if (contentView != null) {
               return contentView;
          }
          return null;
     }

     /**
      * 初始化View
      */
     protected abstract void initView();

     /**
      * 初始数据
      */
     protected abstract void initData();

     /**
      * 视图销毁的时候讲Fragment是否初始化的状态变为false
      */
     @Override
     public void onDestroyView() {
          super.onDestroyView();
          unbinder.unbind();
          ((ViewGroup) contentView.getParent()).removeView(contentView);
          DestroyView();
     }

     /**
      * 销毁
      */
     protected abstract void DestroyView();

     /**
      * 按钮最后一次点击时间
      */
     private static long mLastClickTime = 0;

     /**
      * 空闲时间
      */
     private static final int SPACE_TIME = 1000;

     /**
      * 是否连续点击按钮多次
      *
      * @return 是否快速多次点击
      */
     public static boolean isFastDoubleClick() {
          long time = SystemClock.elapsedRealtime();
          if (time - mLastClickTime <= SPACE_TIME) {
               return true;
          } else {
               mLastClickTime = time;
               return false;
          }
     }

     /**
      * 加载到下一个Activity，并传值
      *
      * @param targetActivity
      * @param bundle
      */
     public void startActivity(Class<?> targetActivity, Bundle bundle) {
          Intent intent = new Intent(getActivity(), targetActivity);
          if (bundle != null) {
               intent.putExtras(bundle);
          }
          startActivity(intent);
     }

     /**
      * 跳转到下个Activity
      *
      * @param targetActivity
      */
     public void startActivity(Class<?> targetActivity) {
          startActivity(targetActivity, null);
     }

     /**
      * 跳转下一个Activity,并关闭当前Activity
      *
      * @param targetActivity
      */
     public void startActivityAndCloseThis(Class<?> targetActivity) {
          startActivity(targetActivity);
          getActivity().finish();
     }
}
