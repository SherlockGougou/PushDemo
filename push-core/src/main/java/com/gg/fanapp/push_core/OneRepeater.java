package com.gg.fanapp.push_core;

import android.content.Context;
import com.gg.fanapp.push_core.entity.PushCommand;
import com.gg.fanapp.push_core.entity.PushMsg;
import com.gg.fanapp.push_core.log.OneLog;
import com.gg.fanapp.push_core.receiver.OnePushAction;
import com.gg.fanapp.push_core.receiver.TransmitDataManager;
import java.util.Map;

/**
 * The message the repeater
 * Push platform message to forward a third party
 * Created by pyt on 2017/5/10.
 */

public class OneRepeater {

    private static final String TAG = "OneRepeater";

    /**
     * 命令消息
     * Repeater instructions operating results
     *
     * @param extraMsg 额外信息
     * @param error 错误信息
     * @see OnePush#TYPE_ADD_TAG
     * @see OnePush#TYPE_DEL_TAG
     * @see OnePush#TYPE_AND_OR_DEL_TAG
     * @see OnePush#TYPE_REGISTER
     * @see OnePush#TYPE_UNREGISTER
     * @see OnePush#TYPE_BIND_ALIAS
     * @see OnePush#TYPE_UNBIND_ALIAS
     * @see OnePush#RESULT_ERROR
     * @see OnePush#RESULT_OK
     */
    public static void transmitCommandResult(Context context, int type, int resultCode, String token, String extraMsg,
        String error) {
        transmit(context, new PushCommand(type, resultCode, token, extraMsg, error));
    }

    /**
     * 透传消息
     * Repeater passthrough message
     */
    public static void transmitMessage(Context context, String msg, String extraMsg, Map<String, String> keyValue) {
        transmit(context, OnePushAction.RECEIVE_MESSAGE,
            new PushMsg(0, null, null, msg, extraMsg, keyValue, System.currentTimeMillis()));
    }

    /**
     * 通知栏点击消息
     * Repeater the notification bar click event
     */
    public static void transmitNotificationClick(Context context, int notifyId, String title, String content,
        String extraMsg, Map<String, String> keyValue) {
        transmit(context, OnePushAction.RECEIVE_NOTIFICATION_CLICK,
            new PushMsg(notifyId, title, content, null, extraMsg, keyValue, System.currentTimeMillis()));
    }

    /**
     * 收到通知消息
     * Repeater notice
     */
    public static void transmitNotification(Context context, int notifyId, String title, String content,
        String extraMsg, Map<String, String> keyValue) {
        transmit(context, OnePushAction.RECEIVE_NOTIFICATION,
            new PushMsg(notifyId, title, content, null, extraMsg, keyValue, System.currentTimeMillis()));
    }

    /**
     * 收到友盟通知消息
     * Repeater umeng notice
     */
    public static void transmitUmengNotification(Context context, int notifyId, String title, String content,
        String extraMsg, Map<String, String> keyValue) {
        transmit(context, OnePushAction.RECEIVE_NOTIFICATION_UMENG,
            new PushMsg(notifyId, title, content, null, extraMsg, keyValue, System.currentTimeMillis()));
    }

    /**
     * The main method to repeater information
     */
    private static void transmit(Context context, PushCommand data) {
        OneLog.i(TAG, "收到Command：" + data.toString());
        TransmitDataManager.sendPushData(context, OnePushAction.RECEIVE_COMMAND_RESULT, data);
    }

    /**
     * The main method to repeater information
     */
    private static void transmit(Context context, String action, PushMsg data) {
        OneLog.i(TAG, "收到Push：" + data.toString());
        TransmitDataManager.sendPushData(context, action, data);
    }
}