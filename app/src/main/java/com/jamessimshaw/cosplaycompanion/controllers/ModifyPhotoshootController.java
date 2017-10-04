package com.jamessimshaw.cosplaycompanion.controllers;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jamessimshaw.cosplaycompanion.CosplayCompanionApplication;
import com.jamessimshaw.cosplaycompanion.R;
import com.jamessimshaw.cosplaycompanion.helpers.KeyboardHelper;
import com.jamessimshaw.cosplaycompanion.models.ConventionYear;
import com.jamessimshaw.cosplaycompanion.models.Photoshoot;
import com.jamessimshaw.cosplaycompanion.presenters.ModifyPhotoshootPresenter;
import com.jamessimshaw.cosplaycompanion.views.ModifyPhotoshootView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ModifyPhotoshootController.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ModifyPhotoshootController#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ModifyPhotoshootController extends BaseInnerController implements ModifyPhotoshootView {
    @Inject ModifyPhotoshootPresenter mPresenter;

    @BindView(R.id.dateButton) Button mStartDateButton;
    @BindView(R.id.timeButton) Button mStartTimeButton;
    @BindView(R.id.locationEditText) EditText mLocationEditText;
    @BindView(R.id.seriesEditText) EditText mSeriesEditText;
    @BindView(R.id.descriptionEditText) EditText mDescriptionEditText;

    protected ModifyPhotoshootController(@Nullable Bundle args) {
        super(args);
    }

    //    private OnFragmentInteractionListener mListener;
//
    public static ModifyPhotoshootController newInstance(String reference, boolean edit) {
        ModifyPhotoshootController fragment = new ModifyPhotoshootController();
        Bundle args = new Bundle();
        if (edit) {
            args.putString("photoshoot", reference);
        } else {
            args.putString("event", reference);
        }
        return new ModifyPhotoshootController(args);
    }


    public ModifyPhotoshootController() {
        // Required empty public constructor
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        DatabaseReference conventionYearRef = null;
//        DatabaseReference photoshootRef = null;
//        super.onCreate(savedInstanceState);
//        Photoshoot photoshoot = null;
//        ConventionYear conventionYear = null;
//        if (getArguments() != null) {
//            String conventionYearString = getArguments().getString("event");
//            String photoshootString = getArguments().getString("photoshoot");
//
//            if (conventionYearString != null) {
//                conventionYearRef = FirebaseDatabase.getInstance().getReferenceFromUrl(conventionYearString);
//            }
//            if (photoshootString != null) {
//                photoshootRef = FirebaseDatabase.getInstance().getReferenceFromUrl(photoshootString);
//            }
//        }
//
//        ((CosplayCompanionApplication)getActivity().getApplication()).getPhotoshootsComponent()
//                .inject(this);
//
//        mPresenter.setView(this);
//        mPresenter.setConventionYear(conventionYearRef);
//        mPresenter.setPhotoshoot(photoshootRef);
//    }

    @NonNull
    @Override
    public View inflateView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.controller_base, container, false);

        ViewStub stub = view.findViewById(R.id.contentHolder);
        stub.setLayoutResource(R.layout.fragment_new_photoshoot);
        stub.inflate();

        view.findViewById(R.id.fab).setVisibility(View.GONE);

        setHasOptionsMenu(true);

        ((CosplayCompanionApplication)getActivity().getApplication()).getPhotoshootsComponent()
                .inject(this);
        ButterKnife.bind(this, view);

        DatabaseReference conventionYearRef = null;
        DatabaseReference photoshootRef = null;
        Photoshoot photoshoot = null;
        ConventionYear conventionYear = null;
        String conventionYearString = getArgs().getString("event");
        String photoshootString = getArgs().getString("photoshoot");

        if (conventionYearString != null) {
            conventionYearRef = FirebaseDatabase.getInstance().getReferenceFromUrl(conventionYearString);
        }
        if (photoshootString != null) {
            photoshootRef = FirebaseDatabase.getInstance().getReferenceFromUrl(photoshootString);
        }

        ((CosplayCompanionApplication)getActivity().getApplication()).getPhotoshootsComponent()
                .inject(this);

        mPresenter.setView(this);
        mPresenter.setConventionYear(conventionYearRef);
        mPresenter.setPhotoshoot(photoshootRef);
        mPresenter.requestInitialData();
        mStartDateButton.setOnClickListener(mDateOnClickListener);
        mStartTimeButton.setOnClickListener(mTimeOnClickListener);

        return view;
    }

    @Override
    protected void onAttach(@NonNull View view) {
        super.onAttach(view);

        mPresenter.setView(this);
        if (mPresenter.isEditMode()) {
            setTitle("Edit Photoshoot");
        } else {
            setTitle("New Photoshoot");
        }
    }

    @Override
    protected void onDetach(@NonNull View view) {
        super.onDetach(view);
        mPresenter.detachView();
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
//            DatePickerDialogFragment fragment = new DatePickerDialogFragment();
//            fragment.setListener(mDateListener);
//            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
//            fragment.show(transaction, "Start Date");
        }
    };

    private View.OnClickListener mTimeOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            TimePickerDialogFragment fragment = new TimePickerDialogFragment();
//            fragment.setListener(mTimeListener);
//            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
//            fragment.show(transaction, "Start Time");
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
//        mListener.onModifyFragmentInteraction();
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
