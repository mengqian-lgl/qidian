package com.lgl.qidian.other;

/**
 * @auther 刘广林
 */
public class DecorationA extends AbsractDecoration implements Cloneable {
    public DecorationA(String thing) {
        super(thing);
    }

    public void Decraion(Person person){
        this.person = person;
    }

    public void todo(){
        System.out.println("A什么都不想做");
        person.todo();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
