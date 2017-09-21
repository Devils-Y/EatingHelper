package com.hy.eatinghelper.accesstoken;

import com.hy.eatinghelper.quickopen.http.API;
import com.hy.eatinghelper.quickopen.http.HttpClient;
import com.hy.eatinghelper.quickopen.http.HttpListener;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.hy.eatinghelper.constants.Constants.API_KEY;
import static com.hy.eatinghelper.constants.Constants.SECRET_KEY;

/**
 * Created by huyin on 2017/9/10.
 */

public class AccessTokenModel {

     HttpListener<AccessToken> mAccessTokenHttpListener;


     public void getAccessToken(HttpListener<AccessToken> accessTokenHttpListener) {
          this.mAccessTokenHttpListener = accessTokenHttpListener;
          HttpClient.retrofit().create(API.AccessTokenService.class)
                    .getAccessToken("client_credentials", API_KEY, SECRET_KEY)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<AccessToken>() {
                         @Override
                         public void onSubscribe(Subscription s) {
                              s.request(Long.MAX_VALUE);
                         }

                         @Override
                         public void onNext(AccessToken accessToken) {
                              mAccessTokenHttpListener.onSuccess(accessToken);
                         }

                         @Override
                         public void onError(Throwable t) {
                              mAccessTokenHttpListener.onError();
                         }

                         @Override
                         public void onComplete() {
                         }
                    });
     }

}
