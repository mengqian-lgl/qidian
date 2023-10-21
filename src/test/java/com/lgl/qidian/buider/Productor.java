package com.lgl.qidian.buider;

/**
 * @auther 刘广林
 */
public class Productor implements Cloneable{
    int face;
    int hander;
    Main main;

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public int getFace() {
        return face;
    }

    public void setFace(int face) {
        this.face = face;
    }

    public int getHander() {
        return hander;
    }

    public void setHander(int hander) {
        this.hander = hander;
    }


    public Productor clone() throws CloneNotSupportedException {
        Productor clone = (Productor) super.clone();
        clone.setMain(clone.main.clone());
        return clone;
    }
}
