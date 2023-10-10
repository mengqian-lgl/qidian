package com.lgl.qidian.entity.use_slider;

/**
 * @auther 刘广林
 */
//滑块坐标的实体类
public class SliderXY {

    private final String range = "10";

    public SliderXY(String x, String y) {
        this.x = x;
        this.y = y;
    }

    private String x;

    private String y;

    public String getRange() {
        return range;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }
}
