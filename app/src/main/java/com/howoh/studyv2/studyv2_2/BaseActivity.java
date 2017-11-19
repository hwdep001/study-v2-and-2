package com.howoh.studyv2.studyv2_2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.howoh.studyv2.studyv2_2.service.AuthService;
import com.howoh.studyv2.studyv2_2.util.CommonUtil;
import com.howoh.studyv2.studyv2_2.vo.User;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by howoh on 2017-11-03.
 */

public class BaseActivity extends SuperBaseActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = BaseActivity.class.getSimpleName();

    private FragmentManager fm;

    // firebase
    private FirebaseAuth mAuth;
    private FirebaseFirestore mDb;
    private FirebaseUser currentUser;

    // onBackPressed
    private final long FINISH_INTERVAL_TIME = 2000;
    private long   backPressedTime = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fm = getSupportFragmentManager();

        initFirebase();
    }

    private void initFirebase() {

        mAuth = FirebaseAuth.getInstance();
        mDb = FirebaseFirestore.getInstance();

        currentUser = mAuth.getCurrentUser();
        updateUserInfo(currentUser);
    }

    @Override
    public void onBackPressed() {

        if(isTaskRoot()) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                long tempTime = System.currentTimeMillis();
                long intervalTime = tempTime - backPressedTime;

                if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime) {
                    super.onBackPressed();
                } else {
                    backPressedTime = tempTime;
                    Toast.makeText(getApplicationContext(), R.string.back_pressed_msg, Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        hideProgressDialog();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        if (id == R.id.nav_ew) {
            fm.beginTransaction().replace(R.id.content_main, getCatListFragment("ew")).commit();
        } else if (id == R.id.nav_lw) {
            fm.beginTransaction().replace(R.id.content_main, getCatListFragment("lw")).commit();
        } else if (id == R.id.nav_c4) {
            fm.beginTransaction().replace(R.id.content_main, getCatListFragment("c4")).commit();
        } else if (id == R.id.nav_cc) {
            fm.beginTransaction().replace(R.id.content_main, getCatListFragment("cc")).commit();
        } else if (id == R.id.nav_sign_out) {
            signOut();
        } else if (id == R.id.nav_test) {
            fm.beginTransaction().replace(R.id.content_main, new TestFragment()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public CatListFragment getCatListFragment(String subId) {
        CatListFragment catListFragment = new CatListFragment();
        Bundle bundle = new Bundle();

        bundle.putString("subId", subId);
        catListFragment.setArguments(bundle);

        return catListFragment;
    }

    private void updateUserInfo(final FirebaseUser currentUser) {
        mDb.collection("users").document(currentUser.getUid()).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()) {
                            User user = documentSnapshot.toObject(User.class);
                            if(!user.getAuthenticated()) {
                                signOut();
                            } else {
                                Map<String, Object> map = new HashMap<>();
                                map.put("email", currentUser.getEmail());
                                map.put("name", currentUser.getDisplayName());
                                map.put("photoURL", currentUser.getPhotoUrl().toString());
                                map.put("lastSignInDate", CommonUtil.dateToyyyy_MM_dd_HH_mm_ss(new Date()));

                                mDb.collection("users").document(currentUser.getUid()).update(map)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                AuthService.setAuthInfo(currentUser.getUid());
                                            }
                                        });
                            }
                        }
                    }
                });
    }

    private void signOut() {

        // Firebase sign out
        mAuth.signOut();

        // Google sign out
        Auth.GoogleSignInApi.signOut(getmGoogleApiClient()).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        startIntent(BaseActivity.this, SignInActivity.class, true, true);
                    }
                });
    }

    public FirebaseUser getCurrentUser() {
        return currentUser;
    }

    public void startIntent(Context startContext, Class nextClass, boolean isActivityClear, boolean isNotAnimation) {
        Intent intent = new Intent(startContext, nextClass);
        if(isActivityClear) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        startActivity(intent);
        if(isNotAnimation) {
            overridePendingTransition(0, 0);
        }
    }
}
