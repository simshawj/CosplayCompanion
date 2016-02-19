package com.jamessimshaw.cosplaycompanion.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.jamessimshaw.cosplaycompanion.R;
import com.jamessimshaw.cosplaycompanion.models.Convention;
import com.jamessimshaw.cosplaycompanion.models.ConventionYear;
import com.jamessimshaw.cosplaycompanion.presenters.ModifyConventionYearPresenter;
import com.jamessimshaw.cosplaycompanion.presenters.ModifyConventionYearPresenterImpl;
import com.jamessimshaw.cosplaycompanion.views.ModifyConventionYearView;

import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by james on 10/11/15.
 */
public class ModifyConventionYearFragment extends Fragment implements ModifyConventionYearView {
    private OnFragmentInteractionListener mListener;
    private ModifyConventionYearPresenter mPresenter;

    @Bind(R.id.conventionLocation) EditText mLocationEditText;
    @Bind(R.id.startDateButton) Button mStartButton;
    @Bind(R.id.endDateButton) Button mEndButton;

    public static ModifyConventionYearFragment newInstance(Convention convention) {
        ModifyConventionYearFragment fragment = new ModifyConventionYearFragment();
        Bundle args = new Bundle();
        args.putParcelable("convention", convention);
        fragment.setArguments(args);
        return fragment;
    }

    public static ModifyConventionYearFragment newInstance(ConventionYear conventionYear) {
        ModifyConventionYearFragment fragment = new ModifyConventionYearFragment();
        Bundle args = new Bundle();
        args.putParcelable("conventionYear", conventionYear);
        fragment.setArguments(args);
        return fragment;
    }

    public ModifyConventionYearFragment() {
        // Required Default constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Convention convention = null;
        ConventionYear conventionYear = null;
        if (getArguments() != null) {
            convention = getArguments().getParcelable("convention");
            conventionYear = getArguments().getParcelable("conventionYear");
        }
        if(mPresenter == null)
            mPresenter = new ModifyConventionYearPresenterImpl(this, convention, conventionYear);
        else {
            mPresenter.setView(this);
            mPresenter.setConvention(convention);
            mPresenter.setConventionYear(conventionYear);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_convention_year, container, false);
        setHasOptionsMenu(true);
        ButterKnife.bind(this, view);
        mStartButton.setOnClickListener(mStartButtonListener);
        mEndButton.setOnClickListener(mEndButtonListener);
        mPresenter.requestInitialData();

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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.new_item_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_submit) {
            mPresenter.submit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        mPresenter.removeView(this);
    }

    // Listeners

    private View.OnClickListener mStartButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DatePickerDialogFragment datePickerDialogFragment = new DatePickerDialogFragment();
            datePickerDialogFragment.setListener(mStartDateListener);
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            datePickerDialogFragment.show(transaction, "Start Date");
        }
    };

    private View.OnClickListener mEndButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DatePickerDialogFragment datePickerDialogFragment = new DatePickerDialogFragment();
            datePickerDialogFragment.setListener(mEndDateListener);
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            datePickerDialogFragment.show(transaction, "End Date");
        }
    };

    private DatePickerDialog.OnDateSetListener mStartDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(year, monthOfYear, dayOfMonth);
                    mPresenter.setStartDate(calendar.getTime());
                }
            };

    private DatePickerDialog.OnDateSetListener mEndDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(year, monthOfYear, dayOfMonth);
                    mPresenter.setFinishDate(calendar.getTime());
                }
            };


    public interface OnFragmentInteractionListener {
        void onModifyFragmentInteraction();
    }

    // ModifyConventionYearView methods

    @Override
    public String getLocation() {
        return mLocationEditText.getText().toString();
    }

    @Override
    public void displayStartDate(String date) {
        mStartButton.setText(date);
    }

    @Override
    public void displayFinishDate(String date) {
        mEndButton.setText(date);
    }

    @Override
    public void displayLocation(String location) {
        mLocationEditText.setText(location);
    }

    @Override
    public void displayWarning(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void done() {
        mListener.onModifyFragmentInteraction();
    }

}