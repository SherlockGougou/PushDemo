package com.gg.fanapp.push_core.receiver;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import com.gg.fanapp.push_core.entity.PushCommand;
import com.gg.fanapp.push_core.entity.PushMsg;

/**
 * Forwarding message management class
 * Created by pyt on 2017/5/12.
 */

public class TransmitDataManager {

    //The inside of the Intent of push message tag
    public static final String INTENT_DATA_PUSH_COMMAND = "one_push_data_command";
    public static final String INTENT_DATA_PUSH_MESSAGE = "one_push_data_message";

    /**
     * Send push data (through radio form to forward)
     */
    @SuppressLint("WrongConstant") public static void sendPushData(Context context, String action, PushCommand data) {
        Intent intent = new Intent(action);
        intent.putExtra(INTENT_DATA_PUSH_COMMAND, data);
        intent.addCategory(context.getPackageName());
        intent.addFlags(0x01000000);
        context.getApplicationContext().sendBroadcast(intent);
    }

    /**
     * Send push data (through radio form to forward)
     */
    @SuppressLint("WrongConstant") public static void sendPushData(Context context, String action, PushMsg data) {
        Intent intent = new Intent(action);
        intent.putExtra(INTENT_DATA_PUSH_MESSAGE, data);
        intent.addCategory(context.getPackageName());
        intent.addFlags(0x01000000);
        context.getApplicationContext().sendBroadcast(intent);
    }

    /**
     * Analytical push message data from the Intent
     */
    public static PushCommand parseCommandData(Intent intent) {
        return (PushCommand) intent.getSerializableExtra(INTENT_DATA_PUSH_COMMAND);
    }

    /**
     * Analytical push message data from the Intent
     */
    public static PushMsg parsePushData(Intent intent) {
        return (PushMsg) intent.getSerializableExtra(INTENT_DATA_PUSH_MESSAGE);
    }
}