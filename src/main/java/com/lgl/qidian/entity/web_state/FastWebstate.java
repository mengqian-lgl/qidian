package com.lgl.qidian.entity.web_state;

/**
 * @auther 刘广林
 */
public abstract class FastWebstate {

    public int state;

    public String message;

    public abstract int getState();

    public abstract String getMessage();

}
