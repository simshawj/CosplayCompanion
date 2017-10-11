package com.jamessimshaw.cosplaycompanion.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jamessimshaw.cosplaycompanion.CosplayCompanionApplication;
import com.jamessimshaw.cosplaycompanion.R;
import com.jamessimshaw.cosplaycompanion.activities.MainActivity;
import com.jamessimshaw.cosplaycompanion.helpers.KeyboardHelper;
import com.jamessimshaw.cosplaycompanion.presenters.ModifyPhotoshootPresenter;
import com.jamessimshaw.cosplaycompanion.views.ModifyPhotoshootView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ModifyPhotoshootDialogFragment extends DialogFragment implements ModifyPhotoshootView {
    @Inject ModifyPhotoshootPresenter mPresenter;

    @BindView(R.id.dateButton) Button mStartDateButton;
    @BindView(R.id.timeButton) Button mStartTimeButton;
    @BindView(R.id.locationEditText) EditText mLocationEditText;
    @BindView(R.id.seriesEditText) EditText mSeriesEditText;
    @BindView(R.id.descriptionEditText) EditText mDescriptionEditText;
    @BindView(R.id.dialogSubmitTextView) TextView mSubmitTextView;

    public static ModifyPhotoshootDialogFragment newInstance(String reference, boolean edit) {
        ModifyPhotoshootDialogFragment fragment = new ModifyPhotoshootDialogFragment();
        Bundle args = new Bundle();
        if (edit) {
            args.putString("photoshoot", reference);
        } else {
            args.putString("event", reference);
        }
        fragment.setArguments(args);
        return fragment;
    }


    public ModifyPhotoshootDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((CosplayCompanionApplication)getActivity().getApplication()).getPhotoshootsComponent().inject(this);

        DatabaseReference conventionYearRef = null;
        DatabaseReference photoshootRef = null;
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String conventionYearString = getArguments().getString("event");
            String photoshootString = getArguments().getString("photoshoot");

            if (conventionYearString != null) {
                conventionYearRef = FirebaseDatabase.getInstance().getReferenceFromUrl(conventionYearString);
            }
            if (photoshootString != null) {
                photoshootRef = FirebaseDatabase.getInstance().getReferenceFromUrl(photoshootString);
            }
        }

        ((CosplayCompanionApplication)getActivity().getApplication()).getPhotoshootsComponent()
                .inject(this);

        mPresenter.setView(this);
        mPresenter.setConventionYear(conventionYearRef);
        mPresenter.setPhotoshoot(photoshootRef);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_photoshoot, container, false);

        ButterKnife.bind(this, view);

        mStartDateButton.setOnClickListener(mDateOnClickListener);
        mStartTimeButton.setOnClickListener(mTimeOnClickListener);
        mSubmitTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.submit();
            }
        });

        mPresenter.requestInitialData();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.setView(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.detachView();
    }

    private View.OnClickListener mDateOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DatePickerDialogFragment fragment = new DatePickerDialogFragment();
            fragment.setListener(mDateListener);
            FragmentTransaction transaction = ((MainActivity)getActivity()).getSupportFragmentManager().beginTransaction();
            fragment.show(transaction, "Start Date");
        }
    };

    private View.OnClickListener mTimeOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TimePickerDialogFragment fragment = new TimePickerDialogFragment();
            fragment.setListener(mTimeListener);
            FragmentTransaction transaction = ((MainActivity)getActivity()).getSupportFragmentManager().beginTransaction();
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


    // ModifyPhotoshootView methods

    @Override
    public void displayMessage(String warning) {
        Toast.makeText(getActivity(), warning, Toast.LENGTH_LONG).show();
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
        KeyboardHelper.hideKeyboard(getActivity());
        dismiss();
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
