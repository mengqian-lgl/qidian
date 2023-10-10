package com.lgl.qidian.entity.web_state;

/**
 * @auther 刘广林
 */
public class Reject extends FastWebstate{
    public static final int STATE = 403;

    public int state = STATE;
    @Override
    public int getState() {
        return state;
    }

    @Override
    public String getMessage() {
        return "凭证异常，请重新访问";
    }
}
