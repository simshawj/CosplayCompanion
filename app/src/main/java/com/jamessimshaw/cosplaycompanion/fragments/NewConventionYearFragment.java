package com.jamessimshaw.cosplaycompanion.fragments;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.jamessimshaw.cosplaycompanion.R;
import com.jamessimshaw.cosplaycompanion.datasources.SQLiteDataSource;
import com.jamessimshaw.cosplaycompanion.models.Convention;
import com.jamessimshaw.cosplaycompanion.models.ConventionYear;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by james on 10/11/15.
 */
public class NewConventionYearFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";

    private OnFragmentInteractionListener mListener;
    private Convention mConvention;
    private DatePicker mDayDatePicker;
    private ImageButton mUpButton;
    private ImageButton mDownButton;
    private TextView mDaysTextView;
    private EditText mLocationEditText;
    private int mDays;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_convention_year, container, false);

        setHasOptionsMenu(true);

        mDaysTextView = (TextView) view.findViewById(R.id.days);
        mUpButton = (ImageButton) view.findViewById(R.id.addDay);
        mDownButton = (ImageButton) view.findViewById(R.id.minusDay);
        mDayDatePicker = (DatePicker) view.findViewById(R.id.date);
        mLocationEditText = (EditText) view.findViewById(R.id.conventionLocation);
        mDays = 2;
        mDaysTextView.setText(Integer.toString(mDays));
        mUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDays++;
                mDaysTextView.setText(Integer.toString(mDays));
            }
        });
        mDownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDays > 1) {
                    mDays--;
                } else {
                    mDays = 1;
                }
                mDaysTextView.setText(Integer.toString(mDays));
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
            int month = mDayDatePicker.getMonth();
            int day = mDayDatePicker.getDayOfMonth();
            int year = mDayDatePicker.getYear();
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day);
            Date date = calendar.getTime();

            ConventionYear conventionYear = new ConventionYear(date, mDays, mConvention.getId(),
                    mLocationEditText.getText().toString());
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

    public interface OnFragmentInteractionListener {
        public void onNewConventionYearFragmentInteraction();
    }
}