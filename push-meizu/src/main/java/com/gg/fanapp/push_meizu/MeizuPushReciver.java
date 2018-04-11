package com.gg.fanapp.push_meizu;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.gg.fanapp.push_core.OnePush;
import com.gg.fanapp.push_core.OneRepeater;
import com.gg.fanapp.push_core.cache.OnePushCache;
import com.gg.fanapp.push_core.log.OneLog;
import com.meizu.cloud.pushsdk.MzPushMessageReceiver;
import com.meizu.cloud.pushsdk.handler.MzPushMessage;
import com.meizu.cloud.pushsdk.notification.PushNotificationBuilder;
import com.meizu.cloud.pushsdk.platform.message.PushSwitchStatus;
import com.meizu.cloud.pushsdk.platform.message.RegisterStatus;
import com.meizu.cloud.pushsdk.platform.message.SubAliasStatus;
import com.meizu.cloud.pushsdk.platform.message.SubTagsStatus;
import com.meizu.cloud.pushsdk.platform.message.UnRegisterStatus;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by SherlockHolmes on 2017/8/24.
 */

public class MeizuPushReciver extends MzPushMessageReceiver {

	private static final String TAG = "MeizuPushReciver";

	@Deprecated @Override public void onRegister(Context context, String pushId) {
	}

	@Deprecated @Override public void onUnRegister(Context context, boolean success) {
	}

	@Override public void onPushStatus(Context context, PushSwitchStatus pushSwitchStatus) {
		//OnePush不支持这个状态
	}

	@Override public void onMessage(Context context, Intent intent) {
		//flyme3.0的时候使用
		OneRepeater.transmitMessage(context.getApplicationContext(), Intent2Json.toJson(intent), null, null);
	}

	@Override public void onMessage(Context context, String message) {
		//flyme4.0以上的时候使用
		OneRepeater.transmitMessage(context.getApplicationContext(), message, null, null);
	}

	@Override public void onRegisterStatus(Context context, RegisterStatus registerStatus) {
		if (registerStatus.getCode().equals(RegisterStatus.SUCCESS_CODE)) {
			/**
			 * 保存token 和 平台
			 */
			OnePushCache.putToken(context, registerStatus.getPushId());
			OnePushCache.putPlatform(context, OnePushCache.PLATFORM_MEIZU);
			OneRepeater.transmitCommandResult(context.getApplicationContext(), OnePush.TYPE_REGISTER, OnePush.RESULT_OK,
				registerStatus.getPushId(), null, "success");
		}
		OneRepeater.transmitCommandResult(context.getApplicationContext(), OnePush.TYPE_REGISTER,
			RegisterStatus.SUCCESS_CODE.equals(registerStatus.getCode()) ? OnePush.RESULT_OK : OnePush.RESULT_ERROR,
			registerStatus.getPushId(), null, registerStatus.getMessage());
	}

	// 通知栏消息点击回调
	@Override public void onNotificationClicked(Context context, MzPushMessage mzPushMessage) {
		Log.d(TAG, "onNotificationClicked: mzPushMessage = " + mzPushMessage.toString());
		OneRepeater.transmitNotificationClick(context.getApplicationContext(), mzPushMessage.getNotifyId(), mzPushMessage.getTitle(), mzPushMessage.getContent(),
			"", json2Map(mzPushMessage.getSelfDefineContentString()));
	}

	// 通知栏消息到达回调，flyme6基于android6.0以上不再回调
	@Override public void onNotificationArrived(Context context, MzPushMessage mzPushMessage) {
		Log.d(TAG, "onNotificationArrived: mzPushMessage = " + mzPushMessage.toString());
		OneRepeater.transmitNotification(context.getApplicationContext(), mzPushMessage.getNotifyId(), mzPushMessage.getTitle(), mzPushMessage.getContent(),
			"", json2Map(mzPushMessage.getSelfDefineContentString()));
	}

	@Override public void onUnRegisterStatus(Context context, UnRegisterStatus unRegisterStatus) {
		OneRepeater.transmitCommandResult(context.getApplicationContext(), OnePush.TYPE_UNREGISTER,
			unRegisterStatus.isUnRegisterSuccess() ? OnePush.RESULT_OK : OnePush.RESULT_ERROR, null, null,
			unRegisterStatus.getMessage());
	}

	@Override public void onSubTagsStatus(Context context, SubTagsStatus subTagsStatus) {
		List<SubTagsStatus.Tag> tagList = TagAndAliasManager.getInstance().getTagList();
		boolean isAddAlias =
			tagList == null || subTagsStatus.getTagList().size() >= tagList.size() && !tagList.isEmpty();
		TagAndAliasManager.getInstance().setTagList(subTagsStatus.getTagList());
		OneRepeater.transmitCommandResult(context.getApplicationContext(), isAddAlias ? OnePush.TYPE_ADD_TAG : OnePush.TYPE_DEL_TAG,
			OnePush.RESULT_OK, null, subTagsStatus.getTagList().toString(), subTagsStatus.getMessage());
	}

	@Override public void onSubAliasStatus(Context context, SubAliasStatus subAliasStatus) {
		String alias = TagAndAliasManager.getInstance().getAlias();
		boolean isSetAlias = !alias.equals(subAliasStatus.getAlias()) || !TextUtils.isEmpty(alias);
		TagAndAliasManager.getInstance().setAlias(alias);
		OneRepeater.transmitCommandResult(context.getApplicationContext(), isSetAlias ? OnePush.TYPE_BIND_ALIAS : OnePush.TYPE_UNBIND_ALIAS,
			OnePush.RESULT_OK, null, subAliasStatus.getAlias(), subAliasStatus.getMessage());
	}

	@Override public void onUpdateNotificationBuilder(PushNotificationBuilder pushNotificationBuilder) {
		// 设置大图标和状态栏小图标
		pushNotificationBuilder.setmLargIcon(R.drawable.flyme_status_ic_notification);
		pushNotificationBuilder.setmStatusbarIcon(R.drawable.mz_push_notification_small_icon);
	}

	/**
	 * json转换map
	 *
	 * @param json
	 * @return
	 */
	private Map<String, String> json2Map(String json) {
		try {
			JSONObject jsonObject = new JSONObject(json);
			Map<String, String> map = new HashMap<>();
			Iterator<String> iterator = jsonObject.keys();
			while (iterator.hasNext()) {
				String key = iterator.next();
				String value = jsonObject.getString(key);
				map.put(key, value);
			}
			return map;
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return null;
	}
}
