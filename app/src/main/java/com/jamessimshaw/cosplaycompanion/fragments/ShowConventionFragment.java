package com.jamessimshaw.cosplaycompanion.fragments;

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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jamessimshaw.cosplaycompanion.R;
import com.jamessimshaw.cosplaycompanion.adapters.ConYearRecViewAdapter;
import com.jamessimshaw.cosplaycompanion.datasources.InternalAPI;
import com.jamessimshaw.cosplaycompanion.datasources.SQLiteDataSource;
import com.jamessimshaw.cosplaycompanion.models.Convention;
import com.jamessimshaw.cosplaycompanion.models.ConventionYear;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ShowConventionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ShowConventionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowConventionFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";

    private Convention mConvention;
    private ArrayList<ConventionYear> mConventionYears;
    private SQLiteDataSource mSQLiteDataSource;

    private OnFragmentInteractionListener mListener;

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
        if (getArguments() != null) {
            mConvention = getArguments().getParcelable(ARG_PARAM1);
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
                    mListener.onFragmentInteraction("create conventionYear", mConvention);
            }
        });

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(mConvention.getName());

        RecyclerView conventionDetailsRecyclerView = (RecyclerView)view
                .findViewById(R.id.list_fragment_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        conventionDetailsRecyclerView.setLayoutManager(linearLayoutManager);

        //mSQLiteDataSource = new SQLiteDataSource(getActivity());
        //mConventionYears = mSQLiteDataSource.read(mConvention);
        mConventionYears = new ArrayList<>();
        final ConYearRecViewAdapter adapter = new ConYearRecViewAdapter(mConvention, mConventionYears,
                getActivity());
        conventionDetailsRecyclerView.setAdapter(adapter);

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.internalAPIBase))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        InternalAPI internalAPI = retrofit.create(InternalAPI.class);
        internalAPI.getConventionYears(mConvention.getId()).enqueue(new Callback<List<ConventionYear>>() {
            @Override
            public void onResponse(Response<List<ConventionYear>> response, Retrofit retrofit) {
                mConventionYears.addAll(response.body());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });

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

}
