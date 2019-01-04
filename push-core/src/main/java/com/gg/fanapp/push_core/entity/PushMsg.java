package com.gg.fanapp.push_core.entity;

import com.gg.fanapp.push_core.core.OnePushCode;
import java.util.Map;

/**
 * @author 工藤
 * @email gougou@16fan.com
 * com.gg.fanapp.push_core.entity
 * create at 2019/1/4  11:40
 * description:
 */
public class PushMsg extends Base implements OnePushCode {

    private int notifyId;
    private String title;
    private String content;
    private String msg;
    //额外消息（例如小米推送里面的传输数据）
    private String extraMsg;
    //对应所谓的键值对(初始化值，防止序列化出错)
    private Map<String, String> keyValue;
    // 收到通知的时间戳
    private long time;

    public PushMsg(int notifyId, String title, String content, String msg, String extraMsg,
        Map<String, String> keyValue, long time) {
        this.notifyId = notifyId;
        this.title = title;
        this.content = content;
        this.msg = msg;
        this.extraMsg = extraMsg;
        this.keyValue = keyValue;
        this.time = time;
    }

    public int getNotifyId() {
        return notifyId;
    }

    public void setNotifyId(int notifyId) {
        this.notifyId = notifyId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getExtraMsg() {
        return extraMsg;
    }

    public void setExtraMsg(String extraMsg) {
        this.extraMsg = extraMsg;
    }

    public Map<String, String> getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(Map<String, String> keyValue) {
        this.keyValue = keyValue;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override public String toString() {
        return "PushMsg{"
            + "notifyId="
            + notifyId
            + ", title='"
            + title
            + '\''
            + ", content='"
            + content
            + '\''
            + ", msg='"
            + msg
            + '\''
            + ", extraMsg='"
            + extraMsg
            + '\''
            + ", keyValue="
            + keyValue
            + ", time="
            + time
            + '}';
    }
}