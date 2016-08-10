package com.jamessimshaw.cosplaycompanion.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.TimePicker;
import android.widget.Toast;

import com.jamessimshaw.cosplaycompanion.R;
import com.jamessimshaw.cosplaycompanion.models.ConventionYear;
import com.jamessimshaw.cosplaycompanion.models.Photoshoot;
import com.jamessimshaw.cosplaycompanion.presenters.ModifyPhotoshootPresenter;
import com.jamessimshaw.cosplaycompanion.presenters.ModifyPhotoshootPresenterImpl;
import com.jamessimshaw.cosplaycompanion.views.ModifyPhotoshootView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ModifyPhotoshootFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ModifyPhotoshootFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ModifyPhotoshootFragment extends Fragment implements ModifyPhotoshootView {
    private static final String ARG_PARAM1 = "param1";

    private ModifyPhotoshootPresenter mPresenter;

    @BindView(R.id.dateButton) Button mStartDateButton;
    @BindView(R.id.timeButton) Button mStartTimeButton;
    @BindView(R.id.locationEditText) EditText mLocationEditText;
    @BindView(R.id.seriesEditText) EditText mSeriesEditText;
    @BindView(R.id.descriptionEditText) EditText mDescriptionEditText;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param conventionYear ConventionYear to add the photoshoot to
     * @return A new instance of fragment ModifyPhotoshootFragment.
     */
    public static ModifyPhotoshootFragment newInstance(ConventionYear conventionYear) {
        ModifyPhotoshootFragment fragment = new ModifyPhotoshootFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, conventionYear);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param photoshoot Photoshoot to edit
     * @return A new instance of fragment ModifyPhotoshootFragment.
     */
    public static ModifyPhotoshootFragment newInstance(Photoshoot photoshoot) {
        ModifyPhotoshootFragment fragment = new ModifyPhotoshootFragment();
        Bundle args = new Bundle();
        args.putParcelable("photoshoot", photoshoot);
        fragment.setArguments(args);
        return fragment;
    }

    public ModifyPhotoshootFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Photoshoot photoshoot = null;
        ConventionYear conventionYear = null;
        if (getArguments() != null) {
            conventionYear = getArguments().getParcelable(ARG_PARAM1);
            photoshoot = getArguments().getParcelable("photoshoot");
        }

        if (mPresenter == null)
            mPresenter = new ModifyPhotoshootPresenterImpl(this, conventionYear, photoshoot);
        else {
            mPresenter.setView(this);
            mPresenter.setConventionYear(conventionYear);
            mPresenter.setPhotoshoot(photoshoot);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_photoshoot, container, false);
        setHasOptionsMenu(true);
        ButterKnife.bind(this, view);
        mPresenter.requestInitialData();
        mStartDateButton.setOnClickListener(mDateOnClickListener);
        mStartTimeButton.setOnClickListener(mTimeOnClickListener);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnFragmentInteractionListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
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

    private View.OnClickListener mDateOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DatePickerDialogFragment fragment = new DatePickerDialogFragment();
            fragment.setListener(mDateListener);
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            fragment.show(transaction, "Start Date");
        }
    };

    private View.OnClickListener mTimeOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TimePickerDialogFragment fragment = new TimePickerDialogFragment();
            fragment.setListener(mTimeListener);
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            fragment.show(transaction, "Start Time");
        }
    };

    private TimePickerDialog.OnTimeSetListener mTimeListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            mPresenter.timeChanged(hourOfDay, minute);
        }
    };

    private DatePickerDialog.OnDateSetListener mDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mPresenter.dateChanged(year, monthOfYear, dayOfMonth);
        }
    };

    public interface OnFragmentInteractionListener {
        void onModifyFragmentInteraction();
    }

    // ModifyPhotoshootView methods

    @Override
    public void displayWarning(String warning) {
        Toast.makeText(getContext(), warning, Toast.LENGTH_LONG).show();
    }

    @Override
    public void updateDate(String date) {
        mStartDateButton.setText(date);
    }

    @Override
    public void updateTime(String time) {
        mStartTimeButton.setText(time);
    }

    @Override
    public void done() {
        mListener.onModifyFragmentInteraction();
    }

    @Override
    public String getLocation() {
        return mLocationEditText.getText().toString();
    }

    @Override
    public String getSeries() {
        return mSeriesEditText.getText().toString();
    }

    @Override
    public String getDescription() {
        return mDescriptionEditText.getText().toString();
    }

    @Override
    public void displayLocation(String location) {
        mLocationEditText.setText(location);
    }

    @Override
    public void displaySeries(String series) {
        mSeriesEditText.setText(series);
    }

    @Override
    public void displayDescription(String description) {
        mDescriptionEditText.setText(description);
    }


}
