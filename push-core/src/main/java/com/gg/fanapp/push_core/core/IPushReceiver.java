package com.gg.fanapp.push_core.core;

import android.content.Context;

import com.gg.fanapp.push_core.entity.OnePushCommand;
import com.gg.fanapp.push_core.entity.OnePushMsg;

/**
 * Created by pyt on 2017/3/23.
 */

public interface IPushReceiver {

    /**
     * 通知栏消息
     * When you received notice
     *
     * @param context
     * @param msg
     */
    void onReceiveNotification(Context context, OnePushMsg msg);

    /**
     * 点击事件
     * When you received the notice by clicking
     *
     * @param context
     * @param msg
     */
    void onReceiveNotificationClick(Context context, OnePushMsg msg);

    /**
     * 透传消息
     * When you received passthrough message
     *
     * @param context
     * @param msg
     */
    void onReceiveMessage(Context context, OnePushMsg msg);

    /**
     * When the client calls to execute the command, the callback
     * such as
     * @see IPushClient#addTag(String)
     * @see IPushClient#deleteTag(String)
     * @see IPushClient#bindAlias(String)
     * @see IPushClient#unBindAlias(String)
     * @see IPushClient#unRegister()
     * @see IPushClient#register()
     *
     * @param context
     * @param command
     */
    void onCommandResult(Context context, OnePushCommand command);

    /**
     * 收到友盟的推送
     * When you receive umeng notification message
     *
     * @param context
     * @param msg
     */
    void onReceiveUmengNotification(Context context, OnePushMsg msg);
}
