package com.jamessimshaw.cosplaycompanion.controllers;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Controller;
import com.jamessimshaw.cosplaycompanion.R;
import com.jamessimshaw.cosplaycompanion.activities.MainActivity;

/**
 * Created by james on 10/2/17.
 */

public abstract class BaseInnerController extends Controller {

    protected Toolbar mToolbar;

    protected BaseInnerController(@Nullable Bundle args) {
        super(args);
    }

    public BaseInnerController() {
        super();
    }

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        View view = inflateView(inflater, container);
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                getRouter().popCurrentController();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onAttach(@NonNull View view) {
        super.onAttach(view);

        ((MainActivity)getActivity()).setSupportActionBar(mToolbar);
        ((MainActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((MainActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
