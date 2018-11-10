package com.jamessimshaw.cosplaycompanion.controllers;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jamessimshaw.cosplaycompanion.R;
import com.jamessimshaw.cosplaycompanion.activities.LoginActivity;
import com.jamessimshaw.cosplaycompanion.activities.MainActivity;
import com.jamessimshaw.cosplaycompanion.fragments.CreateSuggestionDialogFragment;
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
        Picasso.get()
                .load(user.getPhotoUrl()).fit().centerInside()
                .into(avatar);
        return view;
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
                getRouter().setRoot(RouterTransaction.with(controller).pushChangeHandler(new FadeChangeHandler()));
                break;
            case R.id.nav_feedback:
                CreateSuggestionDialogFragment modifyConventionYearDialogFragment = CreateSuggestionDialogFragment.newInstance();
                modifyConventionYearDialogFragment.show(getActivity().getFragmentManager(), "Modify Convention");
                break;
            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                return true;
        }


        DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
