package com.jamessimshaw.cosplaycompanion.controllers;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Toast;

import com.bluelinelabs.conductor.RouterTransaction;
import com.jamessimshaw.cosplaycompanion.CosplayCompanionApplication;
import com.jamessimshaw.cosplaycompanion.R;
import com.jamessimshaw.cosplaycompanion.adapters.ConventionRecViewAdapter;
import com.jamessimshaw.cosplaycompanion.models.Convention;
import com.jamessimshaw.cosplaycompanion.presenters.ListConventionsPresenter;
import com.jamessimshaw.cosplaycompanion.views.ListConventionsView;

import javax.inject.Inject;

public class ListConventionsController extends BaseLandingController implements ListConventionsView {

    @Inject ListConventionsPresenter mPresenter;
    private ConventionRecViewAdapter mAdapter;

    public static ListConventionsController newInstance() {
        return new ListConventionsController();
    }

    public ListConventionsController() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public View inflateView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.controller_base, container, false);

        ViewStub stub = view.findViewById(R.id.contentHolder);
        stub.setLayoutResource(R.layout.lists_content);
        stub.inflate();

        ((CosplayCompanionApplication)(getActivity().getApplication())).getConventionsComponent().inject(this);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRouter().pushController(RouterTransaction.with(ModifyConventionController.newInstance()));
            }
        });

        RecyclerView conventionRecyclerView = (RecyclerView)view.findViewById(R.id.list_fragment_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        conventionRecyclerView.setLayoutManager(linearLayoutManager);

        mAdapter = new ConventionRecViewAdapter(Convention.class, R.layout.row_convention, ConventionRecViewAdapter.ViewHolder.class, mPresenter.getFirebaseReference(), getActivity(), getRouter());
        conventionRecyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    protected void onDetach(@NonNull View view) {
        super.onDetach(view);
        mPresenter.detachView();
    }

    @Override
    protected void onAttach(@NonNull View view) {
        super.onAttach(view);
        setTitle("Conventions");
        mPresenter.setView(this);
    }

    @Override
    public void displayMessage(String warning) {
        Toast.makeText(getActivity(), warning, Toast.LENGTH_LONG).show();
    }
}
