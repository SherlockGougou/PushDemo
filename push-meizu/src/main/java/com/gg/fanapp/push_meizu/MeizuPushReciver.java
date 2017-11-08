package com.gg.fanapp.push_meizu;

import android.content.Context;
import android.util.Log;

import com.gg.fanapp.push_core.OnePush;
import com.gg.fanapp.push_core.OneRepeater;
import com.gg.fanapp.push_core.cache.OnePushCache;
import com.gg.fanapp.push_core.log.OneLog;
import com.meizu.cloud.pushsdk.MzPushMessageReceiver;
import com.meizu.cloud.pushsdk.notification.PushNotificationBuilder;
import com.meizu.cloud.pushsdk.platform.message.PushSwitchStatus;
import com.meizu.cloud.pushsdk.platform.message.RegisterStatus;
import com.meizu.cloud.pushsdk.platform.message.SubAliasStatus;
import com.meizu.cloud.pushsdk.platform.message.SubTagsStatus;
import com.meizu.cloud.pushsdk.platform.message.UnRegisterStatus;

import java.util.Map;

/**
 * Created by SherlockHolmes on 2017/8/24.
 */

public class MeizuPushReciver extends MzPushMessageReceiver {

    private static final String TAG = "MeizuPushReciver";

    private String mRegId;
    private String mTopic;
    private String mAlias;
    private String mAccount;
    private String mStartTime;
    private String mEndTime;

    @Override
    @Deprecated
    public void onRegister(Context context, String pushid) {
        //注册成功返回的pushid（即设备ID
        /**
         * 保存token 和 平台
         */
        OnePushCache.putToken(context, pushid);
        OnePushCache.putPlatform(context, OnePushCache.PLATFORM_MEIZU);
        OneRepeater.transmitCommandResult(context, OnePush.TYPE_REGISTER, OnePush.RESULT_OK, pushid, null, "success");
    }

    @Override
    public void onRegisterStatus(Context context, RegisterStatus registerStatus) {
        Log.i(TAG, "onRegisterStatus " + registerStatus);
        //新版订阅回调
    }

    @Override
    public void onUnRegisterStatus(Context context, UnRegisterStatus unRegisterStatus) {
        Log.i(TAG, "onUnRegisterStatus " + unRegisterStatus);
        //新版反订阅回调
    }

    @Override
    public void onMessage(Context context, String s) {
        //接收服务器推送的消息
    }

    @Override
    @Deprecated
    public void onUnRegister(Context context, boolean b) {
        //调用PushManager.unRegister(context）方法后，会在此回调反注册状态
    }

    @Override
    public void onPushStatus(Context context, PushSwitchStatus pushSwitchStatus) {
        //检查通知栏和透传消息开关状态回调
        Log.d(TAG, "onPushStatus: " + pushSwitchStatus);
    }

    @Override
    public void onSubTagsStatus(Context context, SubTagsStatus subTagsStatus) {
        Log.i(TAG, "onSubTagsStatus " + subTagsStatus);
        //标签回调
    }

    @Override
    public void onSubAliasStatus(Context context, SubAliasStatus subAliasStatus) {
        Log.i(TAG, "onSubAliasStatus " + subAliasStatus);
        //别名回调
    }


    @Override
    public void onUpdateNotificationBuilder(PushNotificationBuilder pushNotificationBuilder) {
        // 设置大图标和状态栏小图标
        pushNotificationBuilder.setmLargIcon(R.drawable.flyme_status_ic_notification);
        pushNotificationBuilder.setmStatusbarIcon(R.drawable.mz_push_notification_small_icon);
    }

    @Override
    public void onNotificationArrived(Context context, String title, String content, String selfDefineContentString) {
        //通知栏消息到达回调
        Log.i(TAG, "onNotificationArrived title " + title + "content " + content + " selfDefineContentString " + selfDefineContentString);
    }

    @Override
    public void onNotificationClicked(Context context, String title, String content, String selfDefineContentString) {
        //通知栏消息点击回调
        Log.i(TAG, "onNotificationClicked title " + title + "content " + content + " selfDefineContentString " + selfDefineContentString);
        OneLog.i(TAG, "onNotificationMessageClicked() called with: context = [" + context + "], " +
                "title = [" + title + "], content = [" + content + "], selfDefineContentString = [" + selfDefineContentString + "]");

        Map map = OneRepeater.jsonToHashMap(selfDefineContentString);
        Log.d(TAG, "onNotificationClicked: " + map.toString());
        OneRepeater.transmitNotificationClick(context, 0, title, content, content, map);
    }

    @Override
    public void onNotificationDeleted(Context context, String title, String content, String selfDefineContentString) {
        //通知栏消息删除回调；flyme6以上不再回调
        Log.i(TAG, "onNotificationDeleted title " + title + "content " + content + " selfDefineContentString " + selfDefineContentString);
    }

}
