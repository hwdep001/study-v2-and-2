package com.howoh.studyv2.studyv2_2.vo;

/**
 * Created by howoh on 2017-11-05.
 */

public class Lecture {

    private String id;
    private String name;
    private int num;

    private String categoryId;

    public Lecture() {}

    public Lecture(String id) {
        this.id = id;
    }

    public Lecture(String id, String name, int num) {
        this.id = id;
        this.name = name;
        this.num = num;
    }

    public Lecture(String id, String name, int num, String categoryId) {
        this.id = id;
        this.name = name;
        this.num = num;
        this.categoryId = categoryId;
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

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "Lecture{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", num=" + num +
                ", categoryId='" + categoryId + '\'' +
                '}';
    }
}
