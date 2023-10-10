package com.lgl.qidian.entity.web_state;

/**
 * @auther 刘广林
 */
public abstract class FastWebstateFactory {
   protected FastWebstate fastWebstate;

   public abstract FastWebstate createWebstate();
}
