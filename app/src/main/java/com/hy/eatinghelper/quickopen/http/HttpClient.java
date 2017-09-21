package com.hy.eatinghelper.quickopen.http;

import com.google.gson.Gson;
import com.hy.eatinghelper.BuildConfig;
import com.hy.eatinghelper.quickopen.http.gsonconverter.JsonConverterFactory;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

import static com.hy.eatinghelper.quickopen.http.API.BASE_URL;

/**
 * Created by huyin on 2017/8/7.
 */

public class HttpClient {
     private static Retrofit retrofit = null;

     public static Retrofit retrofit() {
          if (retrofit == null) {
               OkHttpClient.Builder builder = new OkHttpClient.Builder();

               builder.addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                         Request request = chain.request()
                                   .newBuilder()
                                   .addHeader("Content-Type", "application/json; charset=UTF-8")
                                   .addHeader("Connection", "keep-alive")
                                   .addHeader("Accept", "*/*")
                                   .addHeader("Access-Control-Allow-Origin", "*")
                                   .addHeader("Access-Control-Allow-Headers", "X-Requested-With")
                                   .addHeader("Vary", "Accept-Encoding")
                                   .build();
                         return chain.proceed(request);
                    }

               });
               /**
                * 设置Log信息拦截器
                */
               if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                    builder.addInterceptor(loggingInterceptor);
               }

               builder.connectTimeout(10, TimeUnit.SECONDS);
               builder.readTimeout(10, TimeUnit.SECONDS);
               builder.writeTimeout(10, TimeUnit.SECONDS);
               builder.retryOnConnectionFailure(true);

               OkHttpClient okHttpClient = builder.build();

               retrofit = new Retrofit.Builder()
                         .baseUrl(BASE_URL)
                         .client(okHttpClient)
                         .addConverterFactory(JsonConverterFactory.create(new Gson()))
                         .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                         .build();
          }
          return retrofit;
     }
}
