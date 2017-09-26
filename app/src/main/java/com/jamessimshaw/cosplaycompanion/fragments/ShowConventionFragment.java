package com.jamessimshaw.cosplaycompanion.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jamessimshaw.cosplaycompanion.CosplayCompanionApplication;
import com.jamessimshaw.cosplaycompanion.R;
import com.jamessimshaw.cosplaycompanion.adapters.ConYearRecViewAdapter;
import com.jamessimshaw.cosplaycompanion.models.ConventionYear;
import com.jamessimshaw.cosplaycompanion.presenters.ListConventionYearsPresenterImpl;
import com.jamessimshaw.cosplaycompanion.views.ListConventionYearsView;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

public class ShowConventionFragment extends Fragment implements ListConventionYearsView {
    private static final String ARG_PARAM1 = "param1";

    @Inject ListConventionYearsPresenterImpl mYearsPresenter;
    private ConYearRecViewAdapter mAdapter;
    private DatabaseReference mConventionReference;
    private View mLayoutView;

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
        mLayoutView = inflater.inflate(R.layout.fragment_lists_with_fab, container, false);

        mConventionReference = FirebaseDatabase.getInstance().getReferenceFromUrl(getArguments().getString(ARG_PARAM1));

        FloatingActionButton fab = (FloatingActionButton) mLayoutView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ModifyConventionYearFragment fragment = ModifyConventionYearFragment.newInstance(mConventionReference.toString(), false);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container_main, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        ViewStub stub = (ViewStub) mLayoutView.findViewById(R.id.list_header);
        stub.setLayoutResource(R.layout.convention_year_list_header);
        stub.inflate();

        mYearsPresenter.setConventionReference(mConventionReference);

        RecyclerView conventionDetailsRecyclerView = mLayoutView.findViewById(R.id.list_fragment_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        conventionDetailsRecyclerView.setLayoutManager(linearLayoutManager);

        mAdapter = new ConYearRecViewAdapter(ConventionYear.class, R.layout.row_convention_year, ConYearRecViewAdapter.ViewHolder.class, mYearsPresenter.getEventsRef(), mYearsPresenter.getEventsDataRef(), getActivity());
        conventionDetailsRecyclerView.setAdapter(mAdapter);

        return mLayoutView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mYearsPresenter.setView(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        mYearsPresenter.detachView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    // ListConventionYearsView methods

    @Override
    public void displayMessage(String warning) {
        Toast.makeText(getContext(), warning, Toast.LENGTH_LONG).show();
    }

    @Override
    public void done() {

    }

    @Override
    public void updateData(List<ConventionYear> conventionYears) {
//        mAdapter.updateData(conventionYears);
    }

    @Override
    public void updateConventionLogo(String url) {
        Picasso.with(getActivity())
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
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }
}
