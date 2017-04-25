package com.jamessimshaw.cosplaycompanion.activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.jamessimshaw.cosplaycompanion.CosplayCompanionApplication;
import com.jamessimshaw.cosplaycompanion.R;
import com.jamessimshaw.cosplaycompanion.datasources.UserManager;
import com.jamessimshaw.cosplaycompanion.fragments.ListConventionsFragment;
import com.jamessimshaw.cosplaycompanion.fragments.ModifyConventionFragment;
import com.jamessimshaw.cosplaycompanion.fragments.ModifyConventionYearFragment;
import com.jamessimshaw.cosplaycompanion.fragments.ModifyPhotoshootFragment;
import com.jamessimshaw.cosplaycompanion.fragments.ShowConventionFragment;
import com.jamessimshaw.cosplaycompanion.fragments.ShowConventionYearFragment;
import com.jamessimshaw.cosplaycompanion.models.Convention;
import com.jamessimshaw.cosplaycompanion.models.ConventionYear;
import com.jamessimshaw.cosplaycompanion.models.Photoshoot;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        ListConventionsFragment.OnFragmentInteractionListener,
        ModifyConventionFragment.OnFragmentInteractionListener,
        ShowConventionFragment.OnFragmentInteractionListener,
        ModifyConventionYearFragment.OnFragmentInteractionListener,
        ShowConventionYearFragment.OnFragmentInteractionListener,
        ModifyPhotoshootFragment.OnFragmentInteractionListener, SignedOut {

    @Inject UserManager mUserManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((CosplayCompanionApplication)getApplication()).getUserManagerComponent().inject(this);

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
        usernameTextView.setText(mUserManager.retrieveUser().getUsername());
        emailTextView.setText(mUserManager.retrieveUser().getEmail());

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
                mUserManager.sign_out(this);
            } else {
                super.onBackPressed();
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camara) {
            // Handle the camera action
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
    public void onFragmentInteraction(String event, Object item) {
        Fragment fragment = null;

        switch(event) {
            case "show convention":
                if (item instanceof Convention)
                    fragment = ShowConventionFragment.newInstance((Convention) item);
                break;
            case "show conventionYear":
                if (item instanceof ConventionYear)
                    fragment = ShowConventionYearFragment.newInstance((ConventionYear) item);
                break;
            case "create convention":
                fragment = ModifyConventionFragment.newInstance();
                break;
            case "create conventionYear":
                if (item instanceof Convention)
                    fragment = ModifyConventionYearFragment.newInstance((Convention) item);
                break;
            case "create photoshoot":
                if (item instanceof ConventionYear)
                    fragment = ModifyPhotoshootFragment.newInstance((ConventionYear) item);
                break;
            case "edit convention":
                if (item instanceof Convention)
                    fragment = ModifyConventionFragment.newInstance((Convention) item);
                break;
            case "edit conventionYear":
                if (item instanceof ConventionYear)
                    fragment = ModifyConventionYearFragment.newInstance((ConventionYear) item);
                break;
            case "edit photoshoot":
                if (item instanceof Photoshoot)
                    fragment = ModifyPhotoshootFragment.newInstance((Photoshoot) item);
                break;
            default: break;
        }

        if (fragment != null)
            gotoFragment(fragment);
    }

    @Override
    public void signedOut() {
        super.onBackPressed();
    }
}
