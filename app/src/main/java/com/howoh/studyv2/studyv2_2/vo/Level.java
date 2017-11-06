package com.howoh.studyv2.studyv2_2.vo;

/**
 * Created by howoh on 2017-11-05.
 */

public class Level {

    private int id;
    private String name;
    private boolean df;

    public Level() {}

    public Level(int id) {
        this.id = id;
    }

    public Level(int id, String name, boolean df) {
        this.id = id;
        this.name = name;
        this.df = df;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDf() {
        return df;
    }

    public void setDf(boolean df) {
        this.df = df;
    }

    @Override
    public String toString() {
        return "Level{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", df=" + df +
                '}';
    }
}
