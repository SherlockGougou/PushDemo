package com.gg.fanapp.push_huawei;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import com.gg.fanapp.push_core.OneRepeater;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONObject;

/**
 * Created by pyt on 2017/5/15.
 */

public class NotificationClickActivity extends Activity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Uri uri = getIntent().getData();
        if (uri != null) {
            String title = uri.getQueryParameter("title");
            String content = uri.getQueryParameter("content");
            String extraMsg = uri.getQueryParameter("extraMsg");
            String keyValue = uri.getQueryParameter("keyValue");
            OneRepeater.transmitNotificationClick(getApplicationContext(), -1, title, content, extraMsg,
                json2Map(keyValue));
        }
        finish();
    }

    /**
     * json转换map
     */
    private Map<String, String> json2Map(String json) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}