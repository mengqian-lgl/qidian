package com.lgl.qidian.other;

/**
 * @auther 刘广林
 */
public class DecrationB extends AbsractDecoration{

    public DecrationB(String thing) {
        super(thing);
    }

    @Override
    public void Decodration(Person person) {
        this.person = person;
    }


    public void todo() {
        System.out.println("B什么都不想做");
        person.todo();
    }
}
