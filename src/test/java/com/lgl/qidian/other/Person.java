package com.lgl.qidian.other;

/**
 * @auther 刘广林
 */
public class Person {

    String thing;
    Person(String thing){
        this.thing = thing;
    }
    public void todo(){
        System.out.println("I do" + this.thing);
    }
}
