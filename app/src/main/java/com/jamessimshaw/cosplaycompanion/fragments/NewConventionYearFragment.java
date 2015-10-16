package com.jamessimshaw.cosplaycompanion.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jamessimshaw.cosplaycompanion.R;
import com.jamessimshaw.cosplaycompanion.datasources.SQLiteDataSource;
import com.jamessimshaw.cosplaycompanion.models.Convention;
import com.jamessimshaw.cosplaycompanion.models.ConventionYear;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by james on 10/11/15.
 */
public class NewConventionYearFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final int START_DATE = 0;
    private static final int END_DATE = 1;

    private OnFragmentInteractionListener mListener;
    private Convention mConvention;
    private EditText mLocationEditText;
    private Button mStartButton;
    private Button mEndButton;
    private Date mStartDate;
    private Date mEndDate;
    private SimpleDateFormat mDateFormat;

    public static NewConventionYearFragment newInstance(Convention convention) {
        NewConventionYearFragment fragment = new NewConventionYearFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, convention);
        fragment.setArguments(args);
        return fragment;
    }

    public NewConventionYearFragment() {
        // Required Default constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mConvention = getArguments().getParcelable(ARG_PARAM1);
        }
        Calendar calendar = Calendar.getInstance();
        mStartDate = calendar.getTime();
        mEndDate = calendar.getTime();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_convention_year, container, false);
        mDateFormat = new SimpleDateFormat("cccc MMMM dd yyyy", Locale.getDefault());

        setHasOptionsMenu(true);

        mStartButton = (Button) view.findViewById(R.id.startDateButton);
        mEndButton = (Button) view.findViewById(R.id.endDateButton);
        mLocationEditText = (EditText) view.findViewById(R.id.conventionLocation);

        mStartButton.setText(mDateFormat.format(mStartDate));
        mEndButton.setText(mDateFormat.format(mEndDate));
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
            if (mStartDate.after(mEndDate)) {
                Toast.makeText(getContext(), "End date must be after the start date",
                        Toast.LENGTH_LONG).show();
                return true;
            }
            ConventionYear conventionYear = new ConventionYear(mStartDate, mEndDate,
                    mConvention.getId(), mLocationEditText.getText().toString());
            SQLiteDataSource sqLiteDataSource = new SQLiteDataSource(getContext());
            sqLiteDataSource.create(conventionYear);
            mListener.onNewConventionYearFragmentInteraction();
            return true;
        }
        return super.onOptionsItemSelected(item);
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
        public void onNewConventionYearFragmentInteraction();
    }
}