package com.lgl.qidian.buider;

import org.springframework.security.core.parameters.P;

/**
 * @auther 刘广林
 */
public class ConcreteBuider1 extends Buider{
    private Productor productor = new Productor();

    @Override
    protected void buiderFace() {
        productor.setFace(1);
    }

    @Override
    protected void buiderHander() {
        productor.setHander(2);
    }

    @Override
    protected void buiderMain() {
        productor.setMain(new Main(100));
    }

    @Override
    protected Productor getResult() {
        return productor;
    }
}
