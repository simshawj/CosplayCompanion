package com.jamessimshaw.cosplaycompanion.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jamessimshaw.cosplaycompanion.R;
import com.jamessimshaw.cosplaycompanion.fragments.CreateSuggestionFragment;
import com.jamessimshaw.cosplaycompanion.fragments.ListConventionsFragment;
import com.jamessimshaw.cosplaycompanion.fragments.ModifyConventionFragment;
import com.jamessimshaw.cosplaycompanion.fragments.ModifyConventionYearFragment;
import com.jamessimshaw.cosplaycompanion.fragments.ModifyPhotoshootFragment;
import com.jamessimshaw.cosplaycompanion.fragments.ShowConventionFragment;
import com.jamessimshaw.cosplaycompanion.fragments.ShowConventionYearFragment;
import com.jamessimshaw.cosplaycompanion.models.Convention;
import com.jamessimshaw.cosplaycompanion.models.ConventionYear;
import com.jamessimshaw.cosplaycompanion.models.Photoshoot;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        ModifyConventionFragment.OnFragmentInteractionListener,
        ModifyConventionYearFragment.OnFragmentInteractionListener,
        ShowConventionYearFragment.OnFragmentInteractionListener,
        ModifyPhotoshootFragment.OnFragmentInteractionListener,
        CreateSuggestionFragment.OnFragmentInteractionListener {

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

        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_main);

        TextView usernameTextView = (TextView) headerView.findViewById(R.id.username);
        TextView emailTextView = (TextView) headerView.findViewById(R.id.email);
        ImageView avatar = (ImageView) headerView.findViewById(R.id.imageView);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        usernameTextView.setText(user.getDisplayName());
        emailTextView.setText(user.getEmail());
        Picasso.with(this)
                .load(user.getPhotoUrl()).fit().centerInside()
                .into(avatar);

        if (savedInstanceState != null)
            return;

        Fragment fragment = ListConventionsFragment.newInstance();
        //Make sure the container for the fragments is present
        if (findViewById(R.id.fragment_container_main) != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_main, fragment)
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                finish();
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            case R.id.nav_conventions:
                clearBackStack();
                break;
            case R.id.nav_feedback:
                Fragment fragment = CreateSuggestionFragment.newInstance();
                gotoFragment(fragment);
                break;
            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                finish();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void clearBackStack() {
        FragmentManager fm = getSupportFragmentManager();
        int entries = fm.getBackStackEntryCount();
        for (int i = 0; i < entries; i++) {
            fm.popBackStack();
        }
    }

    private void gotoFragment(Fragment fragment) {
        //Make sure the container for the fragments is present
        if (findViewById(R.id.fragment_container_main) != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_main, fragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    private void leaveFragment() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onModifyFragmentInteraction() {
        leaveFragment();
    }

    @Override
    public void onFragmentInteraction(String event, String item) {
        Fragment fragment = null;

        switch(event) {
            case "show convention":
                fragment = ShowConventionFragment.newInstance(item);
                break;
            case "show conventionYear":
                fragment = ShowConventionYearFragment.newInstance(item);
                break;
            case "create conventionYear":
                fragment = ModifyConventionYearFragment.newInstance(item, false);
                break;
            case "create photoshoot":
                fragment = ModifyPhotoshootFragment.newInstance(item, false);
                break;
            case "edit convention":
                fragment = ModifyConventionFragment.newInstance(item);
                break;
            case "edit conventionYear":
                fragment = ModifyConventionYearFragment.newInstance(item, true);
                break;
            case "edit photoshoot":
                fragment = ModifyPhotoshootFragment.newInstance(item, true);
            default: break;
        }

        if (fragment != null)
            gotoFragment(fragment);
    }
}
