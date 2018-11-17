package com.gg.fanapp.push_core.cache;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * 保存消息推送注册的时候，返回的唯一标示
 * Created by pyt on 2017/5/16.
 */

public class OnePushCache {

    private static final String FILE_ONE_PUSH_CACHE = "share_name";
    private static final String KEY_TOKEN = "one_push_token";
    private static final String KEY_PLATFORM = "one_push_platform";
    private static final String TAG = "OnePushCache";
    public static String PLATFORM_HUAWEI = "huawei";
    public static String PLATFORM_XIAOMI = "xiaomi";
    public static String PLATFORM_MEIZU = "meizu";
    public static String PLATFORM_OTHER = "other";

    public static void putToken(Context context, String token) {
        Log.d(TAG, "putToken: " + token);
        getSharedPreferences(context).edit().putString(KEY_TOKEN, token).commit();
    }

    public static String getToken(Context context) {
        Log.d(TAG, "getToken: " + getSharedPreferences(context).getString(KEY_TOKEN, null));
        return getSharedPreferences(context).getString(KEY_TOKEN, null);
    }

    public static void delToken(Context context) {
        getSharedPreferences(context).edit().remove(KEY_TOKEN).commit();
    }

    public static void putPlatform(Context context, String platform) {
        Log.d(TAG, "putPlatform: " + platform);
        getSharedPreferences(context).edit().putString(KEY_PLATFORM, platform).commit();
    }

    public static String getPlatform(Context context) {
        Log.d(TAG, "getPlatform: " + getSharedPreferences(context).getString(KEY_PLATFORM, null));
        return getSharedPreferences(context).getString(KEY_PLATFORM, null);
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(FILE_ONE_PUSH_CACHE, Context.MODE_PRIVATE);
    }

    public static void delPlatform(Context context) {
        getSharedPreferences(context).edit().remove(KEY_PLATFORM).commit();
    }
}
