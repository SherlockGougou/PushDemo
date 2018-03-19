package com.gg.fanapp.push_umeng;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.gg.fanapp.push_core.OnePush;
import com.gg.fanapp.push_core.OneRepeater;
import com.gg.fanapp.push_core.cache.OnePushCache;
import com.gg.fanapp.push_core.core.IPushClient;
import com.umeng.message.IUmengCallback;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.common.inter.ITagManager;
import com.umeng.message.tag.TagManager;
import java.util.List;

/**
 * umeng push client Created by pyt on 2017/6/12.
 */
public class UMengPushClient implements IPushClient {

    public static final String FAN_PUSH_ALIAS = "FAN_PUSH";
    public static String deviceToken;
    private Application app;
    private Context context;
    private PushAgent mPushAgent;

    @Override public void initContext(Context context) {
        if (context instanceof Application) {
            // app添加的兼容的属性
            this.app = (Application) context;
            this.context = context.getApplicationContext();

            mPushAgent = PushAgent.getInstance(context);
            mPushAgent.setNotificationClickHandler(new OnePushNotificationClickHandler());
            mPushAgent.setMessageHandler(new OnePushMessageHandler());
            mPushAgent.setDisplayNotificationNumber(0);
        } else {
            throw new IllegalArgumentException("UMengPush must init by Application,you can call OnePush.init() method "
                + ""
                + "at Application onCreate that you custom application class!");
        }
    }

    @Override public void register() {
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override public void onSuccess(String deviceToken) {
                // 注册成功会返回device token
                Log.i("deviceToken registerID", deviceToken);
                UMengPushClient.deviceToken = deviceToken;
                // 注册成功会返回device token
                /** 保存token 和 平台 */
                OnePushCache.putToken(context, deviceToken);
                OnePushCache.putPlatform(context, OnePushCache.PLATFORM_OTHER);
                OneRepeater.transmitCommandResult(context, OnePush.TYPE_REGISTER, OnePush.RESULT_OK, deviceToken, null,
                    "success");
            }

            @Override public void onFailure(String s, String s1) {
                Log.i("registerID注册失败", s + "    " + s1);
                OneRepeater.transmitCommandResult(context, OnePush.TYPE_REGISTER, OnePush.RESULT_ERROR, null, s, s1);
            }
        });
    }

    @Override public void unRegister() {
        mPushAgent.disable(new IUmengCallback() {
            @Override public void onSuccess() {
                String token = OnePushCache.getToken(context);
                if (!TextUtils.isEmpty(token)) {
                    OnePushCache.delToken(context);
                }
                OneRepeater.transmitCommandResult(UMengPushClient.this.context, OnePush.TYPE_UNREGISTER,
                    OnePush.RESULT_OK, deviceToken, null, null);
            }

            @Override public void onFailure(String s, String s1) {
                OneRepeater.transmitCommandResult(UMengPushClient.this.context, OnePush.TYPE_UNREGISTER,
                    OnePush.RESULT_ERROR, deviceToken, null, null);
            }
        });
    }

    @Override public void bindAlias(final String alias) {
        mPushAgent.addAlias(alias, FAN_PUSH_ALIAS, new UTrack.ICallBack() {
            @Override public void onMessage(boolean isSuccess, String message) {
                OneRepeater.transmitCommandResult(UMengPushClient.this.context, OnePush.TYPE_BIND_ALIAS,
                    isSuccess ? OnePush.RESULT_OK : OnePush.RESULT_ERROR, null, alias, message);
            }
        });
    }

    @Override public void unBindAlias(final String alias) {
        mPushAgent.deleteAlias(alias, FAN_PUSH_ALIAS, new UTrack.ICallBack() {
            @Override public void onMessage(boolean isSuccess, String message) {
                OneRepeater.transmitCommandResult(UMengPushClient.this.context, OnePush.TYPE_UNBIND_ALIAS,
                    isSuccess ? OnePush.RESULT_OK : OnePush.RESULT_ERROR, null, alias, message);
            }
        });
    }

    @Override public void addTag(final String tag) {
        mPushAgent.getTagManager().addTags(new TagManager.TCallBack() {
            @Override public void onMessage(final boolean isSuccess, final ITagManager.Result result) {
                // isSuccess表示操作是否成功
                OneRepeater.transmitCommandResult(UMengPushClient.this.context, OnePush.TYPE_ADD_TAG,
                    isSuccess ? OnePush.RESULT_OK : OnePush.RESULT_ERROR, null, tag,
                    result == null ? "" : result.errors);
            }
        }, tag);
    }

    @Override public void deleteTag(final String tag) {
        mPushAgent.getTagManager().deleteTags(new TagManager.TCallBack() {
            @Override public void onMessage(final boolean isSuccess, final ITagManager.Result result) {
                // isSuccess表示操作是否成功
                OneRepeater.transmitCommandResult(UMengPushClient.this.context, OnePush.TYPE_DEL_TAG,
                    isSuccess ? OnePush.RESULT_OK : OnePush.RESULT_ERROR, null, tag,
                    result == null ? "" : result.errors);
            }
        }, tag);
    }

    @Override public void resetTag() {
        mPushAgent.getTagManager().getTags(new TagManager.TagListCallBack() {
            @Override public void onMessage(boolean isSuccess, List<String> result) {
                for (String tag : result) {
                    deleteTag(tag);
                }
            }
        });
    }

    @Override public void getTagList() {
        mPushAgent.getTagManager().getTags(new TagManager.TagListCallBack() {
            @Override public void onMessage(boolean isSuccess, List<String> result) {
                OneRepeater.transmitCommandResult(UMengPushClient.this.context, OnePush.TYPE_AND_OR_DEL_TAG,
                    isSuccess ? OnePush.RESULT_OK : OnePush.RESULT_ERROR, null, "获取所有标签",
                    result == null ? "" : result.toString());
            }
        });
    }
}
