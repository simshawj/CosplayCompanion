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
import com.jamessimshaw.cosplaycompanion.adapters.ConventionRecViewAdapter;
import com.jamessimshaw.cosplaycompanion.models.Convention;
import com.jamessimshaw.cosplaycompanion.presenters.ListConventionsPresenter;
import com.jamessimshaw.cosplaycompanion.views.ListConventionsView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListConventionsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListConventionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListConventionsFragment extends Fragment implements ListConventionsView {


    private OnFragmentInteractionListener mListener;
    @Inject ListConventionsPresenter mPresenter;
    private ConventionRecViewAdapter mAdapter;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ListConventionsFragment.
     */
    public static ListConventionsFragment newInstance() {
        ListConventionsFragment fragment = new ListConventionsFragment();
        return fragment;
    }

    public ListConventionsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((CosplayCompanionApplication)(getActivity().getApplication())).getConventionsComponent()
                .inject(this);
        mPresenter.setView(this);
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
                    mListener.onFragmentInteraction("create convention", null);
            }
        });

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Conventions");

        RecyclerView conventionRecyclerView = (RecyclerView)view
                .findViewById(R.id.list_fragment_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        conventionRecyclerView.setLayoutManager(linearLayoutManager);

        mAdapter = new ConventionRecViewAdapter(new ArrayList<Convention>(), getActivity());
        conventionRecyclerView.setAdapter(mAdapter);
        mPresenter.requestConventions();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnFragmentInteractionListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        mPresenter.removeView(this);
        mPresenter = null;
    }

    @Override
    public void displayWarning(String warning) {
        Toast.makeText(getContext(), warning, Toast.LENGTH_LONG).show();
    }

    @Override
    public void done() {
        // Nothing until a further version
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

    // ListConventionsView methods

    @Override
    public void addConventions(List<Convention> conventions) {
        mAdapter.addNewConventions(conventions);
    }

}
