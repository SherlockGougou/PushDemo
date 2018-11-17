package com.gg.fanapp.push_meizu;

import android.content.Intent;
import android.os.Bundle;
import java.util.Iterator;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

/*
 * @author 工藤
 * @emil gougou@16fan.com
 * com.gg.fanapp.push_meizu
 * create at 2018/4/11  11:07
 * description:
 */

public class Intent2Json {

    /**
     * 转换成json字符的形式
     */
    public static String toJson(Intent intent) {
        if (intent == null) {
            return null;
        }
        JSONObject jsonObject = new JSONObject();
        try {
            Bundle extras = intent.getExtras();
            Set<String> keySet = extras.keySet();
            Iterator<String> iterator = keySet.iterator();
            while (iterator.hasNext()) {
                String next = iterator.next();
                Object value = extras.get(next);
                if (value instanceof Boolean) {
                    jsonObject.put(next, (Boolean) value);
                } else if (value instanceof Integer) {
                    jsonObject.put(next, (Integer) value);
                } else if (value instanceof Long) {
                    jsonObject.put(next, (Long) value);
                } else if (value instanceof Double) {
                    jsonObject.put(next, (Double) value);
                } else if (value instanceof String) {
                    jsonObject.put(next, value);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }
}
