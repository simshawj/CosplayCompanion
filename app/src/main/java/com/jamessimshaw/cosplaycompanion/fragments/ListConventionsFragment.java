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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jamessimshaw.cosplaycompanion.R;
import com.jamessimshaw.cosplaycompanion.adapters.ConventionRecViewAdapter;
import com.jamessimshaw.cosplaycompanion.datasources.InternalAPI;
import com.jamessimshaw.cosplaycompanion.datasources.SQLiteDataSource;
import com.jamessimshaw.cosplaycompanion.models.Convention;
import com.jamessimshaw.cosplaycompanion.serialization.ConventionDeserializer;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListConventionsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListConventionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListConventionsFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private ArrayList<Convention> mConventions;
    private SQLiteDataSource mSQLiteDataSource;

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

        //mSQLiteDataSource = new SQLiteDataSource(getActivity());
        //mConventions = mSQLiteDataSource.read();
        mConventions = new ArrayList<>();
        final ConventionRecViewAdapter adapter = new ConventionRecViewAdapter(mConventions, getActivity());
        conventionRecyclerView.setAdapter(adapter);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Convention.class, new ConventionDeserializer(getContext()))
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.internalAPIBase))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        InternalAPI internalAPI = retrofit.create(InternalAPI.class);
        internalAPI.getConventions().enqueue(new Callback<List<Convention>>() {
            @Override
            public void onResponse(Call<List<Convention>> call, Response<List<Convention>> response) {
                mConventions.addAll(response.body());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Convention>> call, Throwable t) {
                t.printStackTrace();
            }
        });

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
