package com.gg.fanapp.push_core.entity;

import com.gg.fanapp.push_core.core.OnePushCode;

/**
 * @author 工藤
 * @email gougou@16fan.com
 * com.gg.fanapp.push_core.entity
 * create at 2019/1/4  11:41
 * description:
 */
public class PushCommand extends Base implements OnePushCode {

    private int type;
    private int resultCode;
    private String token;
    private String extraMsg;
    private String error;

    public PushCommand(int type, int resultCode, String token, String extraMsg, String error) {
        this.type = type;
        this.resultCode = resultCode;
        this.token = token;
        this.extraMsg = extraMsg;
        this.error = error;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getExtraMsg() {
        return extraMsg;
    }

    public void setExtraMsg(String extraMsg) {
        this.extraMsg = extraMsg;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    private String getTypeText(int type) {
        String typeText = null;
        switch (type) {
            case TYPE_REGISTER:
                typeText = "TYPE_REGISTER";
                break;

            case TYPE_UNREGISTER:
                typeText = "TYPE_UNREGISTER";
                break;

            case TYPE_ADD_TAG:
                typeText = "TYPE_ADD_TAG";
                break;

            case TYPE_DEL_TAG:
                typeText = "TYPE_DEL_TAG";
                break;

            case TYPE_BIND_ALIAS:
                typeText = "TYPE_BIND_ALIAS";
                break;

            case TYPE_UNBIND_ALIAS:
                typeText = "TYPE_UNBIND_ALIAS";
                break;

            default:
                break;
        }
        return typeText;
    }

    @Override public String toString() {
        return "PushCommand{"
            + "type="
            + type
            + ", resultCode="
            + resultCode
            + ", token='"
            + token
            + '\''
            + ", extraMsg='"
            + extraMsg
            + '\''
            + ", error='"
            + error
            + '\''
            + '}';
    }
}