package com.gg.fanapp.push_core;

import android.content.Context;
import android.os.Parcelable;
import android.util.Log;
import com.gg.fanapp.push_core.entity.OnePushCommand;
import com.gg.fanapp.push_core.entity.OnePushMsg;
import com.gg.fanapp.push_core.log.OneLog;
import com.gg.fanapp.push_core.receiver.OnePushAction;
import com.gg.fanapp.push_core.receiver.TransmitDataManager;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

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
    public static void transmitCommandResult(Context context, int type, int resultCode,
        String token, String extraMsg, String error) {
        Log.d(TAG, "transmitCommandResult: " + token);
        transmit(context, OnePushAction.RECEIVE_COMMAND_RESULT,
            new OnePushCommand(type, resultCode, token, extraMsg, error));
    }

    /**
     * 透传消息
     * Repeater passthrough message
     */
    public static void transmitMessage(Context context, String msg, String extraMsg,
        Map<String, String> keyValue) {
        transmit(context, OnePushAction.RECEIVE_MESSAGE,
            new OnePushMsg(0, null, null, msg, extraMsg, keyValue, System.currentTimeMillis()));
    }

    /**
     * 通知栏点击消息
     * Repeater the notification bar click event
     */
    public static void transmitNotificationClick(Context context, int notifyId, String title,
        String content, String extraMsg, Map<String, String> keyValue) {
        transmit(context, OnePushAction.RECEIVE_NOTIFICATION_CLICK,
            new OnePushMsg(notifyId, title, content, null, extraMsg, keyValue,
                System.currentTimeMillis()));
    }

    /**
     * 收到通知消息
     * Repeater notice
     */
    public static void transmitNotification(Context context, int notifyId, String title,
        String content, String extraMsg, Map<String, String> keyValue) {
        transmit(context, OnePushAction.RECEIVE_NOTIFICATION,
            new OnePushMsg(notifyId, title, content, null, extraMsg, keyValue,
                System.currentTimeMillis()));
    }

    /**
     * 收到友盟通知消息
     * Repeater umeng notice
     */
    public static void transmitUmengNotification(Context context, int notifyId, String title,
        String content, String extraMsg, Map<String, String> keyValue) {
        transmit(context, OnePushAction.RECEIVE_NOTIFICATION_UMENG,
            new OnePushMsg(notifyId, title, content, null, extraMsg, keyValue,
                System.currentTimeMillis()));
    }

    /**
     * The main method to repeater information
     */
    private static void transmit(Context context, String action, Parcelable data) {
        OneLog.i(TAG, "transmit() called with: context = ["
            + context
            + "], action = ["
            + action
            + "], data = ["
            + data
            + "]");
        TransmitDataManager.sendPushData(context, action, data);
    }

    /**
     * 将json格式的字符串解析成Map对象 <li>
     * json格式：{"name":"admin","retries":"3fff","testname"
     * :"ddd","testretries":"fffffffff"}
     */
    public static HashMap<String, String> jsonToHashMap(String json) {
        HashMap<String, String> data = new HashMap<String, String>();
        // 将json字符串转换成jsonObject
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
            Iterator it = jsonObject.keys();
            // 遍历jsonObject数据，添加到Map对象
            while (it.hasNext()) {
                String key = String.valueOf(it.next());
                String value = (String) jsonObject.get(key);
                data.put(key, value);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }
}
