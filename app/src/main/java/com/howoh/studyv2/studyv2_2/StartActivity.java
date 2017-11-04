package com.howoh.studyv2.studyv2_2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

/**
 * Created by howoh on 2017-11-03.
 */

public class StartActivity extends AppCompatActivity {

    private static final String TAG = StartActivity.class.getSimpleName();
    private FirebaseFirestore mDb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        mDb = FirebaseFirestore.getInstance();

        if(currentUser == null) {
            startIntent(SignInActivity.class, true, true);
        } else {
            startIntent(MainActivity.class, true, true);
        }
    }

    private void startIntent(Class nextClass, boolean isActivityClear, boolean isNotAnimation) {
        Intent intent = new Intent(StartActivity.this, nextClass);
        if(isActivityClear) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        startActivity(intent);
        if(isNotAnimation) {
            overridePendingTransition(0, 0);
        }
    }
}
