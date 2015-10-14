package com.jamessimshaw.cosplaycompanion.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jamessimshaw.cosplaycompanion.R;
import com.jamessimshaw.cosplaycompanion.models.ConventionYear;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ShowConventionYearFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ShowConventionYearFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowConventionYearFragment extends Fragment {
    private static final String ARG_PARAM1 = "conventionYear";

    private ConventionYear mConventionYear;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param conventionYear ConventionYear to show.
     * @return A new instance of fragment ShowConventionYearFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShowConventionYearFragment newInstance(ConventionYear conventionYear) {
        ShowConventionYearFragment fragment = new ShowConventionYearFragment();
        Bundle args = new Bundle();
        //args.putParcelable(ARG_PARAM1, conventionYear);
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
            //mConventionYear = getArguments().getParcelable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText(R.string.hello_blank_fragment);
        return textView;
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
