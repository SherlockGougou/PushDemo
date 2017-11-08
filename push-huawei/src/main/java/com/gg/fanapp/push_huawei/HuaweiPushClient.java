package com.gg.fanapp.push_huawei;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.gg.fanapp.push_core.cache.OnePushCache;
import com.gg.fanapp.push_core.core.IPushClient;
import com.huawei.android.pushagent.PushException;
import com.huawei.android.pushagent.PushManager;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/** Created by pyt on 2017/5/15. */
public class HuaweiPushClient implements IPushClient {

    private static final String TAG = "HuaweiPushClient";

    private Context mContext;
    private Map<String, String> tags;

    @Override
    public void initContext(Context context) {
        this.mContext = context.getApplicationContext();
    }

    @Override
    public void register() {
        PushManager.requestToken(mContext);
        PushManager.enableReceiveNotifyMsg(mContext, true);
        PushManager.enableReceiveNormalMsg(mContext, true);
        Log.d(TAG, "register: ");
    }

    @Override
    public void unRegister() {
        // 很奇怪的问题，就是在EMUI5.0上，就算调用取消注册token的方法，服务端任然能够通过token发送通知，fuck。
        // EMUI3.0 和EMUI4.0 没有手机测试不了
        String token = OnePushCache.getToken(mContext);
        if (!TextUtils.isEmpty(token)) {
            PushManager.deregisterToken(mContext, token);
            PushManager.enableReceiveNotifyMsg(mContext, false);
            PushManager.enableReceiveNormalMsg(mContext, false);
            PushManager.deleteTags(
                    mContext, Arrays.asList(PushManager.getTags(mContext).keySet().toArray()));
            OnePushCache.delToken(mContext);
        }
    }

    @Override
    public void bindAlias(String alias) {
        // hua wei push is not support bind account
    }

    @Override
    public void unBindAlias(String alias) {
        // hua wei push is not support unbind account
    }

    @Override
    public void addTag(String tag) {
        if (TextUtils.isEmpty(tag)) {
            return;
        }
        HashMap<String, String> tagsMap = new HashMap<>(1);
        tagsMap.put(tag, tag);
        try {
            PushManager.setTags(mContext, tagsMap);
        } catch (PushException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteTag(String tag) {
        if (TextUtils.isEmpty(tag)) {
            return;
        }
        try {
            PushManager.deleteTags(mContext, Collections.singletonList(tag));
        } catch (PushException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void resetTag() {
        getTagList();
        if (null != tags && !tags.isEmpty()) {
            for (Map.Entry<String, String> mapEntry : tags.entrySet()) {
                String value = mapEntry.getValue();
                deleteTag(value);
            }
        }
    }

    @Override
    public void getTagList() {
        try {
            this.tags = PushManager.getTags(mContext);
        } catch (PushException e) {
            e.printStackTrace();
            this.tags = null;
        }
    }
}
