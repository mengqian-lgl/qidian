package com.lgl.qidian.util;

import com.lgl.qidian.algorithm.IdWorker;

/**
 * @auther 刘广林
 */
public class IdWorkerUtils {

    public static long getFlnowId(){
        IdWorker idWorker = new IdWorker(0, 0);
        return idWorker.getNextId();
    }
}
