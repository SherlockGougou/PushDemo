package com.gg.fanapp.push_core.core;

import android.app.Application;
import com.gg.fanapp.push_core.log.OneLog;
import java.util.Arrays;
import java.util.List;

/**
 * the one push context
 *
 * <p>Created by pyt on 2017/5/9.
 */
public class OnePushContext {

    // 平台标识
    public static final String PLATFORM_UMENG = "com.gg.fanapp.push_umeng.UMengPushClient";
    public static final String PLATFORM_XIAOMI = "com.gg.fanapp.push_xiaomi.XiaomiPushClient";
    public static final String PLATFORM_HUAWEI = "com.gg.fanapp.push_huawei.HuaweiPushClient";
    public static final String PLATFORM_MEIZU = "com.gg.fanapp.push_meizu.MeizuPushClient";
    private static final String TAG = "OnePushContext";
    private IPushClient mIPushClient;

    private OnePushContext() {
    }

    /**
     * Using the simple interest
     */
    public static OnePushContext getInstance() {
        return Single.sInstance;
    }

    public void init(Application application, String metaPlatformClassName) {
        try {
            Class<?> currentClz = Class.forName(metaPlatformClassName);
            Class<?>[] interfaces = currentClz.getInterfaces();
            List<Class<?>> allInterfaces = Arrays.asList(interfaces);
            if (allInterfaces.contains(IPushClient.class)) {
                // create object with no params
                IPushClient iPushClient = (IPushClient) currentClz.newInstance();
                this.mIPushClient = iPushClient;
                // invoke IPushClient initContext method
                OneLog.i(TAG, "current register platform is " + metaPlatformClassName);
                iPushClient.initContext(application);
            } else {
                throw new IllegalArgumentException(
                    metaPlatformClassName + "is not implements " + IPushClient.class.getName());
            }
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("can not find class " + metaPlatformClassName);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (mIPushClient == null) {
            throw new IllegalStateException("onRegisterPush must at least one of them returns to true");
        }
    }

    public void register() {
        mIPushClient.register();
    }

    public void unRegister() {
        mIPushClient.unRegister();
    }

    public void bindAlias(String alias) {
        mIPushClient.bindAlias(alias);
    }

    public void unBindAlias(String alias) {
        mIPushClient.unBindAlias(alias);
    }

    public void addTag(String tag) {
        mIPushClient.addTag(tag);
    }

    public void deleteTag(String tag) {
        mIPushClient.deleteTag(tag);
    }

    public void resetTag() {
        mIPushClient.resetTag();
    }

    public void getTagList() {
        mIPushClient.getTagList();
    }

    private static class Single {
        static OnePushContext sInstance = new OnePushContext();
    }
}
