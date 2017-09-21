package com.hy.eatinghelper;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;

import com.hy.eatinghelper.quickopen.base.BaseSwipeActivity;

import butterknife.BindView;

/**
 * Created by huyin on 2017/9/20.
 */

public class NuomiActivity extends BaseSwipeActivity{


     @BindView(R.id.toolbar)
     Toolbar toolbar;
     @BindView(R.id.webView)
     WebView webView;

     @Override
     protected int layoutID() {
          return R.layout.activity_nuomi;
     }

     @Override
     protected void initTitle() {
          toolbar.setTitle("糯米");
          setSupportActionBar(toolbar);
          toolbar.setNavigationOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                    finish();
               }
          });
          getSupportActionBar().setDisplayHomeAsUpEnabled(true);
          setImmerseLayout(toolbar);
     }

     @Override
     protected void initView() {
          webView.loadUrl("https://sh.nuomi.com/");
     }

     @Override
     protected void initData() {

     }

     @Override
     protected void DestroyView() {

     }
}
