package com.lgl.qidian.buider;

/**
 * @auther 刘广林
 */
public abstract class Buider {

    protected abstract void buiderFace();

    protected abstract void buiderHander();

    protected abstract void buiderMain();

    protected void generyByOrder(){
        this.buiderFace();
        this.buiderHander();
        this.buiderMain();
    }

    protected abstract Productor getResult();

}
