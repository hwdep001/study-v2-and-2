package com.howoh.studyv2.studyv2_2.vo;

/**
 * Created by howoh on 2017-11-05.
 */

public class Count {

    private int id;
    private boolean df;

    public Count() {}

    public Count(int id) {
        this.id = id;
    }

    public Count(int id, boolean df) {
        this.id = id;
        this.df = df;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isDf() {
        return df;
    }

    public void setDf(boolean df) {
        this.df = df;
    }

    @Override
    public String toString() {
        return "Count{" +
                "id=" + id +
                ", df=" + df +
                '}';
    }
}
