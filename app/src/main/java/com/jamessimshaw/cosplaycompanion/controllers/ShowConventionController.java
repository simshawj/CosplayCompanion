package com.jamessimshaw.cosplaycompanion.controllers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jamessimshaw.cosplaycompanion.CosplayCompanionApplication;
import com.jamessimshaw.cosplaycompanion.R;
import com.jamessimshaw.cosplaycompanion.adapters.ConYearRecViewAdapter;
import com.jamessimshaw.cosplaycompanion.fragments.ModifyConventionYearDialogFragment;
import com.jamessimshaw.cosplaycompanion.models.ConventionYear;
import com.jamessimshaw.cosplaycompanion.presenters.ListConventionYearsPresenterImpl;
import com.jamessimshaw.cosplaycompanion.views.ListConventionYearsView;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ShowConventionController extends BaseInnerController implements ListConventionYearsView {
    private static final String ARG_PARAM1 = "param1";

    @Inject ListConventionYearsPresenterImpl mYearsPresenter;
    private ConYearRecViewAdapter mAdapter;
    private DatabaseReference mConventionReference;
    private View mLayoutView;

    public static ShowConventionController newInstance(String reference) {
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, reference);
        return new ShowConventionController(args);
    }

    public ShowConventionController() {
        // Required empty public constructor
    }

    private ShowConventionController(@Nullable Bundle args) {
        super(args);
    }



    @NonNull
    @Override
    public View inflateView(LayoutInflater inflater, ViewGroup container) {
        mLayoutView = inflater.inflate(R.layout.controller_base, container, false);

        ViewStub stub = mLayoutView.findViewById(R.id.contentHolder);
        stub.setLayoutResource(R.layout.lists_content);
        stub.inflate();

        mConventionReference = FirebaseDatabase.getInstance().getReferenceFromUrl(getArgs().getString(ARG_PARAM1));
        ((CosplayCompanionApplication)getActivity().getApplication()).getConventionYearsComponent().inject(this);
        FloatingActionButton fab = (FloatingActionButton) mLayoutView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ModifyConventionYearDialogFragment modifyConventionYearDialogFragment = ModifyConventionYearDialogFragment.newInstance(mConventionReference.toString(), false);
                modifyConventionYearDialogFragment.show(getActivity().getFragmentManager(), "Modify Convention");
            }
        });

        stub = (ViewStub) mLayoutView.findViewById(R.id.list_header);
        stub.setLayoutResource(R.layout.convention_year_list_header);
        stub.inflate();

        mYearsPresenter.setConventionReference(mConventionReference);

        RecyclerView conventionDetailsRecyclerView = mLayoutView.findViewById(R.id.list_fragment_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        conventionDetailsRecyclerView.setLayoutManager(linearLayoutManager);

        mAdapter = new ConYearRecViewAdapter(ConventionYear.class, R.layout.row_convention_year, ConYearRecViewAdapter.ViewHolder.class, mYearsPresenter.getEventsRef(), mYearsPresenter.getEventsDataRef(), getActivity(), getRouter());
        conventionDetailsRecyclerView.setAdapter(mAdapter);

        return mLayoutView;
    }

    @Override
    protected void onAttach(@NonNull View view) {
        super.onAttach(view);
        mYearsPresenter.setView(this);
        mAdapter.startListening();
    }

    @Override
    protected void onDetach(@NonNull View view) {
        super.onDetach(view);
        mAdapter.stopListening();
        mYearsPresenter.detachView();
    }

    // ListConventionYearsView methods

    @Override
    public void displayMessage(String warning) {
        Toast.makeText(getActivity(), warning, Toast.LENGTH_LONG).show();
    }

    @Override
    public void updateConventionLogo(String url) {
        Picasso.get()
                .load(url).fit().centerInside()
                .into((ImageView)mLayoutView.findViewById(R.id.convention_logo));
    }

    @Override
    public void updateConventionName(String name) {
        ((TextView)mLayoutView.findViewById(R.id.convention_name)).setText(name);
    }

    @Override
    public void updateConventionDescription(String description) {
        ((TextView)mLayoutView.findViewById(R.id.conDescriptionTextView)).setText(description);
    }

    @Override
    public void setTitle(String title) {
        super.setTitle(title);
    }
}
