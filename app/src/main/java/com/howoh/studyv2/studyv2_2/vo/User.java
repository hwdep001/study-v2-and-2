package com.howoh.studyv2.studyv2_2.vo;

import android.util.Log;

import com.howoh.studyv2.studyv2_2.StartActivity;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by howoh on 2017-11-03.
 */

public class User extends Object {

    private String uid;
    private String email;
    private String name;
    private String photoURL;
    private String creationDate;
    private String lastSignInDate;
    private boolean authenticated;

    public User() {

    }

    public User(String uid) {
        this.uid = uid;
    }

    public User(String uid, String email, String name, String photoURL) {
        this.uid = uid;
        this.email = email;
        this.photoURL = photoURL;
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getLastSignInDate() {
        return lastSignInDate;
    }

    public void setLastSignInDate(String lastSignInDate) {
        this.lastSignInDate = lastSignInDate;
    }

    public boolean getAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", email='" + email + '\'' +
                ", photoURL='" + photoURL + '\'' +
                ", name='" + name + '\'' +
                ", lastSignInDate='" + lastSignInDate + '\'' +
                ", authenticated=" + authenticated +
                '}';
    }

    public Map<String, Object> convertMap() {
        Map<String, Object> map = new HashMap<>();

        Class<?> thisClass = null;

        try {
            thisClass = Class.forName(this.getClass().getName());
            Field[] fields = User.class.getDeclaredFields();

            for(Field field: fields) {
                if (Modifier.isPrivate(field.getModifiers())) {
                    Object value = field.get(this);
                    if(value != null) {
                        map.put(field.getName(), value);
                    }
                }
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return map;
    }
}
