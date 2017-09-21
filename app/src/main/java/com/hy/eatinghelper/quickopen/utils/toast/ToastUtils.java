package com.hy.eatinghelper.quickopen.utils.toast;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.hy.eatinghelper.quickopen.base.BaseApplication;

/**
 * 吐司工具类
 */
public class ToastUtils {

     public static void toast(final String content) {
          new Handler(Looper.getMainLooper()).post(new Runnable() {

               @Override
               public void run() {
                    CustomToast.showToast(BaseApplication.getContext(), content, 1000);
               }
          });
     }

     public static void toast(final int resID) {
          new Handler(Looper.getMainLooper()).post(new Runnable() {

               @Override
               public void run() {
                    CustomToast.showToast(BaseApplication.getContext(), resID, 1000);
               }
          });
     }

     public static void toast(final Context context, final String content) {
          new Handler(Looper.getMainLooper()).post(new Runnable() {

               @Override
               public void run() {
                    CustomToast.showToast(context, content, 1000);
               }
          });
     }

     public static void toast(final Context context, final int resID) {
          new Handler(Looper.getMainLooper()).post(new Runnable() {

               @Override
               public void run() {
                    CustomToast.showToast(context, resID, 1000);
               }
          });
     }


     public static void longToast(final Context context, final String content) {
          new Handler(Looper.getMainLooper()).post(new Runnable() {

               @Override
               public void run() {
                    CustomToast.showToast(context, content, 3000);
               }
          });
     }

     public static void longToast(final Context context, final int resID) {
          new Handler(Looper.getMainLooper()).post(new Runnable() {

               @Override
               public void run() {
                    CustomToast.showToast(context, resID, 3000);
               }
          });
     }

}
