package com.jamessimshaw.cosplaycompanion.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jamessimshaw.cosplaycompanion.R;
import com.jamessimshaw.cosplaycompanion.adapters.ConYearRecViewAdapter;
import com.jamessimshaw.cosplaycompanion.adapters.PhotoshootRecViewAdapter;
import com.jamessimshaw.cosplaycompanion.datasources.SQLiteDataSource;
import com.jamessimshaw.cosplaycompanion.models.Convention;
import com.jamessimshaw.cosplaycompanion.models.ConventionYear;
import com.jamessimshaw.cosplaycompanion.models.Photoshoot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ShowConventionYearFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ShowConventionYearFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowConventionYearFragment extends Fragment {
    private static final String ARG_PARAM1 = "convention";
    private static final String ARG_PARAM2 = "conventionYear";

    private ConventionYear mConventionYear;
    private Convention mConvention;
    private SQLiteDataSource mSQLiteDataSource;
    private ArrayList<Photoshoot> mPhotoshoots;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param convention Convention associated with the ConventionYear
     * @param conventionYear ConventionYear to show.
     * @return A new instance of fragment ShowConventionYearFragment.
     */
    public static ShowConventionYearFragment newInstance(Convention convention, ConventionYear conventionYear) {
        ShowConventionYearFragment fragment = new ShowConventionYearFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, convention);
        args.putParcelable(ARG_PARAM2, conventionYear);
        fragment.setArguments(args);
        return fragment;
    }

    public ShowConventionYearFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mConvention = getArguments().getParcelable(ARG_PARAM1);
            mConventionYear = getArguments().getParcelable(ARG_PARAM2);
        }
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
                    mListener.onShowConventionYearFragmentInteraction(mConventionYear);
            }
        });

        String title = mConvention.getName() + " " + mConventionYear.getYearAsString();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(title);

        RecyclerView conventionYearDetailsRecyclerView = (RecyclerView)view
                .findViewById(R.id.list_fragment_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        conventionYearDetailsRecyclerView.setLayoutManager(linearLayoutManager);

        mSQLiteDataSource = new SQLiteDataSource(getActivity());
        mPhotoshoots = mSQLiteDataSource.read(mConventionYear);
        PhotoshootRecViewAdapter adapter = new PhotoshootRecViewAdapter(mConvention, mConventionYear,
                mPhotoshoots, getActivity());
        conventionYearDetailsRecyclerView.setAdapter(adapter);

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
        public void onShowConventionYearFragmentInteraction(ConventionYear conventionYear);
    }

}
