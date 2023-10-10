package com.lgl.qidian.entity.use_slider;

/**
 * @auther 刘广林
 */
//在webConfig配置拦截器的时候使用
public enum UseSliderInterceptorEntity {
    REGISTER("/register");

    private String name;

    private UseSliderInterceptorEntity(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
