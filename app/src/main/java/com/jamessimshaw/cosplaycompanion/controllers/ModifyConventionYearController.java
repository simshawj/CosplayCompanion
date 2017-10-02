package com.jamessimshaw.cosplaycompanion.controllers;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import com.bluelinelabs.conductor.Controller;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jamessimshaw.cosplaycompanion.CosplayCompanionApplication;
import com.jamessimshaw.cosplaycompanion.R;
import com.jamessimshaw.cosplaycompanion.helpers.KeyboardHelper;
import com.jamessimshaw.cosplaycompanion.presenters.ModifyConventionYearPresenter;
import com.jamessimshaw.cosplaycompanion.views.ModifyConventionYearView;

import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by james on 10/11/15.
 */
public class ModifyConventionYearController extends Controller implements ModifyConventionYearView {
//    private OnFragmentInteractionListener mListener;
    @Inject ModifyConventionYearPresenter mPresenter;

    @BindView(R.id.conventionLocation) EditText mLocationEditText;
    @BindView(R.id.startDateButton) Button mStartButton;
    @BindView(R.id.endDateButton) Button mEndButton;
    @BindView(R.id.displayNameEditText) EditText mDisplayNameEditText;

    protected ModifyConventionYearController(@Nullable Bundle args) {
        super(args);
    }

    public static ModifyConventionYearController newInstance(String reference, boolean edit) {
        ModifyConventionYearController fragment = new ModifyConventionYearController();
        Bundle args = new Bundle();
        if (edit) {
            args.putString("conventionYear", reference);
        } else {
            args.putString("convention", reference);
        }
//        fragment.setArguments(args);
        return new ModifyConventionYearController(args);
    }

    public ModifyConventionYearController() {
        // Required Default constructor
    }


    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_new_convention_year, container, false);
        setHasOptionsMenu(true);
        ButterKnife.bind(this, view);

        DatabaseReference convention = null;
        DatabaseReference conventionYear = null;
        String conventionRefString = getArgs().getString("convention");
        String conventionYearRefString = getArgs().getString("conventionYear");
        if (conventionRefString == null) {
            convention = null;
        } else {
            convention = FirebaseDatabase.getInstance().getReferenceFromUrl(conventionRefString);
        }
        if (conventionYearRefString == null) {
            conventionYear = null;
        } else {
            conventionYear = FirebaseDatabase.getInstance().getReferenceFromUrl(conventionYearRefString);
        }


        ((CosplayCompanionApplication)getActivity().getApplication()).getConventionYearsComponent()
                .inject(this);

        mPresenter.setView(this);
        mPresenter.setConvention(convention);
        mPresenter.setConventionYear(conventionYear);

        mStartButton.setOnClickListener(mStartButtonListener);
        mEndButton.setOnClickListener(mEndButtonListener);
        mPresenter.requestInitialData();

        return view;
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
    protected void onAttach(@NonNull View view) {
        super.onAttach(view);
        mPresenter.setView(this);
    }

    @Override
    protected void onDetach(@NonNull View view) {
        super.onDetach(view);
        mPresenter.detachView();
    }

    // Listeners

    private View.OnClickListener mStartButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            DatePickerDialogFragment datePickerDialogFragment = new DatePickerDialogFragment();
//            datePickerDialogFragment.setListener(mStartDateListener);
//            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
//            datePickerDialogFragment.show(transaction, "Start Date");
        }
    };

    private View.OnClickListener mEndButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            DatePickerDialogFragment datePickerDialogFragment = new DatePickerDialogFragment();
//            datePickerDialogFragment.setListener(mEndDateListener);
//            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
//            datePickerDialogFragment.show(transaction, "End Date");
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


//    public interface OnFragmentInteractionListener {
//        void onModifyFragmentInteraction();
//    }

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
    public void displayDisplayName(String displayName) {
        mDisplayNameEditText.setText(displayName);
    }

    @Override
    public String getDisplayName() {
        return mDisplayNameEditText.getText().toString();
    }

    @Override
    public void displayMessage(String warning) {
        Toast.makeText(getActivity(), warning, Toast.LENGTH_LONG).show();
    }

    @Override
    public void done() {
        KeyboardHelper.hideKeyboard(getActivity());
//        mListener.onModifyFragmentInteraction();
    }

}