package com.gg.fanapp.push_core.receiver;

/**
 * Record all news push related Action
 * Created by pyt on 2017/5/12.
 */
public interface OnePushAction {
    String RECEIVE_NOTIFICATION = "com.fan.app.ACTION_RECEIVE_NOTIFICATION";
    String RECEIVE_NOTIFICATION_CLICK = "com.fan.app.ACTION_RECEIVE_NOTIFICATION_CLICK";
    String RECEIVE_MESSAGE = "com.fan.app.ACTION_RECEIVE_MESSAGE";
    String RECEIVE_COMMAND_RESULT = "com.fan.app.ACTION_RECEIVE_COMMAND_RESULT";
}
