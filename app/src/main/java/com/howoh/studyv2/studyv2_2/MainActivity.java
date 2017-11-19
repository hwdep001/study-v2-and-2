package com.howoh.studyv2.studyv2_2;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
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

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private FragmentManager fm;

    // firebase
    private FirebaseAuth mAuth;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseFirestore mDb;
    private FirebaseUser currentUser;

    // onBackPressed
    private final long FINISH_INTERVAL_TIME = 2000;
    private long   backPressedTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.content_main, getCatListFragment("ew")).commit();

        initFirebase();
        initNavText(navigationView);
    }

    private void initNavText(NavigationView navigationView) {
        View navHeaderMain = navigationView.getHeaderView(0);
        ImageView photoURLView = (ImageView) navHeaderMain.findViewById(R.id.nav_header_main_photoURL);
        TextView nameView = (TextView) navHeaderMain.findViewById(R.id.nav_header_main_name);
        TextView emailView = (TextView) navHeaderMain.findViewById(R.id.nav_header_main_email);
        Glide.with(MainActivity.this).load(currentUser.getPhotoUrl()).into(photoURLView);
        nameView.setText(currentUser.getDisplayName());
        emailView.setText(currentUser.getEmail());
    }

    private void initFirebase() {

        mAuth = FirebaseAuth.getInstance();
        mDb = FirebaseFirestore.getInstance();

        currentUser = mAuth.getCurrentUser();
        updateUserInfo(currentUser);

        // firebase auth
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if(fm.getBackStackEntryCount() > 0) {
            super.onBackPressed();
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

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

    private CatListFragment getCatListFragment(String subId) {
        CatListFragment catListFragment = new CatListFragment();
        Bundle bundle = new Bundle();

        bundle.putString("subId", subId);
        catListFragment.setArguments(bundle);

        return catListFragment;
    }

    ////////////////////////////////////////////////////////////////

    private void updateUserInfo(final FirebaseUser currentUser) {
        mDb.collection("users").document(currentUser.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
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

                        mDb.collection("users").document(currentUser.getUid()).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
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
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        startIntent(MainActivity.this, SignInActivity.class, true, true);
                    }
                });
    }

}
