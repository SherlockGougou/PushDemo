package com.gg.fanapp.push_core.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.gg.fanapp.push_core.core.IPushReceiver;
import com.gg.fanapp.push_core.core.OnePushCode;
import com.gg.fanapp.push_core.entity.PushCommand;
import com.gg.fanapp.push_core.entity.PushMsg;

/**
 * Finally unified message push processing Receiver
 * Created by pyt on 2017/5/10.
 */

public abstract class BaseOnePushReceiver extends BroadcastReceiver implements IPushReceiver, OnePushCode {

    @Override public final void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (OnePushAction.RECEIVE_COMMAND_RESULT.equals(action)) {// 收到注册等相关的SDK初始化消息
            PushCommand onePushCommand = TransmitDataManager.parseCommandData(intent);
            onCommandResult(context, onePushCommand);
        } else if (OnePushAction.RECEIVE_NOTIFICATION.equals(action)) {// 收到通知栏消息（包含下面的友盟的通知栏消息）
            PushMsg onePushMsg = TransmitDataManager.parsePushData(intent);
            onReceiveNotification(context, onePushMsg);
        } else if (OnePushAction.RECEIVE_NOTIFICATION_CLICK.equals(action)) {// 收到通知栏点击事件
            PushMsg onePushMsg = TransmitDataManager.parsePushData(intent);
            onReceiveNotificationClick(context, onePushMsg);
        } else if (OnePushAction.RECEIVE_MESSAGE.equals(action)) {// 收到透传消息
            PushMsg onePushMsg = TransmitDataManager.parsePushData(intent);
            onReceiveMessage(context, onePushMsg);
        } else if (OnePushAction.RECEIVE_NOTIFICATION_UMENG.equals(action)) {// 收到友盟的通知栏消息
            PushMsg onePushMsg = TransmitDataManager.parsePushData(intent);
            onReceiveUmengNotification(context, onePushMsg);
        }
    }

    @Override public void onReceiveNotification(Context context, PushMsg msg) {
        //this is method is not always invoke,if you application is dead ,when you click
        //notification ,this method is not invoke,so don't do important things in this method
    }

    /**
     * 收到友盟的推送
     * When you receive umeng notification message
     */
    @Override public void onReceiveUmengNotification(Context context, PushMsg msg) {

    }
}