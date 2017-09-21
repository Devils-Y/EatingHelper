package com.hy.eatinghelper;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.baidu.aip.chatkit.ImageLoader;
import com.baidu.aip.chatkit.message.MessageInput;
import com.baidu.aip.chatkit.message.MessagesList;
import com.baidu.aip.chatkit.message.MessagesListAdapter;
import com.baidu.aip.chatkit.model.Message;
import com.baidu.aip.chatkit.model.User;
import com.hy.eatinghelper.accesstoken.AccessToken;
import com.hy.eatinghelper.quickopen.base.BaseFragment;
import com.hy.eatinghelper.quickopen.utils.toast.ToastUtils;
import com.hy.eatinghelper.sharedpreference.EHSharedPreferences;
import com.hy.eatinghelper.speech.SpeechUtil;
import com.hy.eatinghelper.unit.Unit;
import com.hy.eatinghelper.unit.UnitPresenter;
import com.hy.eatinghelper.unit.UnitView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

import static com.hy.eatinghelper.constants.Constants.ACCESSTOKEN_KEY;
import static com.hy.eatinghelper.constants.Constants.SCENE_Id;

/**
 * Created by huyin on 2017/9/20.
 */

public class ChatFragment extends BaseFragment implements
          MessageInput.InputListener, UnitView, MessagesListAdapter.OnLoadMoreListener,
          MessageInput.VoiceInputListener {

     @BindView(R.id.messagesList)
     MessagesList messagesList;
     @BindView(R.id.input)
     MessageInput input;

     UnitPresenter unitPresenter;

     private User sender;
     private User cs;
     private int id = 0;

     AccessToken accessToken = EHSharedPreferences.ReadAccessToken(ACCESSTOKEN_KEY);

     protected ImageLoader imageLoader;
     protected MessagesListAdapter<Message> messagesAdapter;
     private String sessionId = "";

          //语音识别
//     private VoiceRecognizer voiceRecognizer;

     //语音合成
     private SpeechUtil speechUtil;

     @Override
     protected int layoutId() {
          return R.layout.fragment_chat;
     }

     @Override
     protected void initView() {
          input.setInputListener(this);
          input.setAudioInputListener(this);
//          voiceRecognizer = new VoiceRecognizer();
//          voiceRecognizer.init(getActivity(), input.getVoiceInputButton());
//          voiceRecognizer.setVoiceRecognizerCallback(new VoiceRecognizeCallback() {
//               @Override
//               public void callback(String text) {
//                    Message message = new Message(String.valueOf(id++), sender, text);
//                    messagesAdapter.addToStart(message, true);
//
//                    sendMessage(message);
//               }
//          });
          input.getInputEditText().setOnEditorActionListener(new TextView.OnEditorActionListener() {
               @Override
               public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEND) {
                         onSubmit(v.getEditableText());
                         v.setText("");
                    }
                    return true;
               }
          });
          //语音说话功能初始化
          speechUtil = new SpeechUtil(getActivity());
     }

     @Override
     protected void initData() {
          sender = new User("0", "me", "", true);
          cs = new User("1", "helper", "", true);
          initAdapter();
          unitPresenter = new UnitPresenter(this);
     }

     @Override
     protected void DestroyView() {

     }

     @Override
     public void setUnit(Unit unit) {
          if (unit != null) {
               sessionId = unit.getSession_id();

               //  如果有对于的动作action，请执行相应的逻辑
               for (Unit.ActionListBean action : unit.getAction_list()) {

                    if (!TextUtils.isEmpty(action.getSay())) {
                         StringBuilder sb = new StringBuilder();
                         sb.append(action.getSay());
                         //开始说话
                         speechUtil.speak(action.getSay().toString());

                         Message message = new Message(String.valueOf(id++), cs, sb.toString(), new Date());
                         messagesAdapter.addToStart(message, true);
                         List<String> strings = new ArrayList<>();
                         if (action.getHintList().size() > 0) {
                              for (int i = 0; i < action.getHintList().size(); i++) {
                                   strings.add(action.getHintList().get(i).getHintQuery());
                              }
                              message.setHintList(strings);
                         }
                    }

                    /**
                     * 执行查询到外卖
                     */
                    if ("user_take_out_foot_satisfy".equals(action.getActionId())) {
                         Message message = new Message(String.valueOf(id++), cs,
                                   Html.fromHtml("http://waimai.baidu.com/mobile/waimai?qt=confirmcity").toString(), new Date());
                         messagesAdapter.addToStart(message, true);
                         messagesAdapter.setOnMessageClickListener(new MessagesListAdapter.OnMessageClickListener<Message>() {
                              @Override
                              public void onMessageClick(Message message) {
                                   startActivity(TakeOutFoodActivity.class);
                              }
                         });
                    } else if ("".equals(action.getActionId())) {
                         Log.e("TAG", "----执行查询满足要求的餐厅----");
                         Message message = new Message(String.valueOf(id++), cs,
                                   Html.fromHtml("https://sh.nuomi.com/").toString(), new Date());
                         messagesAdapter.addToStart(message, true);
                         messagesAdapter.setOnMessageClickListener(new MessagesListAdapter.OnMessageClickListener<Message>() {
                              @Override
                              public void onMessageClick(Message message) {
                                   startActivity(NuomiActivity.class);
                              }
                         });
                    } else if ("user_cate_nagivation_satisfy".equals(action.getActionId())) {
                         Log.e("TAG", "----执行导航----");
                         Message message = new Message(String.valueOf(id++), cs,"开始导航", new Date());
                         messagesAdapter.addToStart(message, true);
                         messagesAdapter.setOnMessageClickListener(new MessagesListAdapter.OnMessageClickListener<Message>() {
                              @Override
                              public void onMessageClick(Message message) {
                                   if(isAppInstalled(getActivity(), "com.baidu.BaiduMap")){
                                        Intent i1 = new Intent();
                                        i1.setData(Uri.parse("baidumap://map?"));
                                        startActivity(i1);
                                   }else{
                                        ToastUtils.toast("请先下载百度地图");
                                   }
                              }
                         });
                    }
               }
          }
     }

     public boolean isAppInstalled(Context context, String packageName) {
          final PackageManager packageManager = context.getPackageManager();
          List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
          List<String> pName = new ArrayList<String>();
          if (pinfo != null) {
               for (int i = 0; i < pinfo.size(); i++) {
                    String pn = pinfo.get(i).packageName;
                    pName.add(pn);
               }
          }
          return pName.contains(packageName);
     }

     @Override
     public void showOther() {

     }

     @Override
     public void showNotConnected() {

     }


     @Override
     public boolean onSubmit(CharSequence input) {
          Message message = new Message(String.valueOf(id++), sender, input.toString(), new Date());
          messagesAdapter.addToStart(message, true);
          sendMessage(message);
          return true;
     }

     private void sendMessage(Message message) {
          if ("".equals(accessToken.getAccessToken())) {
               accessToken = EHSharedPreferences.ReadAccessToken(ACCESSTOKEN_KEY);
          }
          unitPresenter.getUnit(accessToken.getAccessToken(), SCENE_Id, message.getText(), sessionId);
     }

     private void initAdapter() {
          messagesAdapter = new MessagesListAdapter<>(sender.getId(), imageLoader);
          // messagesAdapter.enableSelectionMode(this);
          messagesAdapter.setLoadMoreListener(this);
          messagesAdapter.setOnHintClickListener(new MessagesListAdapter.OnHintClickListener<Message>() {
               @Override
               public void onHintClick(String hint) {
                    Message message = new Message(String.valueOf(id++), sender, hint, new Date());
                    sendMessage(message);
                    messagesAdapter.addToStart(message, true);
               }
          });
          messagesAdapter.registerViewClickListener(R.id.messageUserAvatar,
                    new MessagesListAdapter.OnMessageViewClickListener<Message>() {
                         @Override
                         public void onMessageViewClick(View view, Message message) {
//                              ToastUtils.toast(getActivity(),
//                                        message.getUser().getName() + " avatar click");
                         }
                    });
          this.messagesList.setAdapter(messagesAdapter);

     }

     @Override
     public void onLoadMore(int page, int totalItemsCount) {

     }

     @Override
     public void onVoiceInputClick() {
          if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO) != PackageManager
                    .PERMISSION_GRANTED) {
               ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.RECORD_AUDIO}, 100);
               return;
          }

//          voiceRecognizer.onClick();
     }

     @Override
     public void onActivityResult(int requestCode, int resultCode, Intent data) {
          super.onActivityResult(requestCode, resultCode, data);
//          voiceRecognizer.onActivityResult(requestCode, resultCode, data);
     }

     @Override
     public void onDestroyView() {
          super.onDestroyView();
//          voiceRecognizer.onDestroy();
     }

     @Override
     public void onPause() {
          super.onPause();
          speechUtil.pause();
     }

     @Override
     public void onStop() {
          super.onStop();
          speechUtil.stop();
     }

     @Override
     public void onResume() {
          super.onResume();
          speechUtil.resume();
     }
}
