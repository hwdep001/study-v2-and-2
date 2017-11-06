package com.howoh.studyv2.studyv2_2.vo;

/**
 * Created by howoh on 2017-11-05.
 */

public class Category {

    private String id;
    private String name;
    private int num;

    private String subjectId;

    public Category() {}

    public Category(String id) {
        this.id = id;
    }

    public Category(String id, String name, int num) {
        this.id = id;
        this.name = name;
        this.num = num;
    }

    public Category(String id, String name, int num, String subjectId) {
        this.id = id;
        this.name = name;
        this.num = num;
        this.subjectId = subjectId;
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

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", num=" + num +
                ", subjectId='" + subjectId + '\'' +
                '}';
    }
}
