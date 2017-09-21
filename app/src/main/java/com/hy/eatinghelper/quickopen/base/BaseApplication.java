package com.hy.eatinghelper.quickopen.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by huyin on 2017/8/9.
 */

public class BaseApplication extends Application{
     private static BaseApplication myApplication = null;

     public BaseApplication() {
     }

     public static BaseApplication getIntances() {
          if (myApplication == null) {
               synchronized (BaseApplication.class){
                    if(myApplication == null){
                         myApplication = new BaseApplication();
                    }
               }
          }
          return myApplication;
     }

     private static Context context;


     public static Context getContext() {
          return context;
     }

     @Override
     public void onCreate() {
          super.onCreate();
          context = getApplicationContext();
     }
}
