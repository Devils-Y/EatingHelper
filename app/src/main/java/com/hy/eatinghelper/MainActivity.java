package com.hy.eatinghelper;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

import com.hy.eatinghelper.accesstoken.AccessToken;
import com.hy.eatinghelper.accesstoken.AccessTokenPresenter;
import com.hy.eatinghelper.accesstoken.AccessTokenView;
import com.hy.eatinghelper.quickopen.base.BaseActivity;
import com.hy.eatinghelper.sharedpreference.EHSharedPreferences;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;

import static com.hy.eatinghelper.constants.Constants.ACCESSTOKEN_KEY;

public class MainActivity extends BaseActivity implements AccessTokenView {

     @BindView(R.id.toolbar)
     Toolbar toolbar;

     @BindString(R.string.app_name)
     String appName;

     AccessTokenPresenter accessTokenPresenter;
     ChatFragment chatFragment;

     @Override
     protected int layoutID() {
          return R.layout.activity_main;
     }

     @Override
     protected void initTitle() {
          toolbar.setTitle(appName);
          setSupportActionBar(toolbar);
          setImmerseLayout(toolbar);
     }

     @Override
     protected void initView() {
          chatFragment = new ChatFragment();
          if (!"".equals(EHSharedPreferences.ReadAccessToken(ACCESSTOKEN_KEY).getAccessToken())) {
               getSupportFragmentManager().beginTransaction().replace(R.id.container, chatFragment).commit();
          }
     }

     @Override
     protected void initData() {
          if ("".equals(EHSharedPreferences.ReadAccessToken(ACCESSTOKEN_KEY).getAccessToken())) {
               accessTokenPresenter = new AccessTokenPresenter(this);
               accessTokenPresenter.getAccessToken();
          }
     }

     @Override
     protected boolean isFull() {
          return false;
     }

     @Override
     protected void DestroyView() {

     }

     @Override
     public void setAccessToken(AccessToken accessToken) {
          EHSharedPreferences.WriteInfo(ACCESSTOKEN_KEY, accessToken);
          getSupportFragmentManager().beginTransaction().replace(R.id.container, chatFragment).commit();
     }

     @Override
     public void showOther() {

     }

     @Override
     public void showNotConnected() {

     }

     @Override
     protected void onActivityResult(int requestCode, int resultCode, Intent data) {
          super.onActivityResult(requestCode, resultCode, data);
          handleResult(chatFragment, requestCode, resultCode, data);
     }

     /**
      * 递归调用，对所有的子Fragment生效
      *
      * @param fragment
      * @param requestCode
      * @param resultCode
      * @param data
      */
     private void handleResult(Fragment fragment, int requestCode, int resultCode, Intent data) {
          fragment.onActivityResult(requestCode, resultCode, data);//调用每个Fragment的onActivityResult
          List<Fragment> childFragment = fragment.getChildFragmentManager().getFragments(); //找到第二层Fragment
          if (childFragment != null)
               for (Fragment f : childFragment)
                    if (f != null) {
                         handleResult(f, requestCode, resultCode, data);
                    }
     }
}
