package com.jamessimshaw.cosplaycompanion.controllers;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Controller;
import com.jamessimshaw.cosplaycompanion.R;
import com.jamessimshaw.cosplaycompanion.activities.MainActivity;

/**
 * Created by james on 10/2/17.
 */

public abstract class BaseLandingController extends Controller {

    protected Toolbar mToolbar;

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        View view = inflateView(inflater, container);
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
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
}
