package com.gg.fanapp.push_umeng;

import android.app.Notification;
import android.content.Context;
import android.util.Log;
import com.gg.fanapp.push_core.OneRepeater;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.entity.UMessage;

/**
 * Created by pyt on 2017/6/12.
 */
public class OnePushMessageHandler extends UmengMessageHandler {

    @Override public void dealWithCustomMessage(Context context, UMessage uMessage) {
        Log.d("dealWithCustomMessage", "--->dealWithCustomMessage");
        OneRepeater.transmitMessage(context, uMessage.custom, null, uMessage.extra);
    }

    @Override public Notification getNotification(Context context, UMessage uMessage) {
        Log.d("getNotification", "uMessage: " + uMessage.extra.toString());
        OneRepeater.transmitUmengNotification(context, 0, uMessage.title, uMessage.custom, uMessage.extra.toString(),
            uMessage.extra);
        return super.getNotification(context, uMessage);
    }
}