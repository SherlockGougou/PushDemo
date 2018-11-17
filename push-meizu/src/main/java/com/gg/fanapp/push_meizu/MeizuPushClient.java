package com.gg.fanapp.push_meizu;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.gg.fanapp.push_core.OnePush;
import com.gg.fanapp.push_core.OneRepeater;
import com.gg.fanapp.push_core.cache.OnePushCache;
import com.gg.fanapp.push_core.core.IPushClient;
import com.meizu.cloud.pushsdk.PushManager;

/**
 * Created by SherlockHolmes on 2017/8/24.
 */

public class MeizuPushClient implements IPushClient {

    public static final String MEIZU_PUSH_APP_ID = "MEIZU_PUSH_APP_ID";
    public static final String MEIZU_PUSH_APP_KEY = "MEIZU_PUSH_APP_KEY";
    private static String TAG = "MeizuPushClient";
    private String mAppId;
    private String mAppKey;
    private String mPushId;
    private Context mContext;

    @Override public void initContext(Context context) {
        this.mContext = context.getApplicationContext();
        //读取魅族对应的appId和appkey
        try {
            Bundle metaData = context.getPackageManager()
                .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA).metaData;
            mAppId = metaData.getString(MEIZU_PUSH_APP_ID);
            mAppKey = metaData.getString(MEIZU_PUSH_APP_KEY);
            Log.d(TAG, "initContext: mAppId mAppKey :" + mAppId + mAppKey);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override public void register() {
        if (TextUtils.isEmpty(mAppId) || TextUtils.isEmpty(mAppKey)) {
            throw new IllegalArgumentException("meizu push appId or appKey is not init,"
                + "check you AndroidManifest.xml is has MEIZU_PUSH_APP_ID or MEIZU_PUSH_APP_KEY meta-data flag please");
        }
        if (TextUtils.isEmpty(PushManager.getPushId(mContext))) {
            PushManager.register(mContext, mAppId, mAppKey);
        } else {
            Log.d(TAG, "register: ID:" + PushManager.getPushId(mContext));
        }
        this.mPushId = PushManager.getPushId(mContext);
        /**
         * 保存token 和 平台
         */
        OnePushCache.putToken(mContext, mPushId);
        OnePushCache.putPlatform(mContext, OnePushCache.PLATFORM_MEIZU);
        OneRepeater.transmitCommandResult(mContext, OnePush.TYPE_REGISTER, OnePush.RESULT_OK, mPushId, null, "success");

        PushManager.register(mContext, String.valueOf(mAppId), mAppKey);
        PushManager.switchPush(mContext, String.valueOf(mAppId), mAppKey, PushManager.getPushId(mContext), 1, true);
    }

    @Override public void unRegister() {
        String token = OnePushCache.getToken(mContext);
        if (!TextUtils.isEmpty(token)) {
            PushManager.switchPush(mContext, String.valueOf(mAppId), mAppKey, PushManager.getPushId(mContext), 1,
                false);
            PushManager.unRegister(mContext, String.valueOf(mAppId), mAppKey);
            OnePushCache.delToken(mContext);
        }
    }

    @Override public void bindAlias(String alias) {
        PushManager.subScribeAlias(mContext, mAppId, mAppKey, mPushId, alias);
    }

    @Override public void unBindAlias(String alias) {
        PushManager.unSubScribeAlias(mContext, mAppId, mAppKey, mPushId, alias);
    }

    @Override public void addTag(String tag) {
        PushManager.subScribeTags(mContext, mAppId, mAppKey, mPushId, tag);
    }

    @Override public void deleteTag(String tag) {
        PushManager.unSubScribeTags(mContext, mAppId, mAppKey, mPushId, tag);
    }

    @Override public void resetTag() {
        PushManager.unSubScribeAllTags(mContext, mAppId, mAppKey, mPushId);
    }

    @Override public void getTagList() {
        PushManager.checkSubScribeTags(mContext, mAppId, mAppKey, mPushId);
    }
}