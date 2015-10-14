package com.jamessimshaw.cosplaycompanion.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jamessimshaw.cosplaycompanion.R;
import com.jamessimshaw.cosplaycompanion.models.ConventionYear;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewPhotoshootFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewPhotoshootFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewPhotoshootFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";

    private ConventionYear mConventionYear;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param conventionYear ConventionYear to add the photoshoot to
     * @return A new instance of fragment NewPhotoshootFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewPhotoshootFragment newInstance(ConventionYear conventionYear) {
        NewPhotoshootFragment fragment = new NewPhotoshootFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, conventionYear);
        fragment.setArguments(args);
        return fragment;
    }

    public NewPhotoshootFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mConventionYear = getArguments().getParcelable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_photoshoot, container, false);
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
        public void onNewPhotoshootFragmentInteraction();
    }

}
