package com.lgl.qidian.other;

/**
 * @auther 刘广林
 */
public class AbsractDecoration extends Person{

    Person person;

    public AbsractDecoration(String thing) {
        super(thing);
    }
    public void Decodration(Person person){
        this.person = person;
    }

    public void todo(){

        if (person != null){
            person.todo();
        }
    }
}
