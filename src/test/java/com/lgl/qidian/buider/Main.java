package com.lgl.qidian.buider;

/**
 * @auther 刘广林
 */
public class Main implements Cloneable{
    private int caption;

    public Main(int caption) {
        this.caption = caption;
    }

    public Main clone() throws CloneNotSupportedException {
        return (Main) super.clone();
    }
}
