package com.jamessimshaw.cosplaycompanion.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.jamessimshaw.cosplaycompanion.CosplayCompanionApplication;
import com.jamessimshaw.cosplaycompanion.R;
import com.jamessimshaw.cosplaycompanion.adapters.ConYearRecViewAdapter;
import com.jamessimshaw.cosplaycompanion.models.ConventionYear;
import com.jamessimshaw.cosplaycompanion.presenters.ListConventionYearsPresenterImpl;
import com.jamessimshaw.cosplaycompanion.views.ListConventionYearsView;

import javax.inject.Inject;

public class ShowConventionFragment extends Fragment implements ListConventionYearsView {
    private static final String ARG_PARAM1 = "param1";

    @Inject ListConventionYearsPresenterImpl mYearsPresenter;
    private ConYearRecViewAdapter mAdapter;
    private DatabaseReference mConventionReference;

    public static ShowConventionFragment newInstance(String reference) {
        ShowConventionFragment fragment = new ShowConventionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, reference);
        fragment.setArguments(args);
        return fragment;
    }

    public ShowConventionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((CosplayCompanionApplication)getActivity().getApplication()).getConventionYearsComponent().inject(this);
        mYearsPresenter.setView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lists_with_fab, container, false);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ModifyConventionYearFragment fragment = ModifyConventionYearFragment.newInstance(mConventionReference);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container_main, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(mConvention.getName());

        RecyclerView conventionDetailsRecyclerView = (RecyclerView)view
                .findViewById(R.id.list_fragment_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        conventionDetailsRecyclerView.setLayoutManager(linearLayoutManager);

        mAdapter = new ConYearRecViewAdapter(ConventionYear.class, R.layout.row_convention_year,
                ConYearRecViewAdapter.ViewHolder.class, mYearsPresenter.getFirebaseReference(mConventionReference), getActivity());
        conventionDetailsRecyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mYearsPresenter.detachView();
        mYearsPresenter = null;
    }

    // ListConventionYearsView methods

    @Override
    public void displayMessage(String warning) {
        Toast.makeText(getContext(), warning, Toast.LENGTH_LONG).show();
    }

    @Override
    public void done() {

    }

}
