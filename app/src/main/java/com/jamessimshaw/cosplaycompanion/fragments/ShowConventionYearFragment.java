package com.jamessimshaw.cosplaycompanion.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jamessimshaw.cosplaycompanion.CosplayCompanionApplication;
import com.jamessimshaw.cosplaycompanion.R;
import com.jamessimshaw.cosplaycompanion.adapters.PhotoshootRecViewAdapter;
import com.jamessimshaw.cosplaycompanion.models.Photoshoot;
import com.jamessimshaw.cosplaycompanion.presenters.ListPhotoshootsPresenter;
import com.jamessimshaw.cosplaycompanion.views.ListPhotoshootsView;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ShowConventionYearFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ShowConventionYearFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowConventionYearFragment extends Fragment implements ListPhotoshootsView {
    private static final String ARG_PARAM2 = "conventionYear";

    @Inject ListPhotoshootsPresenter mPresenter;
    private DatabaseReference mConventionYearRef;
    private PhotoshootRecViewAdapter mAdapter;
    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param conventionYearRef ConventionYear to show.
     * @return A new instance of fragment ShowConventionYearFragment.
     */
    public static ShowConventionYearFragment newInstance(String conventionYearRef) {
        ShowConventionYearFragment fragment = new ShowConventionYearFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM2, conventionYearRef);
        fragment.setArguments(args);
        return fragment;
    }

    public ShowConventionYearFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().getString(ARG_PARAM2) != null) {
            mConventionYearRef = FirebaseDatabase.getInstance().getReferenceFromUrl(getArguments().getString(ARG_PARAM2));
        }
        ((CosplayCompanionApplication)getActivity().getApplication()).getPhotoshootsComponent().inject(this);
        mPresenter.setView(this);
        mPresenter.setConventionYearRef(mConventionYearRef);
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
                    mListener.onFragmentInteraction("create photoshoot", mConventionYearRef.toString());
            }
        });

//        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(mConventionYearRef.getDisplayName());

        RecyclerView conventionYearDetailsRecyclerView = (RecyclerView)view
                .findViewById(R.id.list_fragment_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        conventionYearDetailsRecyclerView.setLayoutManager(linearLayoutManager);

        mAdapter = new PhotoshootRecViewAdapter(Photoshoot.class, R.layout.row_photoshoot, PhotoshootRecViewAdapter.ViewHolder.class, mPresenter.getPhotoshootListRef(), mPresenter.getPhotoshootDataRef(), getActivity());
        conventionYearDetailsRecyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onAttach(Context contexty) {
        super.onAttach(contexty);
        try {
            mListener = (OnFragmentInteractionListener) contexty;
        } catch (ClassCastException e) {
            throw new ClassCastException(contexty.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        mPresenter.detachView();
        mPresenter = null;
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
        void onFragmentInteraction(String event, String item);
    }

    // ListPhotoshootsView Methods

    @Override
    public void displayMessage(String warning) {
        Toast.makeText(getContext(), warning, Toast.LENGTH_LONG).show();
    }

    @Override
    public void done() {

    }

}
