package com.hy.eatinghelper.quickopen.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.hy.eatinghelper.quickopen.utils.ScreenUtils;

import java.lang.reflect.Field;

import butterknife.ButterKnife;

/**
 * Created by huyin on 2017/8/7.
 */

public abstract class BaseSwipeActivity extends AppCompatActivity implements SlidingPaneLayout.PanelSlideListener {

     @Override
     protected void onCreate(@Nullable Bundle savedInstanceState) {
          initSwipeBackFinish();
          super.onCreate(savedInstanceState);
          setContentView(layoutID());
          ButterKnife.bind(this);

          initTitle();
          initView();
          initData();
     }

     /**
      * 沉浸式
      *
      * @param view
      */
     protected void setImmerseLayout(View view) {
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
               Window window = getWindow();
               window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
               int statusBarHeight = ScreenUtils.getStatusBarHeight(getBaseContext());
               view.setPadding(0, statusBarHeight, 0, 0);
          }
     }

     /**
      * 布局ID
      *
      * @return
      */
     protected abstract int layoutID();

     /**
      * 初始化Title
      */
     protected abstract void initTitle();

     /**
      * 初始化View
      */
     protected abstract void initView();

     /**
      * 初始数据
      */
     protected abstract void initData();


     @Override
     protected void onDestroy() {
          super.onDestroy();
          DestroyView();
     }

     /**
      * 销毁
      */
     protected abstract void DestroyView();

     /**
      * 加载到下一个Activity，并传值
      *
      * @param targetActivity
      * @param bundle
      */
     public void startActivity(Class<?  extends Activity> targetActivity, Bundle bundle) {
          Intent intent = new Intent(this, targetActivity);
          if (bundle != null) {
               intent.putExtras(bundle);
          }
          startActivity(intent);
     }

     /**
      * 跳转到下个Activity，并设置返回码
      *
      * @param targetActivity
      * @param RequestCode
      */
     public void startActvityForRsult(Class<? extends Activity> targetActivity, int RequestCode) {
          Intent intent = new Intent(this, targetActivity);
          startActivityForResult(intent, RequestCode);
     }

     /**
      * 带参数返回上一个Activity，并关闭
      *
      * @param bundle
      */
     public void setActivityResult(Bundle bundle) {
          Intent intent = new Intent();
          if (bundle != null) {
               intent.putExtras(bundle);
          }
          setResult(RESULT_OK, intent);
          this.finish();
     }

     /**
      * 跳转到下个Activity
      *
      * @param targetActivity
      */
     public void startActivity(Class<? extends Activity> targetActivity) {
          startActivity(targetActivity, null);
     }

     /**
      * 跳转下一个Activity,并关闭当前Activity
      *
      * @param targetActivity
      */
     public void startActivityAndCloseThis(Class<? extends Activity> targetActivity) {
          startActivity(targetActivity);
          this.finish();
     }

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
      * 初始化滑动返回
      */
     private void initSwipeBackFinish() {
          if (isSupportSwipeBack()) {
               SlidingPaneLayout slidingPaneLayout = new SlidingPaneLayout(this);
               //通过反射改变mOverhangSize的值为0，这个mOverhangSize值为菜单到右边屏幕的最短距离，
               //默认是32dp
               try {
                    //更改属性
                    Field field = SlidingPaneLayout.class.getDeclaredField("mOverhangSize");
                    field.setAccessible(true);
                    field.set(slidingPaneLayout, 0);
               } catch (Exception e) {
                    e.printStackTrace();
               }
               //设置监听事件
               slidingPaneLayout.setPanelSlideListener(this);
               slidingPaneLayout.setSliderFadeColor(getResources().getColor(android.R.color.transparent));

               View leftView = new View(this);
               leftView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
               //在左边添加这个视图
               slidingPaneLayout.addView(leftView, 0);
               //获取到最顶层的视图容器
               ViewGroup decor = (ViewGroup) getWindow().getDecorView();
               //获取到左边的视图
               ViewGroup decorChild = (ViewGroup) decor.getChildAt(0);
               //设置左边的视图为透明
               decor.removeView(decorChild);
               decor.addView(slidingPaneLayout);
               //在右边添加这个视图
               slidingPaneLayout.addView(decorChild, 1);
          }
     }

     /**
      * 是否支持滑动退出
      */
     protected boolean isSupportSwipeBack() {
          return true;
     }

     @Override
     public void onPanelClosed(View view) {
     }

     @Override
     public void onPanelOpened(View view) {
          finish();

          //设置Activity退出的动画
          overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
     }

     @Override
     public void onPanelSlide(View view, float v) {
          if (v > 0.8f) {
               v = 0.8f;
          }
          view.setAlpha(1 - v);
     }



     @Override
     public boolean dispatchTouchEvent(MotionEvent ev) {
          if (ev.getAction() == MotionEvent.ACTION_DOWN) {
               View v = getCurrentFocus();
               if (isShouldHideKeyboard(v, ev)) {

                    hideKeyboard(v.getWindowToken());
               }
          }
          return super.dispatchTouchEvent(ev);
     }

     /**
      * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
      *
      * @param v
      * @param event
      * @return
      */
     private boolean isShouldHideKeyboard(View v, MotionEvent event) {
          if (v != null && (v instanceof EditText)) {
               int[] l = {0, 0};
               v.getLocationInWindow(l);
               int left = l[0],
                         top = l[1],
                         bottom = top + v.getHeight(),
                         right = left + v.getWidth();
               if (event.getX() > left && event.getX() < right
                         && event.getY() > top && event.getY() < bottom) {
                    // 点击EditText的事件，忽略它。
                    return false;
               } else {
                    return true;
               }
          }
          // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
          return false;
     }

     /**
      * 获取InputMethodManager，隐藏软键盘
      *
      * @param token
      */
     private void hideKeyboard(IBinder token) {
          if (token != null) {
               InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
               im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
          }
     }
}
