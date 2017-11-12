package com.howoh.studyv2.studyv2_2.vo;

/**
 * Created by howoh on 2017-11-05.
 */

public class Word {

    private String id;
    private String head1;
    private String head2;
    private String body1;
    private String body2;
    private int num;

    private String lectureId;
    private int levelId;

    public Word() {
    }

    public Word(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHead1() {
        return head1;
    }

    public void setHead1(String head1) {
        this.head1 = head1;
    }

    public String getHead2() {
        return head2;
    }

    public void setHead2(String head2) {
        this.head2 = head2;
    }

    public String getBody1() {
        return body1;
    }

    public void setBody1(String body1) {
        this.body1 = body1;
    }

    public String getBody2() {
        return body2;
    }

    public void setBody2(String body2) {
        this.body2 = body2;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getLectureId() {
        return lectureId;
    }

    public void setLectureId(String lectureId) {
        this.lectureId = lectureId;
    }

    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Word word = (Word) o;

        if (num != word.num) return false;
        if (!id.equals(word.id)) return false;
        if (!head1.equals(word.head1)) return false;
        if (head2 != null ? !head2.equals(word.head2) : word.head2 != null) return false;
        if (!body1.equals(word.body1)) return false;
        return body2 != null ? body2.equals(word.body2) : word.body2 == null;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + head1.hashCode();
        result = 31 * result + (head2 != null ? head2.hashCode() : 0);
        result = 31 * result + body1.hashCode();
        result = 31 * result + (body2 != null ? body2.hashCode() : 0);
        result = 31 * result + num;
        return result;
    }

    @Override
    public String toString() {
        return "Word{" +
                "id='" + id + '\'' +
                ", head1='" + head1 + '\'' +
                ", head2='" + head2 + '\'' +
                ", body1='" + body1 + '\'' +
                ", body2='" + body2 + '\'' +
                ", num=" + num +
                ", lectureId='" + lectureId + '\'' +
                ", levelId=" + levelId +
                '}';
    }
}
