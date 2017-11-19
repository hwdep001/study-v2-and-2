package com.howoh.studyv2.studyv2_2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Created by howoh on 2017-11-03.
 */

public class StartActivity extends SuperBaseActivity {

    private static final String TAG = StartActivity.class.getSimpleName();
    private FirebaseFirestore mDb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        mDb = FirebaseFirestore.getInstance();

        if(currentUser == null) {
            startIntent(StartActivity.this, SignInActivity.class, true, true);
        } else {
            startIntent(StartActivity.this, MainActivity.class, true, true);
        }
    }
}
