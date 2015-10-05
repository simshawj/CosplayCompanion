package com.jamessimshaw.cosplaycompanion.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jamessimshaw.cosplaycompanion.R;
import com.jamessimshaw.cosplaycompanion.adapters.ConventionRecViewAdapter;
import com.jamessimshaw.cosplaycompanion.datasources.SQLiteDataSource;
import com.jamessimshaw.cosplaycompanion.models.Convention;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ConventionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ConventionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConventionFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private ArrayList<Convention> mConventions;
    private SQLiteDataSource mSQLiteDataSource;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ConventionFragment.
     */
    public static ConventionFragment newInstance() {
        ConventionFragment fragment = new ConventionFragment();
        return fragment;
    }

    public ConventionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_convention, container, false);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        RecyclerView conventionRecyclerView = (RecyclerView)view
                .findViewById(R.id.convention_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        conventionRecyclerView.setLayoutManager(linearLayoutManager);

        mSQLiteDataSource = new SQLiteDataSource(getActivity());
        mConventions = mSQLiteDataSource.read();
        ConventionRecViewAdapter adapter = new ConventionRecViewAdapter(mConventions);
        conventionRecyclerView.setAdapter(adapter);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
