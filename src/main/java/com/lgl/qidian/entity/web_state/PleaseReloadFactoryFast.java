package com.lgl.qidian.entity.web_state;

/**
 * @auther 刘广林
 */
public class PleaseReloadFactoryFast extends FastWebstateFactory {
    @Override
    public  FastWebstate createWebstate() {
        return new PleaseReload();
    }
}
