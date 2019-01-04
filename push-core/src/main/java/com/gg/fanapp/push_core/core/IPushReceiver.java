package com.gg.fanapp.push_core.core;

import android.content.Context;
import com.gg.fanapp.push_core.entity.PushCommand;
import com.gg.fanapp.push_core.entity.PushMsg;

/**
 * Created by pyt on 2017/3/23.
 */

public interface IPushReceiver {

    /**
     * 通知栏消息
     * When you received notice
     */
    void onReceiveNotification(Context context, PushMsg msg);

    /**
     * 点击事件
     * When you received the notice by clicking
     */
    void onReceiveNotificationClick(Context context, PushMsg msg);

    /**
     * 透传消息
     * When you received passthrough message
     */
    void onReceiveMessage(Context context, PushMsg msg);

    /**
     * When the client calls to execute the command, the callback
     * such as
     *
     * @see IPushClient#addTag(String)
     * @see IPushClient#deleteTag(String)
     * @see IPushClient#bindAlias(String)
     * @see IPushClient#unBindAlias(String)
     * @see IPushClient#unRegister()
     * @see IPushClient#register()
     */
    void onCommandResult(Context context, PushCommand command);

    /**
     * 收到友盟的推送
     * When you receive umeng notification message
     */
    void onReceiveUmengNotification(Context context, PushMsg msg);
}