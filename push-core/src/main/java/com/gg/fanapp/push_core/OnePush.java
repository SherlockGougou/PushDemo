package com.gg.fanapp.push_core;

import com.gg.fanapp.push_core.core.OnePushCode;
import com.gg.fanapp.push_core.log.OneLog;

/**
 * message push access class
 * Created by pyt on 2017/5/9.
 */

public class OnePush implements OnePushCode {
    /**
     * current debug statue
     */
    public static void setDebug(boolean isDebug) {
        OneLog.setDebug(isDebug);
    }
}
