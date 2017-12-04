package com.howoh.studyv2.studyv2_2;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private FragmentManager fm;

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

        initNavText(navigationView);
    }

    private void initNavText(NavigationView navigationView) {
        FirebaseUser currentUser = getCurrentUser();
        View navHeaderMain = navigationView.getHeaderView(0);
        ImageView photoURLView = (ImageView) navHeaderMain.findViewById(R.id.nav_header_main_photoURL);
        TextView nameView = (TextView) navHeaderMain.findViewById(R.id.nav_header_main_name);
        TextView emailView = (TextView) navHeaderMain.findViewById(R.id.nav_header_main_email);
        Glide.with(this).load(currentUser.getPhotoUrl()).into(photoURLView);
        nameView.setText(currentUser.getDisplayName());
        emailView.setText(currentUser.getEmail());
    }
}
