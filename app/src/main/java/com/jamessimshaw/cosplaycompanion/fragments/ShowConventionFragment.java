package com.jamessimshaw.cosplaycompanion.fragments;

import android.content.Context;
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

import com.jamessimshaw.cosplaycompanion.CosplayCompanionApplication;
import com.jamessimshaw.cosplaycompanion.R;
import com.jamessimshaw.cosplaycompanion.adapters.ConYearRecViewAdapter;
import com.jamessimshaw.cosplaycompanion.models.Convention;
import com.jamessimshaw.cosplaycompanion.models.ConventionYear;
import com.jamessimshaw.cosplaycompanion.presenters.ListConventionYearsPresenterImpl;
import com.jamessimshaw.cosplaycompanion.views.ListConventionYearsView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ShowConventionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ShowConventionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowConventionFragment extends Fragment implements ListConventionYearsView {
    private static final String ARG_PARAM1 = "param1";

    @Inject ListConventionYearsPresenterImpl mYearsPresenter;
    private OnFragmentInteractionListener mListener;
    private Convention mConvention;
    private ConYearRecViewAdapter mAdapter;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param convention The convention to show
     * @return A new instance of fragment ShowConventionFragment.
     */
    public static ShowConventionFragment newInstance(Convention convention) {
        ShowConventionFragment fragment = new ShowConventionFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, convention);
        fragment.setArguments(args);
        return fragment;
    }

    public ShowConventionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mConvention = null;
        if (getArguments() != null) {
            mConvention = getArguments().getParcelable(ARG_PARAM1);
        }
        ((CosplayCompanionApplication)getActivity().getApplication()).getConventionYearsComponent()
                .inject(this);
        mYearsPresenter.setView(this);
        mYearsPresenter.setConvention(mConvention);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lists_with_fab, container, false);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null)
                    mListener.onFragmentInteraction("create conventionYear", mConvention);
            }
        });

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(mConvention.getName());

        RecyclerView conventionDetailsRecyclerView = (RecyclerView)view
                .findViewById(R.id.list_fragment_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        conventionDetailsRecyclerView.setLayoutManager(linearLayoutManager);

        mAdapter = new ConYearRecViewAdapter(mConvention, new ArrayList<ConventionYear>(),
                getActivity());
        conventionDetailsRecyclerView.setAdapter(mAdapter);

        mYearsPresenter.requestConventionYears();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        mYearsPresenter.removeView(this);
        mYearsPresenter = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String event, Object item);
    }

    // ListConventionYearsView methods

    @Override
    public void addConventionYears(List<ConventionYear> conventionYears) {
        mAdapter.addConventionYears(conventionYears);
    }

    @Override
    public void displayWarning(String warning) {
        Toast.makeText(getContext(), warning, Toast.LENGTH_LONG).show();
    }

    @Override
    public void done() {

    }

}
