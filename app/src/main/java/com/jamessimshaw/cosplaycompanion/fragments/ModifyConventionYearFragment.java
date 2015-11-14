package com.jamessimshaw.cosplaycompanion.fragments;

import android.app.DatePickerDialog;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.jamessimshaw.cosplaycompanion.datasources.SQLiteDataSource;
import com.jamessimshaw.cosplaycompanion.models.Convention;
import com.jamessimshaw.cosplaycompanion.models.ConventionYear;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by james on 10/11/15.
 */
public class ModifyConventionYearFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private Convention mConvention;
    private EditText mLocationEditText;
    private Button mStartButton;
    private Button mEndButton;
    private Date mStartDate;
    private Date mEndDate;
    private SimpleDateFormat mDateFormat;
    private ConventionYear mConventionYear;

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
        mConventionYear = null;
        if (getArguments() != null) {
            mConvention = getArguments().getParcelable("convention");
            mConventionYear = getArguments().getParcelable("conventionYear");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_convention_year, container, false);

        setHasOptionsMenu(true);

        mStartButton = (Button) view.findViewById(R.id.startDateButton);
        mEndButton = (Button) view.findViewById(R.id.endDateButton);
        mLocationEditText = (EditText) view.findViewById(R.id.conventionLocation);

        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialogFragment datePickerDialogFragment = new DatePickerDialogFragment();
                datePickerDialogFragment.setListener(mStartDateListener);
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                datePickerDialogFragment.show(transaction, "Start Date");
            }
        });
        mEndButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialogFragment datePickerDialogFragment = new DatePickerDialogFragment();
                datePickerDialogFragment.setListener(mEndDateListener);
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                datePickerDialogFragment.show(transaction, "End Date");
            }
        });

        populateView();

        return view;
    }

    private void populateView() {
        mDateFormat = new SimpleDateFormat("EEEE MMMM dd yyyy", Locale.getDefault());
        if (mConventionYear == null) {
            Calendar calendar = Calendar.getInstance();
            mStartDate = calendar.getTime();
            mEndDate = calendar.getTime();
        } else {
            mStartDate = mConventionYear.getStartDate();
            mEndDate = mConventionYear.getEndDate();
            mLocationEditText.setText(mConventionYear.getLocation());
        }

        mStartButton.setText(mDateFormat.format(mStartDate));
        mEndButton.setText(mDateFormat.format(mEndDate));
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
            if (mStartDate.after(mEndDate)) {
                Toast.makeText(getContext(), "End date must be after the start date",
                        Toast.LENGTH_LONG).show();
                return true;
            }
            SQLiteDataSource sqLiteDataSource = new SQLiteDataSource(getContext());
            String displayName = mConvention.getName() + " " + getYearFromDate(mStartDate);
            if (mConventionYear == null) {
                ConventionYear conventionYear = new ConventionYear(mStartDate, mEndDate,
                        mConvention.getId(), mLocationEditText.getText().toString(),
                        displayName);
                sqLiteDataSource.create(conventionYear);
            } else {
                mConventionYear.setStart(mStartDate);
                mConventionYear.setEnd(mEndDate);
                mConventionYear.setLocation(mLocationEditText.getText().toString());
                sqLiteDataSource.update(mConventionYear);
            }
            mListener.onModifyFragmentInteraction();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private String getYearFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return Integer.toString(calendar.get(Calendar.YEAR));
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private DatePickerDialog.OnDateSetListener mStartDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(year, monthOfYear, dayOfMonth);
                    mStartDate = calendar.getTime();
                    mStartButton.setText(mDateFormat.format(mStartDate));
                }
            };

    private DatePickerDialog.OnDateSetListener mEndDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(year, monthOfYear, dayOfMonth);
                    mEndDate = calendar.getTime();
                    mEndButton.setText(mDateFormat.format(mEndDate));
                }
            };

    public interface OnFragmentInteractionListener {
        public void onModifyFragmentInteraction();
    }
}