package com.gg.fanapp.push_huawei;

import android.content.Context;
import android.text.TextUtils;
import com.gg.fanapp.push_core.OnePush;
import com.gg.fanapp.push_core.OneRepeater;
import com.gg.fanapp.push_core.cache.OnePushCache;
import com.gg.fanapp.push_core.core.IPushClient;
import com.huawei.hms.api.ConnectionResult;
import com.huawei.hms.api.HuaweiApiClient;
import com.huawei.hms.support.api.client.ResultCallback;
import com.huawei.hms.support.api.push.HuaweiPush;
import com.huawei.hms.support.api.push.TokenResult;

/**
 * Created by pyt on 2017/5/15.
 */
public class HuaweiPushClient implements IPushClient {

	private static final String TAG = "HuaweiPushClient";

	private Context mContext;
	private HuaweiApiClient huaweiApiClient;

	@Override
	public void initContext(Context context) {
		this.mContext = context.getApplicationContext();
		huaweiApiClient = new HuaweiApiClient.Builder(context).addApi(HuaweiPush.PUSH_API)
				.addConnectionCallbacks(new HuaweiApiClient.ConnectionCallbacks() {
					@Override
					public void onConnected() {
						//华为移动服务client连接成功，在这边处理业务自己的事件
						getToken();
						new Thread(new Runnable() {
							@Override
							public void run() {
								HuaweiPush.HuaweiPushApi.enableReceiveNormalMsg(huaweiApiClient,
										true);
								HuaweiPush.HuaweiPushApi.enableReceiveNotifyMsg(huaweiApiClient,
										true);
							}
						}).start();
					}

					@Override
					public void onConnectionSuspended(int i) {
						if (huaweiApiClient != null) {
							huaweiApiClient.connect();
						}
					}
				})
				.addOnConnectionFailedListener(new HuaweiApiClient.OnConnectionFailedListener() {
					@Override
					public void onConnectionFailed(ConnectionResult connectionResult) {
						OneRepeater.transmitCommandResult(HuaweiPushClient.this.mContext, OnePush
										.TYPE_REGISTER,
								OnePush.RESULT_ERROR, null, String.valueOf(connectionResult
										.getErrorCode()), null);
					}
				})
				.build();
	}

	@Override
	public void register() {
		if (!huaweiApiClient.isConnected()) {
			huaweiApiClient.connect();
		} else {
			getToken();
		}
	}

	@Override
	public void unRegister() {
		final String token = OnePushCache.getToken(mContext);
		if (!TextUtils.isEmpty(token)) {
			new Thread() {
				@Override
				public void run() {
					HuaweiPush.HuaweiPushApi.deleteToken(huaweiApiClient, token);
					HuaweiPush.HuaweiPushApi.enableReceiveNormalMsg(huaweiApiClient, false);
					HuaweiPush.HuaweiPushApi.enableReceiveNotifyMsg(huaweiApiClient, false);
					huaweiApiClient.disconnect();
				}
			}.start();
		}
	}

	private void getToken() {
		HuaweiPush.HuaweiPushApi.getToken(huaweiApiClient)
				.setResultCallback(new ResultCallback<TokenResult>() {
					@Override
					public void onResult(TokenResult tokenResult) {
						if (tokenResult.getTokenRes() != null && !TextUtils.isEmpty(
								tokenResult.getTokenRes().getToken())) {
							OneRepeater.transmitCommandResult(HuaweiPushClient.this.mContext,
									OnePush.TYPE_REGISTER,
									OnePush.RESULT_OK, tokenResult.getTokenRes().getToken(), null,
									null);
						}
					}
				});
	}

	@Override
	public void bindAlias(String alias) {

	}

	@Override
	public void unBindAlias(String alias) {

	}

	@Override
	public void addTag(String tag) {

	}

	@Override
	public void resetTag() {

	}

	@Override
	public void getTagList() {

	}

	@Override
	public void deleteTag(String tag) {

	}
}