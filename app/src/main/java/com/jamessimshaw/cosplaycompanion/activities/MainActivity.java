package com.jamessimshaw.cosplaycompanion.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.jamessimshaw.cosplaycompanion.R;
import com.jamessimshaw.cosplaycompanion.controllers.ListConventionsController;

public class MainActivity extends AppCompatActivity {

    private Router mRouter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        ViewGroup container = (ViewGroup) findViewById(R.id.fragment_container_main);

        mRouter = Conductor.attachRouter(this, container, savedInstanceState);
        if (!mRouter.hasRootController()) {
            mRouter.setRoot(RouterTransaction.with(ListConventionsController.newInstance()));
        }

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.setDrawerSlideAnimationEnabled(false);
//        toggle.syncState();
//
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
//
//        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_main);
//
//        TextView usernameTextView = (TextView) headerView.findViewById(R.id.username);
//        TextView emailTextView = (TextView) headerView.findViewById(R.id.email);
//        ImageView avatar = (ImageView) headerView.findViewById(R.id.imageView);
//
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        usernameTextView.setText(user.getDisplayName());
//        emailTextView.setText(user.getEmail());
//        Picasso.with(this)
//                .load(user.getPhotoUrl()).fit().centerInside()
//                .into(avatar);

//        if (savedInstanceState != null)
//            return;

//        getSupportFragmentManager().addOnBackStackChangedListener(this);
//
//        Fragment fragment = ListConventionsController.newInstance();
//        //Make sure the container for the fragments is present
//        if (findViewById(R.id.fragment_container_main) != null) {
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.fragment_container_main, fragment)
//                    .commit();
//        }
    }

    @Override
    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
//                finish();
//            } else {
//                super.onBackPressed();
//            }
//        }
        if (!mRouter.handleBack()) {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        // Handle navigation view item clicks here.
//        Fragment fragment;
//        switch (item.getItemId()) {
//            case R.id.nav_conventions:
//                fragment = ListConventionsController.newInstance();
//                getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.fragment_container_main, fragment)
//                        .commit();
//                break;
//            case R.id.nav_feedback:
//                fragment = CreateSuggestionController.newInstance();
//                getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.fragment_container_main, fragment)
//                        .commit();
//                break;
//            case R.id.nav_logout:
//                FirebaseAuth.getInstance().signOut();
//                finish();
//                break;
//        }
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }

//    private void clearBackStack() {
//        FragmentManager fm = getSupportFragmentManager();
//        int entries = fm.getBackStackEntryCount();
//        for (int i = 0; i < entries; i++) {
//            fm.popBackStack();
//        }
//    }
//
//    private void gotoFragment(Fragment fragment) {
//        //Make sure the container for the fragments is present
//        if (findViewById(R.id.fragment_container_main) != null) {
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.fragment_container_main, fragment)
//                    .addToBackStack(null)
//                    .commit();
//        }
//    }
//
//    private void leaveFragment() {
//        getSupportFragmentManager().popBackStack();
//    }
//
//    @Override
//    public void onModifyFragmentInteraction() {
//        leaveFragment();
//    }
//
//    @Override
//    public void onFragmentInteraction(String event, String item) {
//        Fragment fragment = null;
//
//        switch(event) {
//            case "show convention":
//                fragment = ShowConventionController.newInstance(item);
//                break;
//            case "show conventionYear":
//                fragment = ShowConventionYearController.newInstance(item);
//                break;
//            case "create conventionYear":
//                fragment = ModifyConventionYearController.newInstance(item, false);
//                break;
//            case "create photoshoot":
//                fragment = ModifyPhotoshootController.newInstance(item, false);
//                break;
//            case "edit convention":
//                fragment = ModifyConventionController.newInstance(item);
//                break;
//            case "edit conventionYear":
//                fragment = ModifyConventionYearController.newInstance(item, true);
//                break;
//            case "edit photoshoot":
//                fragment = ModifyPhotoshootController.newInstance(item, true);
//            default: break;
//        }
//
//        if (fragment != null)
//            gotoFragment(fragment);
//    }
//
//    @Override
//    public void onBackStackChanged() {
//        boolean landing = getSupportFragmentManager().getBackStackEntryCount() == 0;
//
//        getSupportActionBar().setDisplayHomeAsUpEnabled(!landing);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//    }
}
