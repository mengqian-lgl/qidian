package com.lgl.qidian.other;

/**
 * @auther 刘广林
 */
public abstract class MoBan {
    protected void moban(){
        System.out.println("我来自父类模板" + zilei());
    }

    protected abstract String zilei();
}
