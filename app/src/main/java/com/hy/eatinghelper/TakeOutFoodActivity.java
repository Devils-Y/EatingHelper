package com.hy.eatinghelper;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;

import com.hy.eatinghelper.quickopen.base.BaseSwipeActivity;

import butterknife.BindView;

public class TakeOutFoodActivity extends BaseSwipeActivity {

     @BindView(R.id.toolbar)
     Toolbar toolbar;
     @BindView(R.id.webView)
     WebView webView;


     @Override
     protected int layoutID() {
          return R.layout.activity_take_out_food;
     }

     @Override
     protected void initTitle() {
          toolbar.setTitle("外卖");
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
          webView.loadUrl("http://waimai.baidu.com/mobile/waimai?qt=confirmcity");
     }

     @Override
     protected void initData() {

     }

     @Override
     protected void DestroyView() {

     }
}
