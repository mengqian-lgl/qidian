package com.lgl.qidian.entity.web_state;

/**
 * @auther 刘广林
 */

//认证失败拒绝返回的实体类
public class PleaseReload extends FastWebstate {

    public static final int STATE = 401;

    public int state = STATE;

    public String messge = "认证过期，请重新登入";

    @Override
    public int getState() {
        return state;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
