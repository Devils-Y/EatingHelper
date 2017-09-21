package com.hy.eatinghelper.quickopen.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by huyin on 2017/8/3.
 */

public class ScreenUtils {
     /**
      * 用于获取状态栏的高度。 使用Resource对象获取（推荐这种方式）
      *
      * @return 返回状态栏高度的像素值。
      */
     public static int getStatusBarHeight(Context context) {
          int result = 0;
          int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen",
                    "android");
          if (resourceId > 0) {
               result = context.getResources().getDimensionPixelSize(resourceId);
          }
          return result;
     }

     /**
      * 获得屏幕高度
      *
      * @param context
      * @return
      */
     public static int getScreenWidth(Context context)
     {
          WindowManager wm = (WindowManager) context
                    .getSystemService(Context.WINDOW_SERVICE);
          DisplayMetrics outMetrics = new DisplayMetrics();
          wm.getDefaultDisplay().getMetrics(outMetrics);
          return outMetrics.widthPixels;
     }
}
