package com.howoh.studyv2.studyv2_2.vo;

/**
 * Created by howoh on 2017-11-05.
 */

public class Subject {

    private String id;
    private String name;
    private int num;

    public Subject() {}

    public Subject(String id) {
        this.id = id;
    }

    public Subject(String id, String name, int num) {
        this.id = id;
        this.name = name;
        this.num = num;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", num=" + num +
                '}';
    }
}
