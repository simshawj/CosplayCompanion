package com.jamessimshaw.cosplaycompanion.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.net.Uri;
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

import com.jamessimshaw.cosplaycompanion.R;
import com.jamessimshaw.cosplaycompanion.datasources.SQLiteDataSource;
import com.jamessimshaw.cosplaycompanion.models.ConventionYear;
import com.jamessimshaw.cosplaycompanion.models.Photoshoot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
    private Calendar mStart;
    private Button mStartDateButton;
    private Button mStartTimeButton;
    private EditText mLocationEditText;
    private EditText mSeriesEditText;
    private EditText mDescriptionEditText;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param conventionYear ConventionYear to add the photoshoot to
     * @return A new instance of fragment NewPhotoshootFragment.
     */
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
        mStart = Calendar.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_photoshoot, container, false);

        setHasOptionsMenu(true);

        mStartDateButton = (Button) view.findViewById(R.id.dateButton);
        mStartTimeButton = (Button) view.findViewById(R.id.timeButton);
        mLocationEditText = (EditText) view.findViewById(R.id.locationEditText);
        mSeriesEditText = (EditText) view.findViewById(R.id.seriesEditText);
        mDescriptionEditText = (EditText) view.findViewById(R.id.descriptionEditText);

        updateDateAndTimeButtons();

        mStartDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialogFragment fragment = new DatePickerDialogFragment();
                fragment.setListener(mDateListener);
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                fragment.show(transaction, "Start Date");
            }
        });

        mStartTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialogFragment fragment = new TimePickerDialogFragment();
                fragment.setListener(mTimeListener);
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                fragment.show(transaction, "Start Time");
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
            throw new ClassCastException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
            Photoshoot photoshoot = new Photoshoot(mSeriesEditText.getText().toString(),
                    mStart.getTime(), mLocationEditText.getText().toString(),
                    mDescriptionEditText.getText().toString(), mConventionYear.getId());
            SQLiteDataSource sqLiteDataSource = new SQLiteDataSource(getContext());
            sqLiteDataSource.create(photoshoot);
            mListener.onNewPhotoshootFragmentInteraction();
        }
        return super.onOptionsItemSelected(item);
    }

    private TimePickerDialog.OnTimeSetListener mTimeListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            mStart.set(Calendar.HOUR_OF_DAY, hourOfDay);
            mStart.set(Calendar.MINUTE, minute);

            updateDateAndTimeButtons();
        }
    };

    private DatePickerDialog.OnDateSetListener mDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mStart.set(year, monthOfYear, dayOfMonth);

            updateDateAndTimeButtons();
        }
    };

    private void updateDateAndTimeButtons() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("cccc MMMM dd", Locale.getDefault());
        mStartDateButton.setText(dateFormat.format(mStart.getTime()));

        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa", Locale.getDefault());
        mStartTimeButton.setText(timeFormat.format(mStart.getTime()));
    }

    public interface OnFragmentInteractionListener {
        public void onNewPhotoshootFragmentInteraction();
    }

}
