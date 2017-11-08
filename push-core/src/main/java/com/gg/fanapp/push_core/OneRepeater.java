package com.gg.fanapp.push_core;

import android.content.Context;
import android.os.Parcelable;
import android.util.Log;

import com.gg.fanapp.push_core.entity.OnePushCommand;
import com.gg.fanapp.push_core.entity.OnePushMsg;
import com.gg.fanapp.push_core.log.OneLog;
import com.gg.fanapp.push_core.receiver.OnePushAction;
import com.gg.fanapp.push_core.receiver.TransmitDataManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * The message the repeater
 * Push platform message to forward a third party
 * Created by pyt on 2017/5/10.
 */

public class OneRepeater {

    private static final String TAG = "OneRepeater";

    /**
     * Repeater instructions operating results
     * @param context
     *
     * @param type
     * @see OnePush#TYPE_ADD_TAG
     * @see OnePush#TYPE_DEL_TAG
     * @see OnePush#TYPE_AND_OR_DEL_TAG
     * @see OnePush#TYPE_REGISTER
     * @see OnePush#TYPE_UNREGISTER
     * @see OnePush#TYPE_BIND_ALIAS
     * @see OnePush#TYPE_UNBIND_ALIAS
     *
     * @param resultCode
     * @see OnePush#RESULT_ERROR
     * @see OnePush#RESULT_OK
     *
     * @param extraMsg 额外信息
     * @param error 错误信息
     */
    public static void transmitCommandResult(Context context, int type, int resultCode,String token
            , String extraMsg, String error){
        Log.d(TAG, "transmitCommandResult: " + token);
        transmit(context, OnePushAction.RECEIVE_COMMAND_RESULT
                , new OnePushCommand(type, resultCode, token, extraMsg, error));
    }

    /**
     * Repeater passthrough message
     * @param context
     * @param msg
     * @param extraMsg
     */
    public static void transmitMessage(Context context, String msg
            , String extraMsg, Map<String,String> keyValue){
        transmit(context, OnePushAction.RECEIVE_MESSAGE
                , new OnePushMsg(0, null, null, msg, extraMsg,keyValue));
    }

    /**
     * Repeater the notification bar click event
     * @param context
     * @param notifyId
     * @param title
     * @param content
     * @param extraMsg
     */
    public static void transmitNotificationClick(Context context,int notifyId,String title
            ,String content,String extraMsg,Map<String,String> keyValue){
        transmit(context, OnePushAction.RECEIVE_NOTIFICATION_CLICK
                , new OnePushMsg(notifyId, title, content, null, extraMsg,keyValue));
    }

    /**
     * Repeater notice
     *
     * @param context
     * @param notifyId
     * @param title
     * @param content
     * @param extraMsg
     */
    public static void transmitNotification(Context context, int notifyId, String title
            , String content, String extraMsg, Map<String, String> keyValue) {
        transmit(context, OnePushAction.RECEIVE_NOTIFICATION
                , new OnePushMsg(notifyId, title, content, null, extraMsg, keyValue));
    }

    /**
     * Repeater umeng notice
     *
     * @param context
     * @param notifyId
     * @param title
     * @param content
     * @param extraMsg
     */
    public static void transmitUmengNotification(Context context, int notifyId, String title
            , String content, String extraMsg, Map<String, String> keyValue) {
        transmit(context, OnePushAction.RECEIVE_NOTIFICATION_UMENG
                , new OnePushMsg(notifyId, title, content, null, extraMsg, keyValue));
    }

    /**
     * The main method to repeater information
     * @param context
     * @param action
     * @param data
     */
    private static void transmit(Context context, String action, Parcelable data) {
        OneLog.i(TAG, "transmit() called with: context = [" + context + "], action = [" + action + "], data = [" + data + "]");
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
