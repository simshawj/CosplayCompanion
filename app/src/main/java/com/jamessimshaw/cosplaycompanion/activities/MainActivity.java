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

import com.jamessimshaw.cosplaycompanion.R;
import com.jamessimshaw.cosplaycompanion.fragments.ListConventionsFragment;
import com.jamessimshaw.cosplaycompanion.fragments.ModifyConventionFragment;
import com.jamessimshaw.cosplaycompanion.fragments.ModifyConventionYearFragment;
import com.jamessimshaw.cosplaycompanion.fragments.ModifyPhotoshootFragment;
import com.jamessimshaw.cosplaycompanion.fragments.ShowConventionFragment;
import com.jamessimshaw.cosplaycompanion.fragments.ShowConventionYearFragment;
import com.jamessimshaw.cosplaycompanion.models.Convention;
import com.jamessimshaw.cosplaycompanion.models.ConventionYear;
import com.jamessimshaw.cosplaycompanion.models.Photoshoot;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        ListConventionsFragment.OnFragmentInteractionListener,
        ModifyConventionFragment.OnFragmentInteractionListener,
        ShowConventionFragment.OnFragmentInteractionListener,
        ModifyConventionYearFragment.OnFragmentInteractionListener,
        ShowConventionYearFragment.OnFragmentInteractionListener,
        ModifyPhotoshootFragment.OnFragmentInteractionListener {

    private static String CONVENTIONS_FRAGMENT = "conventions";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Make sure the container for the fragments is present
        if (findViewById(R.id.fragment_container_main) != null) {

            if(savedInstanceState != null)
                return;

            ListConventionsFragment listConventionsFragment = ListConventionsFragment.newInstance();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container_main, listConventionsFragment)
                    .commit();

        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /*@Override
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
    }*/

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
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_main, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void leaveFragment() {
        getSupportFragmentManager().popBackStack();
    }

    public void switchToConventionFragment(Convention convention) {
        ShowConventionFragment fragment = ShowConventionFragment.newInstance(convention);

        gotoFragment(fragment);
    }

    public void switchtoEditConvention(Convention convention) {
        ModifyConventionFragment fragment = ModifyConventionFragment.newInstance(convention);

        gotoFragment(fragment);
    }

    public void switchtoEditConventionYear(ConventionYear conventionYear) {
        ModifyConventionYearFragment fragment = ModifyConventionYearFragment.newInstance(conventionYear);

        gotoFragment(fragment);
    }

    public void switchtoEditPhotoshoot(Photoshoot photoshoot) {
        ModifyPhotoshootFragment fragment = ModifyPhotoshootFragment.newInstance(photoshoot);

        gotoFragment(fragment);
    }

    public void switchToConventionYearFragment(Convention convention, ConventionYear conventionYear) {
        ShowConventionYearFragment fragment = ShowConventionYearFragment.newInstance(convention,
                conventionYear);

        gotoFragment(fragment);
    }

    @Override
    public void onConventionFragmentInteraction() {
        ModifyConventionFragment fragment = ModifyConventionFragment.newInstance();

        gotoFragment(fragment);
    }

    @Override
    public void onShowConventionFragmentInteraction(Convention convention) {
        ModifyConventionYearFragment fragment = ModifyConventionYearFragment.newInstance(convention);

        gotoFragment(fragment);
    }

    @Override
    public void onShowConventionYearFragmentInteraction(ConventionYear conventionYear) {
        ModifyPhotoshootFragment fragment = ModifyPhotoshootFragment.newInstance(conventionYear);

        gotoFragment(fragment);
    }

    @Override
    public void onNewConventionFragmentInteraction(Convention convention) {
        leaveFragment();
    }

    @Override
    public void onNewConventionYearFragmentInteraction() {
        leaveFragment();
    }

    @Override
    public void onNewPhotoshootFragmentInteraction() {
        leaveFragment();
    }
}
