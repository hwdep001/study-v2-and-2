package com.howoh.studyv2.studyv2_2.service;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.howoh.studyv2.studyv2_2.vo.User;

/**
 * Created by howoh on 2017-11-04.
 */

public class AuthService {

    private static CollectionReference usersCol = FirebaseFirestore.getInstance().collection("users");

    private static String uid;
    private static String email;
    private static String name;
    private static String photoURL;
    private static String creationDate;
    private static String lastSignInDate;
    private static boolean authenticated;

    public static String getUid() {
        return uid;
    }

    public static String getEmail() {
        return email;
    }

    public static String getName() {
        return name;
    }

    public static String getPhotoURL() {
        return photoURL;
    }

    public static String getCreationDate() {
        return creationDate;
    }

    public static String getLastSignInDate() {
        return lastSignInDate;
    }

    public static boolean getAuthenticated() {
        return authenticated;
    }

    private static void setUid(String uid) {
        uid = uid;
    }

    private static void setEmail(String email) {
        email = email;
    }

    private static void setName(String name) {
        name = name;
    }

    private static void setPhotoURL(String photoURL) {
        photoURL = photoURL;
    }

    private static void setCreationDate(String creationDate) {
        creationDate = creationDate;
    }

    private static void setLastSignInDate(String lastSignInDate) {
        lastSignInDate = lastSignInDate;
    }

    private static void setAuthenticated(boolean authenticated) {
        authenticated = authenticated;
    }

    public static void setAuthInfo(String uid) {
        usersCol.document(uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()) {
                    User user = documentSnapshot.toObject(User.class);
                    setUid(user.getUid());
                    setEmail(user.getEmail());
                    setName(user.getName());
                    setPhotoURL(user.getPhotoURL());
                    setCreationDate(user.getCreationDate());
                    setLastSignInDate(user.getLastSignInDate());
                    setAuthenticated(user.getAuthenticated());
                }
            }
        });
    }
}
