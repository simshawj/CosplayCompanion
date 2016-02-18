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

import com.jamessimshaw.cosplaycompanion.R;
import com.jamessimshaw.cosplaycompanion.adapters.PhotoshootRecViewAdapter;
import com.jamessimshaw.cosplaycompanion.dagger.components.DaggerNetworkComponent;
import com.jamessimshaw.cosplaycompanion.dagger.modules.NetworkModule;
import com.jamessimshaw.cosplaycompanion.datasources.InternalAPI;
import com.jamessimshaw.cosplaycompanion.datasources.SQLiteDataSource;
import com.jamessimshaw.cosplaycompanion.models.ConventionYear;
import com.jamessimshaw.cosplaycompanion.models.Photoshoot;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ShowConventionYearFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ShowConventionYearFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowConventionYearFragment extends Fragment {
    private static final String ARG_PARAM2 = "conventionYear";

    @Inject Retrofit mRetrofit;

    private ConventionYear mConventionYear;
    private SQLiteDataSource mSQLiteDataSource;
    private ArrayList<Photoshoot> mPhotoshoots;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param conventionYear ConventionYear to show.
     * @return A new instance of fragment ShowConventionYearFragment.
     */
    public static ShowConventionYearFragment newInstance(ConventionYear conventionYear) {
        ShowConventionYearFragment fragment = new ShowConventionYearFragment();
        Bundle args = new Bundle();
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
            mConventionYear = getArguments().getParcelable(ARG_PARAM2);
        }

        DaggerNetworkComponent.builder()
                .networkModule(new NetworkModule(getString(R.string.internalAPIBase)))
                .build().inject(this);
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
                    mListener.onFragmentInteraction("create photoshoot", mConventionYear);
            }
        });

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(mConventionYear.getDisplayName());

        RecyclerView conventionYearDetailsRecyclerView = (RecyclerView)view
                .findViewById(R.id.list_fragment_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        conventionYearDetailsRecyclerView.setLayoutManager(linearLayoutManager);

        mSQLiteDataSource = new SQLiteDataSource(getActivity());
        //mPhotoshoots = mSQLiteDataSource.read(mConventionYear);
        mPhotoshoots = new ArrayList<>();
        final PhotoshootRecViewAdapter adapter = new PhotoshootRecViewAdapter(mConventionYear,
                mPhotoshoots, getActivity());
        conventionYearDetailsRecyclerView.setAdapter(adapter);

        InternalAPI internalAPI = mRetrofit.create(InternalAPI.class);
        internalAPI.getPhotoShoots(mConventionYear.getId()).enqueue(new Callback<List<Photoshoot>>() {
            @Override
            public void onResponse(Call<List<Photoshoot>> call, Response<List<Photoshoot>> response) {
                mPhotoshoots.addAll(response.body());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Photoshoot>> call, Throwable t) {
                t.printStackTrace();
            }
        });

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
        void onFragmentInteraction(String event, Object item);
    }

}
