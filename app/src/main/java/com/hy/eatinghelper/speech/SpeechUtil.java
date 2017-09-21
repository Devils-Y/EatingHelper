package com.hy.eatinghelper.speech;

import android.content.Context;
import android.media.AudioManager;
import android.util.Log;

import com.baidu.tts.auth.AuthInfo;
import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;

import static com.hy.eatinghelper.constants.Constants.API_KEY;
import static com.hy.eatinghelper.constants.Constants.APP_ID;
import static com.hy.eatinghelper.constants.Constants.SECRET_KEY;

/**
 * Created by huyin on 2017/9/17.
 */

public class SpeechUtil implements SpeechSynthesizerListener {

     public static final String TAG = SpeechUtil.class.getName();

     private SpeechSynthesizer speechSynthesizer;
     private Context context;

     public SpeechUtil(Context activity) {
          this.context = activity;
          init();
     }

     /**
      * 初始化合成相关组件
      */
     private void init() {
          speechSynthesizer = SpeechSynthesizer.getInstance();
          speechSynthesizer.setContext(context);
          speechSynthesizer.setSpeechSynthesizerListener(this);
          speechSynthesizer.libVersion();
          // 此处需要将setApiKey方法的两个参数替换为你在百度开发者中心注册应用所得到的apiKey和secretKey
          speechSynthesizer.setApiKey(API_KEY, SECRET_KEY);
          // 设置离线语音合成授权，需要填入从百度语音官网申请的app_id
          speechSynthesizer.setAppId(APP_ID);
          speechSynthesizer.setAudioStreamType(AudioManager.STREAM_MUSIC);
          setParams();
          AuthInfo authInfo = speechSynthesizer.auth(TtsMode.MIX);
          if (authInfo.isSuccess()) {
               speechSynthesizer.initTts(TtsMode.MIX);
          } else {
               // 授权失败
               Log.e(TAG, "-----auth error------");
          }
     }

     /**
      * 开始文本合成并朗读
      *
      * @param content
      */
     public void speak(final String content) {
          new Thread(new Runnable() {
               @Override
               public void run() {
                    setParams();
                    int ret = speechSynthesizer.speak(content.toString());
                    if (ret != 0) {
                         Log.e(TAG, "开始合成失败：" + ret);
                    }
               }
          }).start();
     }

     /**
      * 停止朗读
      */
     public void stop() {
          speechSynthesizer.stop();
     }

     public void release() {
          speechSynthesizer.release();
     }

     /**
      * 暂停文本朗读，如果没有调用speak(String)方法或者合成器初始化失败，该方法将无任何效果
      */
     public void pause() {
          speechSynthesizer.pause();
     }

     /**
      * 继续文本朗读，如果没有调用speak(String)方法或者合成器初始化失败，该方法将无任何效果
      */
     public void resume() {
          speechSynthesizer.resume();
     }

     /**
      * 为语音合成器设置相关参数
      */
     private void setParams() {
          speechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, "0");//发音人，目前支持女声(0)和男声(1)
          speechSynthesizer.setParam(SpeechSynthesizer.PARAM_VOLUME, "9");//音量，取值范围[0, 9]，数值越大，音量越大
          speechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEED, "5");//朗读语速，取值范围[0, 9]，数值越大，语速越快
          speechSynthesizer.setParam(SpeechSynthesizer.PARAM_PITCH, "5");//音调，取值范围[0, 9]，数值越大，音量越高
          speechSynthesizer.setParam(SpeechSynthesizer.PARAM_AUDIO_ENCODE,
                    SpeechSynthesizer.AUDIO_ENCODE_AMR);//音频格式，支持bv/amr/opus/mp3，取值详见随后常量声明
          speechSynthesizer.setParam(SpeechSynthesizer.PARAM_AUDIO_RATE,
                    SpeechSynthesizer.AUDIO_BITRATE_AMR_15K85);//音频比特率，各音频格式支持的比特率详见随后常量声明
     }


     @Override
     public void onSynthesizeStart(String s) {
          // 监听到合成开始，在此添加相关操作
     }

     @Override
     public void onSynthesizeDataArrived(String s, byte[] bytes, int i) {
          // 监听到有合成数据到达，在此添加相关操作
     }

     @Override
     public void onSynthesizeFinish(String s) {
          // 监听到合成结束，在此添加相关操作
     }

     @Override
     public void onSpeechStart(String s) {
          // 监听到合成并播放开始，在此添加相关操作
     }

     @Override
     public void onSpeechProgressChanged(String s, int i) {
          // 监听到播放进度有变化，在此添加相关操作
     }

     @Override
     public void onSpeechFinish(String s) {
          // 监听到播放结束，在此添加相关操作
     }

     @Override
     public void onError(String s, SpeechError speechError) {
          // 监听到出错，在此添加相关操作
          Log.e(TAG, "发生错误：" + speechError.description + "(" + speechError.code
                    + ")");
     }


}
