package com.jamessimshaw.cosplaycompanion.controllers;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.TextView;
import android.widget.Toast;

import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.HorizontalChangeHandler;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jamessimshaw.cosplaycompanion.CosplayCompanionApplication;
import com.jamessimshaw.cosplaycompanion.R;
import com.jamessimshaw.cosplaycompanion.adapters.PhotoshootRecViewAdapter;
import com.jamessimshaw.cosplaycompanion.models.Photoshoot;
import com.jamessimshaw.cosplaycompanion.presenters.ListPhotoshootsPresenter;
import com.jamessimshaw.cosplaycompanion.views.ListPhotoshootsView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

public class ShowConventionYearController extends BaseInnerController implements ListPhotoshootsView {
    private static final String ARG_PARAM2 = "conventionYear";

    @Inject ListPhotoshootsPresenter mPresenter;
    private DatabaseReference mConventionYearRef;
    private PhotoshootRecViewAdapter mAdapter;
    private View mLayoutView;

    protected ShowConventionYearController(@Nullable Bundle args) {
        super(args);
    }

    public static ShowConventionYearController newInstance(String conventionYearRef) {
        Bundle args = new Bundle();
        args.putString(ARG_PARAM2, conventionYearRef);
        return new ShowConventionYearController(args);
    }

    public ShowConventionYearController() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public View inflateView(LayoutInflater inflater, ViewGroup container) {
        mLayoutView = inflater.inflate(R.layout.controller_base, container, false);

        ViewStub stub = mLayoutView.findViewById(R.id.contentHolder);
        stub.setLayoutResource(R.layout.lists_content);
        stub.inflate();

        if (getArgs().getString(ARG_PARAM2) != null) {
            mConventionYearRef = FirebaseDatabase.getInstance().getReferenceFromUrl(getArgs().getString(ARG_PARAM2));
        }
        ((CosplayCompanionApplication)getActivity().getApplication()).getPhotoshootsComponent().inject(this);
        mPresenter.setView(this);
        mPresenter.setConventionYearRef(mConventionYearRef);

        FloatingActionButton fab = (FloatingActionButton) mLayoutView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRouter().pushController(RouterTransaction.with(ModifyPhotoshootController.newInstance(mConventionYearRef.toString(), false)).pushChangeHandler(new HorizontalChangeHandler()));
            }
        });

        stub = (ViewStub) mLayoutView.findViewById(R.id.list_header);
        stub.setLayoutResource(R.layout.photoshoot_list_header);
        stub.inflate();

        RecyclerView conventionYearDetailsRecyclerView = (RecyclerView)mLayoutView
                .findViewById(R.id.list_fragment_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        conventionYearDetailsRecyclerView.setLayoutManager(linearLayoutManager);

        mAdapter = new PhotoshootRecViewAdapter(Photoshoot.class, R.layout.row_photoshoot, PhotoshootRecViewAdapter.ViewHolder.class, mPresenter.getPhotoshootListRef(), mPresenter.getPhotoshootDataRef(), getActivity(), getRouter());
        conventionYearDetailsRecyclerView.setAdapter(mAdapter);

        return mLayoutView;
    }

    @Override
    protected void onAttach(@NonNull View view) {
        super.onAttach(view);

        mPresenter.setView(this);
    }

    @Override
    protected void onDetach(@NonNull View view) {
        super.onDetach(view);

        mPresenter.detachView();
    }

    @Override
    public void updateTitle(String title) {
        ((TextView)mLayoutView.findViewById(R.id.convention_year)).setText(title);
    }

    @Override
    public void updateDates(long start, long end) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("cccc MMMM dd", Locale.getDefault());
        String dateString = dateFormat.format(new Date(start)) + " to " +
                dateFormat.format(new Date(end));
        ((TextView)mLayoutView.findViewById(R.id.convention_dates)).setText(dateString);
    }

    @Override
    public void setTitle(String title) {
        super.setTitle(title);
    }

    @Override
    public void displayMessage(String warning) {
        Toast.makeText(getActivity(), warning, Toast.LENGTH_LONG).show();
    }

}
