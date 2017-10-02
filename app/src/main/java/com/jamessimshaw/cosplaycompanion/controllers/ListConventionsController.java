package com.jamessimshaw.cosplaycompanion.controllers;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.RouterTransaction;
import com.jamessimshaw.cosplaycompanion.CosplayCompanionApplication;
import com.jamessimshaw.cosplaycompanion.R;
import com.jamessimshaw.cosplaycompanion.activities.MainActivity;
import com.jamessimshaw.cosplaycompanion.adapters.ConventionRecViewAdapter;
import com.jamessimshaw.cosplaycompanion.models.Convention;
import com.jamessimshaw.cosplaycompanion.presenters.ListConventionsPresenter;
import com.jamessimshaw.cosplaycompanion.views.ListConventionsView;

import javax.inject.Inject;

public class ListConventionsController extends Controller implements ListConventionsView {

    @Inject ListConventionsPresenter mPresenter;
    private ConventionRecViewAdapter mAdapter;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ListConventionsController.
     */
    public static ListConventionsController newInstance() {
        return new ListConventionsController();
    }

    public ListConventionsController() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_lists_with_fab, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((MainActivity)getActivity()).setSupportActionBar(toolbar);
        ((CosplayCompanionApplication)(getActivity().getApplication())).getConventionsComponent().inject(this);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRouter().pushController(RouterTransaction.with(ModifyConventionController.newInstance()));
            }
        });

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Conventions");

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
        mPresenter.setView(this);
    }

    @Override
    public void displayMessage(String warning) {
        Toast.makeText(getActivity(), warning, Toast.LENGTH_LONG).show();
    }

    @Override
    public void done() {
        // Nothing until a further version
    }
}
