package com.jamessimshaw.cosplaycompanion.controllers;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.RouterTransaction;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jamessimshaw.cosplaycompanion.R;
import com.jamessimshaw.cosplaycompanion.activities.MainActivity;
import com.squareup.picasso.Picasso;

/**
 * Created by james on 10/2/17.
 */

public abstract class BaseLandingController extends Controller implements NavigationView.OnNavigationItemSelectedListener {

    protected Toolbar mToolbar;

    protected BaseLandingController() {
        super();
    }

    protected BaseLandingController(@Nullable Bundle args) {
        super(args);
    }

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        View view = inflateView(inflater, container);
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);

        ((MainActivity)getActivity()).setSupportActionBar(mToolbar);

        DrawerLayout drawer = (DrawerLayout) view.findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.setDrawerSlideAnimationEnabled(false);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) drawer.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_main);

        TextView usernameTextView = (TextView) headerView.findViewById(R.id.username);
        TextView emailTextView = (TextView) headerView.findViewById(R.id.email);
        ImageView avatar = (ImageView) headerView.findViewById(R.id.imageView);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        usernameTextView.setText(user.getDisplayName());
        emailTextView.setText(user.getEmail());
        Picasso.with(getActivity())
                .load(user.getPhotoUrl()).fit().centerInside()
                .into(avatar);
        return view;
    }

    @Override
    protected void onAttach(@NonNull View view) {
        super.onAttach(view);

        ((MainActivity)getActivity()).setSupportActionBar(mToolbar);
    }

    @NonNull
    protected abstract View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container);

    protected void setTitle(String title) {
        mToolbar.setTitle(title);
    }

    protected void setTitle(@StringRes int stringresource) {
        mToolbar.setTitle(stringresource);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        Controller controller;
        switch (item.getItemId()) {
            case R.id.nav_conventions:
                controller = ListConventionsController.newInstance();
                getRouter().pushController(RouterTransaction.with(controller));
                break;
            case R.id.nav_feedback:
                controller = CreateSuggestionController.newInstance();
                getRouter().pushController(RouterTransaction.with(controller));
                break;
            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                getActivity().finish();
                return true;
        }


        DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
