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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jamessimshaw.cosplaycompanion.R;
import com.jamessimshaw.cosplaycompanion.datasources.InternalAPI;
import com.jamessimshaw.cosplaycompanion.datasources.SQLiteDataSource;
import com.jamessimshaw.cosplaycompanion.models.ConventionYear;
import com.jamessimshaw.cosplaycompanion.models.Photoshoot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ModifyPhotoshootFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ModifyPhotoshootFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ModifyPhotoshootFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";

    private ConventionYear mConventionYear;
    private Photoshoot mPhotoshoot;
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
        mPhotoshoot = null;
        if (getArguments() != null) {
            mConventionYear = getArguments().getParcelable(ARG_PARAM1);
            mPhotoshoot = getArguments().getParcelable("photoshoot");
        }
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

        populateView();

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

    private void populateView() {
        mStart = Calendar.getInstance();
        if(mPhotoshoot != null) {
            mStart.setTime(mPhotoshoot.getStart());
            mLocationEditText.setText(mPhotoshoot.getLocation());
            mSeriesEditText.setText(mPhotoshoot.getSeries());
            mDescriptionEditText.setText(mPhotoshoot.getDescription());
        }

        updateDateAndTimeButtons();
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
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(getString(R.string.internalAPIBase))
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            InternalAPI internalAPI = retrofit.create(InternalAPI.class);
            //SQLiteDataSource sqLiteDataSource = new SQLiteDataSource(getContext());
            if (mPhotoshoot == null) {
                Photoshoot photoshoot = new Photoshoot(mSeriesEditText.getText().toString(),
                        mStart.getTime(), mLocationEditText.getText().toString(),
                        mDescriptionEditText.getText().toString(), mConventionYear.getId());
                internalAPI.createPhotoShoot(photoshoot).enqueue(new Callback<Photoshoot>() {
                    @Override
                    public void onResponse(Response<Photoshoot> response, Retrofit retrofit) {
                        if (response.code() == 201)
                            mListener.onModifyFragmentInteraction();
                        else {
                            Toast.makeText(getContext(), "Failed to create photo shoot.",
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(getContext(), "Failed to create photo shoot, please check your connection and try again.",
                                Toast.LENGTH_LONG).show();
                    }
                });
                //sqLiteDataSource.create(photoshoot);
            } else {
                mPhotoshoot.setDescription(mDescriptionEditText.getText().toString());
                mPhotoshoot.setLocation(mLocationEditText.getText().toString());
                mPhotoshoot.setSeries(mSeriesEditText.getText().toString());
                mPhotoshoot.setStart(mStart.getTime());
                //sqLiteDataSource.update(mPhotoshoot);
                internalAPI.updatePhotoShoot(mPhotoshoot.getId(), mPhotoshoot).enqueue(new Callback<Photoshoot>() {
                    @Override
                    public void onResponse(Response<Photoshoot> response, Retrofit retrofit) {
                        if (response.code() == 200)
                            mListener.onModifyFragmentInteraction();
                        else {
                            Toast.makeText(getContext(), "Failed to update photo shoot.",
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(getContext(), "Failed to update photo shoot, please check your connection and try again.",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE MMMM dd", Locale.getDefault());
        mStartDateButton.setText(dateFormat.format(mStart.getTime()));

        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa", Locale.getDefault());
        mStartTimeButton.setText(timeFormat.format(mStart.getTime()));
    }

    public interface OnFragmentInteractionListener {
        public void onModifyFragmentInteraction();
    }

}
