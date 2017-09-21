package com.hy.eatinghelper.quickopen.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by huyin on 2017/8/7.
 */

public class UiControlUtils {
     public static void hideSystemUi(Activity activity) {
          activity.getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                              | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                              | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                              | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                              | View.SYSTEM_UI_FLAG_FULLSCREEN
                              | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
     }

     public static void hideKeyboard(Context context, View et) {
          InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
          imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
     }
}
