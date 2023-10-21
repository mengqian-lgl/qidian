package com.lgl.qidian.buider;

/**
 * @auther 刘广林
 */
public class Test {
    public static void main(String[] args) throws CloneNotSupportedException {
        Buider buider = new ConcreteBuider1();
        buider.generyByOrder();
        Productor result = buider.getResult();
        Productor clone = result.clone();
        System.out.println(result);
    }
}
